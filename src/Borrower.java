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

    // Updates the borrow limit of the borrower.
    public void updateBorrowLimit(int newBorrowLimit) {
        borrowLimit = newBorrowLimit;
    }

    // Updates the availability of an item to unavailable and adds the item to the borrower's rented items list, only if
    // the borrower is able to borrow the item.
    public void requestItem(Object obj, String borrowerName) {
        if (obj instanceof Book) {
            if (ableToBorrow(obj)) {
                rentedItem.add(new TransactionRecord(((Book) obj).getTitle(), borrowerName, TransactionRecord.BOOK, false));
                ((Book) obj).itemUnavailable();
            }
        }
        else if (obj instanceof Movie) {
            if (ableToBorrow(obj)) {
                rentedItem.add(new TransactionRecord(((Movie) obj).getTitle(), borrowerName, TransactionRecord.MOVIE, false));
                ((Movie) obj).itemUnavailable();
            }
        }
    }

    // Updates the availability of an item to available and removes the borrowed item from the borrower's rented items
    // list it is not past the item's due date. If it is past the item's due day, late fine must be paid before returning
    // the item.
    public void returnItem(Object obj, LocalDate returnDate) {
        TransactionRecord item = findTransactionRecord(rentedItem, obj);

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

    // Verifies that the borrower has not exceeded the borrow limit, the item requested to be borrowed is available, and
    // there are no outstanding late fines. If those conditions are satisfied the item can be borrowed.
    public Boolean ableToBorrow(Object obj) {

        if (rentedItem.size() > borrowLimit) {
            System.out.println("Borrow limit has been exceeded");
            return false;
        }

        if (obj instanceof Book) {
            if (((Book) obj).getAvailability() == false) {
                System.out.println("Book is not available to be borrowed");
                return false;
            }
        }
        else if (obj instanceof Movie) {
            if (((Movie) obj).getAvailability() == false) {
                System.out.println("Movie is not available to be borrowed");
                return false;
            }
        }

        TransactionRecord item = findTransactionRecord(rentedItem, obj);
        if (item != null) {
            if (item.getFineAmount() > 0 ) {
                System.out.println("Item cannot be borrowed as there is an outstanding late fine.");
                return false;
            }
        }

        return true;
    }

    // Used to pay the fine associated with the rental item if it's returned past it's due date.
    public void paidFine(Object obj) {
        TransactionRecord item = findTransactionRecord(rentedItem, obj);
        item.setFineAmount(0);
    }

    // Extends the due date for when an item is suppose to be returned.
    public void renewItem(Object obj, int num) {
        TransactionRecord item = findTransactionRecord(rentedItem, obj);
        item.extendDueDate(num);
    }

    // Prints out details about current item(s) being borrowed.
    public String getItemDetails(Object obj) {
        TransactionRecord item = findTransactionRecord(rentedItem, obj);

        if (item == null) {
            return "Item details not found";
        }
        return item.transactionDetails();
    }

    public TransactionRecord findTransactionRecord(List<TransactionRecord> transactionRecords, Object obj) {
        for (TransactionRecord item : rentedItem) {
            if (obj instanceof Book && item.getItemName().equals(((Book) obj).getTitle())) {
                return item;
            }
            else if (obj instanceof Movie && item.getItemName().equals(((Movie) obj).getTitle())) {
                return item;
            }
        }
        return null;
    }

    // Returns information about the borrower's name and related details.
    public String borrowerNameAndDetails() {
        return "Borrower's Name: " + name + " | " + "Number of Items Borrowed: " + Integer.toString(rentedItem.size())
                + " | " + "Borrow Limit: " + borrowLimit;
    }

}
