package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;

/**
 * ------------------------------------------------------------------
 * 创建时间：2015-10-26 下午1:52:47 项目名称：wyst
 * 
 * @author Ping Wang
 * @version 1.0
 * @since JDK 1.6.0_21 文件名称：CourseName.java 类说明：
 *        ------------------------------------------------------------------
 */
public class BookSeletct implements Serializable {

	private String bookId;
	private String bookName;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookName() {
		return bookName;
	}

	@Override
	public String toString() {
		return "BookSeletct [bookId=" + bookId + ", bookName=" + bookName + "]";
	}

}
