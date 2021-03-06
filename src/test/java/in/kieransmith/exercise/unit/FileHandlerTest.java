package in.kieransmith.exercise.unit;

import static org.junit.Assert.assertEquals;

import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.nio.file.Path;
import java.nio.file.Paths;

import in.kieransmith.exercise.FileHandler;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
// This is more extensively tested in AppTests.java due to the nature of FileIO
// needing its own methods for validation
public class FileHandlerTest {
    static FileHandler fileHandler;

    @BeforeClass
    public static void before() {
        fileHandler = new FileHandler();
    }

    @Test
    public void ReadFile_ExpectKnownContent() throws IOException {
        // Arrange
        ArrayList<String> desiredContent = new ArrayList<String>(Arrays.asList("Line1", "Line2", "Line3"));
        Path writtenFilePath = fileHandler.getUniquePath();
        // verify(mockFileHandler).getUniquePath();

        // Create unique file
        fileHandler.write(writtenFilePath, desiredContent);

        // Act
        ArrayList<String> resultantContent = fileHandler.read(writtenFilePath);

        // Assert
        assertEquals(desiredContent, resultantContent);

        // Clean
        writtenFilePath.toFile().delete();
    }

    @Test
    public void ReadNonExistentFile_ExpectFileNotFound() {
        // Arrange
        Class<? extends Exception> caughtException = Exception.class;
        Path writtenFilePath = fileHandler.getUniquePath();

        // Act
        try {
            fileHandler.read(writtenFilePath);
        } catch (FileNotFoundException e) {
            caughtException = FileNotFoundException.class;
        }

        // Assert
        assertEquals(FileNotFoundException.class, caughtException);
    }

    @Test
    public void ModifyPath_ExpectModifiedPath() {
        // Arrange
        Path basePath = Paths.get("test.txt");

        // Act
        Path resultPath = fileHandler.modifyPathName(basePath, "%s-test.test");

        // Assert
        assertEquals("test-test.test", resultPath.toString());
    }

}
