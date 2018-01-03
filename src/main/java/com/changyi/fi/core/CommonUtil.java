package com.changyi.fi.core;

import com.changyi.fi.core.tool.Distance;
import com.changyi.fi.vo.Position;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class CommonUtil {

    public static Double getDistance(Position p1, Position p2){
        Distance distance = new Distance(p1.getLatitude(), p2.getLatitude(), p1.getLongitude(), p2.getLongitude());
        return distance.caculate();
    }

    public static long toUnsigned(long signed) {
        return signed & 0x0FFFFFFFFl;
    }

    public static String urlEncode(String str, String charset) throws Exception {
        String utf8Str = new String(str.toString().getBytes(charset));
        return URLEncoder.encode(utf8Str, charset);
    }

}
