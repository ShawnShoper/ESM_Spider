package org.shoper.spider.db;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class TestPojo implements Serializable {
	
	private static final long serialVersionUID = -1724799549031476226L;
	@SuppressWarnings("unused")
	private String id;
	private String name;
	private int age;
	private Date birthday;
	private File file;
	private byte[] fileByte;
	private boolean gender;
	
	public String getId() {
		return UUID.randomUUID().toString();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public byte[] getFileByte() {
		return fileByte;
	}
	public void setFileByte(byte[] fileByte) {
		this.fileByte = fileByte;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	
}
