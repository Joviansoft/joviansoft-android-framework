package com.joviansoft.android.domain.gps;

/**
 * Created by bigbao on 14-3-14.
 */
public class GpsBean {
    private float x;
    private float y;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public GpsBean(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public GpsBean() {
    }
}
