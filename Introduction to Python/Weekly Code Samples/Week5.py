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

def get_points():
    score = -1
    while score < 0 or score > 100:
        score = input()
        score = int( score )
    return score


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



print( "Week 5 Code Sample" )

print( "Grade calculator" )

print( "Please enter the maximum points possible on the assignment: ", end="" )

points_possible = get_points()

print( "Please enter how many points were earned: ", end="" )

earned_points = get_points()

grade = get_grade( earned_points / points_possible )

print( "The letter grade is ", grade, sep = "" )
