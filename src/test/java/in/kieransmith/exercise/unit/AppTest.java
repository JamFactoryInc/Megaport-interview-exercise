package in.kieransmith.exercise.unit;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import in.kieransmith.exercise.App;

public class AppTest {

    private static final InputStream stdIn = System.in;
    private static final PrintStream stdOut = System.out;
    private static ByteArrayOutputStream outStream;
    private static ByteArrayInputStream inStream;

    private static App app;

    @BeforeClass
    public static void beforeAll() {
        app = new App();
    }

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
    public void PromptForInput_ExpectCorrectPrompt() {
        before("input message");
        // Arrange
        String userInput = app.promptForInput("prompt %s", new String[] { "message" }, new Scanner(System.in));

        // Assert
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
