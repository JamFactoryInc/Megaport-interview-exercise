package in.kieransmith.exercise.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import in.kieransmith.exercise.NameList;
import in.kieransmith.exercise.Name;
import in.kieransmith.exercise.MalformedInputException;

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
        listToSort.add(new Name("smith", "bob"));
        listToSort.add(new Name("SMITH", "ABBY"));
        listToSort.add(new Name("BERIMY", "JERIMY"));
        listToSort.add(new Name("BLACK", "JOHN JOHNSON"));
        NameList desiredResult = new NameList();
        desiredResult.add(new Name("BERIMY", "JERIMY"));
        desiredResult.add(new Name("BLACK", "JOHN JOHNSON"));
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

    @Test
    public void CompareDifferentLengthNameLists_ExpectFalse() throws MalformedInputException {
        // Arrange
        NameList nameList1 = NameList.from(new ArrayList<String>(
                Arrays.asList("BOB,SMITH", "DANIEL,JOHNSON", "JERIMY,BERIMY")));
        NameList nameList2 = NameList.from(new ArrayList<String>(
                Arrays.asList("BOB,SMITH", "DANIEL,JOHNSON")));

        // Assert
        assertFalse(nameList1.equals(nameList2));
    }

    @Test
    public void CompareIdenticalNameLists_ExpectTrue() throws MalformedInputException {
        // Arrange
        NameList nameList1 = NameList.from(new ArrayList<String>(
                Arrays.asList("BOB,SMITH", "DANIEL,JOHNSON", "JERIMY,BERIMY")));
        NameList nameList2 = NameList.from(new ArrayList<String>(
                Arrays.asList("BOB,SMITH", "DANIEL,JOHNSON", "JERIMY,BERIMY")));

        // Assert
        assertTrue(nameList1.equals(nameList2));
    }

    @Test
    public void CompareShuffledNameLists_ExpectFalse() throws MalformedInputException {
        // Arrange
        NameList nameList1 = NameList.from(new ArrayList<String>(
                Arrays.asList("BOB,SMITH", "DANIEL,JOHNSON", "JERIMY,BERIMY")));
        NameList nameList2 = NameList.from(new ArrayList<String>(
                Arrays.asList("DANIEL,JOHNSON", "BOB,SMITH", "JERIMY,BERIMY")));

        // Assert
        assertFalse(nameList1.equals(nameList2));
    }

    @Test
    public void CompareSameInstance_ExpectTrue() throws MalformedInputException {
        // Arrange
        NameList nameList1 = NameList.from(new ArrayList<String>(
                Arrays.asList("BOB,SMITH", "DANIEL,JOHNSON", "JERIMY,BERIMY")));
        NameList nameList2 = nameList1;

        // Assert
        assertTrue(nameList1.equals(nameList2));
    }

    @Test
    public void TestToStringArray_ExpectOrderedStringArray() throws MalformedInputException {
        // Arrange
        ArrayList<String> expected = new ArrayList<String>(
                Arrays.asList("BOB, SMITH", "DANIEL, JOHNSON", "JERIMY, BERIMY"));
        NameList nameList = NameList.from(expected);

        // Act
        ArrayList<String> result = nameList.toStringArray();

        // Assert
        assertTrue(expected.equals(result));
    }
}
