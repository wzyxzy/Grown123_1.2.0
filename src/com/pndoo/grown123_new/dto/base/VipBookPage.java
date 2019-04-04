package com.pndoo.grown123_new.dto.base;

import java.util.List;

import com.pndoo.grown123_new.annotations.ComplexSerializableType;

public class VipBookPage {
	protected List<VipBookVO> list;
	public int pageSize; // 每页显示数
	public int currentPage; // 当前页面数
	protected int totalRecords; // 总记录数
	protected int totalPages; // 总页数
	protected int startRecords; // 开始条数
	protected int endRecords; // 截至条数

	@ComplexSerializableType(clazz = VipBookVO.class)
	public List<VipBookVO> getList() {
		return list;
	}

	@ComplexSerializableType(clazz = VipBookVO.class)
	public void setList(List<VipBookVO> list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getStartRecords() {
		return startRecords;
	}

	public void setStartRecords(int startRecords) {
		this.startRecords = startRecords;
	}

	public int getEndRecords() {
		return endRecords;
	}

	public void setEndRecords(int endRecords) {
		this.endRecords = endRecords;
	}

}