data = []
data_file = ""
id_list = []

def get_file_data_list( filename ):
    global data
    global data_file
    data_file = open( filename, 'r' )
    data = data_file.readlines()
    data_file.close()
    return data

def fix_readlines(filename):  ### In hindsight, should really just have stripped all the strings from the start and just manually added \n
    global data               ### but this version works and I don't want to screw it up now
    global data_file
    data_file = open(filename,'w')
    data[-1] = data[-1] + "\n"
    print("\n")
    for i in range(len(data)):
            new_line = data[i]
            new_line = str(new_line)
            data_file.write(new_line)
    data_file.flush()
    data_file.close()

def display_records( filename ):
    global data
    global id_list
    id_list = []
    for i in range(len(data)):
        data_string = data[i]
        id_num = data_string[0:7]
        id_list.append(id_num)
        item_description = data_string[7:22]
        item_manufacturer = data_string[22:32]
        item_price = data_string[32:38]
        print(id_num + " " + item_manufacturer + " " + item_description + " $" + item_price  +"") ### I didn't know if you wanted it single or double spaced
    print("\n")
    # Each list entry is a string
    # Data in the string follows this format:
    # ID_NUM = 7 characters
    # ITEM_MANUFACTURER = 10 characters
    # ITEM_DESCRIPTION = 15 characters
    # ITEM_PRICE = 6 characters
    # Print the ITEM_ID followed by the manufacturer
    # then print the description (all on the same line)
    # for each item in the file
    

def add_item( filename ):
    global data
    global data_file
    global check
    global id_list
    data_file = open(filename, 'w')
    print("You will now be asked to input Item Information.  To exit this part of the program, please input 'Exit' when prompetd for input [4 times]\n")
    user_id_num = input("Please enter the new item's ID Number: ")          ### Written in file order not data order
    user_item_manufacturer = input("Please enter the new item's Item Manufacturer: ")
    user_item_description = input("Please enter the new item's Item Description: ")
    user_item_price = input("Please enter the new item's Item Price [No '$' needed]: ")
    if user_item_description == "Exit" or user_item_description == "exit":
        print("")
    elif len(user_item_description) <= 15:
        blank_space2 = 15 - len(user_item_description)
        blank_space2 = " " * blank_space2
    else:
        print("Sorry that Item Manufacturer must be no longer than 10 characters.")
    if user_item_manufacturer == "Exit" or user_item_manufacturer == "exit":
        print("")
    elif len(user_item_manufacturer) <= 10:
        blank_space3 = 10 - len(user_item_manufacturer)
        blank_space3 = " " * blank_space3
    else:
        print("Sorry that Item Description must be no longer than 15 characters.")
    if user_item_price == "Exit" or user_item_price == "exit":
        print("")
    elif len(user_item_price) <= 6:
        blank_space4 = 6 - len(user_item_price)
        blank_space4 = " " * blank_space4 
    else:
        print("Sorry that Item Price must be no longer than 6 characters.")
    if user_id_num == "Exit" or user_id_num == "exit":
        print("")
    elif len(user_id_num) <= 7:
        blank_space1 = 7 - len(user_id_num)
        blank_space1 = " " * blank_space1
        new_string = user_id_num + blank_space1 + user_item_description + blank_space2 + user_item_manufacturer + blank_space3 + blank_space4 + user_item_price ### This is the new string to write into file
        data.append(new_string + "\n")       ### Will add user string into end of list
        for i in range(len(id_list)):
            if user_id_num == id_list[i]:
                print("Sorry but an item with that ID Number already exists")
                data.remove(new_string + "\n")
                prevention = ""
                prevention = data[-1]
                prevention = prevention.rstrip()
                data[-1] = prevention
            elif int(user_id_num) > int(id_list[-1]):
                data[-1] = new_string
            elif int(user_id_num) > int(id_list[i]):  ### In order to sort by ascending order, probably terribly inefficient because it rewrites it everytime
                data.remove(new_string + "\n")
                data.insert(i+1,new_string + "\n")
            elif int(user_id_num) < int(id_list[1]):
                data.remove(new_string + "\n")
                data.insert(0,new_string + "\n")
                prevention = ""
                prevention = data[-1]
                prevention = prevention.rstrip()
                data[-1] = prevention
            else:
                print("")      
    else:
        print("Sorry that ID Number must be no longer than 7 characters.")
    if user_item_price == "Exit" or user_item_price == "exit":
        prevention = ""
        prevention = data[-1]
        prevention = prevention.rstrip()
        data[-1] = prevention    
        for i in range(len(data)):  ### If user doesn't want to add anything, rewrite the existing file
            new_line = data[i]
            new_line = str(new_line)
            data_file.write(new_line)
    else:
        prevention = ""
        prevention = data[-1]
        prevention = prevention.rstrip()   ### My program kept adding a \n to the last string, this removes it before it happens so I only get one \n not 2
        data[-1] = prevention
        for i in range(len(data)):
            new_line = data[i]
            new_line = str(new_line)
            data_file.write(new_line)
    data_file.flush()
    data_file.close()
    #
    # Ask the user to enter the ID_NUM, ITEM_DESCRIPTION,
    # ITEM_MANUFACTURER, and ITEM_PRICE for a new item.
    # Insert it in the file ensuring that the ID_NUMs
    # increase in ascending order
    #

def delete_item( filename ):
    global data
    global data_file
    global id_list
    unwanted_id = input("What Item would you like to delete [Input ID Number]: ")
    data_file = open(filename, 'w')
    if len(unwanted_id) <= 7:
        for i in range(len(id_list)):
            existing_id = data[i]
            existing_id_num = existing_id [0:7]
            if unwanted_id == existing_id_num:
                data.remove(existing_id)
                break
            else:
                print("")
    else:
        print("Sorry but that is an invalid ID Number.")
    data_file.flush()                   ### These lines are only to make the file blank, with the way my function is set
    data_file.close                     ### up, it will write until it reaches end of list, leaving a remainder. I do not
    data_file = open(filename, 'w')     ### know how to actually "unwrite" a line.
    for i in range(len(data)):
        new_line = data[i]
        new_line = str(new_line)
        data_file.write(new_line)
        
    data_file.flush()
    data_file.close
    #
    # For bonus:
    # Allow the user to remove an entry from the file by
    # having them provide an ID_NUM
    #

filename = input( 'Where is your hardware data file? ' )
file_data = get_file_data_list( filename )
fix_readlines(filename)         ### Simply adds a  \n to the last string because readlines() is silly and doesn't
while True:
    file_data = get_file_data_list( filename )
    display_records( filename )
    print("\n------------------------------\nIt is highly recommended that you input 'Exit' before closing this program")
    action = input( "\nAdd, Delete, Exit? ")
    if action == "Exit" or action == "exit":  
        data_file = open(filename,'w')
        prevention = ""
        prevention = data[-1]
        prevention = prevention.rstrip()
        data[-1] = prevention
        for i in range(len(data)):
            new_line = data[i]
            new_line = str(new_line)
            data_file.write(new_line)
        data_file.flush()
        data_file.close()
        break
    elif action == "Add" or action == "add":
        add_item( filename )
        fix_readlines(filename)
    elif action == "Delete" or action == "delete":
        delete_item( filename )
    else:
        print("\nCould not compute command\n")
    
