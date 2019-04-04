package com.pndoo.grown123_new.dto.base;

/**
 * Created by BAO on 2016-09-06.
 */
public class CountyBean {
    String code;
    String name;

    public CountyBean(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
