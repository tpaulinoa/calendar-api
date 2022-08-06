package code.thiago.app.exceptions;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(String id) {
        super(String.format("Maybe this person with id=%s is yet to be born...", id));
    }
}
