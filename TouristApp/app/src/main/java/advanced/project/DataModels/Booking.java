package advanced.project.DataModels;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Bassam on 4/23/2015.
 */
public class Booking {
    private int dbId;
    private int cId;
    private int fId;


    public Booking() {
    }

    public int getDbId() {
        return dbId;
    }
    @JsonProperty("dbId")
    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public int getcId() {
        return cId;
    }
    @JsonProperty("cId")
    public void setcId(int cId) {
        this.cId = cId;
    }

    public int getfId() {
        return fId;
    }
    @JsonProperty("fId")
    public void setfId(int fId) {
        this.fId = fId;
    }
}
