"""
Name         Roi Avraham
ID           318778081
Group        1
Assignment   ex7
"""
import sys
from collections import defaultdict
COMMA=','
SEMICOLON=';'
COLON=':'
INTERSECTION='&'
UNION='|'
SYMETRIC_DIFFRENCE='^'

"""
* Function Name:print_menu
* Input:
* Output:
* Function Operation:the function prints all the opations
*                    that can be done in the store
"""
def print_menu():
    print("Please select an operation:")
    print("\t0. Exit.")
    print("\t1. Query by category.")
    print("\t2. Query by item.")
    print("\t3. Purchase an item.")
    print("\t4. Admin panel.")

"""
* Function Name:menu
* Input:categories,admin_file,out
* Output:
* Function Operation:the function lets the user choose 
*                    different operation options related to
*                    the store
"""
def menu(categories,admin_file,out):
    #dictionaries that save queries for save on recurring calculations
    query_by_category_caching=dict()
    query_by_item_caching=dict()
    #print the menu
    print_menu()
    #the choose of the user
    choose = input()
    #the user selects options until the user press 0
    while(choose!='0'):
        #in case the user press 1
        if (choose == '1'):
            #print the names of the products according to the user choose
            query_by_category(categories,query_by_category_caching)
            print_menu()
            #reinput
            choose=input()
            continue
        # in case the user press 2
        if (choose == '2'):
            #print all the products accrording the user choose
            query_by_item(categories,query_by_item_caching)
            print_menu()
            #reinput
            choose = input()
            continue
        # in case the user press 3
        if (choose == '3'):
            #buy product
            purchase_an_item(categories,query_by_category_caching,query_by_item_caching)
            print_menu()
            #reinput
            choose = input()
            continue
        # in case the user press 4
        if (choose == '4'):
            #the operations are reserved only for the store manager
            admin_panel(categories,query_by_category_caching,query_by_item_caching,admin_file,out)
            print_menu()
            #reinput
            choose = input()
            continue
        #in case the user did not press 0-4
        print("Error: unrecognized operation.")
        print_menu()
        choose=input()

"""
* Function Name:check_cach
* Input:user_query,query_caching,catgory_one, catgory_two,operator
* Output:1 or 0
* Function Operation:the function recives the query from the
*                    user and the dict that saved all the previous
*                    queries of the user.if the query was already 
*                    the function print the result of the query from the 
*                    last time and return 1. else the function only return 0 
"""
def check_cach(user_query,query_caching,catgory_one, catgory_two,operator):
    CAHCHED=1
    NOT_CAHCHED=0

    """
    since the operation is between 2 groups of catgories it is the same query
    no matter what is the arrange of the catgories
    """
    operation_one=catgory_one+COMMA+catgory_two+COMMA+operator
    operation_two=catgory_two+COMMA+catgory_one+COMMA+operator
    #go through all the previous queries
    for query in query_caching.keys():
        #in case the user_query was already
        if(query==operation_one or query==operation_two):
            #print the result of the query from the last time the user input the query
            print("Cached:", query_caching[query])
            return CAHCHED
    #in case the query is new
    return NOT_CAHCHED

"""
* Function Name:vaild_query_by_category
* Input:categories,query_by_category_caching,user_query
* Output:
* Function Operation:the function recives from the user 2 catgories of products and 
*                    operation on them(intersection,union or symmetric_difference)
*                    then if the input is vailed the function prints all the products 
*                    respectively to the action the user chose              
"""
def vaild_query_by_category(categories,query_by_category_caching,user_query):
    # split the input of the user into the diffrence catgories and the operation
    catgory_one, catgory_two, action = user_query.split(",")
    # the opeation between the catgories
    action = action.lstrip(' ')
    #the first catgory
    catgory_one = catgory_one.lstrip(' ')
    #the secound catgory
    catgory_two = catgory_two.lstrip(' ')
    # in case the operator is not valid
    if (action != INTERSECTION and action != UNION and action != SYMETRIC_DIFFRENCE):
        print("Error: unsupported query operation.")
        return

    """
    checks if the user already choose these 2 catgories 
    and this operation and there was no change in the store 
    """
    old_query = check_cach(user_query, query_by_category_caching, catgory_one, catgory_two,action)
    TRUE=1
    # in case the user already choose this query
    if (old_query == TRUE):
        # the function "check_cach" gived the correct result so the mission is over
        return
    #put all the products in the catgories into sets
    catgory_one_products=set(categories[catgory_one].keys())
    catgory_two_products=set(categories[catgory_two].keys())
    #in case the opertor is intersection
    if(action==INTERSECTION):
        #do the intersection between the products of the 2 catgories
        intersection_opertor=catgory_one_products.intersection(catgory_two_products)
        intersection_opertor=list(intersection_opertor)
        #sort the prodcuts in the intersection
        intersection_opertor.sort()
        #save the query for the next time
        query_by_category_caching[catgory_one+COMMA+catgory_two+COMMA+action] = intersection_opertor
        print(intersection_opertor)
    # in case the opertor is union
    if(action==UNION):
        # do the union between the products of the 2 catgories
        union_opertor=catgory_one_products.union(catgory_two_products)
        union_opertor=list(union_opertor)
        # sort the prodcuts in the union
        union_opertor.sort()
        # save the query for the next time
        query_by_category_caching[catgory_one+COMMA+catgory_two+COMMA+action] = union_opertor
        print(union_opertor)
    # in case the opertor is symetric_diffrence
    if(action==SYMETRIC_DIFFRENCE):
        # do the symetric_diffrence between the products of the 2 catgories
        symetric_diffrence_opertor=catgory_one_products.symmetric_difference(catgory_two_products)
        symetric_diffrence_opertor=list(symetric_diffrence_opertor)
        # sort the prodcuts in the symetric_diffrence
        symetric_diffrence_opertor.sort()
        # save the query for the next time
        query_by_category_caching[catgory_one+COMMA+catgory_two+COMMA+action] = symetric_diffrence_opertor
        print(symetric_diffrence_opertor)

"""
* Function Name:query_by_category
* Input:categories,query_by_category_caching
* Output:
* Function Operation:the function recives from the user 2 catgories of products and 
*                    operation on them(intersection,union or symmetric_difference)
*                    then the function check the valied of the input 
"""
def query_by_category(categories,query_by_category_caching):
    #the 2 catgories and the operation
    user_query=input()
    #count how many commas there in the query
    counter_comma = 0
    ZERO=0
    ONE=1
    #the minimom comma that should have to be for have enough data
    MINIMOM_COMMAS=2
    TREE_COMMAS=3
    for char in user_query:
        if (char == COMMA):
            counter_comma += ONE
    #in case the user didnt input a catgory or operation or both
    if (counter_comma < MINIMOM_COMMAS):
        print("Error: not enough data.")
        return
    user_query=user_query.lstrip()
    #split to catgories
    check_user_query=user_query.split(",")
    #the first catgory
    catgory_one=check_user_query[ZERO]
    #the secound catgory
    catgory_two=check_user_query[ONE]
    catgory_one = catgory_one.lstrip(' ')
    catgory_two = catgory_two.lstrip(' ')
    # count the number of times catgory number one is appear in the store
    count_catagory_one = 0
    # count the number of times catgory number two is appear in the store
    count_catgory_two = 0
    # go through the catgories names in the store
    for name in categories.keys():
        if (name == catgory_one):
            count_catagory_one += ONE
        if (name == catgory_two):
            count_catgory_two += ONE
    # in case catgory number one is not exsist in the store
    if (count_catagory_one == ZERO):
        print("Error: one of the categories does not exist.")
        return
    # in case catgory number two is not exsist in the store
    if (count_catgory_two == ZERO):
        print("Error: one of the categories does not exist.")
        return
    #in case the opertor is with comma so the opertor unsupported
    if(len(check_user_query)>TREE_COMMAS):
        print("Error: unsupported query operation.")
        return
    #countnie checks the vailed of the input and if vailed to do the query
    vaild_query_by_category(categories,query_by_category_caching,user_query)

"""
* Function Name:exists_item
* Input:uquery_item
* Output:1 or 0
* Function Operation:the function recives the input from the
*                    user(the product).the function returns 1 
*                    if the product exists in the store else 
*                    the function returns 0
"""
def exists_item(query_item,categories):
    ONE=1
    EXSIST=1
    #count the number times the product is appear in the store
    exists = 0
    IS_NOT_EXSIST=0
    #go through all the products in the store
    for i in categories.keys():
        for j in categories[i].keys():
            #in case reached to the product
            if (query_item == j):
                exists += ONE
    #in case the product is not exists in the store
    if (exists == IS_NOT_EXSIST):
        print("Error: no such item exists.")
        return IS_NOT_EXSIST
    #the product exsist in the store
    return EXSIST

"""
* Function Name:check_caching
* Input:query_item,query_by_item_caching
* Output:1 or 0
* Function Operation:the function recives the input from the
*                    user and the dict that saved all the previous
*                    queries of the user.if the input was alreay 
*                    the function print the result of the query from the 
*                    last time and return 1 else the function only return 0 
"""
def check_caching(query_item,query_by_item_caching):
    CACHED=1
    UNCACHED=0
    # go through all the previous queries
    for query in query_by_item_caching.keys():
        # in case the user_query was already
        if (query == query_item):
            # print the result of the query from the last time the user input the query
            print("Cached:", query_by_item_caching[query])
            return CACHED
    # in case the query is new
    return UNCACHED

"""
* Function Name:query_by_item
* Input:categories,query_by_item_caching
* Output:
* Function Operation:the function gets product from the user
*                    then if the product is appear in the store 
*                    the function prints in sort way all the products
*                    (not included the wanted product)
*                    in all the catgories that the product is exsist
"""
def query_by_item(categories,query_by_item_caching):
    #the product the user input
    query_item=input()

    """
    checks if the user already choose this product but there 
    was not no change in the store 
    """
    old_query=check_caching(query_item,query_by_item_caching)
    TRUE=1
    #in csae the user already choose this product
    if(old_query==TRUE):
        #the function "check_cach" gived the correct result so the mission is over
        return
    #remove the spaces form the product name
    query_item=query_item.lstrip(" ")
    #checks if the product is exsist in the store
    exists=exists_item(query_item,categories)
    #in case the product is no exsist in the store
    NOT_EXSIST=0
    if(exists==NOT_EXSIST):
        return
    #list of the items that are in the same ctagories as the product
    items=[]
    exists=0
    ONE=1
    #go throuth the products in the store
    for i in categories.keys():
        for j in categories[i].keys():
            #in case the product is exsist in the current catgory
            if(query_item==j):
                exists+=ONE
        # in case the product is exsist in the current catgory
        if(exists!=NOT_EXSIST):
            #go through the products in the current the catgory
            for k in categories[i].keys():
                #in case the current the product is not the product the user choose
                if(k!=query_item):
                    #add the product into the list
                    items.append(k)
        exists=0
    #remove all double shows of the products in the list
    for p in range(len(items)-ONE):
        if (items.count(items[p]) > ONE):
            items.remove(items[p])
    #sort the products
    items.sort()
    #save the result of this query for the next time the user input the same product
    query_by_item_caching[query_item]=items
    print(items)

"""
* Function Name:purchase_an_item
* Input:categories,query_by_category_caching,query_by_item_caching
* Output:
* Function Operation:the function gets product that the user wants to buy
*                    from the user then if the product is exsist in the store 
*                    the function remove this product and prints a message to the user 
"""
def purchase_an_item(categories,query_by_category_caching,query_by_item_caching):
    #the user input the product he wants to buy
    query_item = input()
    #remove the spaces from the product name
    query_item = query_item.lstrip(" ")
    #checks if the product is exsist in the store
    exists = exists_item(query_item,categories)
    NOT_EXSIST=0
    #in case the product is not exsist in the store
    if (exists == NOT_EXSIST):
        #return to the main menu
        return
    exists = 0
    #go through all the products in the store
    for i in list(categories.keys()):
        for j in list(categories[i].keys()):
            #checks if the product is exsist in the current catgory
            if (query_item == j):
                #save the price of the product
                price = categories[i][j]
                #remove the product from the store
                del categories[i][j]
    print('You bought a brand new "'+query_item+'" for '+price+'$.')
    #delete all the caching since the store changed
    query_by_item_caching.clear()
    query_by_category_caching.clear()

"""
* Function Name:admin_menu
* Input:
* Output:
* Function Operation:the function prints the menu of all the options the store manager can choose
"""
def admin_menu():
    print("Admin panel:")
    print("\t0. Return to main menu.")
    print("\t1. Insert or update an item.")
    print("\t2. Save.")

"""
* Function Name:exsist_in_the_store
* Input:categories,category_list_without_spaces,insert_product
* Output:1 or 0
* Function Operation:the function gets product and all the store.
*                    the function return 1 if the product exsist in the store 
*                    else the function returns 0
"""
def exsist_in_the_store(categories,category_list_without_spaces,insert_product):
    ONE=1
    NOT_EXSIST=0
    EXSIST=1
    exsist = 0
    # go through the catgories and check if all the catgories the user entered are really exsists
    for i in category_list_without_spaces:
        for j in categories.keys():
            if (i == j):
                exsist += ONE
        # in case the user enterd name of catgory which is not exsist in the store
        if (exsist == NOT_EXSIST):
            print("Error: one of the categories does not exist.")
            return NOT_EXSIST
        exsist = 0
    return EXSIST

"""
* Function Name:insert_the_product
* Input:categories,query_by_category_caching,query_by_item_caching,name_catgories,product_and_price,
        price,product
* Output:
* Function Operation:the function checks if all the names of the name_catgories exsist
*                    and if so the function insert the product to the theses catgories 
*                    (if the product is already exsist in the catgory then the function 
*                     update the price of the product to new price) 
"""
def insert_the_product(categories,query_by_category_caching,query_by_item_caching,name_catgories,product_and_price,
                       price,product):
    NOT_EXSIST = 0
    ONE = 1
    # check if all the catgories names are really exsist in the store
    catgory_exsist = 0
    for i in range(len(name_catgories)):
        for j in categories.keys():
            if (name_catgories[i].lstrip() == j):
                catgory_exsist += ONE
        # in case their is a catgory which is not exsist in the store
        if (catgory_exsist == NOT_EXSIST):
            print("Error: one of the categories does not exist.")
            return
        # initialize the counter
        catgory_exsist = 0
    TWO_COMMAS = 2
    # in case the price is not a postive number
    if (len(product_and_price) > TWO_COMMAS or not price.isdigit()):
        print("Error: price is not a positive integer.")
        return
    # go throuth the products in the store
    for i in list(name_catgories):
        i = i.lstrip()
        for j in list(categories[i].keys()):
            # update the price of the product
            categories[i][product] = price
    # go through the store to update the price of the product in all the catgories the product is show
    for i in categories.keys():
        for j in categories[i].keys():
            if (j == product):
                # update the price of the product the admin entered
                categories[i][product] = price
    print('Item "' + product + '" added.')
    # delete the dicts of the caching since the store has changed
    query_by_item_caching.clear()
    query_by_category_caching.clear()

"""
* Function Name:insert_or_update_an_item
* Input:categories,query_by_category_caching,query_by_item_caching
* Output:
* Function Operation:the function checks if the admin enterd valied input.
*                    if so the function adds new product to the sutiable catgories or updated 
*                    price of exsist product in the store 
"""
def insert_or_update_an_item(categories,query_by_category_caching,query_by_item_caching):
    # gets the input from the admin
    admin_input = input()
    #remove the spaces in the start of the admin_input
    admin_input=admin_input.lstrip()
    # count how many colons in the admin input
    count_colon = 0
    # count how many commas in the admin input
    count_comma = 0
    ONE=1
    # go through the admin_input
    for i in range(len(admin_input)):
        if (admin_input[i] == COLON):
            count_colon += ONE
        if (admin_input[i] == COMMA):
            count_comma += ONE
    NOT_EXSIST=0
    # in case there is no colon-thats mean there is no product and price
    if (count_colon==NOT_EXSIST):
        print("Error: not enough data.")
        return
    #in case there is no comma-thats mean there is no price
    if (count_comma==NOT_EXSIST):
        print("Error: not enough data.")
        return
    count_comma=0
    ZERO=0
    #split the admin_input into catgories and product&price
    admin_array=admin_input.split(":")
    #count the how many catgories the admin input
    for i in range(len(admin_array[ZERO])):
        if(admin_array[ZERO][i]==COMMA):
            count_comma+=ONE
    name_catgories=[]
    #split the catgory names
    name_catgories = admin_array[ZERO].split(",")
    count_comma = 0
    #count how many commas in the string of the product&price
    for i in range(len(admin_array[ONE])):
        if (admin_array[ONE][i] == COMMA):
            count_comma += ONE
    #in case there is no comma that means there is no price
    if(count_comma==NOT_EXSIST):
        print("Error: not enough data.")
        return
    #exstracts the product and the price
    product_and_price=admin_array[ONE].split(',')
    product=product_and_price[ZERO].lstrip()
    price=product_and_price[ONE].lstrip()
    insert_the_product(categories, query_by_category_caching, query_by_item_caching, name_catgories,
                       product_and_price,price, product)


"""
* Function Name:save
* Input:categories,out
* Output:
* Function Operation:the function prints all the store by categories into file 
"""
def save(categories,out):
    #open the file which the function will print the store into
    store_file=open(out,'w')
    #go through all the products by categories
    for i in sorted(categories.keys()):
        #save the catgory name into the file
        store_file.write(i)
        #write : after the catgory name
        store_file.write(COLON)
        #go throuth the products in the store
        for k, j in enumerate (sorted(categories[i])):
            space=' '
            store_file.write(space)
            store_file.write(j)
            store_file.write(COMMA)
            store_file.write(space)
            #save the product into the file
            store_file.write(categories[i][j])
            store_file.write(SEMICOLON)
        line_drop='\n'
        #go down line by category
        store_file.write(line_drop)
    TREE_ARGUMENT=3
    print('Store saved to "'+sys.argv[TREE_ARGUMENT]+'".')
    store_file.close()

"""
* Function Name:admin_panel
* Input:categories,query_by_category_caching,query_by_item_caching,admin_file,out
* Output:
* Function Operation:this function is intended for store manager only.
*                    the function Offers 2 action options for the store manager 
*                    the function executes the option selected by the store manager 
*                    the function does that until the manger press 0
"""
def admin_panel(categories,query_by_category_caching,query_by_item_caching,admin_file,out):
    #get password form the user for unsure the user is really the manger of the store
    password = input("Password: ")
    #read the right password form file
    admin=open(admin_file,"r")

    #in case the password the user input is not like the same the right password
    if (password != (admin.readline().strip('\n'))):
        print("Error: incorrect password, returning to main menu.")
        #return to the main menu
        return
    admin.close()
    #print the menu of all the options the manger of the store can do
    admin_menu()
    #the choose of the manger
    admin_choose=input()
    #the function offers the mangger some options to do until he press 0
    while (admin_choose != '0'):
        #in case the manger press 1
        if (admin_choose == '1'):
            #add new product into the store or update price of exsist product
            insert_or_update_an_item(categories,query_by_category_caching,query_by_item_caching)
            admin_menu()
            #reinput
            admin_choose = input()
            continue
        # in case the manger press 2
        if (admin_choose == '2'):
            #print all the store into a file
            save(categories,out)
            admin_menu()
            # reinput
            admin_choose = input()
            continue
        #in case the manger did not press 0-2
        print("Error: unrecognized operation.")
        admin_menu()
        admin_choose = input()

def main():
    FIRST_ARGUMENT=1
    SECOUND_ARGUMENT=2
    THIRD_ARGUMENT=3
    #file with all the categories and products in the store
    store = sys.argv[FIRST_ARGUMENT]
    #file with the password of the store manager
    admin_file = sys.argv[SECOUND_ARGUMENT]
    #file for saving all the products in the store
    out = sys.argv[THIRD_ARGUMENT]
    #all the products of the store divided by categories
    categories = dict()
    file = open(store, "r")

    """
    put all the products with their prices in "categories"
    """
    for line in file:
        #skip blank line in the file
        if not line.strip():
            continue
        #dictionary of products and their prices
        products = dict()
        #divided the products and their prices from the category name
        category_name, all_products = line.split(':')
        #remove spaces
        category_name = category_name.lstrip(' ')
        #remove spaces
        all_products = all_products.lstrip(' ')
        #remove \n
        all_products = all_products.rstrip("\n")
        #separate the diffrence products
        product=all_products.rstrip(";").split(";")
        for p in product:
            p = p.lstrip(' ')
            if(p!=''):
                # separate the product name and the price
                product_name, price = p.split(",")
                # adjust price to product
                products[product_name] = price
                # remove spaces from the product name
                product_name = product_name.lstrip(' ')
                # remove spaces from the price of the product
                products[product_name] = products[product_name].lstrip(' ')
        #adjust all the products in the current category
        categories[category_name] = products
    file.close()
    #Send to the menu all the action options that can be done in the store
    menu(categories,admin_file,out)

if __name__ == '__main__':
    main()












