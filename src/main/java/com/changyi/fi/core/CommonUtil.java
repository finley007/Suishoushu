package com.changyi.fi.core;

import com.changyi.fi.vo.Position;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CommonUtil {

    public static Double getDistance(Position p1, Position p2){
        double x = Math.abs(p1.getLatitude() - p2.getLatitude());
        double y = Math.abs(p1.getLongitude() - p2.getLongitude());
        return Math.sqrt(x*x+y*y);
    }

    public static long toUnsigned(long signed) {
        return signed & 0x0FFFFFFFFl;
    }

    public static String urlEncode(String str, String charset) throws Exception {
        String utf8Str = new String(str.toString().getBytes(charset));
        return URLEncoder.encode(utf8Str, charset);
    }

}
