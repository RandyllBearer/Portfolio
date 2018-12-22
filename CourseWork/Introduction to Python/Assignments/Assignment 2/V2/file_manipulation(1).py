data = []
data_file = ""
data_list = []
hardware_list = []
hardware_string = ""
id_list = []
def get_file_data_list( filename ):
    global data
    global data_file
    data_file = open( filename, 'r' )
    data = data_file.readlines()
    data_file.close()
    return data

def display_records( data_list ):
    global data
    global hardware_list
    global hardware_string
    for i in range(len(data)):
        hardware_string = data[i]
        hardware_list.append(hardware_string)
        
        
    print(hardware_list)

    #
    # Each list entry is a string
    # Data in the string follows this format:
    # ID_NUM = 7 characters
    # ITEM_DESCRIPTION = 15 characters
    # ITEM_MANUFACTURER = 10 characters
    # ITEM_PRICE = 6 characters
    #
    # Print the ITEM_ID followed by the manufacturer
    # then print the description (all on the same line)
    # for each item in the file
    print( "todo" )

def add_item( filename ):
    #
    # Ask the user to enter the ID_NUM, ITEM_DESCRIPTION,
    # ITEM_MANUFACTURER, and ITEM_PRICE for a new item.
    # Insert it in the file ensuring that the ID_NUMs
    # increase in ascending order
    #
    print( "todo" )

def delete_item( item_number, filename ):
    #
    # For bonus:
    # Allow the user to remove an entry from the file by
    # having them provide an ID_NUM
    #
    print( "bonus" )

filename = input( 'Where is your hardware data file? ' )
while True:
    file_data = get_file_data_list( filename )
    display_records( file_data )
    
    action = input( "Add item? (y/n) " )
    if action != 'y':
        break
    add_item( filename )

