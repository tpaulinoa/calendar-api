package code.thiago.app.exceptions;

public class RequiredFieldException extends RuntimeException {

    public RequiredFieldException(String field) {
        super(String.format("Field '%s' is required.", field));
    }
}
