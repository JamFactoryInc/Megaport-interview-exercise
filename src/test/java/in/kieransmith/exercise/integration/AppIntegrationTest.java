package in.kieransmith.exercise.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.ArrayList;

import in.kieransmith.exercise.FileHandler;
import in.kieransmith.exercise.NameList;
import in.kieransmith.exercise.Name;
import in.kieransmith.exercise.MalformedInputException;

import in.kieransmith.exercise.App;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppIntegrationTest {

        private static final InputStream stdIn = System.in;
        private static final PrintStream stdOut = System.out;
        private static ByteArrayOutputStream outStream;
        private static ByteArrayInputStream inStream;

        private static FileHandler fileHandler;

        @BeforeClass
        public static void before() {
                fileHandler = new FileHandler();
        }

        public void before(String input) {
                outStream = new ByteArrayOutputStream();
                inStream = new ByteArrayInputStream(input.getBytes());
                System.setIn(inStream);
                System.setOut(new PrintStream(outStream));
        }

        @Test
        public void NameListFromFile_ExpectOrderedNameList() throws IOException, MalformedInputException {
                // Arrange
                Path inputPath = fileHandler.getUniquePath();
                NameList desiredNameList = new NameList();
                desiredNameList.add(new Name("BAKER", "THEODORE"));
                desiredNameList.add(new Name("KENT", "MADISON"));
                desiredNameList.add(new Name("SMITH", "ANDREW"));
                desiredNameList.add(new Name("SMITH", "FREDRICK"));

                // Act
                fileHandler.write(inputPath, new ArrayList<String>(
                                Arrays.asList("BAKER, THEODORE", "SMITH, ANDREW", "KENT, MADISON", "SMITH, FREDRICK")));
                NameList actual = NameList.from(fileHandler.read(inputPath)).sort();

                // Assert
                assertTrue(String.format("%s should equal %s", desiredNameList.toString(), actual.toString()),
                                desiredNameList.equals(actual));

                // Clean
                inputPath.toFile().delete();
        }

        @Test
        public void RunBulkFileSort_ExpectSortedFiles() throws IOException {
                // Arrange
                Path filePath1 = fileHandler.getUniquePath();
                Path filePath2 = fileHandler.getUniquePath();
                ArrayList<String> unsortedNames = new ArrayList<String>(
                                Arrays.asList("BAKER, THEODORE", "SMITH, ANDREW", "KENT, MADISON", "SMITH, FREDRICK"));
                ArrayList<String> desiredNames = new ArrayList<String>(
                                Arrays.asList("BAKER, THEODORE", "KENT, MADISON", "SMITH, ANDREW", "SMITH, FREDRICK"));
                Path sortedFile1 = Paths.get(String.format("%s-sorted.txt",
                                fileHandler.stripExtension(filePath1.toString())));
                Path sortedFile2 = Paths.get(String.format("%s-sorted.txt",
                                fileHandler.stripExtension(filePath2.toString())));
                fileHandler.write(filePath1, unsortedNames);
                fileHandler.write(filePath2, unsortedNames);

                // Act
                App.main(new String[] { filePath1.toString(), filePath2.toString() });

                // Assert
                ArrayList<String> actual1 = fileHandler.read(sortedFile1);
                assertTrue(String.format("%s should equal %s", unsortedNames.toString(),
                                actual1.toString()),
                                actual1.equals(desiredNames));
                ArrayList<String> actual2 = fileHandler.read(sortedFile2);
                assertTrue(String.format("%s should equal %s", unsortedNames.toString(),
                                actual2.toString()),
                                actual2.equals(desiredNames));

                // Clean
                filePath1.toFile().delete();
                filePath2.toFile().delete();
                sortedFile1.toFile().delete();
                sortedFile2.toFile().delete();
        }

        @Test
        public void ReadFileWriteSortedFile_ExpectSortedFile() throws IOException, MalformedInputException {
                // Arrange
                Path filePath = fileHandler.getUniquePath();
                ArrayList<String> unsortedNames = new ArrayList<String>(
                                Arrays.asList("BAKER, THEODORE", "SMITH, ANDREW", "KENT, MADISON", "SMITH, FREDRICK"));
                ArrayList<String> desiredNames = new ArrayList<String>(
                                Arrays.asList("BAKER, THEODORE", "KENT, MADISON", "SMITH, ANDREW", "SMITH, FREDRICK"));

                fileHandler.write(filePath, unsortedNames);

                // Act
                NameList names = NameList.from(fileHandler.read(filePath)).sort();
                fileHandler.write(filePath, names.toStringArray());

                ArrayList<String> actualNames = fileHandler.read(filePath);

                // Assert
                assertTrue(String.format("%s should equal %s", actualNames.toString(), desiredNames.toString()),
                                actualNames.equals(desiredNames));

                // Clean
                filePath.toFile().delete();
        }

        @Test
        public void HandleNonExistentFile_ExpectCorrectPrompt() throws IOException, MalformedInputException {
                before("exit");
                // Arrange
                String filePath = fileHandler.getUniquePath().toString();
                App.main(new String[] { filePath });

                // Assert
                assertEquals(String.format(
                                "Could not find '%s'. Please enter a valid path:", filePath),
                                outStream.toString().trim());

        }

        @Test
        public void HandleMalformedFile_ExpectCorrectPrompt() throws IOException, MalformedInputException {
                before("exit");
                // Arrange
                Path filePath = fileHandler.getUniquePath();
                fileHandler.write(filePath, new ArrayList<String>(Arrays.asList("A,B,C")));
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
                Path filePath = fileHandler.getUniquePath();
                fileHandler.write(filePath, new ArrayList<String>(Arrays.asList("A,B")));
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
                Path filePath1 = fileHandler.getUniquePath();
                Path filePath2 = fileHandler.getUniquePath();
                App.main(new String[] { filePath1.toString(), filePath2.toString() });

                // Assert
                assertEquals(String.format("Error caught while sorting '%s'. Skipped.",
                                fileHandler.modifyPathName(filePath1, "%s-sorted.txt"))
                                + System.lineSeparator() +
                                String.format("Error caught while sorting '%s'. Skipped.",
                                                fileHandler.modifyPathName(filePath2, "%s-sorted.txt")),
                                outStream.toString().trim());
        }

        @After
        public void after() {
                System.setOut(stdOut);
                System.setIn(stdIn);
        }
}
