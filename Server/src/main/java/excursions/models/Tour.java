package excursions.models;

import java.util.ArrayList;
import java.util.List;

public class Tour {
    private int id;
    private int ownerId;
    private String startDate;
    private String startTime;
    private int tourTypeId;
    private int statusId;
    private List<Resource> resources;
    private List<TourGroup> tourGroups;

    public Tour(){
        resources = new ArrayList<>();
        tourGroups = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getTourTypeId() {
        return tourTypeId;
    }

    public void setTourTypeId(int tourTypeId) {
        this.tourTypeId = tourTypeId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public List<TourGroup> getTourGroups() {
        return tourGroups;
    }

    public void setTourGroups(List<TourGroup> tourGroups) {
        this.tourGroups = tourGroups;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void addResource(Resource resource){
        this.resources.add(resource);
    }

    public void addTourGroup(TourGroup tourGroup){
        this.tourGroups.add(tourGroup);
    }
}
