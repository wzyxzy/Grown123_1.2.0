package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;

public class ShopListBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String authorInfo;
	private long bookAddTime;
	private String bookAuthor;
	private String bookCatalog;
	private int bookDiscount;
	private int bookEncrypt;
	private String bookEndPage;
	private int bookId;
	private String bookImg;
	private String bookImgPath;
	private String bookIntro;
	private String bookName;
	private int bookOnshell;
	private float bookPrice;
	private String bookRemark;
	private String bookSaveName;
	private String bookSavePath;
	private String bookSize;
	private String bookStartPage;
	private int bookType;
	private int currentPage;
	private int endRecords;
	private int isEpubXml;
	private int pageSize;
	private int publishId;
	private int startRecords;
	private int totalPages;
	private int totalRecords;
	
	
	public ShopListBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ShopListBean(String authorInfo, long bookAddTime, String bookAuthor, String bookCatalog, int bookDiscount, int bookEncrypt, String bookEndPage, int bookId, String bookImg, String bookImgPath, String bookIntro, String bookName, int bookOnshell, int bookPrice, String bookRemark, String bookSaveName, String bookSavePath, String bookSize, String bookStartPage, int bookType, int currentPage, int endRecords, int isEpubXml, int pageSize, int publishId, int startRecords, int totalPages, int totalRecords) {
		super();
		this.authorInfo = authorInfo;
		this.bookAddTime = bookAddTime;
		this.bookAuthor = bookAuthor;
		this.bookCatalog = bookCatalog;
		this.bookDiscount = bookDiscount;
		this.bookEncrypt = bookEncrypt;
		this.bookEndPage = bookEndPage;
		this.bookId = bookId;
		this.bookImg = bookImg;
		this.bookImgPath = bookImgPath;
		this.bookIntro = bookIntro;
		this.bookName = bookName;
		this.bookOnshell = bookOnshell;
		this.bookPrice = bookPrice;
		this.bookRemark = bookRemark;
		this.bookSaveName = bookSaveName;
		this.bookSavePath = bookSavePath;
		this.bookSize = bookSize;
		this.bookStartPage = bookStartPage;
		this.bookType = bookType;
		this.currentPage = currentPage;
		this.endRecords = endRecords;
		this.isEpubXml = isEpubXml;
		this.pageSize = pageSize;
		this.publishId = publishId;
		this.startRecords = startRecords;
		this.totalPages = totalPages;
		this.totalRecords = totalRecords;
	}
	public String getBookCatalog() {
		return bookCatalog;
	}
	public void setBookCatalog(String bookCatalog) {
		this.bookCatalog = bookCatalog;
	}
	public String getBookEndPage() {
		return bookEndPage;
	}
	public void setBookEndPage(String bookEndPage) {
		this.bookEndPage = bookEndPage;
	}
	public String getBookRemark() {
		return bookRemark;
	}
	public void setBookRemark(String bookRemark) {
		this.bookRemark = bookRemark;
	}
	public String getBookStartPage() {
		return bookStartPage;
	}
	public void setBookStartPage(String bookStartPage) {
		this.bookStartPage = bookStartPage;
	}
	public String getAuthorInfo() {
		return authorInfo;
	}
	public void setAuthorInfo(String authorInfo) {
		this.authorInfo = authorInfo;
	}
	public long getBookAddTime() {
		return bookAddTime;
	}
	public void setBookAddTime(long bookAddTime) {
		this.bookAddTime = bookAddTime;
	}
	public String getBookAuthor() {
		return bookAuthor;
	}
	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}
	public int getBookDiscount() {
		return bookDiscount;
	}
	public void setBookDiscount(int bookDiscount) {
		this.bookDiscount = bookDiscount;
	}
	public int getBookEncrypt() {
		return bookEncrypt;
	}
	public void setBookEncrypt(int bookEncrypt) {
		this.bookEncrypt = bookEncrypt;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
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
	public String getBookIntro() {
		return bookIntro;
	}
	public void setBookIntro(String bookIntro) {
		this.bookIntro = bookIntro;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public int getBookOnshell() {
		return bookOnshell;
	}
	public void setBookOnshell(int bookOnshell) {
		this.bookOnshell = bookOnshell;
	}
	public float getBookPrice() {
		return bookPrice;
	}
	public void setBookPrice(float bookPrice) {
		this.bookPrice = bookPrice;
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
	public String getBookSize() {
		return bookSize;
	}
	public void setBookSize(String bookSize) {
		this.bookSize = bookSize;
	}
	public int getBookType() {
		return bookType;
	}
	public void setBookType(int bookType) {
		this.bookType = bookType;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getEndRecords() {
		return endRecords;
	}
	public void setEndRecords(int endRecords) {
		this.endRecords = endRecords;
	}
	public int getIsEpubXml() {
		return isEpubXml;
	}
	public void setIsEpubXml(int isEpubXml) {
		this.isEpubXml = isEpubXml;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPublishId() {
		return publishId;
	}
	public void setPublishId(int publishId) {
		this.publishId = publishId;
	}
	public int getStartRecords() {
		return startRecords;
	}
	public void setStartRecords(int startRecords) {
		this.startRecords = startRecords;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
		
		
	
}
