package birzeit.edu.backup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Booking {
	private int dbId;
	private int cId;
	private int fId;
	
	@JsonCreator
    public Booking(@JsonProperty("dbId") int dbId,
    			    @JsonProperty("cId") int cId,
    	        	@JsonProperty("fId") int fId)
    	         {
		this.dbId=dbId;
		this.cId=cId;
		this.fId=fId;
    }
	
	public int getDbId() {
		return dbId;
	}
	public void setDbId(int dbId) {
		this.dbId = dbId;
	}
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public int getfId() {
		return fId;
	}
	public void setfId(int fId) {
		this.fId = fId;
	}
	
	
}
