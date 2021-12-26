package com.business.finder.security;

import lombok.Data;

@Data
class LoginCommand {
    private String email;
    private String password;
}
