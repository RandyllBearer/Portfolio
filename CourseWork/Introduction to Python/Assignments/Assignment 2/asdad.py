li = ["001", "002", "004"]
user_id_num = "005"
li.insert(0,user_id_num)
for i in range(len(li)):
    if int(user_id_num) > int(li[i]):
        li.remove(user_id_num)
        li.insert(i,user_id_num)

print(li)
