package com.apapedia.user.service;

import com.apapedia.user.model.Role;
import java.util.List;

public interface RoleService {
    List<Role> getAllList();
    Role getRoleByRoleName(String name);
}
