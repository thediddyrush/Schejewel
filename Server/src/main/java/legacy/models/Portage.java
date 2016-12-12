package legacy.models;

import java.util.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Portage {
    private int portageId;
    private int cruiseShipId;
    private long arrival;
    private long departure;
    private Integer passengerCount = null;
    private Long allAboard = null;
    private Integer dock = null;
    private String voyage = null;
    private String location = null;
   
    public void setPortageId(int PortageId){
    	this.portageId = PortageId;
    }
    public int getPortageId(){
    	return portageId;
    }
    
    public void setCruiseShipId(int CruiseShipId){
    	this.cruiseShipId = CruiseShipId;
    }
    public int getCruiseShipId(){
    	return cruiseShipId;
    }
    
    public void setDock(Integer Dock){
    	this.dock = Dock;
    }
    public Integer getDock(){
    	return dock;
    }
    
    public void setVoyage(String Voyage){
    	this.voyage = Voyage;
    }
    public String getVoyage(){
    	return voyage;
    }
    
    public long getArrival() {
    	return arrival;
    }
    public String getArrivalDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.setTimeInMillis(arrival);
        return sdf.format(calendar.getTime());
    }
    public String getArrivalTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSSSSS");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.setTimeInMillis(arrival);
        return sdf.format(calendar.getTime());
    }
    public void setArrival(long millis) {
        arrival = millis;
    }
    public void setArrival(Date date, Time time) {
        arrival = date.getTime() + timeToMillis(time);
    }
	
    public long getDeparture() {
    	return departure;
    }
    public String getDepartureDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.setTimeInMillis(departure);
        return sdf.format(calendar.getTime());
    }
    public String getDepartureTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSSSSS");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.setTimeInMillis(departure);
        return sdf.format(calendar.getTime());
    }
    public void setDeparture(long millis) {
        departure = millis;
    }
    public void setDeparture(Date date, Time time) {
            departure = date.getTime() + timeToMillis(time);
    }
    
    public String getLocation() {
    	return location;
    }
    public void setLocation(String location) {
    	this.location = location;
    }
	
    public Integer getPassengerCount() {
    	return passengerCount;
    }
    public void setPassengerCount(Integer PassengerCount) {
    	this.passengerCount = PassengerCount;
    }
	
    public Long getAllAboard() {
    	return allAboard;
    }
    public String getAllAboardTime() {
        if (allAboard == null)
            return null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSSSSS");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.setTimeInMillis(allAboard);
        return sdf.format(calendar.getTime());
    }
    public void setAllAboard(Long millis) {
    	allAboard = millis;
    }
    public void setAllAboardTime(Time time) {
        if (time == null)
            allAboard = null;
		else 
			allAboard = timeToMillis(time);
    }
	
	private long timeToMillis(Time time) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		return (long)(hour * 3600 + min * 60 + sec) * 1000;
	}
}
