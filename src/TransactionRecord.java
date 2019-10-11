import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class TransactionRecord {
    public static final int BOOK = 0;
    public static final int MOVIE = 1;

    private String itemName;
    private String borrowerName;
    private int itemType;
    private LocalDate dateOfIssue;
    private LocalDate dueDate;
    private double fineAmount;
    private boolean returnStatus;

    public TransactionRecord(String itemName, String borrowerName, int itemType, boolean returnStatus) {
        this.itemName = itemName;
        this.borrowerName = borrowerName;
        this.itemType = itemType;
        dateOfIssue = LocalDate.now();
        dueDate = borrowItemDueDate(itemType);
        fineAmount = -1.0;
        this.returnStatus = returnStatus;
    }

    // Sets the due date for when an item is suppose to be returned.
    public LocalDate borrowItemDueDate(int itemType) {
        switch (itemType) {
            case BOOK:
                return dateOfIssue.plusDays(5);

            case MOVIE:
                return dateOfIssue.plusDays(8);

            default:
                throw new IllegalArgumentException("Invalid item");
        }
    }

    // Checks if an item being returned is past it's due date and if so calculates the late fine.
    public Boolean checkPastDue(LocalDate returnDate) {
        long days = ChronoUnit.DAYS.between(returnDate, dueDate);

        if (days < 0 && fineAmount == -1) {
            fineAmount = 1.5*Math.abs(days);
            return true;
        }

        return false;
    }

    // Retrieves the fine amount associated with the borrowed item.
    public double getFineAmount() {
        return fineAmount;
    }

    // Retrieves the due date associated with the borrowed item.
    public LocalDate getDueDate() {
        return dueDate;
    }

    // Retrieves the name of the item associated with the borrowed item.
    public String getItemName() {
        return itemName;
    }

    // Retrieves the return status associated with the borrowed item.
    public boolean getReturnStatus() {
        return returnStatus;
    }

    // Sets the fine amount associated with the borrowed item.
    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    // Sets the return status associated with the borrowed item.
    public void setReturnStatus(Boolean returnStatus) {
        this.returnStatus = returnStatus;
    }

    // Extends the date that an item is suppose to be returned.
    public void extendDueDate(int num) {
        dueDate = dueDate.plusDays(num);
    }

    // Returns information about the borrowed item's transaction record.
    public String transactionDetails() {
        return "Item: " + itemName + " | " + "Item Type: " + itemType + " | " + "Borrower: " + borrowerName
                + " | " + "Date of Issue: " + dateOfIssue + " | " + "Due Date: " + dueDate + " | "
                + "Fine: " + fineAmount + " | " + "Return Status: " + returnStatus;
    }
}
