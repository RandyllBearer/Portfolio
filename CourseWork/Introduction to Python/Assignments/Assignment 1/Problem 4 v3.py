string = input("Please enter a word you would like to check for a specific suffix.  ")
suffix_string = input("Please enter a suffix (e.x. 'ing' without the 's).  ")
i = 0
flag_wrong = 0    ### Alerts ###
flag_right = 0

if len(suffix_string) == 3:
    for suffix in string:
        if suffix == suffix_string[i:i+1]:
            i = i+1
            for suffix2 in string:
                if suffix2 == suffix_string[i:i+1]:                 
                    i = i + 1
                    for suffix3 in string:
                        if suffix3 == suffix_string[i:i+1]:         
                            flag_right = flag_right + 1
                            flag_wrong = flag_wrong + 1
elif len(suffix_string) == 2:
    for suffix in string:
         if suffix == suffix_string[i:i+1]:
            i = i + 1
            for suffix2 in string:
                 if suffix2 == suffix_string[i:i+1]:
                    flag_right = flag_right + 1
                    flag_wrong = flag_wrong + 1
elif len(suffix_string) == 4:
    for suffix in string:
        if suffix == suffix_string[i:i+1]:
            i = i + 1
            for suffix2 in string:
                if suffix2 == suffix_string[i:i+1]:
                    i = i + 1
                    for suffix3 in string:
                         if suffix3 == suffix_string[i:i+1]:
                             i = i + 1
                             for suffix4 in string:
                                 if suffix4 == suffix_string[i:i+1]:
                                     flag_right = flag_right + 1
                                     flag_wrong = flag_wrong + 1
elif len(suffix_string) == 5:
    for suffix in string:
        if suffix == suffix_string[i:i+1]:
            i = i + 1
            for suffix2 in string:
                if suffix2 == suffix_string[i:i+1]:
                    i = i + 1
                    for suffix3 in string:
                         if suffix3 == suffix_string[i:i+1]:
                             i = i + 1
                             for suffix4 in string:
                                 if suffix4 == suffix_string[i:i+1]:
                                     i = i + 1
                                     for suffix5 in string:
                                         if suffix5 == suffix_string[i:i+1]
                                             flag_right = flag_right + 1
                                             flag_wrong = flag_wrong + 1
elif len(suffix_string) == 6:
    for suffix in string:
        if suffix == suffix_string[i:i+1]:
            i = i + 1
            for suffix2 in string:
                if suffix2 == suffix_string[i:i+1]:
                    i = i + 1
                    for suffix3 in string:
                         if suffix3 == suffix_string[i:i+1]:
                             i = i + 1
                             for suffix4 in string:
                                 if suffix4 == suffix_string[i:i+1]:
                                     i = i + 1
                                     for suffix5 in string:
                                         if suffix5 == suffix_string[i:i+1]
                                             i = i + 1
                                             for suffix6 in string:
                                                 if suffix6 == suffix_string[i:i+1]
                                                     flag_right = flag_right + 1
                                                     flag_wrong = flag_wrong + 1
elif len(suffix_string) == 7:
    for suffix in string:
        if suffix == suffix_string[i:i+1]:
            i = i + 1
            for suffix2 in string:
                if suffix2 == suffix_string[i:i+1]:
                    i = i + 1
                    for suffix3 in string:
                         if suffix3 == suffix_string[i:i+1]:
                             i = i + 1
                             for suffix4 in string:
                                 if suffix4 == suffix_string[i:i+1]:
                                     i = i + 1
                                     for suffix5 in string:
                                         if suffix5 == suffix_string[i:i+1]
                                             i = i + 1
                                             for suffix6 in string:
                                                 if suffix6 == suffix_string[i:i+1]
                                                     i = i + 1
                                                     for suffix7 in string
                                                         if suffix7 == suffix_string[i:i+1]
                                                             flag_right = flag_right + 1
                                                             flag_wrong = flag_wrong + 1


print()

if flag_wrong == 0:
    print("Nope")

if flag_right == 1:
    print("It's a match!")
        
