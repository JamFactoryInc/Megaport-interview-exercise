# Megaport Interview Exercise
Programming Exercise - Java / Kotlin - Junior
Software Engineer

## How to run

### Application

> #### Running the App
> Open the terminal and navigate to the `Megaport-interview-exercise` root directory.
>
> Run the command `java -jar App.jar`.
>
> #### Usage
> 
> Arguments can be included for single- or multiple-file processing, but they are not necessary.
> 
> If the program is run with no arguments it will prompt the user for an input file path.
> 
> `exit` can be input at any point to quit the application.
>
>> #### Single Argument
>>
>> `java -jar App.jar C:\Users\jam\Downloads\names.txt`
>>
>> This command will try to sort the names in `names.txt` and will output to `C:\Users\jam\Downloads\names-sorted.txt` if it succeeds.
>> 
>> Otherwise, it will display one of three possible problems and prompt for a different path.
>>
>> #### Multiple Arguments
>>
>> `java -jar App.jar C:\Users\jam\Downloads\names1.txt C:\Users\jam\Downloads\names2.txt`
>>
>> This command will skip failed attempts, and write to `C:\Users\jam\Downloads\names1-sorted.txt` and `C:\Users\jam\Downloads\names2-sorted.txt` assuming they succeed.


### Tests

> Running `mvn test` in the root directory will run all unit and integration tests.
