package com.joviansoft.android.core;

import java.io.Serializable;

/**
 * Created by bigbao on 14-3-27.
 */
public class CodeOnlyResponse extends JovianResponse {

    private static final long serialVersionUID = 4878861828223929733L;
    private int code;
    private String message;

    public CodeOnlyResponse() {
    }

    public CodeOnlyResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
