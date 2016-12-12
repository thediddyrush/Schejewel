package legacy.models;

public class TourGroup {
	private int id;
	private int portageId;
	private int tourId;
    private int groupSize;
    private boolean settled;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPortageId() {
        return portageId;
    }

    public void setPortageId(int portageId) {
        this.portageId = portageId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }
}
