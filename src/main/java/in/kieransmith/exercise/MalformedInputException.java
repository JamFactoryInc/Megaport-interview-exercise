package in.kieransmith.exercise;

public class MalformedInputException extends Exception {
    public String badString;

    public MalformedInputException(String errorMessage, String badString) {
        super(errorMessage);
        this.badString = badString;
    }

}
