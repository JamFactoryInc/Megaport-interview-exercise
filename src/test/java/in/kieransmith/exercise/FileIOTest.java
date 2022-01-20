package in.kieransmith.exercise;

import static org.junit.Assert.assertEquals;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.FixMethodOrder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import java.nio.file.Path;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FileIOTest {

    @Test
    public void ReadFile_ExpectKnownContent() throws IOException {
        // Arrange
        ArrayList<String> desiredContent = new ArrayList<String>(Arrays.asList("Line1", "Line2", "Line3"));
        Path writtenFilePath = Paths.get(System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + UUID.randomUUID().toString()
                + ".txt");

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
    public void ReadFileContainingSpecialChars_ExpectKnownContent() throws IOException {
        // Arrange
        String evilUTFString = "Ja߭򧗂񀜡󅟘贷􌮺񨄳󳠴Uڎٙȍ񞋛󢓬녫𬀍鱸󏓖ɞ/繙򒬳򍯢#烃ґ2󵋜x稲T󌤕Ÿj󘖠𮯘ˢƶ󳷦򠉘f";
        ArrayList<String> desiredContent = new ArrayList<String>(Arrays.asList(evilUTFString));
        Path writtenFilePath = Paths.get(System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + UUID.randomUUID().toString()
                + ".txt");

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
        Path writtenFilePath = Paths.get(System.getProperty("user.dir")
                + System.getProperty("file.separator")
                + UUID.randomUUID().toString()
                + ".txt");

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
