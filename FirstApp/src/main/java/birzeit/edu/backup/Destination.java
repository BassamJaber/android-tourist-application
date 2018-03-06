package birzeit.edu.backup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Destination {
	
	private int dbId;
	private String name;
	private String country;
	private double longitude;
	private double latitude;
	private String photoPath;
	
	public Destination() {
		super();
	}

	@JsonCreator
    public Destination(@JsonProperty("dbId") int dbId,
    			       @JsonProperty("name") String name,
    			       @JsonProperty("country") String country,
    			       @JsonProperty("longitude") double longitude,
    			       @JsonProperty("latitude") double latitude,
    			       @JsonProperty("photoPath") String photoPath) {
		this.dbId=dbId;
		this.name=name;
		this.country=country;
		this.longitude=longitude;
		this.latitude=latitude;
		this.photoPath=photoPath;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latiude) {
		this.latitude = latiude;
	}
	public String getPhotoPath() {
		return photoPath;
	}
	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	
	
}
