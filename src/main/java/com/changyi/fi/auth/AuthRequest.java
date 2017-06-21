package com.changyi.fi.auth;

import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * Created by finley on 1/29/17.
 */
public class AuthRequest {

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    @Length(max=8)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
