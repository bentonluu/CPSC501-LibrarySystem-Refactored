import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Borrower {

    private String name;
    private int borrowLimit;
    private List<TransactionRecord> rentedItem;

    public Borrower (String name) {
        this.name = name;
        borrowLimit = 2;
        rentedItem = new ArrayList<>();
    }

    // Retrieves the name of the borrower.
    public String getName() {
        return name;
    }

    public List<TransactionRecord> getRentedItem() {
        return rentedItem;
    }

    // Updates the borrow limit of the borrower.
    public void updateBorrowLimit(int newBorrowLimit) {
        borrowLimit = newBorrowLimit;
    }

    // Updates the availability of an item to unavailable and adds the item to the borrower's rented items list, only if
    // the borrower is able to borrow the item.
    public void requestItem(Object obj, String borrowerName, Library library) {
        if (obj instanceof Book) {
            if (library.ableToBorrow(obj, rentedItem)) {
                rentedItem.add(new TransactionRecord(((Book) obj).getTitle(), borrowerName, TransactionRecord.BOOK, false));
                ((Book) obj).itemUnavailable();
            }
        }
        else if (obj instanceof Movie) {
            if (library.ableToBorrow(obj, rentedItem)) {
                rentedItem.add(new TransactionRecord(((Movie) obj).getTitle(), borrowerName, TransactionRecord.MOVIE, false));
                ((Movie) obj).itemUnavailable();
            }
        }
    }

    // Updates the availability of an item to available and removes the borrowed item from the borrower's rented items
    // list it is not past the item's due date. If it is past the item's due day, late fine must be paid before returning
    // the item.
    public void returnItem(Object obj, LocalDate returnDate, Library library) {
        TransactionRecord item = library.findTransactionRecord(rentedItem, obj);

        if (item.checkPastDue(returnDate) == true) {
            System.out.println("Must pay late fine before item can be returned");
        }
        else {
            item.setReturnStatus(true);
            rentedItem.remove(rentedItem.indexOf(item));

            if (obj instanceof Book) {
                ((Book) obj).itemAvailable();
            }
            else if (obj instanceof Movie) {
                ((Movie) obj).itemAvailable();
            }
        }
    }

    // Used to pay the fine associated with the rental item if it's returned past it's due date.
    public void paidFine(Object obj, Library library) {
        TransactionRecord item = library.findTransactionRecord(rentedItem, obj);
        item.setFineAmount(0);
    }

    // Returns information about the borrower's name and related details.
    public String borrowerNameAndDetails() {
        return "Borrower's Name: " + name + " | " + "Number of Items Borrowed: " + Integer.toString(rentedItem.size())
                + " | " + "Borrow Limit: " + borrowLimit;
    }

}
