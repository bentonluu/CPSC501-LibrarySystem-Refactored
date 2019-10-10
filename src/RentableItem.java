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

    public abstract String itemNameAndDetails();

}
