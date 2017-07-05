package com.changyi.fi.component.merchant.request;

import com.changyi.fi.vo.Position;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;


public class MerchantValidateRequest {

    @NotNull
    @NotEmpty
    private String id;

    @NotNull
    private Position position;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
