# Tietorakenteet 2017 course work
Course work for the course Tietorakenteet (= Datastructures in English) of autumn 2017 and early 2018.

##About
This work was about making a program that reads integers from two separate text files and the combines them in the manner of logical operators OR, AND and XOR. The combination process is done using a self-made hashtable and the output of each logical operation is printed into its own corresponding file.

The output files
- or.txt
    - Result of logical operation OR
    - First column has each unique number
    - Second column tells how many times each number occurred in the input.
- and.txt
    - Result of logical operation AND
    - First column has each unique number
    - Second column tells which line the number was originally on (first occurrence) in the first input file.
- xor.txt
    - Result of logical operation XOR ("exclusive or")
    - First column has each unique number
    - Second column specifies which input file the number occurred in (1 for first input, 2 for second)