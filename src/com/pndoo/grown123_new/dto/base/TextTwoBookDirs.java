package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;

/**
 * Created by ping on 2015-10-20.
 */
public class TextTwoBookDirs implements Serializable {

	/**
	 * id : 1 name : 第一章
	 */

	private String id;
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "TextTwoBookDirs [id=" + id + ", name=" + name + "]";
	}

}
