package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;

/**
 * Created by ping on 2015-10-20.
 */
public class PaperInfo implements Serializable {


    /**
     * claTesPapId : 6
     * paperCode : 201510280159557613
     * name : dfk
     */

    private String claTesPapId;
    private String paperCode;
    private String name;

    public void setClaTesPapId(String claTesPapId) {
        this.claTesPapId = claTesPapId;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClaTesPapId() {
        return claTesPapId;
    }

    public String getPaperCode() {
        return paperCode;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "PaperInfo{" +
                "claTesPapId='" + claTesPapId + '\'' +
                ", paperCode='" + paperCode + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

