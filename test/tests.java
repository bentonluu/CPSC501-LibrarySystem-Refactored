import org.junit.Assert;
import org.junit.Test;
import java.time.LocalDate;

public class tests {
    private static final Book b = new Book("The Hunger Games", 0, "Suzanne Collins", true);
    private static final Book b1 = new Book("The Lightning Thief", 1, "Rick Riordan", true);
    private static final Book b2 = new Book("Harry Potter and the Philosopher's Stone", 2, "J. K. Rowling", true);

    private static final Movie m = new Movie("Avengers: Infinity War", 0, "Anthony Russo", true);
    private static final Movie m1 = new Movie("Spider-Man: Far From Home", 1, "Jon Watts", true);
    private static final Movie m2 = new Movie("The Dark Knight", 2, "Christopher Nolan", true);

    private static final Borrower borrower = new Borrower("Benton");
    private static final Borrower borrower1 = new Borrower("Bob");

    @Test
    public void borrowItemTest() {
        // Borrower borrows a book.
        borrower.requestItem(b, borrower.getName());
        Assert.assertEquals("Book Title: The Hunger Games | Author Name: Suzanne Collins | Book ID: 0 | Availability: false", b.itemNameAndDetails());
    }

    @Test
    public void returnItemTest() {
        // Borrower borrows a book and returns the item.
        borrower.requestItem(b, borrower.getName());
        borrower.returnItem(b, LocalDate.of(2019,10,8));
        Assert.assertEquals("Book Title: The Hunger Games | Author Name: Suzanne Collins | Book ID: 0 | Availability: true", b.itemNameAndDetails());
    }

    @Test
    public void availabilityTest() {
        // Checks if book and movie availability is unavailable after being borrowed.
        borrower.requestItem(b, borrower.getName());
        borrower.requestItem(m2, borrower.getName());
        Assert.assertEquals(false, b.getAvailability());
        Assert.assertEquals(false, m2.getAvailability());

        // Checks if book and movie availability is available after being returned.
        borrower.returnItem(b, LocalDate.of(2019,10,9));
        borrower.returnItem(m2, LocalDate.of(2019,10,10));
        Assert.assertEquals(true, b.getAvailability());
        Assert.assertEquals(true, m2.getAvailability());
    }

    @Test
    public void returnItemPastDue() {
        // Borrower borrows a book.
        borrower.requestItem(b, borrower.getName());
        Assert.assertEquals("Borrower's Name: Benton | Number of Items Borrowed: 1 | Borrow Limit: 2", borrower.borrowerNameAndDetails());
        Assert.assertEquals("Item: The Hunger Games | Item Type: 0 | Borrower: Benton | Date of Issue: 2019-10-10 | Due Date: 2019-10-11 | Fine: -1.0 | Return Status: false", borrower.getItemDetails(b));

        // Borrower tries to return item but it's past due and must pay fine first before item can be returned.
        borrower.returnItem(b, LocalDate.of(2019,10,15));
        Assert.assertEquals("Item: The Hunger Games | Item Type: 0 | Borrower: Benton | Date of Issue: 2019-10-10 | Due Date: 2019-10-11 | Fine: 6.0 | Return Status: false", borrower.getItemDetails(b));

        // Borrower pays late fine.
        borrower.paidFine(b);
        Assert.assertEquals("Item: The Hunger Games | Item Type: 0 | Borrower: Benton | Date of Issue: 2019-10-10 | Due Date: 2019-10-11 | Fine: 0.0 | Return Status: false", borrower.getItemDetails(b));

        // Borrower is able to return item.
        borrower.returnItem(b, LocalDate.of(2019,10,15));
        Assert.assertEquals("Borrower's Name: Benton | Number of Items Borrowed: 0 | Borrow Limit: 2", borrower.borrowerNameAndDetails());
        Assert.assertEquals("Item details not found", borrower.getItemDetails(b));
    }
}
