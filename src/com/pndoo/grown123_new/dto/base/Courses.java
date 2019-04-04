package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;

/**
 * Created by ping on 2015-10-20.
 */
public class Courses implements Serializable {


    /**
     * id : 1
     * groupName : fsdjdff
     * groupCode : 1445508965796
     */

    private String id;
    private String groupName;
    private String groupCode;

    public void setId(String id) {
        this.id = id;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "id='" + id + '\'' +
                ", groupName='" + groupName + '\'' +
                ", groupCode='" + groupCode + '\'' +
                '}';
    }
}

