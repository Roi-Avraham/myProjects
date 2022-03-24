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

int main()
{
	//the initialization of the family
	Trin_Ari* root = NULL;
	//print the menu
	printMenu();
	//the choose of the user 
	char choose;
	//input user 
	scanf(" %c", &choose);
	//all the tasks in the menu
	while (choose != '0')
	{
		if (choose == '1')
		{
			//build the family 
			root=buildFamily(root);
			//print only the shor menu 
			printShortMenu();
			//the next choose of the user 
			scanf(" %c", &choose);
			//move to the next task
			continue;
		}
		if (choose == '2')
		{
			//add members to the family 
			addMeeting(root);
			//print only the short menu
			printShortMenu();
			//the next choose of the user
			scanf(" %c", &choose);
			//move to the next task
			continue;
		}
		if (choose == '3')
		{
			//print the family 
			printTreeFamily(root);
			//the next choose of the user
			scanf(" %c", &choose);
			//move to the next task
			continue;
		}
		if (choose == '4')
		{
			insulation(root);
			//print only the short menu
			printShortMenu();
			//the next choose of the user
			scanf(" %c", &choose);
			//move to the next task
			continue;
		}
		if (choose == '5')
		{
			superSpreader(root);
			//print only the short menu
			printShortMenu();
			//the next choose of the user
			scanf(" %c", &choose);
			//move to the next task
			continue;
		}
		if (choose == '6')
		{
			root=vaccine(root);
			//print only the short menu
			printShortMenu();
			//the next choose of the user
			scanf(" %c", &choose);
			//move to the next task
			continue;
		}
		if (choose == '7')
		{
			//Print the menu
			printMenu();
			//the next choose of the user
			scanf(" %c", &choose);
			//move to the next task
			continue;
		}
		//invailed input
		printf("Error: unrecognized operation.");
		printf("\nSelect the next operation (insert 7 for the entire menu):\n");
		//the next choose of the user
		scanf(" %c", &choose);
	}	
	freeAll(root);
	return 0;
}