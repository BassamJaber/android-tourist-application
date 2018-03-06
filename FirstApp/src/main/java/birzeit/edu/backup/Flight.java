package birzeit.edu.backup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Flight {
	private int dbId;
	private String companyName;
	private int cost;
	private String depDate;
	private String arriDate;
	private Destination destination;
	private int destDbId;
	
	
	@JsonCreator
    public Flight(@JsonProperty("dbId") int dbId,
    			  @JsonProperty("companyName") String companyName,
    	          @JsonProperty("cost") int cost,
    	          @JsonProperty("depDate") String depDate,
    	          @JsonProperty("arriDate") String arriDate,
    	          @JsonProperty("destination") Destination destination,
    			  @JsonProperty("destDbId") int destDbId) {
		this.dbId=dbId;
		this.companyName=companyName;
		this.cost=cost;
		this.depDate=depDate;
		this.arriDate=arriDate;
		this.destination=destination;
		this.destDbId=destDbId;
    }


	public int getDbId() {
		return dbId;
	}


	public void setDbId(int dbId) {
		this.dbId = dbId;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public int getCost() {
		return cost;
	}


	public void setCost(int cost) {
		this.cost = cost;
	}


	public String getDepDate() {
		return depDate;
	}


	public void setDepDate(String depDate) {
		this.depDate = depDate;
	}


	public String getArriDate() {
		return arriDate;
	}


	public void setArriDate(String arriDate) {
		this.arriDate = arriDate;
	}

	

	public Destination getDestination() {
		return destination;
	}


	public void setDestination(Destination destination) {
		this.destination = destination;
	}


	public int getDestDbId() {
		return destDbId;
	}


	public void setDestDbId(int destDbId) {
		this.destDbId = destDbId;
	}
	
	
	
	
}
