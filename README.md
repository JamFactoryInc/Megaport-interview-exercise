# Megaport Interview Exercise
interview takehome test submission

## How to run

### Application
Open the terminal and navigate to the `Megaport-interview-exercise` root directory

run the command `java -jar App.jar`

#### Usage
if the program is run with no args it will prompt the user for an input file path, but args can be included for single- or multiple-file processing.

#### Single argument

`java -jar App.jar C:\Users\jam\Downloads\names.txt`

This will try to sort the names in `names.txt` and will output to C:\Users\jam\Downloads\names-sorted.txt` if it succeeds
Otherwise, it will communicate what went wrong and prompt for a different path

#### Multiple arguments

`java -jar App.jar C:\Users\jam\Downloads\names1.txt C:\Users\jam\Downloads\names2.txt`

This will skip failed attempts, and write to `C:\Users\jam\Downloads\names1-sorted.txt` and `C:\Users\jam\Downloads\names2-sorted.txt` assuming they succeed


### Tests

Running `mvn test` in the root directory will run all unit and integration tests
