string = input("Please enter a word you would like to check for a specific suffix.  ")
suffix_string = input("Please enter a suffix (e.x. 'ing' without the 's).  ")
i = 0
flag = 0

if len(suffix_string) == 3:
    for suffix in string:
        if suffix == suffix_string[i:i+1]:
            i = i+1
            for suffix2 in string:
                if suffix2 == suffix_string[i:i+1]:                 ### Possible to do "suffix+1"?  or start another
                    i = i + 1
                    for suffix3 in string:
                        if suffix3 == suffix_string[i:i+1]:         ### for loop in "string[
                            print("It's a match!")
                            flag = flag + 1
elif len(suffix_string) == 2:
    for suffix in string:
         if suffix == suffix_string[i:i+1]:
            i = i + 1
            for suffix2 in string:
                 if suffix2 == suffix_string[i:i+1]:
                    print("It's a match!")
                    flag = flag + 1
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
                                     print("It's a match!")
                                     flag = flag + 1

if flag == 0:
    print("Nope")
        
