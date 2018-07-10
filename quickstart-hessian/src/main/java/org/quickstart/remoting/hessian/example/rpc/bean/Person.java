/**
 * 
 */
/**
 * @author ZhangXiao
 *
 */
package org.quickstart.remoting.hessian.example.rpc.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class Person implements Serializable {

	private static final long serialVersionUID = -1923645274767028479L;

	private int id;
	private boolean gender;
	private String name;
	private Date brithday;
	private double height;
	private float weight;
	private long phone;
	private String[] address;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBrithday() {
		return brithday;
	}

	public void setBrithday(Date brithday) {
		this.brithday = brithday;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String[] getAddress() {
		return address;
	}

	public void setAddress(String[] address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", gender=" + gender + ", name=" + name
				+ ", brithday=" + brithday + ", height=" + height + ", weight="
				+ weight + ", phone=" + phone + ", address="
				+ Arrays.toString(address) + "]";
	}

}