package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;
import java.util.Date;

public class VipBookVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 文件id
	private String fi_fidNo;
	// 文件类型
	private String fi_type;
	// 文件大小
	private long fi_size;
	// 出版社id
	private String fi_userId;
	// 文件作者名
	private String fi_authorUserName;
	// 文件最后修改时间
	private Date fi_userLastTime;
	// 文件同步次数
	private Integer fi_ReadNum;
	// 文件是否加密
	private Boolean fi_encrypt;
	// 文件对应IP服务器
	private String fi_ip;
	// 文件存储硬盘
	private String fi_card;
	// 文件路径
	private String fi_path;
	// 文件是否编辑
	private Boolean fi_edit;
	// 文件名
	private String fi_name;
	// 文件创建时间
	private String fi_creatTime;
	// 文件记录所在的表
	private String fi_tableName;
	// 文件保存名
	private String fi_saveName;
	// 文件封面图片
	private String fi_img;
	// 文件封面图片Id
	private String fi_imgPath;
	private String price;
	private String discount;

	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getFi_img() {
		return fi_img;
	}
	public void setFi_img(String fi_img) {
		this.fi_img = fi_img;
	}
	public String getFi_tableName() {
		return fi_tableName;
	}
	public void setFi_tableName(String fi_tableName) {
		this.fi_tableName = fi_tableName;
	}
	public String getFi_fidNo() {
		return fi_fidNo;
	}
	public void setFi_fidNo(String fi_fidNo) {
		this.fi_fidNo = fi_fidNo;
	}
	public String getFi_type() {
		return fi_type;
	}
	public void setFi_type(String fi_type) {
		this.fi_type = fi_type;
	}
	public long getFi_size() {
		return fi_size;
	}
	public void setFi_size(long fi_size) {
		this.fi_size = fi_size;
	}
	public String getFi_userId() {
		return fi_userId;
	}
	public void setFi_userId(String fi_userId) {
		this.fi_userId = fi_userId;
	}
	public String getFi_authorUserName() {
		return fi_authorUserName;
	}
	public void setFi_authorUserName(String fi_authorUserName) {
		this.fi_authorUserName = fi_authorUserName;
	}
	public Date getFi_userLastTime() {
		return fi_userLastTime;
	}
	public void setFi_userLastTime(Date fi_userLastTime) {
		this.fi_userLastTime = fi_userLastTime;
	}
	public Integer getFi_ReadNum() {
		return fi_ReadNum;
	}
	public void setFi_ReadNum(Integer fi_ReadNum) {
		this.fi_ReadNum = fi_ReadNum;
	}
	public Boolean getFi_encrypt() {
		return fi_encrypt;
	}
	public void setFi_encrypt(Boolean fi_encrypt) {
		this.fi_encrypt = fi_encrypt;
	}
	public String getFi_ip() {
		return fi_ip;
	}
	public void setFi_ip(String fi_ip) {
		this.fi_ip = fi_ip;
	}
	public String getFi_card() {
		return fi_card;
	}
	public void setFi_card(String fi_card) {
		this.fi_card = fi_card;
	}
	public String getFi_path() {
		return fi_path;
	}
	public void setFi_path(String fi_path) {
		this.fi_path = fi_path;
	}
	public Boolean getFi_edit() {
		return fi_edit;
	}
	public void setFi_edit(Boolean fi_edit) {
		this.fi_edit = fi_edit;
	}
	public String getFi_name() {
		return fi_name;
	}
	public void setFi_name(String fi_name) {
		this.fi_name = fi_name;
	}
	public String getFi_creatTime() {
		return fi_creatTime;
	}
	public void setFi_creatTime(String fi_creatTime) {
		this.fi_creatTime = fi_creatTime;
	}
	public String getFi_saveName() {
		return fi_saveName;
	}
	public void setFi_saveName(String fi_saveName) {
		this.fi_saveName = fi_saveName;
	}
	public String getFi_imgPath() {
		return fi_imgPath;
	}
	public void setFi_imgPath(String fi_imgPath) {
		this.fi_imgPath = fi_imgPath;
	}

}
