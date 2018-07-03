# dew39@pitt.edu
# Week 4 Sample Code
# 9/11/2014

# Please briefly explain what you think each part of this code does
# We have discussed some of these concepts, but some should be new
#
# The '#' character precedes a comment
#
# Once you have written your comments submit this file through Courseweb
#
# Due 9/17/2014 before class


#This line will display "Week 4 Code Sample"
print( "Week 4 Code Sample" )

#I would assume that this will leave a blank line
print( )

# This line defines anything in the next statement which is in "" to be labelled as text
text = ""

#These lines will determine whether the user inputs "Start" or "stop"
# For as long as the user does not input start ot stop, the text "Enter 'start' to being or 'stop' to exit: "
while ( text != "start" ) and ( text != "stop" ):
    text = input( "Enter 'start' to being or 'stop' to exit: " )

# If the user inputted "start", these lines will display "Starting countdown!", which will then display a countdown from 10 to 1, ending in a display of "Happy New Year!"
if text == "start":
    print( "Starting countdown!" )
    
    for count in [10, 9, 8, 7, 6, 5, 4, 3, 2, 1]:
        print( count, "...", sep = '' )

    print( "Happy New Year!" )

# If the user did not input "start", the program will display "Exiting..."
else:
    text = "Exiting..."

# As long as the user-inputted amount of characters is > 0, it will print it
    while len( text ) > 0:
        print( text )
        text = text[1:]
