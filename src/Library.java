import java.util.ArrayList;
import java.util.List;

public class Library {

    private String name, address;

    public Library(String name, String address) {
        this.name = name;
        this.address = address;
    }

    // Verifies that the borrower has not exceeded the borrow limit, the item requested to be borrowed is available, and
    // there are no outstanding late fines. If those conditions are satisfied the item can be borrowed.
    public Boolean ableToBorrow(Object obj, List<TransactionRecord> rentedItem) {
        if (rentedItem.size() > 2) {
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

    // Extends the due date for when an item is suppose to be returned.
    public void renewItem(Object obj, Borrower borrower, int num) {
        TransactionRecord item = findTransactionRecord(borrower.getRentedItem(), obj);
        item.extendDueDate(num);
    }

    // Retrieves details about current item(s) being borrowed.
    public String getItemDetails(Object obj, Borrower borrower) {
        TransactionRecord item = findTransactionRecord(borrower.getRentedItem(), obj);

        if (item == null) {
            return "Item details not found";
        }
        return item.transactionDetails();
    }

    // Finds the transaction record corresponding to the object item passed in.
    public TransactionRecord findTransactionRecord(List<TransactionRecord> rentedItem, Object obj) {
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

    // Returns information about the library's name and related details.
    public String libraryNameAndDetails() {
        return "Library Name: " + name + " | " + "Library Address: " + address;
    }
}
