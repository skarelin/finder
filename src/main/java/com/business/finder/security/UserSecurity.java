package com.business.finder.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {

    // objectOwner in our application it's email. Probably, I'll change the name of argument here later.
    private boolean isOwner(String objectOwner, UserDetails user) {
        return user.getUsername().equalsIgnoreCase(objectOwner);
    }

    public boolean isOwnerOrAdmin(String objectOwner, UserDetails user) {
        return isAdmin(user) || isOwner(objectOwner, user);
    }

    private boolean isAdmin(UserDetails user) {
        return user.getAuthorities()
                   .stream()
                   .anyMatch(a -> a.getAuthority().equalsIgnoreCase("ROLE_ADMIN"));
    }
}
