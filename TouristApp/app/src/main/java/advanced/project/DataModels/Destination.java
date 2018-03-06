package advanced.project.DataModels;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by BassamJ on 3/24/2015.
 */
@SuppressWarnings("serial") //with this annotation we are going to hide compiler warning
public class Destination implements Serializable {
    private int dbId;
    private String name;
    private String country;
    private double longitude;
    private double latitude;
    private String photoPath;

    public Destination() {
    }

    public int getDbId() {
        return dbId;
    }

    @JsonProperty("dbId")
    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    @JsonProperty("country")
    public void setCountry(String country) {
        this.country = country;
    }

    public double getLongitude() {
        return longitude;
    }

    @JsonProperty("longitude")
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @JsonProperty("latitude")
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    @JsonProperty("photoPath")
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "dbId=" + dbId +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", photoPath='" + photoPath + '\'' +
                '}';
    }
}
