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

while True:         ### An infinite loop unless stopped by the keyword "break"
    try:            ### Allows the program to run exceptions
        print( 'Specify path to check or \'exit\' to leave the program' )   ### Asking the user to input their specified file path
        path = input( )
        if path == 'exit' or path == 'Exit':        ### Checking if the user wishes to exit the program
            break
        file = open( path, 'r' )                    ### Opening the file designated by earlier path=input(), allows the file to be read only
        file.close()                                ### Closes the file
        print( 'File found!' )                      ### Previous 2 lines were to check if the file path was valid
    except:                         ### If there is an error, this except: will act as a catch all
        print( 'No file found!' )   ### If there was an error, it will print "No file found."
