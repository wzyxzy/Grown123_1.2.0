package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;

/**
 * ����һ����Ƶ
 * @author Administrator
 *
 */
public class AudioItem  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String title;//��Ƶ����
	
	private String duration;//��Ƶ����ʱ��
	
	private String size;//��Ƶ���ļ���С
	
	private String data;//��Ƶ�Ĳ��ž���·��
	
	private String artist;//�ݳ���

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	@Override
	public String toString() {
		return "VideoItem [title=" + title + ", duration=" + duration
				+ ", size=" + size + ", data=" + data + "]";
	}
	
	
	
	
	
	
}
