path = "C:\\Users\\Crassus\\Desktop\\hardware.txt"

example_file = open( path, "r" )

#-------------------------------------------------------------------------------
file_data = []

for line in example_file:
    file_data.append( line )

print( file_data )
example_file.seek( 0, 0 )   # Resets the file pointer ("rewinds" the tape)

#-------------------------------------------------------------------------------
file_data = []

while True:
    line = example_file.readline()
    if line == "":
        break
    file_data.append( line )

print( file_data )
example_file.seek( 0, 0 )

#-------------------------------------------------------------------------------
file_data = []
file_data = example_file.readlines()

print( file_data )

#-------------------------------------------------------------------------------
example_file.close()
