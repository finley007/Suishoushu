package com.changyi.fi.auth;

import com.changyi.fi.core.annotation.Validate;
import com.changyi.fi.core.Token;
import com.changyi.fi.core.LogUtil;
import com.changyi.fi.exception.AuthenticationErrorException;
import com.changyi.fi.exception.AuthenticationFailedException;
import com.changyi.fi.exception.InvalidAccountException;
import com.changyi.fi.exception.NoAccountException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

/**
 * Created by finley on 1/25/17.
 */
@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Validate
    public AuthResponse authenticate(AuthRequest request) throws Exception {
        LogUtil.info(this.getClass(), "Do authentication");
        UsernamePasswordToken token = new UsernamePasswordToken(request.getUsername(), request.getPassword());
       // token.setRememberMe(true);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
        } catch (UnknownAccountException e) {
            LogUtil.info(this.getClass(), "Unknown account: " + request.getUsername());
            throw new NoAccountException("Unknown account: " + request.getUsername());
        } catch (IncorrectCredentialsException e) {
            LogUtil.info(this.getClass(), "Authentication failed for account: " + request.getUsername());
            throw new AuthenticationFailedException("Authentication failed for account: " + request.getUsername());
        } catch (LockedAccountException e) {
            LogUtil.info(this.getClass(), "Account status invalid");
            throw new InvalidAccountException("Account status invalid");
        } catch (ExcessiveAttemptsException e) {
            LogUtil.info(this.getClass(), "Over login attempt limit");
            throw new InvalidAccountException("Over login attempt limit");
        } catch (Throwable t) {
            throw new AuthenticationErrorException(t, "Authentication error: " + t.getMessage());
        }
        return new AuthResponse(new Token(request.getUsername(), currentUser).getKey());
    }

}
