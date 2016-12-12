package legacy.models;

public class CruiseLine {
	private int CruiseLineId;
    private String Name;
    
    public void setName(String Name){
        this.Name = Name;
    }
    public String getName(){
        return Name;
    }
    
    public void setCruiseLineId(int CruiseLineId){
        this.CruiseLineId = CruiseLineId;
    }
    public int getCruiseLineId(){
        return CruiseLineId;
    }
}
