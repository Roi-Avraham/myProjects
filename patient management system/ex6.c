#define _CRT_SECURE_NO_WARNINGS

/****************************
//*Name Roi Avraham
//*ID 318778081
//*Group(01-cs,02-math) 01
//*Assignment Ex6
//***************************/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "ex6.h"
#include "Utils.h"
#include "queue.h"

#define SPACE 3
//the left side in the tree
#define LEFT 1
//the middle side in the tree
#define MIDDLE 2
//the right side in the tree
#define RIGHT 3
#define FAILED_ALLOC 1
#define CORRECT_INPUT 1

/****************************************************************************************
* Function Name:printStayHome
* Input:char* name,int id
* Output:void
* Function Operation:the function gets name and id and print "#StayHome! name id"
******************************************************************************************/
static void printStayHome(char* name, int id)
{
	printf("#StayHome! %s %d\n", name, id);
}

/****************************************************************************************
* Function Name:printNameAndId
* Input:char* name,int id
* Output:void
* Function Operation:the function gets name and id and print "name id"
******************************************************************************************/
static void printNameAndId(char* name, int id)
{
	printf("%s %d\n", name, id);
}

/****************************************************************************************
* Function Name:BFS
* Input:void* root, void(*print)(char*,int)
* Output:void
* Function Operation:the function print the family in BFS order
******************************************************************************************/
static void BFS(void* root, void(*print)(char*, int))
{
	//in case the family was not created 
	if (root == NULL)
	{
		return;
	}
	//the current member the function prints
	Trin_Ari* current = root;
	//create the queue the function use for printing all the members in the family in BFS
	Queue* q = createQueue();
	//enter the current member into the queue
	enQueue(q, root);
	//printing until the queue is empty(there is no left members to print)
	while (!isEmpty(q))
	{
		//the current member is the first member in the queue
		current = q->front->key;
		//the id of the member 
		int id = current->id;
		//print the name and the id of the member according to mission 
		print(current->name, id);
		//removing the member from the queue after printing him
		deQueue(q);
		//in case the member has left member 
		if (current->left != NULL)
		{
			//enter the left member into the queue
			if (enQueue(q, current->left) == FAILED_ALLOC)
			{
				//the malloc in the queue failed so free the family 
				freeAll(root);
				exit(1);
			}
		}
		//in case the member has middle member 
		if (current->middle != NULL)
		{
			//enter the middle member into the queue
			if (enQueue(q, current->middle) == FAILED_ALLOC)
			{
				//the malloc in the queue failed so free the family 
				freeAll(root);
				exit(1);
			}
		}
		//in case the member has right member 
		if (current->right != NULL)
		{
			//enter the right member into the queue
			if (enQueue(q, current->right) == FAILED_ALLOC)
			{
				//the malloc in the queue failed so free the family 
				freeAll(root);
				exit(1);
			}
		}
	}
	//free the queue
	destroyQueue(q);
}

/*****************************************
* Function Name:printMenu
* Input:
* Output:void
* Function Operation:print all the menu
******************************************/
void printMenu()
{
	printf("Please select an operation:\n\t0. Exit.\n\t1. Create family."\
		"\n\t2. Add family member.\n\t3. Print Family-Tree."\
		"\n\t4. Bidud.\n\t5. Superspreader."\
		"\n\t6. Vaccine.\n\t7. Print the menu.\n");
}

/******************************************
* Function Name:printShortMenu
* Input:
* Output:void
* Function Operation:print the short menu
*******************************************/
void printShortMenu()
{
	printf("Select the next operation (insert 7 for the entire menu):\n");
}

/********************************************************************
* Function Name:existsFamily
* Input:Trin_Ari* root
* Output:int 1 or 0
* Function Operation:the function checks if the tree is null or not
*********************************************************************/
static int existsFamily(Trin_Ari* root)
{
	if (root == NULL)
	{
		return 0;
	}
	return 1;
}

/********************************************************************************
* Function Name:notExistsFamily
* Input:Trin_Ari* root
* Output:int 1 or 0
* Function Operation:the function checks if the family is already created or not
**********************************************************************************/
static int notExistsFamily(Trin_Ari* root)
{
	//in case the family was not created yet 
	if (!existsFamily(root))
	{
		printf("Please create the family first\n");
		return 1;
	}
	return 0;
}

/********************************************************************************
* Function Name:createFamily
* Input:Trin_Ari* root, int id, char* name
* Output:Trin_Ari*
* Function Operation:the function puts name and id into the first family member
**********************************************************************************/
static Trin_Ari* createFamily(Trin_Ari* root, int id, char* name)
{
	//create the family 
	root = (Trin_Ari*)malloc(sizeof(Trin_Ari));
	//malloc failed 
	if (root == NULL)
	{
		printf("Malloc Failed!\n");
		//realse memory
		freeAll(root);
		exit(1);
	}
	//create the name of the member 
	root->name = (char*)malloc((strlen(name) + 1) * sizeof(char));
	//malloc failed 
	if (root->name == NULL)
	{
		printf("Malloc Failed!\n");
		//realse memory
		freeAll(root);
		exit(1);
	}
	//put the name into the name of the member 
	strcpy(root->name, name);
	//put the id into the name of the member
	root->id = id;
	//initialize the left to null
	root->left = NULL;
	//initialize the middle to null
	root->middle = NULL;
	//initialize the left to null
	root->right = NULL;
	//print hello massage 
	printf("Hello %s, ID: %d\n", root->name, root->id);
	return root;
}

/********************************************************************************
* Function Name:buildFamily
* Input:Trin_Ari* root
* Output:Trin_Ari*
* Function Operation:the function receives from the user name and id and create
*                    family (just only if the family was not created yet) and puts
*                    the name and the id into the first member of the family(by
*                    calling other function) and return the family
**********************************************************************************/
Trin_Ari* buildFamily(Trin_Ari* root)
{
	//checks if the family was already created
	if (existsFamily(root))
	{
		printf("The family has already been created\n");
		return root;
	}
	//allocate memory to the name of the first member 
	char* nameFirstMember = (char*)malloc(50 * sizeof(char));
	//in case the malloc failed 
	if (nameFirstMember == NULL)
	{
		printf("Malloc Failed!\n");
		//realse memory
		freeAll(root);
		exit(1);
	}
	//the id of the first member
	int idFirstMember = 0;
	printf("enter name\n");
	scanf(" %[^\n]", nameFirstMember);
	printf("enter ID\n");
	scanf("%d", &idFirstMember);
	//create the family 
	root = createFamily(root, idFirstMember, nameFirstMember);
	free(nameFirstMember);
	return root;
}

/************************************************************************************
* Function Name:finidInFamily
* Input:Trin_Ari* root, int id
* Output:Trin_Ari*
* Function Operation:the function receives id and checks if in the family there is
*                    member who has this id and return him if there is no one with
*                    this id the function return null
**************************************************************************************/
static Trin_Ari* finidInFamily(Trin_Ari* root, int id)
{
	//in case the root is null
	if (root == NULL) 
	{
		return NULL;
	}
	//in case the member has the id
	if (root->id == id)
	{
		return root;
	}
	//search the member with the id in the left side of the family 
	Trin_Ari* currrentMember = finidInFamily(root->left, id);
	if (currrentMember == NULL)
	{
		//search the member with the id in the middle side of the family 
		currrentMember = finidInFamily(root->middle, id);
	}
	if (currrentMember == NULL)
	{
		//search the member with the id in the right side of the family 
		currrentMember = finidInFamily(root->right, id);
	}
	//return the member in the family who has the id 
	return currrentMember;
}

/************************************************************************************
* Function Name:add
* Input:Trin_Ari* root , int id, char* name
* Output:Trin_Ari*
* Function Operation:the function receives id and add him into the family
**************************************************************************************/
static Trin_Ari* add(Trin_Ari* root, int id, char* name)
{
	//initialize the sides of the new member 
	root->left = NULL;
	root->right = NULL;
	root->middle = NULL;
	root->id = id;
	//the name of the new member
	root->name = (char*)malloc((strlen(name) + 1) * sizeof(char));
	if (root->name == NULL)
	{
		printf("Malloc Failed!\n");
		exit(1);
	}
	strcpy(root->name, name);
	printf("Hello %s, ID: %d\n", root->name, root->id);
	//retrun the new member 
	return root;
}

/************************************************************************************************
* Function Name:addToFamilyTree
* Input:Trin_Ari* root, int id, char* name
* Output:Trin_Ari*
* Function Operation:the function receives id and add him into the family into the avaible side
*************************************************************************************************/
static void addToFamilyTree(Trin_Ari* root, int id, char* name)
{
	//in case the left side is available then add to new member to here
	if (root->left == NULL)
	{
		root->left = (Trin_Ari*)malloc(sizeof(Trin_Ari));
		if (root->left == NULL) {
			printf("Malloc Failed!\n");
			//realse memory
			freeAll(root);
			exit(1);
		}
		root->left = add(root->left, id, name);
		return;
	}
	//in case the middle side is available then add to new member to here
	if (root->middle == NULL)
	{
		root->middle = (Trin_Ari*)malloc(sizeof(Trin_Ari));
		if (root->middle == NULL) {
			printf("Malloc Failed!\n");
			//realse memory
			freeAll(root);
			exit(1);
		}
		root->middle = add(root->middle, id, name);
		return;
	}
	//in case the right side is available then add to new member to here
	if (root->right == NULL)
	{
		root->right = (Trin_Ari*)malloc(sizeof(Trin_Ari));
		if (root->right == NULL) {
			printf("Malloc Failed!\n");
			//realse memory
			freeAll(root);
			exit(1);
		}
		root->right = add(root->right, id, name);
		return;
	}
}

/********************************************************************************************
* Function Name:addMeeting
* Input:Trin_Ari* root
* Output:void
* Function Operation:the function checks if the family was not created yet and if not
*                    then the function receives id of the member who wants to make
*					 an appointment from the user and if the person is really exsist in
*                    the family then the function receives id and name of a person
*                    and add him to the family (as left or middle or right (in this order)
*                    of the person who wants to make the appointment)
*********************************************************************************************/
void addMeeting(Trin_Ari* root)
{
	//in case the famly was not created yet 
	if (notExistsFamily(root))
	{
		return;
	}
	//the id of the person who wants to make an appointment
	int idExMember = 0;
	printf("Enter the ID of the person who wants to make an appointment?\n");
	scanf("%d", &idExMember);
	//checks if the the person is in the family or not 
	Trin_Ari* member = finidInFamily(root, idExMember);
	if (member == NULL)
	{
		printf("There is no ID %d\n", idExMember);
		return;
	}
	//flag which tells if the input from the user is valied or not 
	int legal = 0;
	//Continues to ask the user to enter input as long as it is invalid
	while (!legal)
	{
		//in case the person already meet 3 pepole
		if (member->left != NULL && member->right != NULL && member->middle != NULL)
		{
			printf("%s ID: %d can't meet more than 3 members!\n", member->name, member->id);
			return;
		}
		//the name of the member who the person in the family wants to meet 
		char* name = (char*)malloc(50 * sizeof(char));
		//in case the malloc failed 
		if (name == NULL)
		{
			printf("Malloc Failed!\n");
			//realse memory
			freeAll(root);
			exit(1);
		}
		int id = 0;
		printf("enter name\n");
		scanf(" %[^\n]", name);
		printf("enter ID\n");
		scanf("%d", &id);
		//check the member is not already in the family 
		Trin_Ari* member1 = finidInFamily(root, id);
		if (member1 != NULL)
		{
			printf("ID %d belongs to %s\n", member1->id, member1->name);
		}
		else
		{
			//the input is valid
			legal = CORRECT_INPUT;
			//make the appointment
			addToFamilyTree(member, id, name);
		}
		free(name);
	}
}

/************************************************************************************
* Function Name:printPreOrder
* Input:Trin_Ari* root
* Output:void
* Function Operation:the function prints the names and the ids of all the members
*                    in the family in pre order
**************************************************************************************/
static void printPreOrder(Trin_Ari* root)
{
	//no more members 
	if (root == NULL)
	{
		return;
	}
	//print the "perent"
	printf("%s ID: %d\n", root->name, root->id);
	//print the left 
	printPreOrder(root->left);
	//print the middle
	printPreOrder(root->middle);
	//print the right 
	printPreOrder(root->right);
}

/************************************************************************************
* Function Name:printLeftOrder
* Input:Trin_Ari* root
* Output:void
* Function Operation:the function prints the names and the ids of all the members
*                    in the family in left order
**************************************************************************************/
static void printLeftOrder(Trin_Ari* root)
{
	//no more members
	if (root == NULL)
	{
		return;
	}
	//print the left
	printLeftOrder(root->left);
	//print the "perent"
	printf("%s ID: %d\n", root->name, root->id);
	//print the middle
	printLeftOrder(root->middle);
	//print the right 
	printLeftOrder(root->right);
}

/************************************************************************************
* Function Name:stayHome
* Input:Trin_Ari* root
* Output:void
* Function Operation:put in isolation the "parent" of the suspect(the member who had
*                    the id the function gets) as well as all the initial contacts of
*					 the suspect
**************************************************************************************/
static void stayHome(Trin_Ari* root, int id, Trin_Ari* per)
{
	//print stay home to the perent 
	printf("#StayHome! %s %d\n", per->name, per->id);
	//print stay home to the suspect
	printf("#StayHome! %s %d\n", root->name, root->id);
	//check if there is left meeting of the suspect
	if (root->left != NULL)
	{
		//print stay home to the left meeting of the suspect
		printf("#StayHome! %s %d\n", root->left->name, root->left->id);
	}
	//check if there is middle meeting of the suspect
	if (root->middle != NULL)
	{
		//print stay home to the  middle meeting of the suspect
		printf("#StayHome! %s %d\n", root->middle->name, root->middle->id);
	}
	//check if there is right meeting of the suspect
	if (root->right != NULL)
	{
		//print stay home to the right meeting of the suspect 
		printf("#StayHome! %s %d\n", root->right->name, root->right->id);
	}
}

/************************************************************************************
* Function Name:perent
* Input:Trin_Ari* root
* Output:void
* Function Operation:the function recives the family and id and return the "perent" of
*                    the member who has the id (if there is no perent the function
*                    return null
**************************************************************************************/
static Trin_Ari* perent(Trin_Ari* root, int id)
{
	//in case there is no perent to the member with the id 
	if (root == NULL)
	{
		return NULL;
	}
	//in case the member is the perent of the member with the id 
	if ((root->left != NULL && root->left->id == id) ||
		(root->middle != NULL && root->middle->id == id) ||
		(root->right != NULL && root->right->id == id))
	{
		//return the perent 
		return root;
	}
	//search the pernet with the id in the left side of the family 
	Trin_Ari* currentMember = perent(root->left, id);
	if (currentMember == NULL)
	{
		if (root->middle != NULL)
		{
			//search the pernet with the id in the left middle of the family 
			currentMember = perent(root->middle, id);
		}
	}
	if (currentMember == NULL)
	{
		if (root->right != NULL)
		{
			//search the pernet with the id in the right side of the family 
			currentMember = perent(root->right, id);
		}
	}
	//return the perent of the member  
	return currentMember;
}

/************************************************************************************
* Function Name:insulation
* Input:Trin_Ari* root
* Output:void
* Function Operation:the function gets id of a suspected member in the family and print
*                    to all his meetings(if there are exist) including him and his
*                    perent(if exist) to stay home
**************************************************************************************/
void insulation(Trin_Ari* root)
{
	//the id of the suspected member 
	int id = 0;
	//in case there is no family at all
	if (notExistsFamily(root))
	{
		//print the menu
		return;
	}
	//get the id of the suspected member from the user 
	printf("Enter the ID of the suspected member\n");
	scanf("%d", &id);
	//check if the suspected member is in the family
	Trin_Ari* suspectedMember = finidInFamily(root, id);
	//in case the suspected member is not in the family 
	if (suspectedMember == NULL)
	{
		printf("ID %d does not exist\n", id);
		return;
	}
	//get to the "perent" of the suspected member 
	Trin_Ari* per = perent(root, id);
	//in case the suspected member has no perent 
	if (per == NULL)
	{
		//print stay home to the suspected member 
		printf("#StayHome! %s %d\n", suspectedMember->name, suspectedMember->id);

		/*
		print stay home to all his sides meetings(left,middle,right in this order)
		if they exsist
		*/
		if (suspectedMember->left != NULL)
		{
			printf("#StayHome! %s %d\n", suspectedMember->left->name, suspectedMember->left->id);
		}
		if (suspectedMember->middle != NULL)
		{
			printf("#StayHome! %s %d\n", suspectedMember->middle->name, suspectedMember->middle->id);
		}
		if (suspectedMember->right != NULL)
		{
			printf("#StayHome! %s %d\n", suspectedMember->right->name, suspectedMember->right->id);
		}
	}
	//in case the suspected member has perent 
	else
	{
		//print stay home to all
		stayHome(suspectedMember, id, per);
	}

}

/****************************************************************************************
* Function Name:insulation
* Input:Trin_Ari* root, int id
* Output:int
* Function Operation:the function get id of the perent of the member how get vaccinated
*                    and return the side the member get vaccinated is
******************************************************************************************/
static int  whichSide(Trin_Ari* root, int id)
{
	//in case the vaccinated member is in the left side 
	if (root->left != NULL && root->left->id == id)
	{
		//return 1 
		return LEFT;
	}
	//in case the vaccinated member is in the middle side 
	if ((root->middle != NULL && root->middle->id == id))
	{
		//retrun 2 
		return MIDDLE;
	}
	//in case the vaccinated member is in the right side
	if (root->right != NULL && root->right->id == id)
	{
		//retrun 3 
		return RIGHT;
	}
}

/****************************************************************************************
* Function Name:vaccine
* Input:Trin_Ari* root
* Output:Trin_Ari*
* Function Operation:the function get id of the vaccinated member and remove him and all
*                    his sub-tree and print to anyone the function remove that he survived
*                    the function return the family after the removing the sub tree
******************************************************************************************/
Trin_Ari* vaccine(Trin_Ari* root)
{
	//the id of the vaccinated member
	int id = 0;
	//in case the family was not created yet 
	if (notExistsFamily(root))
	{
		return root;
	}
	//get from the user the id of the vaccinated member
	printf("Who got vaccinated (ID)?\n");
	scanf("%d", &id);
	//check if the vaccinated member is in the family 
	Trin_Ari* vaccinatedMember = finidInFamily(root, id);
	//in case the vaccinated member is not in the family 
	if (vaccinatedMember == NULL)
	{
		printf("There is no ID %d\n", id);
		return root;
	}
	//find the perent of the vaccinated member
	Trin_Ari* pr = perent(root, id);
	//in case the vaccinated member has no perent 
	if (pr == NULL)
	{
		//remove all the family 
		freeAll(root);
		//initialize the family 
		root = NULL;
		return root;
	}
	else
	{
		int side = 0;
		//the side the vaccinated member is in relation to the parent
		side = whichSide(pr, id);
		//remove all the sub tree of the vaccinated member
		freeAll(vaccinatedMember);
		//initialize the side of the vaccinated member was in relation to the parent
		if (side == LEFT)
		{
			pr->left = NULL;
		}
		if (side == MIDDLE)
		{
			pr->middle = NULL;
		}
		if (side == RIGHT)
		{
			pr->right = NULL;
		}
	}
	//retrun the root after the removing the sub tree 
	return root;
}

/****************************************************************************************
* Function Name:freeAll
* Input:Trin_Ari* root
* Output:Trin_Ari*
* Function Operation:the function get part of the family or all the family the removing
*                    all the members
******************************************************************************************/
void freeAll(Trin_Ari* root)
{
	if (root == NULL)
	{
		return;
	}
	freeAll(root->left);
	freeAll(root->middle);
	freeAll(root->right);
	//print the member had survived the disease (the member removed)
	printf("%s ID: %d Survived!\n", root->name, root->id);
	//release the alloc of the name of the member 
	free(root->name);
	//release the alloc of the member 
	free(root);
}

/****************************************************************************************
* Function Name:menuPrint
* Input:
* Output:void
* Function Operation:the function print the menu of the prints
******************************************************************************************/
static void menuPrint()
{
	printf("Please select an operation:\n\t0. Return to the main menu.\n\t1. Print Trin-Ari family."\
		"\n\t2. Print Pre-order.\n\t3. Print Left-order."\
		"\n\t4. Print BFS.\n");
}

/****************************************************************************************
* Function Name:printTreeFamily
* Input:Trin_Ari* root
* Output:void
* Function Operation:the function print the family according to the user's choice
******************************************************************************************/
void printTreeFamily(Trin_Ari* root)
{
	//print the menu 
	menuPrint();
	char choosePrint;
	//input user 
	scanf(" %c", &choosePrint);

	/*
	ask from the user to choose a way to print the
	family until the user choose valid choose(0-4)
	*/
	while (choosePrint != '0')
	{
		//print the family in visual way 
		if (choosePrint == '1')
		{
			print2DUtil(root, SPACE);
			printMenu();
			return;
		}
		//print the family in pre order way 
		if (choosePrint == '2')
		{
			printPreOrder(root);
			//print the menu
			printMenu();
			return;
		}
		//print the family in left order way 
		if (choosePrint == '3')
		{
			printLeftOrder(root);
			//print the menu
			printMenu();
			return;
		}
		//print the family in BFS way
		if (choosePrint == '4')
		{
			BFS(root, printNameAndId);
			//print the menu
			printMenu();
			return;
		}
		//invalid choose (not 0-4)
		printf("Error: unrecognized operation.\n");
		menuPrint();
		//get from the user aother choose 
		scanf(" %c", &choosePrint);
	}
	printMenu();
}

/****************************************************************************************
* Function Name:stayInHome
* Input:Trin_Ari* root, int id, Trin_Ari* per
* Output:void
* Function Operation:the function print the family according to the user's choice
******************************************************************************************/
static void stayInHome(Trin_Ari* root, int id, Trin_Ari* per)
{
	//print the "father" of the Superspreader member
	if (per != NULL)
	{
		printf("#StayHome! %s %d\n", per->name, per->id);
	}
	if (root == NULL)
	{
		return;
	}
	//print to all the sub tree of the Superspreader member
	BFS(root, printStayHome);
}

/****************************************************************************************
* Function Name:superSpreader
* Input:Trin_Ari* root
* Output:void
* Function Operation:the function print "stay home" to all the sub tree of the suspected
*                    Superspreader member
******************************************************************************************/
void superSpreader(Trin_Ari* root)
{
	//the id of the suspected Superspreader member
	int id = 0;
	//in case the family was not created yet 
	if (notExistsFamily(root))
	{
		return;
	}
	printf("Enter the ID of the suspected Superspreader\n");
	scanf("%d", &id);
	//check if the suspected Superspreader member is in the family 
	Trin_Ari* spreader = finidInFamily(root, id);
	//in case the suspected Superspreader member is not in the family 
	if (spreader == NULL)
	{
		printf("ID %d does not exist\n", id);
		return;
	}
	//find the perent of the suspected Superspreader member
	Trin_Ari* per = perent(root, id);

	/*
	print stay home to all the sub tree of the suspected
	Superspreader including him and his perent
	*/
	stayInHome(spreader, id, per);
}