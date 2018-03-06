package edu.birzeit.bo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {
	
	private String name; 
	private String address; 
	private String major; 
	
	
	@JsonCreator
    public Student(@JsonProperty("name") String name,@JsonProperty("address") String address,@JsonProperty("major") String major) {
        this.name = name;
        this.address = address;
        this.major = major; 
    }

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
}
