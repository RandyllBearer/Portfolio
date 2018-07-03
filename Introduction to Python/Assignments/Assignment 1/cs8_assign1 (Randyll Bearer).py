# CS0008 - Introduction to Programming with Python
#
# Assignment 1 - Loop Exercises
# Points Available - 30
# Due - Wednesday, September 24th by 11:59PM
#
#
# Instructions: Write a loop to solve each question in the
# areas provided. All questions can be solved with either
# a for or while loop.
#
# Some variable names and code have been provided as
# inspiration; you do not need to use what I have provided
# EXCEPT the output strings must match.
#


#       Randyll Bearer
#       Your Pitt email: rlb97@pitt.edu
#


# Multiplication (5 points)
# --------------
# Use a loop to compute the product of 5 and 8. If you're
# unsure how to start, think about how multiplication and
# addition are related


#
#
number = 5
number2 = number + 5

print('This program will multiply 5*8 by using a addition loop.')
print()

while number != 40:
    print(number, "+ 5 =",number2)
    number = number + 5
    number2 = number + 5
print()
#
#


# Exponentiation (5 points)
# --------------
# Use a loop to compute the value of 2 raised to the fourth
# power (2^4). If you're unsure how to start remember the
# relationship between exponents and multiplication is
# similar to multiplication and addition


#
#
number = 2
number2 = number * number

print("This program will raise 2^4 by multiplying 2 by itself 4 times.")
print()

while number != 16:
    print(number, "* 2 =", number2)
    number = number * number
    number2 = number * number
print()
#
#



# Count the letter (10 points)
# ----------------
# Ask the user to enter a sentence. Then ask the user to
# enter a letter. Count how many times the letter appears
# in the sentence

#
#
string = input("Please enter a sentence for which you would like to parse for a specific character.  ")
specified_character = input("Please enter character you wish to count.  ")
character_count = 0

for character in string:
    if character == specified_character:
        character_count = character_count + 1

character_count = str(character_count)
print('This character occurs ' + character_count + ' times.')
print()
#
#

# Suffix tester (10 points)
# -------------
# Ask the user to enter a word. Then ask the user to enter
# a suffix (like -ing, -ly, etc). If the word ends with the
# given suffix print "They're a match!" once. Otherwise
# print "No match" once

### Word of Warning, I did not find the most efficient way to test for a suffix, in fact its pretty
### inefficient, sorry about that.  It works though, so that's nice.

#
#
string = input("Please enter a word you would like to check for a specific suffix.  ")
suffix_string = input("Please enter a suffix (e.x. 'ing' without the 's).  ")
i = 0
flag_wrong = 0    ### Alerts, if the loop is true, both flag_wrong and flag_right will be = 1 ###
flag_right = 0

if len(suffix_string) == 3:
    for suffix in string:
        if suffix == suffix_string[i:i+1]:
            i = i+1
            for suffix2 in string:
                if suffix2 == suffix_string[i:i+1]:                 
                    i = i + 1
                    for suffix3 in string:
                        if suffix3 == suffix_string[i:i+1]:         
                            flag_right = flag_right + 1
                            flag_wrong = flag_wrong + 1
elif len(suffix_string) == 2:
    for suffix in string:
         if suffix == suffix_string[i:i+1]:
            i = i + 1
            for suffix2 in string:
                 if suffix2 == suffix_string[i:i+1]:
                    flag_right = flag_right + 1
                    flag_wrong = flag_wrong + 1
elif len(suffix_string) == 4:
    for suffix in string:
        if suffix == suffix_string[i:i+1]:
            i = i + 1
            for suffix2 in string:
                if suffix2 == suffix_string[i:i+1]:
                    i = i + 1
                    for suffix3 in string:
                         if suffix3 == suffix_string[i:i+1]:
                             i = i + 1
                             for suffix4 in string:
                                 if suffix4 == suffix_string[i:i+1]:
                                     flag_right = flag_right + 1
                                     flag_wrong = flag_wrong + 1
elif len(suffix_string) == 5:
    for suffix in string:
        if suffix == suffix_string[i:i+1]:
            i = i + 1
            for suffix2 in string:
                if suffix2 == suffix_string[i:i+1]:
                    i = i + 1
                    for suffix3 in string:
                         if suffix3 == suffix_string[i:i+1]:
                             i = i + 1
                             for suffix4 in string:
                                 if suffix4 == suffix_string[i:i+1]:
                                     i = i + 1
                                     for suffix5 in string:
                                         if suffix5 == suffix_string[i:i+1]:
                                             flag_right = flag_right + 1
                                             flag_wrong = flag_wrong + 1
elif len(suffix_string) == 6:
    for suffix in string:
        if suffix == suffix_string[i:i+1]:
            i = i + 1
            for suffix2 in string:
                if suffix2 == suffix_string[i:i+1]:
                    i = i + 1
                    for suffix3 in string:
                         if suffix3 == suffix_string[i:i+1]:
                             i = i + 1
                             for suffix4 in string:
                                 if suffix4 == suffix_string[i:i+1]:
                                     i = i + 1
                                     for suffix5 in string:
                                         if suffix5 == suffix_string[i:i+1]:
                                             i = i + 1
                                             for suffix6 in string:
                                                 if suffix6 == suffix_string[i:i+1]:
                                                     flag_right = flag_right + 1
                                                     flag_wrong = flag_wrong + 1
elif len(suffix_string) == 7:
    for suffix in string:
        if suffix == suffix_string[i:i+1]:
            i = i + 1
            for suffix2 in string:
                if suffix2 == suffix_string[i:i+1]:
                    i = i + 1
                    for suffix3 in string:
                         if suffix3 == suffix_string[i:i+1]:
                             i = i + 1
                             for suffix4 in string:
                                 if suffix4 == suffix_string[i:i+1]:
                                     i = i + 1
                                     for suffix5 in string:
                                         if suffix5 == suffix_string[i:i+1]:
                                             i = i + 1
                                             for suffix6 in string:
                                                 if suffix6 == suffix_string[i:i+1]:
                                                     i = i + 1
                                                     for suffix7 in string:
                                                         if suffix7 == suffix_string[i:i+1]:
                                                             flag_right = flag_right + 1
                                                             flag_wrong = flag_wrong + 1


print()

if flag_wrong == 0:
    print("No match.")

if flag_right == 1:
    print("They're a match!")
        
print()
#
#
