package com.pndoo.grown123_new.soap;

import java.io.Serializable;
import java.util.List;
import java.util.Observer;

import com.google.inject.Singleton;
import com.pndoo.grown123_new.dto.base.AttachOne;
import com.pndoo.grown123_new.dto.base.AttachTwo;
import com.pndoo.grown123_new.dto.base.BookVO;
import com.pndoo.grown123_new.dto.base.ImageMatch;
import com.pndoo.grown123_new.dto.base.PublishVO;
import com.pndoo.grown123_new.dto.base.ShopListBean;
import com.pndoo.grown123_new.dto.base.Update;
import com.pndoo.grown123_new.dto.base.VipBookPage;

/**
 * 登录
 * 
 * @author
 * 
 */
@Singleton
public class BookJson extends java.util.Observable implements Serializable, Observer {

	private BookVO bookVO;
	private String code;
	private String codeInfo;

	private Update update;
	private List<BookVO> bookVOs;
	private List<AttachOne> AttachOnes;
	private List<AttachTwo> attachTwos;
	private List<PublishVO> publishVOs;
	private List<ImageMatch> imageMatchs;

	private VipBookPage vipBookPage;
	
	
	private List<ShopListBean> shoplist;

	public Update getUpdate() {
		return update;
	}

	public void setUpdate(Update update) {
		this.update = update;
	}

	private String booksData;
	private String notice;

	public String getBooksData() {
		return booksData;
	}

	public void setBooksData(String booksData) {
		this.booksData = booksData;
	}

	public List<BookVO> getBookVOs() {
		return bookVOs;
	}

	public void setBookVOs(List<BookVO> bookVOs) {
		this.bookVOs = bookVOs;
	}

	public BookVO getBookVO() {
		return bookVO;
	}

	public void setBookVO(BookVO bookVO) {
		this.bookVO = bookVO;
	}

	public String getCode() {
		return code;
	}

	public List<AttachTwo> getAttachTwos() {
		return attachTwos;
	}

	public void setAttachTwos(List<AttachTwo> attachTwos) {
		this.attachTwos = attachTwos;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeInfo() {
		return codeInfo;
	}

	public void setCodeInfo(String codeInfo) {
		this.codeInfo = codeInfo;
	}

	@Override
	public void update(java.util.Observable observable, Object o) {
		// To change body of implemented methods use File | Settings | File
		// Templates.
		setChanged();
		notifyObservers();
	}

	public List<AttachOne> getAttachOnes() {
		return AttachOnes;
	}

	public void setAttachOnes(List<AttachOne> attachOnes) {
		AttachOnes = attachOnes;
	}

	public VipBookPage getVipBookPage() {
		return vipBookPage;
	}

	public void setVipBookPage(VipBookPage vipBookPage) {
		this.vipBookPage = vipBookPage;
	}

	public List<PublishVO> getPublishVOs() {
		return publishVOs;
	}

	public void setPublishVOs(List<PublishVO> publishVOs) {
		this.publishVOs = publishVOs;
	}

	public List<ImageMatch> getImageMatchs() {
		return imageMatchs;
	}

	public void setImageMatchs(List<ImageMatch> imageMatchs) {
		this.imageMatchs = imageMatchs;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public List<ShopListBean> getShoplist() {
		return shoplist;
	}

	public void setShoplist(List<ShopListBean> shoplist) {
		this.shoplist = shoplist;
	}
	
	

}
