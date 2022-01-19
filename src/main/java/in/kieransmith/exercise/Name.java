package in.kieransmith.exercise;

public class Name {
    String firstName;
    String lastName;

    public static final int NAME_LENGTH = 2;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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
     * @param firstName what to set the first name to
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @param lastName what to set the last name to
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * @param firstLastNamePair
     * @return Name
     * @throws MalformedInputFileException
     */
    public static Name from(String firstLastNamePair) throws MalformedInputFileException {
        String[] names = firstLastNamePair.replaceAll("\\s", "").split(",");
        if (names.length != NAME_LENGTH) {
            throw new MalformedInputFileException(
                    String.format("%s is not a valid first name/ last name pair", firstLastNamePair),
                    firstLastNamePair);
        }
        return new Name(names[0], names[1]);
    }

    /**
     * @param other
     * @return int
     */
    public int compareTo(Name other) {
        int firstNameComparison = this.firstName.compareTo(other.getFirstName());
        if (firstNameComparison != 0) {
            return firstNameComparison;
        } else {
            return this.lastName.compareTo(other.getLastName());
        }
    }

    public boolean equals(Name other) {
        return this.firstName.equals(other.getFirstName()) && this.lastName.equals(other.getLastName());
    }
}
