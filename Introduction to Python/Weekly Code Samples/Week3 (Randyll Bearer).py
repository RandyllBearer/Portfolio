

# Randyll Bearer, Intro to Python, Week 3 Code Sample


# dew39@pitt.edu
# Week 3 Sample Code
# 9/4/2014

# Please briefly explain what you think each part of this code does
# We have discussed some of these concepts, but some should be new
#
# The '#' character precedes a comment
#
# Once you have written your comments submit this file through Courseweb

# The below line is telling Python to output/display the string "Week 3 Code Sample.

print( "Week 3 Code Sample" )

# PI = A constant which has been defined to be '3.14159265358979, this constant can later be used in the program.

PI = 3.14159265358979

# The below line is telling Python to output the following.  "Pi: 3.1415926535897" and then the format keyword, which is telling Python to round Pi to the 5th decimal place.
# and /n, which is telling Python to start a new line.

print( "Pi:", PI, "or", format( PI, '0.5f' ), "\n" )

# These lines are telling Python to ask the user the diameter and depth of their pie pan in inches.  /n = start a new line which will show the Diameter and another line
# which will show Depth.

pie_pan_diameter = float ( \
    input( "How wide is your pie pan (in inches)?\nDiameter: " ) )

pie_pan_depth = float( \
    input( "How deep is your pie pan (in inches)?\nDepth: " ) )

# Python will take the user inputed diameter and divide it by 2, which will then equal a new variable pie_pan_radius.

pie_pan_radius = pie_pan_diameter / 2

#pie_pan_volume = PI * pie_pan_radius ** 2 * pie_pan_depth  - Self explanatory, rounding the pie pan volume to two decimal places.

pie_pan_volume = format( pie_pan_volume, "0.2f" )

# Python displays the string "You can make ___ cubic inches of pie" where the '___' is equal to the variable 'pie_pan_volume', which was defined
# earlier in the previous string.

print( 'You can make', pie_pan_volume, 'cubic inches of pie!' )
