path = "C:\\Users\\Crassus\\Desktop\\top_ten_books.txt"

file = open( path, "rb" )

for line in file:
    print( line )

file.close()


