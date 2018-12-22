### Stock Market Game ###

### I couldn't figure out how the cumulative profit was supposed to work.  I understand that you wanted the difference between the time you bought it
### and the time you sold it, but what would happen if someone bought 20 stocks year 2, but then sold only 15 in year 3?
### Maybe this is what you wanted, and if it is then I couldn't figure it out, oh well it's just bonus.
### My 'load' and 'replay' system are practically the same thing.  I save the users commands for each year then just put them back into the whole program
### It's not the easiest way to do it, but it guarantees the same amount of "rolls" from the random generator, and it seems to work.


#################### Variables #######################
import random
random.seed(100)

user_cash = 10000
user_stocks = 0
year = 0
existing_commands = []
money_spent ={}
money_earned = {}
current_stock_value = 20
random_stock_value = 0
old_stock_value = {0:20}
game_state = 0                  ### Determines whether to load from a save game or not
stock_flag = [0]                ### Used to determine whether to increase the % chance of increase/decrease
stock_flag_int = 0
stock_check = 0

#################### Functions ########################
def buy_stock():
    global current_stock_value
    global money_spent
    global existing_commands
    global user_stocks
    global user_cash
    while True:
        amount_to_buy = input("\nHow many shares of stock would you like to buy? ")
        print("----------------------")
        amount_to_buy = int(amount_to_buy)
        total_cost = amount_to_buy * current_stock_value
        if total_cost <= user_cash:                  ### Does a man have enough money?
            money_spent[year] = total_cost
            user_cash = user_cash - total_cost
            user_stocks = amount_to_buy + user_stocks
            existing_commands.append("buy")
            amount_to_buy = str(amount_to_buy)
            existing_commands.append(amount_to_buy)
            print("\n")
            break
        else:
            existing_commands.append(amount_to_buy)
            print("You cannot spend more money than what you have!")
            print("----------------------")
            print("\n")
    
def sell_stock():
    global current_stock_value
    global money_spent
    global existing_commands
    global user_stocks
    global user_cash
    while True:
        amount_to_sell = input("\nHow many shares of stock would you like to sell? ")
        amount_to_sell = int(amount_to_sell)
        total_profit = amount_to_sell * current_stock_value
        if amount_to_sell <= user_stocks:
            money_earned[year] = total_profit
            user_cash = user_cash + total_profit
            user_stocks = user_stocks - amount_to_sell
            existing_commands.append("sell")
            total_profit = str(amount_to_sell)
            existing_commands.append(amount_to_sell)
            print("----------------------\n")
            break
        else:
            print("You cannot sell more stocks than you own!")
            print("----------------------\n")
            
def hold_stock():
    existing_commands.append("hold")
    print("----------------------\n")
        
def random_stock():   ### Determines stock value each year and records last year's price
    global year
    global current_stock_value
    global old_stock_value
    global stock_flag
    global stock_flag_int
    random_stock_value = random.random()
    if year == 0:
        current_stock_value = 20
    else:
        stock_check = random_stock_value * 10        ### Basically increments the % chance
        stock_check = stock_check + stock_flag[-1]
        stock_check = stock_check // 10
        if random_stock_value >= stock_check:
            random_stock_positive()
            if stock_flag[-1] < 0:
                stock_flag_int = 0
                stock_flag.append(stock_flag_int)
            else:
                stock_flag_int = stock_flag[-1] + 1
                stock_flag.append(stock_flag_int)
        elif random_stock_value < stock_check:
            random_stock_negative()
            if stock_flag[-1] > 0:
                stock_flag_int = 0
                stock_flag.append(stock_flag_int)
            else:
                stock_flag_int = stock_flag[-1] - 1
                stock_flag.append(stock_flag_int)
    old_stock_value[year] = current_stock_value   
            
def random_stock_positive():   ### Determines how much the stock price should increase
    global year
    global current_stock_value
    random_stock_value = random.random()
    if random_stock_value >= 0 and random_stock_value <= .2:
        current_stock_value += 1
    elif random_stock_value > .2 and random_stock_value <= .4:
        current_stock_value += 2
    elif random_stock_value > .4 and random_stock_value <= .6:
        current_stock_value += 3
    elif random_stock_value > .6 and random_stock_value <= .8:
        current_stock_value += 4
    elif random_stock_value > .8 and random_stock_value <= 1:
        current_stock_value += 5
    else:
        print("")
            
def random_stock_negative():   ### Determines how much the stock price should decrease
    global year
    global current_stock_value
    random_stock_value = random.random()
    if random_stock_value >= 0 and random_stock_value <= .2:
        current_stock_value -= 1
    elif random_stock_value > .2 and random_stock_value <= .4:
        current_stock_value -= 2
    elif random_stock_value > .4 and random_stock_value <= .6:
        current_stock_value -= 3
    elif random_stock_value > .6 and random_stock_value <= .8:
        current_stock_value -= 4
    elif random_stock_value > .8 and random_stock_value <= 1:
        current_stock_value -= 5
    else:
        print("")
    
def save_file():
    while True:
        try:
            data_location = input("Please enter the file to which you wish to save to: ")
            data_file = open(data_location, 'w')
            data_file.seek(0)           ### The internet has led me to believe this is how you 'clear' a file
            data_file.truncate()
            for i in existing_commands:
                data_file.write(i + ",")
            data_file.flush()
            data_file.close()
            break
        except EOFError:
            print("Okay")

def display_status():
    print("It is currently year " + str(year) + ".")
    print("You currently have $" + str(user_cash) + " available to spend.")
    print("You currently own " + str(user_stocks) + " shares.")
    print("The stock price is currently $" + str(current_stock_value) + ".")
    if year == 0:
        net_turn = 0
    else:
        net_turn = current_stock_value - old_stock_value[year-1]
        net_turn = net_turn * user_stocks
    if net_turn >= 0:
        print("You have made $" + str(net_turn) + " since last year!\n")
    else:
        print("You have lost $" + str(net_turn) + " since last year!\n")

############################# PROGRAM #####################
print("Welcome to the Stock Market Simulator 2015!\nIn this game you will progress through 10 years, each year you will be able to either 'Buy', 'Sell', or 'Hold' your stock.  Your goal is to gain as much profit as possible!\n")
user_decision = input("Would you like to:\nStart a new game [New]\nLoad a previous game [Load]\nLoad a replay [Replay]\n\n")
print("----------------------")
if user_decision == "New" or user_decision == "new":
    while year < 11:
        display_status()
        user_decision = input("What action would you like to perform:\n[Buy]\n[Sell]\n[Hold]\n[Save]\n\n")
        if user_decision == "Buy" or user_decision == "buy":
            buy_stock()
            year += 1
            random_stock()
        elif user_decision == "Sell" or user_decision == "sell":
            sell_stock()
            year += 1
            random_stock()
        elif user_decision == "Hold" or user_decision == "hold":
            hold_stock()
            year += 1
            random_stock()
        elif user_decision == "Save" or user_decision == "save":
            save_file()
        else:
            print("Sorry that command could not be computed")
            print("----------------------")
    print("The simulation is now over!")
    total_wealth = (user_stocks * current_stock_value) + user_cash
    print("Congratulations, you ended the game with a net worth of $" + str(total_wealth) + "!\n\n")
    user_decision = input("Would you like to save this replay? [Y/N]: ")
    if user_decision == "Y" or user_decision == "y":
        save_file()
    elif user_decision == "Yes" or user_decision == "yes":
        save_file()
    else:
        print("Alright, thank you for playing!")
elif user_decision == "Load" or user_decision == "load":
    while True:
        try:
            data_location = input("Please input the location of your save file: ")
            data_file = open(data_location, 'r')
            existing_commands_string = data_file.read()
            loaded_commands = existing_commands_string.split(sep = ",")
            data_file.flush()
            data_file.close()
            print(loaded_commands)
            break
        except:
            print("Sorry that file location could not be accessed.")
    for i in loaded_commands:
        index = loaded_commands.index(i)
        if loaded_commands[index] == "Buy" or loaded_commands[index] == "buy":    ### Buy Stock on Replay/Load
            display_status()
            print("What action would you like to perform:\n[Buy]\n[Sell]\n[Hold]\n[Save]\n\n")
            print(loaded_commands[index])
            while True:
                print("\nHow many shares of stock would you like to buy? \n")
                loaded_commands_index = loaded_commands.index(i)
                amount_to_buy = loaded_commands[loaded_commands_index+1]  ### Changing input to grabbing it from list
                print(amount_to_buy + "\n")
                print("----------------------")
                amount_to_buy = int(amount_to_buy)
                total_cost = amount_to_buy * current_stock_value
                if total_cost <= user_cash:     
                    money_spent[year] = total_cost
                    user_cash = user_cash - total_cost
                    user_stocks = amount_to_buy + user_stocks
                    existing_commands.append("buy")
                    amount_to_buy = str(amount_to_buy)
                    existing_commands.append(amount_to_buy)
                    print("")
                    break
                else:
                    existing_commands.append(amount_to_buy)
                    print("You cannot spend more money than what you have!")
                    print("\n")
            year += 1
            random_stock()
        elif loaded_commands[index] == "Sell" or loaded_commands[index] == "sell":
            display_status()
            print("What action would you like to perform:\n[Buy]\n[Sell]\n[Hold]\n[Save]\n\n")
            print(loaded_commands[index])
            while True:
                print("\nHow many shares of stock would you like to sell? \n")
                loaded_commands_index = loaded_commands.index(i)
                amount_to_sell = loaded_commands[loaded_commands_index + 1]
                print(amount_to_sell + "\n")
                amount_to_sell = int(amount_to_sell)
                total_profit = amount_to_sell * current_stock_value
                if amount_to_sell <= user_stocks:
                    money_earned[year] = total_profit
                    user_cash = user_cash + total_profit
                    user_stocks = user_stocks - amount_to_sell
                    existing_commands.append("sell")
                    total_profit = str(amount_to_sell)
                    existing_commands.append(amount_to_sell)
                    print("----------------------\n")
                    break
                else:
                    print("You cannot sell more stocks than you own!")
                    print("----------------------\n")
            year += 1
            random_stock()
        elif loaded_commands[index] == "Hold" or loaded_commands[index] == "hold":
            display_status()
            print("What action would you like to perform:\n[Buy]\n[Sell]\n[Hold]\n[Save]\n")
            print(loaded_commands[index] + "\n")
            hold_stock()
            year += 1
            random_stock()
        else:
            print("")
    while year < 11:
        display_status()
        user_decision = input("What action would you like to perform:\n[Buy]\n[Sell]\n[Hold]\n[Save]\n\n")
        if user_decision == "Buy" or user_decision == "buy":
            buy_stock()
            year += 1
            random_stock()
        elif user_decision == "Sell" or user_decision == "sell":
            sell_stock()
            year += 1
            random_stock()
        elif user_decision == "Hold" or user_decision == "hold":
            hold_stock()
            year += 1
            random_stock()
        elif user_decision == "Save" or user_decision == "save":
            save_file()
        else:
            print("Sorry that command could not be computed")
            print("---------------------")
    print("The simulation is now over!")
    total_wealth = (user_stocks * current_stock_value) + user_cash
    print("Congratulations, you ended the game with a net worth of $" + str(total_wealth) + "!\n\n")
    user_decision = input("Would you like to save this replay? [Y/N]: ")
    if user_decision == "Y" or user_decision == "y":
        save_file()
    else:
        print("Alright, thank you for playing!")
elif user_decision == "Replay" or user_decision == "replay":   ### Same thing as Load except for the part where you can't play afterwards.  Basically replays the whole program by saving user commands
    while True:
        try:
            data_location = input("Please input the location of your replay file: ")
            data_file = open(data_location, 'r')
            existing_commands_string = data_file.read()
            loaded_commands = existing_commands_string.split(sep = ",")
            data_file.flush()
            data_file.close()
            print(loaded_commands)
            break
        except:
            print("Sorry that file location could not be accessed.")
    for i in loaded_commands:
        index = loaded_commands.index(i)
        if loaded_commands[index] == "Buy" or loaded_commands[index] == "buy":    ### Buy Stock on Replay/Load
            display_status()
            print("What action would you like to perform:\n[Buy]\n[Sell]\n[Hold]\n[Save]\n\n")
            print(loaded_commands[index])
            while True:
                print("\nHow many shares of stock would you like to buy? \n")
                loaded_commands_index = loaded_commands.index(i)
                amount_to_buy = loaded_commands[loaded_commands_index+1]  ### Changing input to grabbing it from list
                print(amount_to_buy + "\n")
                print("----------------------")
                amount_to_buy = int(amount_to_buy)
                total_cost = amount_to_buy * current_stock_value
                if total_cost <= user_cash:     
                    money_spent[year] = total_cost
                    user_cash = user_cash - total_cost
                    user_stocks = amount_to_buy + user_stocks
                    existing_commands.append("buy")
                    amount_to_buy = str(amount_to_buy)
                    existing_commands.append(amount_to_buy)
                    print("")
                    break
                else:
                    existing_commands.append(amount_to_buy)
                    print("You cannot spend more money than what you have!")
                    print("\n")
            year += 1
            random_stock()
        elif loaded_commands[index] == "Sell" or loaded_commands[index] == "sell":
            display_status()
            print("What action would you like to perform:\n[Buy]\n[Sell]\n[Hold]\n[Save]\n\n")
            print(loaded_commands[index])
            while True:
                print("\nHow many shares of stock would you like to sell? \n")
                loaded_commands_index = loaded_commands.index(i)
                amount_to_sell = loaded_commands[loaded_commands_index + 1]
                print(amount_to_sell + "\n")
                amount_to_sell = int(amount_to_sell)
                total_profit = amount_to_sell * current_stock_value
                if amount_to_sell <= user_stocks:
                    money_earned[year] = total_profit
                    user_cash = user_cash + total_profit
                    user_stocks = user_stocks - amount_to_sell
                    existing_commands.append("sell")
                    total_profit = str(amount_to_sell)
                    existing_commands.append(amount_to_sell)
                    print("----------------------\n")
                    break
                else:
                    print("You cannot sell more stocks than you own!")
                    print("----------------------\n")
            year += 1
            random_stock()
        elif loaded_commands[index] == "Hold" or loaded_commands[index] == "hold":
            display_status()
            print("What action would you like to perform:\n[Buy]\n[Sell]\n[Hold]\n[Save]\n")
            print(loaded_commands[index] + "\n")
            hold_stock()
            year += 1
            random_stock()
        else:
            print("")
    print("The simulation is now over!")
    total_wealth = (user_stocks * current_stock_value) + user_cash
    print("Congratulations, you ended the game with a net worth of $" + str(total_wealth) + "!\n\n")
else:
    print("Sorry, that command could not be computed!")
