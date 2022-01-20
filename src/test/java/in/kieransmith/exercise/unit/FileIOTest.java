package in.kieransmith.exercise.unit;

import static org.junit.Assert.assertEquals;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.FixMethodOrder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.nio.file.Path;

import in.kieransmith.exercise.FileIO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
// This is more extensively tested in AppTests.java due to the nature of FileIO
// needing its own methods for validation
public class FileIOTest {

    @Test
    public void ReadFile_ExpectKnownContent() throws IOException {
        // Arrange
        ArrayList<String> desiredContent = new ArrayList<String>(Arrays.asList("Line1", "Line2", "Line3"));
        Path writtenFilePath = FileIO.getUniquePath();

        // Create unique file
        FileIO.write(writtenFilePath, desiredContent);

        // Act
        ArrayList<String> resultantContent = FileIO.read(writtenFilePath);

        // Assert
        assertEquals(desiredContent, resultantContent);

        // Clean
        writtenFilePath.toFile().delete();
    }

    @Test
    public void ReadNonExistentFile_ExpectFileNotFound() {
        // Arrange
        Class<? extends Exception> caughtException = Exception.class;
        Path writtenFilePath = FileIO.getUniquePath();

        // Act
        try {
            FileIO.read(writtenFilePath);
        } catch (FileNotFoundException e) {
            caughtException = FileNotFoundException.class;
        }

        // Assert
        assertEquals(FileNotFoundException.class, caughtException);
    }

}
