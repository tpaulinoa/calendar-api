package code.thiago.app.exceptions;

public class InvalidDateTimeException extends RuntimeException {

    public InvalidDateTimeException() {
        super("Invalid input! There is something wrong in the input for date/time/daysOfWeek.");
    }
}
