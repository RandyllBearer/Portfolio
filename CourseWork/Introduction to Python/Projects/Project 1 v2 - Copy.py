##### Flag Variables #####
clear = "Clear"
caution = "Yield"
stop = "Stop "

##### Train Variables #####
new_train = 0
train_list = ["-1", "-1", "-1", "-1", "-1"]  ### If the track is full, list should be [0,1,2,3,4]
last_train = "-1"                            ### So I know which number to make the next train (Non-Temporary)
existing_train = 0

##### Block Variables #####
block_list = ["====="] * 5  ### If there is 1 train in block 2; block_list = ["=====", "==1==", etc...]
status_list = [clear]* 5
status_index = 0                 ## Used in move_train to detect if move is possible
status_index2 = 0
number_list = ["  0  ", "  1  ", "  2  ", "  3  ", "  4  "]  ### Just to be nice
new_block = 0

##### Misc. Variables #####
user_input = 0
move_specification = 0    ### Used in program under if user_input == "move"
pointless_variable = 0    ### Used because I can't use "break" to leave the if statement, only whole loop

##### Functions #####
def create_train():
    global train_list
    global block_list
    global last_train
    global new_train                ### Initial check and removal of the -1 in train_list
    if last_train == "-1":
       last_train = int(last_train)
       new_train = last_train + 1
       train_list[0] = str(new_train)
       block_list[0] = "==" + str(new_train) + "=="
       last_train = last_train + 1
       last_train = str(last_train)
       status_list[0] = stop
    else:
       last_train = int(last_train)
       new_train = last_train + 1
       new_train = str(new_train)
       train_list[0] = new_train
       block_list[0] = "==" + str(new_train) + "=="
       last_train = last_train + 1
       last_train = str(last_train)
       status_list[0] = stop

def determine_status():                 ### Used to determine which blocks should display caution
    global status_list                  ### There's got to be a loop for this
    if status_list[0] == clear and status_list[1] == stop:
        status_list[0] = caution
    if status_list[0] == caution and status_list[1] == clear:
        status_list[0] = clear
    if status_list[0] == caution and status_list[1] == caution:
        status_list[0] = clear
    if status_list[1] == clear and status_list[2] == stop:
        status_list[1] = caution
    if status_list[1] == caution and status_list[2] == clear:
        status_list[1] = clear
    if status_list[1] == caution and status_list[2] == caution:
        status_list[1] = clear
    if status_list[2] == clear and status_list[3] == stop:
        status_list[2] = caution
    if status_list[2] == caution and status_list[3] == clear:
        status_list[2] = clear
    if status_list[2] == caution and status_list[3] == caution:
        status_list[2] = clear
    if status_list[3] == clear and status_list[4] == stop:
        status_list[3] = catuion
    if status_list[3] == caution and status_list[4] == clear:
        status_list[3] = clear
    if status_list[3] == caution and status_list[4] == caution:
        status_list[3] = clear

def move_train(self):                         ### Future-me, I'm sorry.
    global move_specification
    global status_list
    global block_list
    global train_list
    global status_index
    global status_index2
    if int(move_specification) > int(last_train) or int(move_specification) < 0:
        print("Sorry, cannot compute user command.")
    existing_train = train_list.index(move_specification)
    new_train = existing_train + 1
    existing_train = int(existing_train)
    new_train = int(new_train)
    status_index = train_list.index(move_specification)
    status_index2 = status_index + 1
    if train_list.index(move_specification) == 4:      ### Tests to remove trains in block 4
        train_list[4] = "-1"
        block_list[4] = "====="
        status_list[4] = clear
    elif status_list[status_index2] == clear or status_list[status_index2] == caution:         ### Tests if next block over is occupied
        existing_train = int(existing_train)
        new_train = int(new_train)
        train_list[existing_train] = "-1"
        train_list[new_train] = str(move_specification)         ### existing_train because train # staying the same
        block_list[existing_train] = "====="
        block_list[new_train] = "==" + str(move_specification) + "=="
        status_list[existing_train] = clear
        status_list[new_train] = stop
        status_index = block_list.index("==" + move_specification + "==")
        status_index = str(status_index)
        print("\nTrain " + str(move_specification) + " is now occupying block " + status_index + ".")   ### Status_index is re-evaluated, previous value no longer necessary
    else:
        print("Sorry, cannot compute command.")
    
########## PROGRAM ############

print("Hello, and welcome to Train Simulator 2015.  Due to a restrained budget, we only had the resources to implement a few commands and ideas.")
print(" - There will be a total of five sections to your rail line.                                  [Numbered 0 - 4].")
print(" - Trains will enter your rail line only from the left, and will exit only from the right.    [Trains will also be numbered 0-4]")
print(" - Trains can only move towards the right, one rail block at a time.                          [If they are in block 4, train will exit rail line].")
print(" - Command syntax is as follows [non-case-sensitive]: ")
print("      - Create       [Will create a train at block 0]")
print("      - Move         [Will prompt user for more information]")
print("      - Pass         [Will prompt user for more information]")
print("      - Exit         [Will return user to rail displar]")

while True:
    print(" \n " * 10)  ### Put something artsy here
    print(block_list)
    determine_status()
    print(status_list)
    print(number_list)
    user_input = input("\nPlease enter a command [Create, Move, Pass]: ")
    if user_input == "Create" or user_input == "create":
        if block_list[0] == "=====":
            create_train()
            print("\nTrain " + str(new_train) + " is now occupying block 0")
        else:
            print("\nI'm sorry, Dave. I'm afraid I can't do that. (An existing train is occupying block 0)")
    elif user_input == "Move" or user_input == "move":
        move_specification = input("What Train # Would you like to move? ")
        if move_specification == "Exit" or move_specification == "exit":
            pointless_variable = pointless_variable + 1
        elif int(move_specification) <= int(last_train):
            move_train(move_specification)
        else:
            print("\nCannot compute user command.")

    elif user_input == "Exit" or user_input == "exit":
        pointless_variable = pointless_variable + 1
        print("\nThank you for playing!")
        break
    else:
        print("\nCannot compute user command.")






        
