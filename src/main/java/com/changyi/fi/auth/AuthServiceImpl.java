package com.changyi.fi.auth;

import com.changyi.fi.core.annotation.Validate;
import com.changyi.fi.core.token.Token;
import com.changyi.fi.core.LogUtil;
import org.springframework.stereotype.Service;

/**
 * Created by finley on 1/25/17.
 */
@Service("authService")
public class AuthServiceImpl implements AuthService {

    public AuthResponse authenticate(String code) throws Exception {
        LogUtil.info(this.getClass(), "Do authentication");
        //调用微信服务获取openid
        String openId = code;
        //根据openid生成session并保存在token中
        return new AuthResponse(new Token(openId).getKey());
    }

}
