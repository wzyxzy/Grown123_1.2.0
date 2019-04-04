package com.pndoo.grown123_new.dto.base;

import org.xutils.db.annotation.Column;

import java.io.Serializable;

public class AttachOne implements Serializable {

	// private String fid;//附件ID
	@Column(name = "bookId")
	private String bookId; // 附件包中文件的父id，如视频的bookid就是视频包的Id
	@Column(name = "attachOneId",isId = true,autoGen = false)
	private String attachOneId; // 附件ID
	@Column(name = "attachOneName")
	private String attachOneName;
	@Column(name = "attachOneIspackage")
	private int attachOneIspackage;
	@Column(name = "attachOneType")
	private String attachOneType; // 文件类型。pdf/txt之类
	@Column(name = "attachOneSaveName")
	private String attachOneSaveName; // 保存的文件名
	@Column(name = "attachOnePath")
	private String attachOnePath; // 保存路径
	@Column(name = "attach_flag")
	private String attachOneFlag;
	@Column(name = "isEncode")
	private String isEncode;
	@Column(name = "isSendAtta1")
	private String isSendAtta1;
	@Column(name = "shareUrl")
	private String shareUrl;
	@Column(name = "updateTime")
	private String updateTime;

	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getShareUrl() {
		return shareUrl;
	}
	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}
	public String getIsSendAtta1() {
		return isSendAtta1;
	}
	public void setIsSendAtta1(String isSendAtta1) {
		this.isSendAtta1 = isSendAtta1;
	}
	public String getAttachOneFlag() {
		return attachOneFlag;
	}
	public void setAttachOneFlag(String attachOneFlag) {
		this.attachOneFlag = attachOneFlag;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getAttachOneId() {
		return attachOneId;
	}
	public void setAttachOneId(String attachOneId) {
		this.attachOneId = attachOneId;
	}
	public String getAttachOneName() {
		return attachOneName;
	}
	public void setAttachOneName(String attachOneName) {
		this.attachOneName = attachOneName;
	}
	public int getAttachOneIspackage() {
		return attachOneIspackage;
	}
	public void setAttachOneIspackage(int attachOneIspackage) {
		this.attachOneIspackage = attachOneIspackage;
	}
	public String getAttachOneType() {
		return attachOneType;
	}
	public void setAttachOneType(String attachOneType) {
		this.attachOneType = attachOneType;
	}
	public String getAttachOneSaveName() {
		return attachOneSaveName;
	}
	public void setAttachOneSaveName(String attachOneSaveName) {
		this.attachOneSaveName = attachOneSaveName;
	}
	public String getAttachOnePath() {
		return attachOnePath;
	}
	public void setAttachOnePath(String attachOnePath) {
		this.attachOnePath = attachOnePath;
	}
	@Override
	public String toString() {
		return "AttachOne [bookId=" + bookId + ", attachOneId=" + attachOneId
				+ ", attachOneName=" + attachOneName + ", attachOneIspackage="
				+ attachOneIspackage + ", attachOneType=" + attachOneType
				+ ", attachOneSaveName=" + attachOneSaveName
				+ ", attachOnePath=" + attachOnePath + ", attachOneFlag="
				+ attachOneFlag + ", isEncode=" + isEncode + ", isSendAtta1="
				+ isSendAtta1 + ", shareUrl=" + shareUrl + "]";
	}

	// private Date lastTime;
	// private Date updateTime;
	// private int updateRemind;
	// private Date creatTime;
	// private String fileName;
	// private int fileSize;
	// private int order;
	// private String dataType; //文件类型。 1为普通文件、2图片包、3音频包、4文档包、5PPT包
	// private String fileLevel; //文件层级 0为跟目录
	// private String remark; //备注
	// private String price; //价格
	// private String discount; //折扣
	// private String author;
	// private String intro;

	// public String getIntro() {
	// return intro;
	// }
	// public void setIntro(String intro) {
	// this.intro = intro;
	// }
	// public String getAuthor() {
	// return author;
	// }
	// public void setAuthor(String author) {
	// this.author = author;
	// }

	// public Date getLastTime() {
	// return lastTime;
	// }
	// public void setLastTime(Date lastTime) {
	// this.lastTime = lastTime;
	// }
	// public Date getUpdateTime() {
	// return updateTime;
	// }
	// public void setUpdateTime(Date updateTime) {
	// this.updateTime = updateTime;
	// }
	// public int getUpdateRemind() {
	// return updateRemind;
	// }
	// public void setUpdateRemind(int updateRemind) {
	// this.updateRemind = updateRemind;
	// }
	// public Date getCreatTime() {
	// return creatTime;
	// }
	// public void setCreatTime(Date creatTime) {
	// this.creatTime = creatTime;
	// }
	// public String getFileName() {
	// return fileName;
	// }
	// public void setFileName(String fileName) {
	// this.fileName = fileName;
	// }
	// public int getFileSize() {
	// return fileSize;
	// }
	// public void setFileSize(int fileSize) {
	// this.fileSize = fileSize;
	// }
	// public int getOrder() {
	// return order;
	// }
	// public void setOrder(int order) {
	// this.order = order;
	// }
	// public String getType() {
	// return attachOneType;
	// }
	// public void setType(String attachOneType) {
	// this.attachOneType = attachOneType;
	// }
	// public String getDataType() {
	// return dataType;
	// }
	// public void setDataType(String dataType) {
	// this.dataType = dataType;
	// }

	// public String getFileLevel() {
	// return fileLevel;
	// }
	// public void setFileLevel(String fileLevel) {
	// this.fileLevel = fileLevel;
	// }
	// public String getRemark() {
	// return remark;
	// }
	// public void setRemark(String remark) {
	// this.remark = remark;
	// }
	// public String getPrice() {
	// return price;
	// }
	// public void setPrice(String price) {
	// this.price = price;
	// }
	// public String getDiscount() {
	// return discount;
	// }
	// public void setDiscount(String discount) {
	// this.discount = discount;
	// }

}
