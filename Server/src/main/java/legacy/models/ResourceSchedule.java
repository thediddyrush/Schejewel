
package legacy.models;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class ResourceSchedule {
	int resourceId;
	int tourId;
	long startTime;
	int duration;
	int statusId;
	
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	
	public int getTourId() {
		return tourId;
	}
	public void setTourId(int tourId) {
		this.tourId = tourId;
	}
	
	public long getStartTimeInMillis() {
		return startTime;
	}
	public String getStartDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.setTimeInMillis(startTime);
        return sdf.format(calendar.getTime());
    }
    public String getStartTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSSSSS");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		calendar.setTimeInMillis(startTime);
        return sdf.format(calendar.getTime());
    }
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public void setStartTime(Date date, Time time) {
		startTime = date.getTime() + timeToMillis(time);
	}
	
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
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
