package com.changyi.fi.component.enterprise.request;

import com.changyi.fi.core.request.Request;
import com.changyi.fi.external.enterprise.manager.EnternalEnterpriseAPIManager;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * Created by finley on 7/8/17.
 */
public class GetEnterpriseRequest implements Request {

    @NotNull(message = "source is required")
    @NotEmpty(message = "source is required")
    @MatchPattern(pattern = "^internal|qxb|tyc$", message = "invalid type value, should be true or false")
    private String source;

    @NotNull(message = "creditCode is required")
    @NotEmpty(message = "creditCode is required")
    private String creditCode;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

}
