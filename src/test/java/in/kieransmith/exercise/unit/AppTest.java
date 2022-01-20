package in.kieransmith.exercise.unit;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.After;
import org.junit.Test;

import in.kieransmith.exercise.App;
import in.kieransmith.exercise.FileIO;
import in.kieransmith.exercise.MalformedInputException;

public class AppTest {

    public static final InputStream stdIn = System.in;
    public static final PrintStream stdOut = System.out;
    public static ByteArrayOutputStream outStream;
    public static ByteArrayInputStream inStream;

    /**
     * Helps with the testing of System.in/out
     */
    public void before(String input) {
        outStream = new ByteArrayOutputStream();
        inStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inStream);
        System.setOut(new PrintStream(outStream));
    }

    @Test
    public void HandleNonExistentFile_ExpectCorrectPrompt() throws IOException, MalformedInputException {
        before("exit");
        // Arrange
        String filePath = FileIO.getUniquePath().toString();
        App.main(new String[] { filePath });

        // Assert
        assertEquals(String.format(
                "Could not find '%s'. Please enter a valid path:", filePath), outStream.toString().trim());

    }

    @Test
    public void HandleMalformedFile_ExpectCorrectPrompt() throws IOException, MalformedInputException {
        before("exit");
        // Arrange
        Path filePath = FileIO.getUniquePath();
        FileIO.write(filePath, new ArrayList<String>(Arrays.asList("A,B,C")));
        App.main(new String[] { filePath.toString() });

        // Assert
        assertEquals(String.format(
                "Line '%s' in '%s' is not formatted correctly. Please enter a valid path:", "A,B,C",
                filePath.toString()),
                outStream.toString().trim());

        // Clean
        filePath.toFile().delete();
    }

    @Test
    public void HandleValidFile_ExpectCorrectPrompt() throws IOException, MalformedInputException {
        before("exit");
        // Arrange
        Path filePath = FileIO.getUniquePath();
        FileIO.write(filePath, new ArrayList<String>(Arrays.asList("A,B")));
        App.main(new String[] { filePath.toString() });

        // Assert
        assertEquals(String.format(
                "Finished: created %s", App.outputFilePath.toString()),
                outStream.toString().trim());

        // Clean
        filePath.toFile().delete();
        App.outputFilePath.toFile().delete();
    }

    @Test
    public void HandleBulkFiles_ExpectCorrectOutput() throws IOException, MalformedInputException {
        before("exit");
        // Arrange
        Path filePath1 = FileIO.getUniquePath();
        Path filePath2 = FileIO.getUniquePath();
        App.main(new String[] { filePath1.toString(), filePath2.toString() });

        // Assert

        assertEquals(String.format("Error caught while sorting %s. Skipped.",
                FileIO.modifyPathName(filePath1, "%s-sorted.txt"))
                + System.lineSeparator() +
                String.format("Error caught while sorting %s. Skipped.",
                        FileIO.modifyPathName(filePath2, "%s-sorted.txt")),
                outStream.toString().trim());
    }

    @Test
    public void PromptForInput_ExpectCorrectPrompt() {
        before("input message");
        // Arrange
        String userInput = App.promptForInput("prompt %s", new String[] { "message" }, new Scanner(System.in));

        assertEquals(
                "prompt message",
                outStream.toString().trim());
        assertEquals(userInput, "input message");
    }

    @After
    public void after() {
        System.setOut(stdOut);
        System.setIn(stdIn);
    }

}
