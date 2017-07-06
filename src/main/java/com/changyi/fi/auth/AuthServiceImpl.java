package com.changyi.fi.auth;

import com.changyi.fi.core.LogUtil;
import com.changyi.fi.core.token.Token;
import com.changyi.fi.external.weixin.WeixinAPIService;
import com.changyi.fi.vo.Session;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by finley on 1/25/17.
 */
@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Resource
    private WeixinAPIService weixinAPIService;

    public AuthResponse authenticate(String code) throws Exception {
        LogUtil.info(this.getClass(), "Execute authenticate service for code: " + code);
        //调用微信服务获取当前登录用户信息
        Session session = weixinAPIService.login(code);
        //根据openid生成session并保存在token中
        return new AuthResponse(new Token(session).getKey());
    }

}
