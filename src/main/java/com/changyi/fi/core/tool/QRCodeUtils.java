package com.changyi.fi.core.tool;

import com.changyi.fi.util.FIConstants;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import java.io.File;
import java.io.FileOutputStream;

public class QRCodeUtils {

    private static final String PROP_QRCODE_CHARSET = "qrcode.invoice.charset";
    private static final String PROP_QRCODE_PATH = "qrcode.invoice.path";
    private static final String PROP_QRCODE_FILETYPE = "qrcode.invoice.filetype";
    private static final String PROP_QRCODE_USER_GROUP = "qrcode.invoice.user.group";

    private static String charset;

    private static String path;

    private static ImageType type;

    private static String userGroup;

    static {
        charset = Properties.get(PROP_QRCODE_CHARSET);
        path = Properties.get(PROP_QRCODE_PATH);
        type = getType(Properties.get(PROP_QRCODE_FILETYPE));
        userGroup = Properties.get(PROP_QRCODE_USER_GROUP);
    }

    private static ImageType getType(String type) {
        if (FIConstants.IMAGE_TYPE_PNG.equalsIgnoreCase(type)) {
            return ImageType.PNG;
        } else if(FIConstants.IMAGE_TYPE_JPG.equalsIgnoreCase(type)) {
            return ImageType.JPG;
        } else if (FIConstants.IMAGE_TYPE_GIF.equalsIgnoreCase(type)) {
            return ImageType.GIF;
        } else {
            //默认
            return ImageType.PNG;
        }
    }

    public static File createQRCode(String content, String fileName) throws Exception {
        File file = new File(path + "/" + fileName + "." + type.toString().toLowerCase());
        QRCode.from(content).withCharset(charset).to(type).writeTo(new FileOutputStream(file));
        return file;
    }

    public static File getQRCode(String fileName) throws Exception {
        return new File(path + "/" + fileName + "." + type.toString().toLowerCase());
    }

    public static String getUserGroup() { return userGroup; }

}
