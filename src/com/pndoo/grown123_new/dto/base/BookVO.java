package com.pndoo.grown123_new.dto.base;

import org.xutils.db.annotation.Column;

import java.io.Serializable;


public class BookVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "book_id",isId = true,autoGen = false)
	private String bookId;// id
	@Column(name = "bookName")
	private String bookName;// 书名
	private String bookAuthor;// 作者
	private String bookType;// 书籍类型
	private String bookIntro;// 书的简介
	private String bookImg;// 图片名称
	private String bookImgPath;// 图片路径
	private String bookPrice;// 价格
	private String bookDiscount;// 折扣
	private String bookSize;// 文件大小
	private String bookCatalog;// 目录
	private String bookStartPage;// 免费试读开始
	private String bookEndPage;// 免费试读结束
	@Column(name = "boookSaveName")
	private String bookSaveName;// 图书存储名称
	@Column(name = "boookSavePath")
	private String bookSavePath;// 图书储存路径
	private String bookPublish;// 出版社
	private String bookAttachNum;// 附件个数
	private String encrypt;// 是否加密
	private String isPaided;//0为没支付，1为支付

	@Override
	public String toString() {
		return "BookVO [bookId=" + bookId + ", bookName=" + bookName
				+ ", bookAuthor=" + bookAuthor + ", bookType=" + bookType
				+ ", bookIntro=" + bookIntro + ", bookImg=" + bookImg
				+ ", bookImgPath=" + bookImgPath + ", bookPrice=" + bookPrice
				+ ", AttachOnes=" + ", bookDiscount=" + bookDiscount
				+ ", bookCatalog=" + bookCatalog + ", bookStartPage="
				+ bookStartPage + ", bookEndPage=" + bookEndPage
				+ ", bookSaveName=" + bookSaveName + ", bookSavePath="
				+ bookSavePath + ", bookPublish=" + bookPublish + ", bookSize="
				+ bookSize + ", bookAttachNum=" + bookAttachNum + "]";
	}
	
	

	public String getIsPaided() {
		return isPaided;
	}

	public void setIsPaided(String isPaided) {
		this.isPaided = isPaided;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	public String getBookIntro() {
		return bookIntro;
	}

	public void setBookIntro(String bookIntro) {
		this.bookIntro = bookIntro;
	}

	public String getBookImg() {
		return bookImg;
	}

	public void setBookImg(String bookImg) {
		this.bookImg = bookImg;
	}

	public String getBookImgPath() {
		return bookImgPath;
	}

	public void setBookImgPath(String bookImgPath) {
		this.bookImgPath = bookImgPath;
	}

	public String getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(String bookPrice) {
		this.bookPrice = bookPrice;
	}

	public String getBookDiscount() {
		return bookDiscount;
	}

	public void setBookDiscount(String bookDiscount) {
		this.bookDiscount = bookDiscount;
	}

	public String getBookSize() {
		return bookSize;
	}

	public void setBookSize(String bookSize) {
		this.bookSize = bookSize;
	}

	public String getBookCatalog() {
		return bookCatalog;
	}

	public void setBookCatalog(String bookCatalog) {
		this.bookCatalog = bookCatalog;
	}

	public String getBookStartPage() {
		return bookStartPage;
	}

	public void setBookStartPage(String bookStartPage) {
		this.bookStartPage = bookStartPage;
	}

	public String getBookEndPage() {
		return bookEndPage;
	}

	public void setBookEndPage(String bookEndPage) {
		this.bookEndPage = bookEndPage;
	}

	public String getBookSaveName() {
		return bookSaveName;
	}

	public void setBookSaveName(String bookSaveName) {
		this.bookSaveName = bookSaveName;
	}

	public String getBookSavePath() {
		return bookSavePath;
	}

	public void setBookSavePath(String bookSavePath) {
		this.bookSavePath = bookSavePath;
	}

	public String getBookPublish() {
		return bookPublish;
	}

	public void setBookPublish(String bookPublish) {
		this.bookPublish = bookPublish;
	}

	public String getBookAttachNum() {
		return bookAttachNum;
	}

	public void setBookAttachNum(String bookAttachNum) {
		this.bookAttachNum = bookAttachNum;
	}

	public String toStringBook() {
		return bookId + ":" + bookName + ":" + bookImg + ":" + bookImgPath;
	}
}
// public String toStringBook() {
// return bookId + ":" + bookName + ":" + bookImg + ":" + bookImgPath;
// }

// private int ispackage;// 是否是包文件 1->是 0->不是
// private String bookUrl;// 书的下载路径
// private String userAddTime;// 用户添加时间
// private List<ReleteVO> relatedBooks;// 相关书籍
// private List<FileAttach> fileAttachs;// 附件
// private List<ImageChapter> imageChapters;// 章节
// private String remark;

// @ComplexSerializableType(clazz = FileAttach.class)
// public List<FileAttach> getFileAttachs() {
// return fileAttachs;
// }
//
// @ComplexSerializableType(clazz = FileAttach.class)
// public void setFileAttachs(List<FileAttach> fileAttachs) {
// this.fileAttachs = fileAttachs;
// }
//
// @ComplexSerializableType(clazz = ReleteVO.class)
// public List<ReleteVO> getRelatedBooks() {
// return relatedBooks;
// }
//
// @ComplexSerializableType(clazz = ReleteVO.class)
// public void setRelatedBooks(List<ReleteVO> relatedBooks) {
// this.relatedBooks = relatedBooks;
// }
//
// @ComplexSerializableType(clazz = ImageChapter.class)
// public List<ImageChapter> getImageChapters() {
// return imageChapters;
// }
//
// @ComplexSerializableType(clazz = ImageChapter.class)
// public void setImageChapters(List<ImageChapter> imageChapters) {
// this.imageChapters = imageChapters;
// }
// public int getIspackage() {
// return ispackage;
// }
//
//
//
// public void setIspackage(int ispackage) {
// this.ispackage = ispackage;
// }
//
//
//
// public String getBookUrl() {
// return bookUrl;
// }
//
//
//
// public void setBookUrl(String bookUrl) {
// this.bookUrl = bookUrl;
// }
//
//
//
// public String getBookSize() {
// return bookSize;
// }
//
//
//
// public void setBookSize(String bookSize) {
// this.bookSize = bookSize;
// }
//
//
//
// public String getUserAddTime() {
// return userAddTime;
// }
//
//
//
// public void setUserAddTime(String userAddTime) {
// this.userAddTime = userAddTime;
// }
//
//
//
// public String getBookAppendCount() {
// return bookAppendCount;
// }
//
//
//
// public void setBookAppendCount(String bookAppendCount) {
// this.bookAppendCount = bookAppendCount;
// }
//
//
//
// public String getRemark() {
// return remark;
// }
//
//
//
// public void setRemark(String remark) {
// this.remark = remark;
// }
