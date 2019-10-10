public abstract class RentableItem {

    protected String title;
    protected Boolean availability;

    protected RentableItem(String title, Boolean availability) {
        this.title = title;
        this.availability = availability;
    }

    // Retrieves the title of the item.
    public String getTitle() {
        return title;
    }

    // Retrieves the availability of the item.
    public Boolean getAvailability() {
        return availability;
    }

    // Sets the availability of the item to unavailable.
    public void itemUnavailable() {
        availability = false;
    }

    // Sets the availability of the item to available.
    public void itemAvailable() {
        availability = true;
    }

    public abstract String itemNameAndDetails();

}
