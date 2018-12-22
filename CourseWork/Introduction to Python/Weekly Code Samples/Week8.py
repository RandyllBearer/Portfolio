# dew39@pitt.edu
# Week 8 Sample Code
# 10/15/2014

# Please briefly explain what you think each part of this code does
# We have discussed some of these concepts, but some should be new
#
# The '#' character precedes a comment
#
# Once you have written your comments submit this file through Courseweb
#
# Due 10/22/2014 before class

while True:
    try:
        print( 'Specify path to check or \'exit\' to leave the program' )
        path = input( )
        if path == 'exit' or path == 'Exit':
            break
        file = open( path, 'r' )
        file.close()
        print( 'File found!' )
    except:
        print( 'No file found!' )
