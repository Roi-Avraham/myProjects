#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "ex5.h"

int main()
{
	//index of the courses array
	int index = 0;
	//the courses array
	course courses[SIZE];
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
			//Insert or update a class
			insertOrUpdateClass(courses, &index);
			//print only the shor menu 
			printShortMenu();
			//the next choose of the user 
			scanf(" %c", &choose);
			//move to the next task
			continue;
		}
		if (choose == '2')
		{
			//Insert or update a student
			insertOrUpdateStudent(courses, &index);
			//print only the shor menu
			printShortMenu();
			//the next choose of the user
			scanf(" %c", &choose);
			//move to the next task
			continue;
		}
		if (choose == '3')
		{
			//Remove a student from classes
			removeStudentFromClasses(courses, &index);
			//print only the short menu
			printShortMenu();
			//the next choose of the user
			scanf(" %c", &choose);
			//move to the next task
			continue;
		}
		if (choose == '4')
		{
			//Print a class
			printClass(courses, &index);
			//print only the short menu
			printShortMenu();
			//the next choose of the user
			scanf(" %c", &choose);
			//move to the next task
			continue;
		}
		if (choose == '5')
		{
			//Print all classes with their computed scores
			printComputedScores(courses, &index);
			//print only the short menu
			printShortMenu();
			//the next choose of the user
			scanf(" %c", &choose);
			//move to the next task
			continue;
		}
		if (choose == '6')
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
		printf("\nSelect the next operation (insert 6 for the entire menu):\n");
		//the next choose of the user
		scanf(" %c", &choose);
	}
	//realse memory
	freeAll(courses, &index);
	return 0;
}
