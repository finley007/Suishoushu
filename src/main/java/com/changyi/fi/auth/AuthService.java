package com.changyi.fi.auth;

import org.springframework.stereotype.Component;

/**
 * Created by finley on 1/23/17.
 */
@Component
public interface AuthService {

    public AuthResponse authenticate(AuthRequest request) throws Exception;

}
