package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;

/**
 * Created by ping on 2015-10-20.
 */
public class TestPaper implements Serializable {


    /**
     * id : 1
     * paperCode : 123456
     * name : fdsaf
     */

    private String id;
    private String paperCode;
    private String name;

    public void setId(String id) {
        this.id = id;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getPaperCode() {
        return paperCode;
    }

    public String getName() {
        return name;
    }

	@Override
	public String toString() {
		return "TestPaper [id=" + id + ", paperCode=" + paperCode + ", name="
				+ name + "]";
	}
    
}

