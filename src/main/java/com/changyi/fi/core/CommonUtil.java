package com.changyi.fi.core;

import com.changyi.fi.vo.Position;

public class CommonUtil {

    public static Double getDistance(Position p1, Position p2){
        double x = Math.abs(p1.getLatitude() - p2.getLatitude());
        double y = Math.abs(p1.getLongitude() - p2.getLongitude());
        return Math.sqrt(x*x+y*y);
    }

}
