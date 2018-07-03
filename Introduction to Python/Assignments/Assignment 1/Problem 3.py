# Problem 3

string = input("Please enter a sentence for which you would like to parse for a specific character.  ")
specified_character = input("Please enter character you wish to count.  ")
character_count = 0

for character in string:
    if character == specified_character:
        character_count = character_count + 1

character_count = str(character_count)
print('This character occurs ' + character_count + ' times.')
