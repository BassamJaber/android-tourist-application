package birzeit.edu.backup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {
	
	private int dbId;
	private String name;
	private long id;
	private long passportNum;
	private String photoPath;
	private String address;
	
	
	@JsonCreator
    public Customer(@JsonProperty("dbId") int dbId,
    			    @JsonProperty("name") String name,
    	        	@JsonProperty("id") long id,
    	        	@JsonProperty("passportNum") long passportNum,
    	        	@JsonProperty("photoPath") String photoPath,
    				@JsonProperty("address") String address) {
		this.dbId=dbId;
		this.name=name;
		this.id=id;
		this.passportNum=passportNum;
		this.photoPath=photoPath;
		this.address=address;
    }
	
	public int getDbId() {
		return dbId;
	}
	public void setDbId(int dbId) {
		this.dbId = dbId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPassportNum() {
		return passportNum;
	}
	public void setPassportNum(long passportNum) {
		this.passportNum = passportNum;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
