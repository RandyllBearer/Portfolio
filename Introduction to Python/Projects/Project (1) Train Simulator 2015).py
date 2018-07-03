########## Flag Variables ##########
clear = "Clear!"
caution = "Yield!"
stop = "Stop!"

########## Train Variables ##########
train0 = -1
train1 = -1
train2 = -1   ### train3 will = its current block, -1 being left before the track, 5 being right past the track
train3 = -1
train4 = -1

########## Display Variables ##########
block0_status = "="
block0_display = "==" + block0_status + "==  "
block1_status = "="
block1_display = "==" + block1_status + "==  "
block2_status = "="                                 ### block3_status will = its corresponding train
block2_display = "==" + block2_status + "==  "
block3_status = "="
block3_display = "==" + block3_status + "==  "
block4_status = "="
block4_display = "==" + block4_status + "==  "
rail_display = block0_display + block1_display + block2_display + block3_display + block4_display

########## Functions ##########
def create_new_train(int):
    global train0
    global train1
    global train2
    global train3
    global train4
    global block0_status
    if train0 < 0:
        block0_status = str(0)      ### Shows that train #0 is now in block #0
        train0 = 0                  ### Train #0 is now residing in block #0
    elif train1 < 0:
        block0_status = str(1)
        train1 = 0
    elif train2 < 0:
        block0_status = str(2)
        train2 = 0
    elif train3 < 0:
        block0_status = str(3)
        train3 = 0
    elif train4 < 0:
        block0_status = str(4)
        train4 = 0

create_new_train(1)
print(rail_display)
    
