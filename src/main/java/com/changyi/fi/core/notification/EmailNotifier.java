package com.changyi.fi.core.notification;

import com.changyi.fi.core.tool.Properties;
import com.changyi.fi.util.FIConstants;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

public class EmailNotifier implements INotifier {

    private static final String EMAIL_SERVER = "email.server";
    private static final String EMAIL_ACCOUNT = "email.account";
    private static final String EMAIL_PASSWORD = "email.password";

    public EmailNotifier(String tgtEmail) {
        this.tgtEmail = tgtEmail;
        server = Properties.get(EMAIL_SERVER);
        systemEmail = Properties.get(EMAIL_ACCOUNT);
        password = Properties.get(EMAIL_PASSWORD);
    }

    private String server;

    private String systemEmail;

    private String password;

    private String tgtEmail;

    public String getTgtEmail() {
        return tgtEmail;
    }

    public void setTgtEmail(String tgtEmail) {
        this.tgtEmail = tgtEmail;
    }

    public void Notify(String title, String message) throws Exception {
        SimpleEmail email = new SimpleEmail();
        //email.setTLS(true); //是否TLS校验，，某些邮箱需要TLS安全校验，同理有SSL校验
        //email.setDebug(true);
        //email.setSSL(true);
        email.setHostName(this.server);
        email.setSmtpPort(25);
        email.setAuthenticator(new DefaultAuthenticator(systemEmail, password));
        email.setFrom(systemEmail); //发送方,这里可以写多个
        email.addTo(this.tgtEmail); // 接收方
        //email.addCc("402******@qq.com"); // 抄送方
        //email.addBcc("yuaio@163.com"); // 秘密抄送方
        email.setCharset(FIConstants.DEFAULT_CHARSET);
        email.setSubject(title);
        email.setMsg(message);
        email.send();
    }
}
