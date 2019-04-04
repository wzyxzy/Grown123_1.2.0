package com.pndoo.grown123_new.dto.base;

import java.io.Serializable;

public class ShopListUpdateTimeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int date;
	private int day;
	private int hours;
	private int minutes;
	private int month;
	private int seconds;
	private long time;
	private int timezoneOffset;
	private int year;
	
	
	public ShopListUpdateTimeBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ShopListUpdateTimeBean(int date, int day, int hours, int minutes, int month, int seconds, long time, int timezoneOffset, int year) {
		super();
		this.date = date;
		this.day = day;
		this.hours = hours;
		this.minutes = minutes;
		this.month = month;
		this.seconds = seconds;
		this.time = time;
		this.timezoneOffset = timezoneOffset;
		this.year = year;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public int getTimezoneOffset() {
		return timezoneOffset;
	}
	public void setTimezoneOffset(int timezoneOffset) {
		this.timezoneOffset = timezoneOffset;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
}
