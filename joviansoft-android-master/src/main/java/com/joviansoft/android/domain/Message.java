package com.joviansoft.android.domain;

import com.joviansoft.android.core.JovianResponse;

/**
 * Created by bigbao on 14-2-21.
 */
public class Message extends JovianResponse{

    private int id=0;
    private String message;

    public Message(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message() {
    }
}
