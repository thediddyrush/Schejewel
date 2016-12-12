package legacy.models;

public class ResourceType{
	private int ResourceTypeId;
	private String Name;
	
	public void setResourceTypeId(int ResourceTypeId){
		this.ResourceTypeId = ResourceTypeId;
	}
	public int getResourceTypeId(){
		return ResourceTypeId;
	}
	
	public void setName(String Name){
		this.Name = Name;
	}
	public String getName(){
		return Name;
	}
}
