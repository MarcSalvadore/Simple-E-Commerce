// package com.apapedia.catalog.security.jwt;

// import java.io.IOException;
// import java.util.UUID;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpHeaders;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.stereotype.Component;
// import org.springframework.util.StringUtils;
// import org.springframework.web.filter.OncePerRequestFilter;
// import org.springframework.web.reactive.function.client.WebClient;

// import com.apapedia.catalog.dto.UserDTO;
// import com.apapedia.catalog.security.UserDetailsServiceImpl;

// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.http.MediaType;
// @Component
// public class JwtTokenFilter extends OncePerRequestFilter{

//     private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
	
//     @Autowired
//     private JwtUtils jwtUtils;

//     @Autowired
//     private UserDetailsServiceImpl userDetailsService;
    
    
//     private static final String JWT_HEADER = "Authorization";
//     private static final String JWT_TOKEN_PREFIX = "Bearer";
//     private UserDTO userDTO;

//     private final WebClient webClient;
//     public JwtTokenFilter(WebClient.Builder webClientBuilder){
//         this.webClient = webClientBuilder.baseUrl("http://localhost:8081")
//                     .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                     .build();
//     }
//     @Override
// 	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
// 			throws ServletException, IOException {
//                 final String authHeader = request.getHeader(JWT_HEADER);
//                 final String jwtToken;
//                 final String username;
//                 final String id;
//                 if (authHeader == null || !authHeader.startsWith(JWT_TOKEN_PREFIX)) {
//                     filterChain.doFilter(request, response);
//                     return;
//                 }

//                 jwtToken = authHeader.substring(7);
//                 username = jwtUtils.getUserNameFromJwtToken(jwtToken);
//                 id = jwtUtils.extractUserId(jwtToken);

//                 if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                     userDTO = getUserDetails(UUID.fromString(jwtToken), jwtToken);

//                     if (jwtToken.isTokenValid(jwtToken, userDetails)) {
//                         var authenticationToken = new UsernamePasswordAuthenticationToken(
//                                 userDetails,
//                                 null,
//                                 userDetails.getAuthorities()
//                         );
//                         authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                         SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                     }
//                 }
//                 filterChain.doFilter(request, response);

// 	}

//     public String parseJwt(HttpServletRequest request) {
//         String headerAuth = request.getHeader("Authorization");
//         if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
//             return headerAuth.substring(7);
//         }
//         return null;
//     }

//     public UserDTO getUserDetails(UUID id, String token) {
//         UserDTO response = this.webClient
//             .get()
//             .uri("/api/user/{id}", id)
//             .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//             .retrieve()
//             .bodyToMono(UserDTO.class)
//             .block();
//         return response;
//     }
    
// }