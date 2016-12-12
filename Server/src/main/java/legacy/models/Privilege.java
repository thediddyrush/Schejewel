package legacy.models;

public class Privilege{
	private int PrivilegeId;
	private String Description;
	
	public void setPrivilegeId(int PrivilegeId){
		this.PrivilegeId = PrivilegeId;
	}
	public int getPrivilegeId(){
		return PrivilegeId;
	}
	
	public void setDescription(String Description){
		this.Description = Description;
	}
	public String getDescription(){
		return Description;
	}
}
