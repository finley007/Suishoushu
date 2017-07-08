package com.changyi.fi.component.enterprise.request;

import com.changyi.fi.core.request.Request;
import net.sf.oval.constraint.MatchPattern;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

/**
 * Created by finley on 7/8/17.
 */
public class GetEnterpriseRequest implements Request {

    @NotNull(message = "isExternal is required")
    @NotEmpty(message = "isExternal is required")
    @MatchPattern(pattern = "^true|false$", message = "invalid type value, should be true or false")
    private String isExternal;

    @NotNull(message = "creditCode is required")
    @NotEmpty(message = "creditCode is required")
    private String creditCode;

    public String getIsExternal() {
        return isExternal;
    }

    public void setIsExternal(String isExternal) {
        this.isExternal = isExternal;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }
}
