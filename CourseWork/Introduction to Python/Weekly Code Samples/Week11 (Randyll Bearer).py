# dew39@pitt.edu
# Week 11 Sample Code
# 11/05/2014

# Please briefly explain what you think each part of this code does
# We have discussed some of these concepts, but some should be new
#
# The '#' character precedes a comment
#
# Once you have written your comments submit this file through Courseweb
#
# Due 11/12/2014 before class

class RowBoat:                      ### Creates the class "RowBoat:"

    def __init__( self, x, y ):     ### Allows for the object attributes "x,y,dir"
        self.__x = x                ### Self is a placeholder for whatever reference you will be using
        self.__y = y                ### .__ means that "y" will be exclusive to this class or function? [non-global]
        self.__dir = 'North'

    def turnLeft( self ):           ### A function allowing the boat to turn left
        if self.__dir == 'North':   ### Simpl if/else statement. Checks the current direction, changes it to whichever direction allows it to move left
            self.__dir = 'West'
        elif self.__dir == 'West':
            self.__dir = 'South'
        elif self.__dir == 'South':
            self.__dir = 'East'
        else:
            self.__dir = 'North'

    def turnRight( self ):          ### Same thing as turnLeft, only this function changes its direction towards the right
        if self.__dir == 'North':
            self.__dir = 'East'
        elif self.__dir == 'East':
            self.__dir = 'South'
        elif self.__dir == 'South':
            self.__dir = 'West'
        else:
            self.__dir = 'North'
    
    # +Y
    #  Y
    # -Y
    #   -X  X +X
    def row( self ):                ### If my presumption is correct, the program is using the range [-1,1] to dictate position
        if self.__dir == 'North':   ### if y = -1; y = -y.  -1 farthest left / farthest down.  +1 = farthest right / farthes up
            self.__y += 1
        elif self.__dir == 'South':
            self.__y -= 1
        elif self.__dir == 'East':
            self.__x += 1
        else:
            self.__x -= 1

    def position( self ):          ### Returns the boat's basic coordinates.
        return ( self.__x, self.__y )

def main( ):      ### "body" of the program.  Turn left, row a bit, turn left again [facing backwards], row some, turn right, turn right again [facing frontwards], then turn right once more
    boat = RowBoat( 0, 0 )
    boat.turnLeft( )
    boat.row( )
    boat.row( )
    boat.turnLeft( )
    boat.row( )
    boat.turnRight( )
    boat.turnRight( )
    boat.turnRight( )
    boat.row( )
    print( boat.position( ) )

main( )
