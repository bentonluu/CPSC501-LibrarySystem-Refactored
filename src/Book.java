public class Book extends RentableItem {

    private int bookID;
    private String authorName;

    public Book(String bookTitle, int bookID, String authorName, Boolean availability) {
        super(bookTitle, availability);
        this.bookID = bookID;
        this.authorName = authorName;
    }

    // Retrieves the availability of the book.
    public Boolean getAvailability() {
        return availability;
    }

    // Retrieves the unique ID of the movie.
    public int getBookID() {
        return bookID;
    }

    // Sets the availability of the movie to unavailable.
    public void bookUnavailable() {
        availability = false;
    }

    // Sets the availability of the movie to available.
    public void bookAvailable() {
        availability = true;
    }

    // Returns information about the book's name and related details.
    public String itemNameAndDetails() {
        return "Book Title: " + getTitle() + " | " + "Author Name: " + authorName + " | "
                + "Book ID: " + bookID + " | " + "Availability: " + availability;

    }

}

