package advanced.project.DataModels;

import java.io.Serializable;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by BassamJ on 3/24/2015.
 */
public class Customer implements Serializable {
    private int dbId;
    private String name;
    private long id;
    private long passportNum;
    private String photoPath;
    private String address;

    public Customer() {
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

    public long getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(long id) {
        this.id = id;
    }

    public long getPassportNum() {
        return passportNum;
    }

    @JsonProperty("passportNum")
    public void setPassportNum(long passportNum) {
        this.passportNum = passportNum;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    @JsonProperty("photoPath")
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "dbId=" + dbId +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", passportNum=" + passportNum +
                ", photoPath='" + photoPath + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
