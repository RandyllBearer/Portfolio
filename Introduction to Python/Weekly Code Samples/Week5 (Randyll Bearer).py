# dew39@pitt.edu
# Week 5 Sample Code
# 9/18/2014

# Please briefly explain what you think each part of this code does
# We have discussed some of these concepts, but some should be new
#
# The '#' character precedes a comment
#
# Once you have written your comments submit this file through Courseweb
#
# Due 9/24/2014 before class

# Defines the function get_points
# This function will ask for the user to input the points possible, convert it into an integer, and then return the value
def get_points():
    score = -1
    while score < 0 or score > 100:
        score = input()
        score = int( score )
    return score

# Defines the function get_grade
# This function will convert a student's score into a percentage then return a corresponding value of "A", "B", Etc...
def get_grade( percentage ):
    if percentage >= .90:
        return "A"
    elif percentage >= .80:
        return "B"
    elif percentage >= .70:
        return "C"
    elif percentage >= .60:
        return "D"
    else:
        return "F"


# Next couple lines simply print out their respective strings
print( "Week 5 Code Sample" )

print( "Grade calculator" )

print( "Please enter the maximum points possible on the assignment: ", end="" )

# The variable points_possible and earned_points are being made equal to get_points so its values can be used outside of the function
points_possible = get_points()

print( "Please enter how many points were earned: ", end="" )

earned_points = get_points()

# Quick calculation to find total grade
grade = get_grade( earned_points / points_possible )

# Displays the final result
print( "The letter grade is ", grade, sep = "" )
