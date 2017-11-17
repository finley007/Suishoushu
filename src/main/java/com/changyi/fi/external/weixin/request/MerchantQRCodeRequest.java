package com.changyi.fi.external.weixin.request;

/**
 * Created by finley on 8/8/17.
 */
public class MerchantQRCodeRequest {

    public MerchantQRCodeRequest(String scene, String page) {
        this.scene = scene;
        this.page = page;
    }

    private String scene = "";

    private String page = "";

    private int width = 430;

//    private Boolean auto_color = false;

//    private String line_color = "{\"r\":\"0\",\"g\":\"0\",\"b\":\"0\"}";

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

//    public Boolean getAuto_color() {
//        return auto_color;
//    }
//
//    public void setAuto_color(Boolean auto_color) {
//        this.auto_color = auto_color;
//    }

//    public String getLine_color() {
//        return line_color;
//    }
//
//    public void setLine_color(String line_color) {
//        this.line_color = line_color;
//    }
}
