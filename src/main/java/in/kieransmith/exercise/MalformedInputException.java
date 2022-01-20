package in.kieransmith.exercise;

public class MalformedInputException extends Exception {
	// Default SerialVersionUID
	private static final long serialVersionUID = 1L;
	public String badString;

    public MalformedInputException(String errorMessage, String badString) {
        super(errorMessage);
        this.badString = badString;
    }

}
