package in.kieransmith.exercise;

import java.util.stream.Collectors;
import java.util.ArrayList;

public class NameList extends ArrayList<Name> {

    // Default SerialVersionUID
    private static final long serialVersionUID = 1L;

    /**
     * Sorts the Namelist instance inplace
     *
     * @return this instance of NameList
     */
    public NameList sort() {
        this.sort((name1, name2) -> name1.compareTo(name2));
        return this;
    }

    /**
     * @param firstLastNamePairs an ArrayList of Strings denoting first and last
     *                           names
     * @return NameList the new NameList instance
     * @throws MalformedInputException if any of the pairs are malformed
     */
    public static NameList from(ArrayList<String> firstLastNamePairs) throws MalformedInputException {
        NameList names = new NameList();

        // this uses a standard for loop instead of a map because `Name.from`
        // throws a MalformedInputFileException, making the lambda passed into
        // the map function have an uncaught MalformedInputFileException
        for (String firstLastNamePair : firstLastNamePairs) {
            names.add(Name.from(firstLastNamePair));
        }
        return names;
    }

    /**
     * @return an array of strings all representative of their respective Name
     *         objects
     */
    public ArrayList<String> toStringArray() {
        return this.stream()
                .map((name) -> name.toString())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * @param other the other instance to compare equality to
     * @return boolean if the two instances have the same values in order
     */
    public boolean equals(NameList other) {
        // fail if the lengths are not equal
        if (this.size() != other.size())
            return false;
        // fail if any of the elements are not equal
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(other.get(i)))
                return false;
        }
        return true;
    }
}
