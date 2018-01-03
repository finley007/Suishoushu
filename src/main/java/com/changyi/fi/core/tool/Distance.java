package com.changyi.fi.core.tool;

public class Distance {

    //地球平均半径
    private static double EARTH_RADIUS = 6371.0;

    public Distance(double lat1, double lat2, double lon1, double lon2) {
        this.lat1 = degreesToRadians(lat1);
        this.lat2 = degreesToRadians(lat2);
        this.lon1 = degreesToRadians(lon1);
        this.lon2 = degreesToRadians(lon2);
    }

    //返回距离，单位m
    public double caculate() {
        //差值
        double vLon = Math.abs(lon1 - lon2);
        double vLat = Math.abs(lat1 - lat2);

        //h is the great circle distance in radians, great circle就是一个球体上的切面，它的圆心即是球心的一个周长最大的圆。
        double h = HaverSin(vLat) + Math.cos(lat1) * Math.cos(lat2) * HaverSin(vLon);

        return 2 * EARTH_RADIUS * Math.asin(Math.sqrt(h)) * 1000;
    }

    private double lat1;
    private double lat2;
    private double lon1;
    private double lon2;

    private double degreesToRadians(double degrees) {
        return degrees * Math.PI / 180;
    }

    private double HaverSin(double theta) {
        double v = Math.sin(theta / 2);
        return v * v;
    }
}
