package in.kieransmith.exercise;

public class MalformedInputFileException extends Exception {
    public String badString;

    public MalformedInputFileException(String errorMessage, String badString) {
        super(errorMessage);
        this.badString = badString;
    }

}
