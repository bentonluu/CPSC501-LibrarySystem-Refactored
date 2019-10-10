public abstract class RentableItem {

    protected String title;

    protected RentableItem(String title) {
        this.title = title;
    }

    // Retrieves the title of the item.
    public String getTitle() {
        return title;
    }

    public abstract String itemNameAndDetails();

}
