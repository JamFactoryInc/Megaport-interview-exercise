package in.kieransmith.exercise.unit;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.FixMethodOrder;

import in.kieransmith.exercise.Name;
import in.kieransmith.exercise.MalformedInputException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NameTest {
    @Test
    public void CompareFirstName_ExpectNegOne() {
        // Arrange
        Name name1 = new Name("A", "B");
        Name name2 = new Name("B", "B");

        // Act
        int result = name1.compareTo(name2);

        // Assert
        assertTrue(result == -1);
    }

    @Test
    public void CompareLastName_ExpectNegOne() {
        // Arrange
        Name name1 = new Name("A", "B");
        Name name2 = new Name("A", "C");

        // Act
        int result = name1.compareTo(name2);

        // Assert
        assertTrue(result == -1);
    }

    @Test
    public void CompareFirstName_ExpectOne() {
        // Arrange
        Name name1 = new Name("B", "B");
        Name name2 = new Name("A", "B");

        // Act
        int result = name1.compareTo(name2);

        // Assert
        assertTrue(result == 1);
    }

    @Test
    public void CompareLastName_ExpectOne() {
        // Arrange
        Name name1 = new Name("A", "C");
        Name name2 = new Name("A", "B");

        // Act
        int result = name1.compareTo(name2);

        // Assert
        assertTrue(result == 1);
    }

    @Test
    // This is first/last name agnostic, so only one test is needed
    public void CompareName_ExpectZero() {
        // Arrange
        Name name1 = new Name("A", "B");
        Name name2 = new Name("A", "B");

        // Act
        int result = name1.compareTo(name2);

        // Assert
        assertTrue(result == 0);
    }

    @Test
    public void CompareEquality_ExpectTrue() {
        // Arrange
        Name name1 = new Name("A", "B");
        Name name2 = new Name("A", "B");

        // Act
        boolean result = name1.equals(name2);

        // Assert
        assertTrue(result);
    }

    @Test
    public void CompareEqualitySameInstance_ExpectTrue() {
        // Arrange
        Name name1 = new Name("A", "B");
        Name name2 = name1;

        // Act
        boolean result = name1.equals(name2);

        // Assert
        assertTrue(result);
    }

    @Test
    public void CompareEqualityDifferentLastName_ExpectFalse() {
        // Arrange
        Name name1 = new Name("A", "B");
        Name name2 = new Name("A", "C");

        // Act
        boolean result = !name1.equals(name2);

        // Assert
        assertTrue(result);
    }

    @Test
    public void CompareEqualityDifferentFirstName_ExpectFalse() {
        // Arrange
        Name name1 = new Name("A", "B");
        Name name2 = new Name("B", "B");

        // Act
        boolean result = !name1.equals(name2);

        // Assert
        assertTrue(result);
    }

    @Test
    public void NameFromTooManyElements_ExpectMalformedInputException() {
        // Arrange
        Class<? extends Exception> caughtException = Exception.class;

        // Act
        try {
            Name.from("A,B,C");
        } catch (MalformedInputException e) {
            caughtException = MalformedInputException.class;
        }

        // Assert
        assertEquals(MalformedInputException.class, caughtException);
    }

    @Test
    public void NameFromNotEnoughElements_ExpectMalformedInputException() {
        // Arrange
        Class<? extends Exception> caughtException = Exception.class;

        // Act
        try {
            Name.from("A");
        } catch (MalformedInputException e) {
            caughtException = MalformedInputException.class;
        }

        // Assert
        assertEquals(MalformedInputException.class, caughtException);
    }

    @Test
    public void NameFromString_ExpectName() throws MalformedInputException {
        // Arrange
        Name name1 = new Name("A", "B");

        // Act
        Name name2 = Name.from("A,B");

        // Assert
        assertTrue(name1.equals(name2));
    }

    @Test
    public void NameToString_ExpectCorrectString() {
        // Assert
        assertEquals(new Name("A", "B").toString(), "A, B");
        assertEquals(new Name(" A ", " B ").toString(), "A, B");
    }

}
