package legacy.models;

public class TourType{

	private int TourTypeId;
	private String Name;
    private int CompanyId;//or this could be a Company
    
    public void setTourTypeId(int TourTypeId){
    	this.TourTypeId = TourTypeId;
    }
    public int getTourTypeId(){
    	return TourTypeId;
    }
    
    public void setName(String Name){
    	this.Name = Name;
    }
    public String getName(){
    	return Name;
    }
    
    public void setCompanyId(int CompanyId){
    	this.CompanyId = CompanyId;
    }
    public int getCompanyId(){
    	return CompanyId;
    }
}
