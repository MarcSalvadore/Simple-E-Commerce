package com.apapedia.frontend_webapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.xml.Jaxb2XmlDecoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import com.apapedia.frontend_webapp.security.xml.Attributes;
import com.apapedia.frontend_webapp.security.xml.ServiceResponse;
import com.apapedia.frontend_webapp.service.UserService;
import com.apapedia.frontend_webapp.setting.Setting;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

    @Autowired 
    UserService userService;

    private WebClient webClient = WebClient.builder()
                    .codecs(configurer -> configurer.defaultCodecs()
                    .jaxb2Decoder(new Jaxb2XmlDecoder()))
                    .build();

    @GetMapping("/validate-ticket")
    public ModelAndView adminLoginSSO(@RequestParam(value = "ticket", required = false) String ticket, HttpServletRequest request){
        
        try {
            ServiceResponse serviceResponse = this.webClient.get().uri(
                String.format(
                    Setting.SERVER_VALIDATE_TICKET,
                    ticket,
                    Setting.CLIENT_LOGIN
                )
            ).retrieve().bodyToMono(ServiceResponse.class).block();
    
            Attributes attributes = serviceResponse.getAuthenticationSuccess().getAttributes();
            String username = serviceResponse.getAuthenticationSuccess().getUser();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, "ta_hmy_9", null);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            
            securityContext.setAuthentication(authentication);
    
            String name = attributes.getNama();
            String token = userService.getToken(username, name);
            HttpSession httpSession = request.getSession(true);

            httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
            httpSession.setAttribute("token", token);
            
            return new ModelAndView("redirect:/");
        }
            catch (Exception e) {
            return new ModelAndView("redirect:/register");
        }
    }

    @GetMapping("/login-sso")
    public ModelAndView loginSSO(){
        return new ModelAndView("redirect:" + Setting.SERVER_LOGIN + Setting.CLIENT_LOGIN);
    }

    @GetMapping("/logout-sso")
    public ModelAndView logoutSSO(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        request.getSession().invalidate();
        
        return new ModelAndView("redirect:" + Setting.SERVER_LOGOUT + Setting.CLIENT_LOGOUT);
    }
}
