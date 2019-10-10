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
                ((Book) obj).bookUnavailable();
            }
        }
        else if (obj instanceof Movie) {
            if (ableToBorrow(obj)) {
                rentedItem.add(new TransactionRecord(((Movie) obj).getTitle(), borrowerName, TransactionRecord.MOVIE, false));
                ((Movie) obj).movieUnavailable();
            }
        }
    }

    // Updates the availability of an item to available and removes the borrowed item from the borrower's rented items
    // list it is not past the item's due date. If it is past the item's due day, late fine must be paid before returning
    // the item.
    public void returnItem(Object obj, LocalDate returnDate) {
        for (int i = 0; i < rentedItem.size(); i++) {
            if (obj instanceof Book) {
                if (rentedItem.get(i).getItemName().equals(((Book) obj).getTitle())) {
                    if (checkPastDue(rentedItem.get(i), returnDate) == true) {
                        System.out.println("Must pay late fine before book can be returned");
                    }
                    else {
                        rentedItem.get(i).setReturnStatus(true);
                        ((Book) obj).bookAvailable();
                        rentedItem.remove(i);
                    }
                }
            }
            else if (obj instanceof Movie) {
                if (rentedItem.get(i).getItemName().equals(((Movie) obj).getTitle())) {
                    if (checkPastDue(rentedItem.get(i), returnDate) == true) {
                        System.out.println("Must pay late fine before movie can be returned");
                    }
                    else {
                        rentedItem.get(i).setReturnStatus(true);
                        ((Movie) obj).movieAvailable();
                        rentedItem.remove(i);
                    }
                }
            }
        }
    }

    // Checks if an item being returned is past it's due date and if so calculates the late fine.
    public Boolean checkPastDue(TransactionRecord item, LocalDate returnDate) {
        long days = ChronoUnit.DAYS.between(returnDate, item.getDueDate());

        if (days < 0 && item.getFineAmount() == -1) {
            double fine = 1.5*Math.abs(days);
            item.setFineAmount(fine);
            return true;
        }

        return false;
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

        for (TransactionRecord item : rentedItem) {
            if (item.getFineAmount() > 0 ) {
                System.out.println("Item cannot be borrowed as there is an outstanding late fine.");
                return false;
            }
        }

        return true;
    }

    // Used to pay the fine associated with the rental item if it's returned past it's due date.
    public void paidFine(Object obj) {
        for (TransactionRecord item : rentedItem) {
            if (item.getItemName().equals(((Book) obj).getTitle())) {
                item.setFineAmount(0);
            }
        }
    }

    // Extends the due date for when an item is suppose to be returned.
    public void renewItem(Object obj, int num) {
        for (TransactionRecord item : rentedItem) {
            if (item.getItemName().equals(((Book) obj).getTitle())) {
                item.extendDueDate(num);
            }
        }
    }

    // Prints out details about current item(s) being borrowed.
    public String getItemDetails(Object obj) {
        for (TransactionRecord item : rentedItem) {
            if (obj instanceof Book) {
                if (item.getItemName().equals(((Book) obj).getTitle())) {
                    return item.transactionDetails();
                }
            }
        }

        return "Item details not found";
    }

    // Returns information about the borrower's name and related details.
    public String borrowerNameAndDetails() {
        return "Borrower's Name: " + name + " | " + "Number of Items Borrowed: " + Integer.toString(rentedItem.size())
                + " | " + "Borrow Limit: " + borrowLimit;
    }

}
