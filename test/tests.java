import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDate;

public class tests {
    private static final Library library = new Library("Calgary Public Library", "93 East High Point Avenue NE");

    private static final Book b = new Book("The Hunger Games", 0, "Suzanne Collins", true);
    private static final Book b1 = new Book("The Lightning Thief", 1, "Rick Riordan", true);
    private static final Book b2 = new Book("Harry Potter and the Philosopher's Stone", 2, "J. K. Rowling", true);

    private static final Movie m = new Movie("Avengers: Infinity War", 0, "Anthony Russo", true);
    private static final Movie m1 = new Movie("Spider-Man: Far From Home", 1, "Jon Watts", true);
    private static final Movie m2 = new Movie("The Dark Knight", 2, "Christopher Nolan", true);

    private static final Borrower borrower = new Borrower("Benton");
    private static final Borrower borrower1 = new Borrower("Bob");

    private static final LocalDate today = LocalDate.now();
    private static final LocalDate returnDateGood = LocalDate.now().plusDays(2);
    private static final LocalDate returnDateBad = LocalDate.now().plusDays(10);
    private static final LocalDate returnDateBook = LocalDate.now().plusDays(5);
    private static final LocalDate returnDateMovie = LocalDate.now().plusDays(8);

    @Test
    public void borrowItemTest() {
        // Borrower borrows a book.
        borrower.requestItem(b, borrower.getName(), library);
        Assert.assertEquals("Book Title: The Hunger Games | Author Name: Suzanne Collins | Book ID: 0 | Availability: false", b.itemNameAndDetails());
    }

    @Test
    public void returnItemTest() {
        // Borrower borrows a book and returns the item.
        borrower.requestItem(b, borrower.getName(), library);
        borrower.returnItem(b, returnDateGood, library);
        Assert.assertEquals("Book Title: The Hunger Games | Author Name: Suzanne Collins | Book ID: 0 | Availability: true", b.itemNameAndDetails());
    }

    @Test
    public void libraryTest() {
        // Checks if able to retrieve library information from the object.
        Assert.assertEquals("Library Name: Calgary Public Library | Library Address: 93 East High Point Avenue NE", library.libraryNameAndDetails());
    }

    @Test
    public void availabilityTest() {
        // Checks if book and movie availability is unavailable after being borrowed.
        borrower.requestItem(b, borrower.getName(), library);
        borrower.requestItem(m2, borrower.getName(), library);
        Assert.assertEquals(false, b.getAvailability());
        Assert.assertEquals(false, m2.getAvailability());

        // Checks if book and movie availability is available after being returned.
        borrower.returnItem(b, returnDateGood, library);
        borrower.returnItem(m2, returnDateGood, library);
        Assert.assertEquals(true, b.getAvailability());
        Assert.assertEquals(true, m2.getAvailability());
    }

    @Test
    public void returnItemPastDueTest() {
        // Borrower borrows a book.
        borrower.requestItem(b, borrower.getName(), library);
        Assert.assertEquals("Borrower's Name: Benton | Number of Items Borrowed: 1 | Borrow Limit: 2", borrower.borrowerNameAndDetails());
        Assert.assertEquals("Item: The Hunger Games | Item Type: 0 | Borrower: Benton | Date of Issue: " + today.toString() + " | Due Date: " + returnDateBook.toString() + " | Fine: -1.0 | Return Status: false", library.getItemDetails(b, borrower));

        // Borrower tries to return item but it's past due and must pay fine first before item can be returned.
        borrower.returnItem(b, returnDateBad, library);
        Assert.assertEquals("Item: The Hunger Games | Item Type: 0 | Borrower: Benton | Date of Issue: " + today.toString() + " | Due Date: " + returnDateBook.toString() + " | Fine: 7.5 | Return Status: false", library.getItemDetails(b, borrower));

        // Borrower pays late fine.
        borrower.paidFine(b, library);
        Assert.assertEquals("Item: The Hunger Games | Item Type: 0 | Borrower: Benton | Date of Issue: " + today.toString() + " | Due Date: " + returnDateBook.toString() + " | Fine: 0.0 | Return Status: false", library.getItemDetails(b, borrower));

        // Borrower is able to return item.
        borrower.returnItem(b, returnDateBad, library);
        Assert.assertEquals("Borrower's Name: Benton | Number of Items Borrowed: 0 | Borrow Limit: 2", borrower.borrowerNameAndDetails());
        Assert.assertEquals("Item details not found", library.getItemDetails(b, borrower));
    }

    @Test
    public void renewItemTest() {
        borrower.requestItem(b, borrower.getName(), library);
        Assert.assertEquals(false, b.getAvailability());
        Assert.assertEquals("Item: The Hunger Games | Item Type: 0 | Borrower: Benton | Date of Issue: " + today.toString() + " | Due Date: " + returnDateBook.toString() + " | Fine: -1.0 | Return Status: false", library.getItemDetails(b,borrower));

        library.renewItem(b,borrower,3);
        Assert.assertEquals("Item: The Hunger Games | Item Type: 0 | Borrower: Benton | Date of Issue: " + today.toString() + " | Due Date: " + returnDateBook.plusDays(3).toString() + " | Fine: -1.0 | Return Status: false", library.getItemDetails(b,borrower));

        borrower.returnItem(b, returnDateGood, library);
        Assert.assertEquals(true, b.getAvailability());
        Assert.assertEquals("Item details not found", library.getItemDetails(b,borrower));
    }
}
