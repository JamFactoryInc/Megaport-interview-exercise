package in.kieransmith.exercise;

public class Name {
    String firstName;
    String lastName;

    public static final int NAME_LENGTH = 2;

    public Name(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }

    /**
     * @return String the first name
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @return String the last name
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @param firstName what to set the first name to. Trims whitespace
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName.replaceAll("\\s", "");
    }

    /**
     * @param lastName what to set the last name to. Trims whitespace
     */
    public void setLastName(String lastName) {
        this.lastName = lastName.replaceAll("\\s", "");
    }

    /**
     * Sets both the first and last name
     *
     * @param firstName the first name to set
     * @param lastName  the last name to set
     */
    public void setName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * @return String a string representation of this object
     */
    public String toString() {
        return String.format("%s, %s", this.firstName, this.lastName);
    }

    /**
     * Creates a new instance of Name from a first name/ last name pair string
     *
     * @param firstLastNamePair the comma-seperated first and last name
     * @return Name the new instance representing the name
     * @throws MalformedInputException More or less than 3 elements are given
     */
    public static Name from(String firstLastNamePair) throws MalformedInputException {
        String[] names = firstLastNamePair.split(",");
        if (names.length != NAME_LENGTH) {
            throw new MalformedInputException(
                    String.format("%s is not a valid first name/ last name pair", firstLastNamePair),
                    firstLastNamePair);
        }
        return new Name(names[0], names[1]);
    }

    /**
     * Compares two Names. -1 = before other in the alphabet, 0 = equal to other, 1
     * = after other in the alphabet
     *
     * @param other the name object to compare this to
     * @return int the result of the comparison
     */
    public int compareTo(Name other) {
        int firstNameComparison = this.firstName.compareTo(other.getFirstName());
        if (firstNameComparison != 0) {
            return firstNameComparison;
        } else {
            return this.lastName.compareTo(other.getLastName());
        }
    }

    /**
     * @param other the other instance to compare equality
     * @return boolean if the two instances have the same value
     */
    public boolean equals(Name other) {
        return this.firstName.equals(other.getFirstName()) && this.lastName.equals(other.getLastName());
    }
}
