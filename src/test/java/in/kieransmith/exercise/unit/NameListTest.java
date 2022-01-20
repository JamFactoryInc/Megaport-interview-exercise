package in.kieransmith.exercise.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import in.kieransmith.exercise.NameList;
import in.kieransmith.exercise.Name;
import in.kieransmith.exercise.MalformedInputException;

/**
 * Unit test for simple App.
 */
public class NameListTest {

    @Test
    public void NameListFromStringArray_ExpectOrderedNameList() throws MalformedInputException {
        // Arrange
        ArrayList<String> firstLastNamePairs = new ArrayList<String>(
                Arrays.asList("BOB,SMITH", "DANIEL,JOHNSON", "JERIMY,BERIMY"));
        NameList desiredResult = new NameList();
        desiredResult.add(new Name("BOB", "SMITH"));
        desiredResult.add(new Name("DANIEL", "JOHNSON"));
        desiredResult.add(new Name("JERIMY", "BERIMY"));

        // Act
        NameList actual = NameList.from(firstLastNamePairs);

        // Assert
        assertTrue(desiredResult.equals(actual));
    }

    @Test
    public void PrivodeUnsortedNameList_ExpectSortedNameList() throws MalformedInputException {
        // Arrange
        NameList listToSort = new NameList();
        listToSort.add(new Name("SMITH", "BOB"));
        listToSort.add(new Name("SMITH", "ABBY"));
        listToSort.add(new Name("BERIMY", "JERIMY"));
        listToSort.add(new Name("BLACK", "JOHN"));
        NameList desiredResult = new NameList();
        desiredResult.add(new Name("BERIMY", "JERIMY"));
        desiredResult.add(new Name("BLACK", "JOHN"));
        desiredResult.add(new Name("SMITH", "ABBY"));
        desiredResult.add(new Name("SMITH", "BOB"));

        // Act
        listToSort.sort();

        // Assert
        assertTrue(desiredResult.equals(listToSort));
    }

    @Test
    public void NameListFromMalformedInput_ExpectMalformedInputException() {
        // Arrange
        Class<? extends Exception> caughtException = Exception.class;

        // Act
        try {
            NameList.from(new ArrayList<String>(
                    Arrays.asList("A,B,C")));
        } catch (MalformedInputException e) {
            caughtException = MalformedInputException.class;
        }

        // Assert
        assertEquals(MalformedInputException.class, caughtException);
    }
}
