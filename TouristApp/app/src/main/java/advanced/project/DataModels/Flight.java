package advanced.project.DataModels;


import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by BassamJ on 3/24/2015.
 */
public class Flight implements Serializable {
    private int dbId;
    private String companyName;
    private int cost;
    private String depDate;
    private String arriDate;
    private Destination destination;
    private int destDbId;

    public Flight() {
    }

    public int getDbId() {
        return dbId;
    }

    @JsonProperty("dbId")
    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public String getCompanyName() {
        return companyName;
    }

    @JsonProperty("companyName")
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCost() {
        return cost;
    }

    @JsonProperty("cost")
    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDepDate() {
        return depDate;
    }

    @JsonProperty("depDate")
    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public String getArriDate() {
        return arriDate;
    }

    @JsonProperty("arriDate")
    public void setArriDate(String arriDate) {
        this.arriDate = arriDate;
    }

    public Destination getDestination() {
        return destination;
    }

    @JsonProperty("destination")
    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public int getDestDbId() {
        return destDbId;
    }

    @JsonProperty("destDbId")
    public void setDestDbId(int destDbId) {
        this.destDbId = destDbId;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "dbId=" + dbId +
                ", companyName='" + companyName + '\'' +
                ", cost=" + cost +
                ", depDate='" + depDate + '\'' +
                ", arriDate='" + arriDate + '\'' +
                ", destination=" + destination +
                ", destDbId=" + destDbId +
                '}';
    }
}
