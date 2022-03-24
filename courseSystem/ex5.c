#define _CRT_SECURE_NO_WARNINGS

/****************************
//*Name Roi Avraham
//*ID 318778081
//*Group(01-cs,02-math) 01
//*Assignment Ex5
//***************************/
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "ex5.h"

/*****************************************
* Function Name:printMenu
* Input:
* Output:void
* Function Operation:print all the menu
******************************************/
void printMenu()
{
	printf("Please select an operation:\n\t0. Exit.\n\t1. Insert or update a class."\
		"\n\t2. Insert or update a student.\n\t3. Remove a student from classes."\
		"\n\t4. Print a class.\n\t5. Print all classes with their computed scores."\
		"\n\t6. Print the menu.");
	printf("\n");
}

/******************************************
* Function Name:printShortMenu
* Input:
* Output:void
* Function Operation:print the short menu
*******************************************/
void printShortMenu()
{
	printf("Select the next operation (insert 6 for the entire menu):");
	printf("\n");
}

/*************************************************************************
* Function Name:freeAll
* Input:course* courses, int* index
* Output:void
* Function Operation:the function realse all the memory of the students
*                    in all the courses
**************************************************************************/
void freeAll(course* courses, int* index)
{
	//go through the array of the courses
	for (int i = 0; i < *index; i++)
	{
		//release the memory
		free(courses[i].students);
	}
}

/*************************************************************************
* Function Name:vaildInputCourses
* Input:course* courses, char courseSt[], int* index
* Output:1 or 0
* Function Operation:the function checks if the number of the course
*                    is already exists in the array of the courses and 
*                    return 1 else return 0
**************************************************************************/
static int vaildInputCourses(course* courses, char courseSt[], int* index)
{
	//the number of the exists courses
	int insertingIndex = *index;
	//go through the exists courses 
	for (int i = 0; i < insertingIndex; i++)
	{
		//check if numberOfCourse is already exists
		if (!strcmp(courses[i].numberOfCourse, courseSt))
		{
			//numberOfCourse is already exists
			return 1;
		}
	}
	//the course is not exists
	return 0;
}

/*************************************************************************
* Function Name:checkNumberCourse
* Input:char userStr[]
* Output:index
* Function Operation:the function checks if the str is 5 digits (valid 
*                    number course)
**************************************************************************/
static int checkNumberCourse(char userStr[])
{
	//the index of the userStr
	int j = 0;
	//skip spaces
	while (userStr[j] == ' ')
	{
		j++;
	}
	//the valid len of the course number
	int lenNumberOfCourse = 5;
	//go through the number course 
	for (int i = j; i < j + lenNumberOfCourse; i++)
	{
		//in case the char is not a digit
		if (userStr[i] < '0' || userStr[i]>'9')
		{
			printf("Error: invalid class number or name.\n");
			return 0;
		}
	}
	int index = lenNumberOfCourse;
	//invailid string 
	if (userStr[index] != ' ')
	{
		printf("Error: invalid class number or name.\n");
		return 0;
	}
	++index;
	//check if the the start of the name of the course is vailed 
	if (userStr[index] != ' ' && (userStr[index] < 'a' || userStr[index] > 'z') &&
		(userStr[index] < 'A' || userStr[index] > 'Z') &&
		(userStr[index] < '0' || userStr[index] > '9'))
	{
		printf("Error: invalid class number or name.\n");
		return 0;
	}
	//return the place the function arrived in the string 
	return index;
}

/*************************************************************************
* Function Name:checkCourseData
* Input:char userStr[]
* Output:0 or 1
* Function Operation:the function checks if the str is represent number of
*                    course and name of course
**************************************************************************/
static int checkCourseData(char userStr[])
{
	//check if the str include number of course
	int index = checkNumberCourse(userStr);
	//check if the number of the course is invailed
	if (index == 0)
	{
		return 0;
	}
	//skip spaces
	while (userStr[index] == ' ')
	{
		index++;
	}
	//count the number of the chars in the name 
	int counter = 0;
	//count the len of the name without spaces 
	int countWithOutSpaces = 0;
	//the vailed len of the name course
	int lenNameCourseValue = 30;
	//go through the string-check if the string is also reprsent name of course
	for (int i = index; i < strlen(userStr); i++)
	{
		//count the len of the name Course 
		++counter;
		//the name of the course is too long 
		if (counter > lenNameCourseValue)
		{
			printf("Error: invalid class number or name.\n");
			return 0;
		}
		//check if the string included only chars that can reprsent name of course
		if (userStr[i] != ' ' && (userStr[i] < 'a' || userStr[i] > 'z') &&
			(userStr[i] < 'A' || userStr[i] > 'Z') &&
			(userStr[i] < '0' || userStr[i] > '9'))
		{
			printf("Error: invalid class number or name.\n");
			return 0;
		}
		//the char is not space 
		if (userStr[i] != ' ')
		{
			countWithOutSpaces++;
		}
	}
	//in case there is no name of course 
	if (!countWithOutSpaces)
	{
		printf("Error: invalid class number or name.\n");
		return 0;
	}
	//the string reprsent name and number of course
	return 1;
}

/*************************************************************************
* Function Name:numberOfCourse
* Input:char userStr[], char* number
* Output:void
* Function Operation:the function puts in "number" array the number of
*                    the course that the user enter
**************************************************************************/
static void numberOfCourse(char userStr[], char* number)
{
	int j = 0;
	//skip spaces
	while (userStr[j] == ' ')
	{
		j++;
	}
	//the index of the "number" array 
	int k = 0;
	//go through the number of the course
	for (int i = j; i < j + SIZE; i++)
	{
		//copy the number of the course to "number"
		number[k] = userStr[i];
		++k;
	}
	number[SIZE] = '\0';
}

/*************************************************************************
* Function Name:nameOfCourse
* Input:char userStr[], char* name
* Output:void
* Function Operation:the function put in "name" array the name of
*                    the course that the user enter
**************************************************************************/
static int nameOfCourse(char userStr[], char* name)
{
	int index = 0;
	//skip spaces 
	while (userStr[index] == ' ')
	{
		++index;
	}
	//skip the number of the course
	index += SIZE;
	while (userStr[index] == ' ')
	{
		++index;
	}
	//the index in the "name" array
	int j = 0;
	//the max len of the course name 
	int lenNameCourseValue = 31;
	//put the chars of the name of the course in "name"
	for (int i = index; i < lenNameCourseValue + index; i++)
	{
		//the user string over 
		if (userStr[i] == '\0')
		{
			//the name is over
			name[j] = '\0';
			return j;
		}
		//put the name in the array 
		name[j] = userStr[i];
		++j;
	}
	//the len of the name of the course
	return j;
}

/*****************************************************************************
* Function Name:newCourse
* Input:char* name, char* number, course* courses, int* index, int len
* Output:void
* Function Operation:the function create new course and put it in the array
*                    of the courses.
******************************************************************************/
static void newCourse(char* name, char* number, course* courses, int* index, int len)
{
	//the number of the courses that are already exsists
	int insertingIndex = *index;
	//there is no place for more coursrs in the array
	if (insertingIndex >= SIZE)
	{
		printf("Unfortunately, there is no room for more classes.\n");
	}
	else
	{
		//inserting the name of the course
		strcpy(courses[insertingIndex].nameCourse, name);
		//inserting the number of the course
		strcpy(courses[insertingIndex].numberOfCourse, number);
		printf("Class \"%s %s\" added.\n", courses[insertingIndex].numberOfCourse,
			courses[insertingIndex].nameCourse);
		//initialize the num of the students in the course
		courses[insertingIndex].numOfStudents = 0;
		////initialize the students array to null
		courses[insertingIndex].students = NULL;
		//now there is one more course in the array
		(*index)++;
	}
}

/***********************************************************************************
* Function Name:addCourse
* Input:char* name, char* number, course* courses, int* index, int len
* Output:void
* Function Operation:the function add new course or update name of exists course
*************************************************************************************/
static void addCourse(char* name, char* number, course* courses, int* index, int len)
{
	//reprsent if course is already exists or not
	int exist = 0;
	//the number of the courses that are already exsists
	int insertingIndex = *index;
	int temp = 0;
	//go through the courses
	for (int i = 0; i < insertingIndex; i++)
	{
		//in case the number of course the user insert is already exsists
		if (!strcmp(courses[i].numberOfCourse, number))
		{
			++exist;
			//updating the name of the course
			strcpy(courses[i].nameCourse, name);
			temp = i;
			break;
		}
	}
	//if the course is already exist
	if (exist)
	{
		printf("Class \"%s %s\" updated.\n", courses[temp].numberOfCourse, courses[temp].nameCourse);
		return;
	}
	else
	{
		//create new course
		newCourse(name, number, courses, index, len);
	}
}

/***********************************************************************************
* Function Name:checkNameStudent
* Input:char userStr[]
* Output:int i(the index in the userStr)
* Function Operation:the function return 1 if the name of studnet is vaild else the
*                    the function return 0
*************************************************************************************/
static int checkNameStudent(char userStr[])
{
	//the index of the userStr
	int i = 0;
	//count the chars in the name
	int counterName = 0;
	//skip the spaces
	while (userStr[i] == ' ')
	{
		i++;
	}
	//the max chars that can be in valied name 
	int maxLenName = 20;
	//check if the name is valid
	while (userStr[i] != ':'&&counterName < maxLenName)
	{
		if ((userStr[i] >= 'a' && userStr[i] <= 'z') ||
			(userStr[i] >= 'A' && userStr[i] <= 'Z') ||
			userStr[i] == ' ')
		{
			++i;
			++counterName;
			continue;
		}
		else
		{
			printf("Error: invalid name, class number or grade.\n");
			return 0;
		}
	}
	//in case there is no name in the string at all
	if (counterName == 0)
	{
		printf("Error: invalid name, class number or grade.\n");
		return 0;
	}
	//check the if the stirng is valid 
	if (userStr[i] != ':')
	{
		printf("Error: invalid name, class number or grade.\n");
		return 0;
	}
	++i;
	return i;
}

/***********************************************************************************
* Function Name:checkTheNumberCourse
* Input:char userStr[], int i, course* courses, int* index
* Output:int i(the index in the userStr)
* Function Operation:the function return 1 if the numer course is vaild else the
*                    the function return 0
*************************************************************************************/
static int checkTheNumberCourse(char userStr[], int i, course* courses, int* index)
{
	//count the number of the courses 
	int counterCourse = 0;
	//skip spaces
	while (userStr[i] == ' ')
	{
		i++;
	}
	char courseSt[6];
	//check if the course is consists from digits only
	for (int j = 0; j < SIZE; j++)
	{
		if (userStr[i] < '0' || userStr[i]>'9')
		{
			printf("Error: invalid name, class number or grade.\n");
			return 0;
		}
		courseSt[j] = userStr[i];
		++i;
	}
	//count the numbers of courses
	++counterCourse;
	courseSt[SIZE] = '\0';
	//check if the course is exsist
	if (!vaildInputCourses(courses, courseSt, index))
	{
		printf("Error: invalid name, class number or grade.\n");
		return 0;
	}
	//check if the string is valid 
	if (userStr[i] != ',')
	{
		printf("Error: invalid name, class number or grade.\n");
		return 0;
	}
	++i;
	//skip spaces
	while (userStr[i] == ' ')
	{
		i++;
	}
	//in case there is no number course at all
	if (!counterCourse)
	{
		printf("Error: invalid name, class number or grade.\n");
		return 0;
	}
	//return the cuurent index of the usrStr 
	return i;
}

/***********************************************************************************
* Function Name:checkTheGrade
* Input:char userStr[], int i, course* courses, int* index
* Output:int 1 or 0
* Function Operation:the function return 1 if the grade is vaild(0-100) else the
*                    the function return 0
*************************************************************************************/
static int checkTheGrade(char userStr[], int i, course* courses, int* index)
{
	//count the number of grades
	int counterGrade = 0;
	//count the digits in the grade
	int count = 0;
	//check if the grade is valid
	while (userStr[i] != '\0' && userStr[i] != ';')
	{
		if (userStr[i] < '0' || userStr[i]>'9')
		{
			printf("Error: invalid name, class number or grade.\n");
			return 0;
		}
		++count;
		//in case the grade has 3 digit and it is not 100
		if (count == LEN_GRADE && (userStr[i - 1] != '0' || userStr[i] != '0'))
		{
			printf("Error: invalid name, class number or grade.\n");
			return 0;
		}
		if (count == LEN_GRADE)
		{
			if (userStr[i - 2] != '1')
			{
				printf("Error: invalid name, class number or grade.\n");
				return 0;
			}
		}
		//count the number of the grades
		++counterGrade;
		++i;
	}
	//in case there is no grade at all or the grade is more than 3 digits
	if (count == 0 || count > LEN_GRADE)
	{
		printf("Error: invalid name, class number or grade.\n");
		return 0;
	}
	//in case there is no numer of course or grade in the string 
	if (counterGrade == 0)
	{
		printf("Error: invalid name, class number or grade.\n");
		return 0;
	}
	++i;
	//skip spaces 
	while (userStr[i] == ' ')
	{
		i++;
	}
	//return the cuurent index of the usrStr
	return i;
}

/***********************************************************************************
* Function Name:checkTheNumberCourseAndGrade
* Input:char userStr[],int i, course* courses, int* index
* Output:int 1 or 0
* Function Operation:the function return 1 if all the number courses and all
*                    all the grades are vaild else the function return 0 (by calling
*					 other functions)
*************************************************************************************/
static int checkTheNumberCourseAndGrade(char userStr[],int i, course* courses, int* index)
{
	//go through the userStr
	while (userStr[i] != '\0')
	{
		i=checkTheNumberCourse(userStr, i, courses, index);
		//check if the current number course is vaild 
		if (!i)
		{
			//the number course is not vaild
			return 0;
		}
		i = checkTheGrade(userStr, i, courses, index);
		//check if the current grade is vaild 
		if (!i)
		{
			//the grade is not vaild
			return 0;
		}
	}
	//all the number courses and all grades are vaildes
	return 1;
}

/***********************************************************************************
* Function Name:checkStudentData
* Input:char userStr[], course* courses, int* index
* Output:void
* Function Operation:the function gets string the user entered.the function retrun 1
*                    if the string represnt name of student,number of exsist course
*                    and vailed grade(0-100).else the function returns 0 and print
*                    a message error (by calling other functions)
*************************************************************************************/
static int checkStudentData(char userStr[], course* courses, int* index)
{
	int i = checkNameStudent(userStr);
	//check if the name is vaild
	if (!i)
	{
		return 0;
	}
    i = checkTheNumberCourseAndGrade(userStr, i, courses, index);
	//check if all the number courses and all the grades are vaildes
	if (!i)
	{
		return 0;
	}
	//the userSter is vaild
	return 1;
}

/***********************************************************************************************
* Function Name:studentData
* Input:char userStr[], char** name, char** numberCourse, char** grade
* Output:void
* Function Operation:the function put the name of the studnet,all numbers of courses and the
*                    the grades the user insert (in the userStr) into arrays
*************************************************************************************************/
static int studentData(char userStr[], char** name, char** numberCourse, char** grade)
{
	//count the number of the courses and the grades
	int counter = 0;
	//the name of the student from the user string 
	char* nameData = strtok(userStr, ":");
	if (nameData != NULL)
	{
		//put the name of the student in name
		*name = nameData;
	}
	//the index of the course/grade
	int i = 0;
	int maxCourses = SIZE;
	//the max of the course 
	while (i < maxCourses)
	{
		//the number of course from the user string 
		char* numberCourseData = strtok(NULL, " ,");
		if (numberCourseData != NULL)
		{
			//put the number course in numberCourse
			numberCourse[i] = numberCourseData;
			//count the amount courses and grades the user insert
			++counter;
		}
		//the grade of the studnet from the user 
		char* gradeData = strtok(NULL, "; ");
		if (gradeData != NULL)
		{
			//put the grade in "grade"
			grade[i] = gradeData;
		}
		i++;
	}
	//return the amount courses and grades the user insert
	return counter;
}

/***********************************************************************************************
* Function Name:addAndUpdate
* Input:course* courses, int* index, char** name, char** number, char** grade, int count
* Output:void
* Function Operation:the function add new student to course or update grade of student in course
*************************************************************************************************/
static void addAndUpdate(course* courses, int* index, char** name, char** number, char** grade, int count)
{
	//go through the courses the student should be add or update 
	for (int j = 0; j < count; j++)
	{
		//go through the exists courses
		for (int i = 0; i < *index; i++)
		{
			if (!strcmp(courses[i].numberOfCourse, number[j]))
			{
				//in case there is no students at all  in the course 
				if (courses[i].numOfStudents == 0)
				{
					//create array of students with size 1 
					courses[i].students = (student*)malloc(1 * sizeof(student));
					//in case the malloc is failed 
					if (courses[i].students->studentName == NULL)
					{
						freeAll(courses, index);
						exit(1);
					}
				}
				//Indicates if the student should be add or update 
				int updated = 0;
				//go through the students in the course 
				for (int k = 0; k < courses[i].numOfStudents; k++)
				{
					//in case the student is already added to the course 
					if (!strcmp(courses[i].students[k].studentName, *name))
					{
						//updating student's grade 
						strcpy(courses[i].students[k].grade, grade[j]);
						//print a message 
						printf("Student \"%s\" updated on class \"%s %s\" with grade %s.\n"
							, courses[i].students[k].studentName, courses[i].numberOfCourse,
							courses[i].nameCourse, courses[i].students[k].grade);
						//indacte the students updated on 
						++updated;
					}
				}
				//in case the students did not updated on and he shouled be added on the course
				if (!updated)
				{
					//add another place in the student array for the new student 
					courses[i].students = (student*)realloc(courses[i].students,
						(courses[i].numOfStudents + 1) * sizeof(student));
					//in case the realloc failed 
					if (courses[i].students == NULL)
					{
						freeAll(courses, index);
						exit(1);
					}
					//insert the grade of the student
					strcpy(courses[i].students[courses[i].numOfStudents].grade, grade[j]);
					//insert the name of the student 
					strcpy(courses[i].students[courses[i].numOfStudents].studentName, *name);
					//print a message
					printf("Student \"%s\" added to class \"%s %s\" with grade %s.\n"
						, courses[i].students[courses[i].numOfStudents].studentName,
						courses[i].numberOfCourse, courses[i].nameCourse,
						courses[i].students[courses[i].numOfStudents].grade);
					//there is one more student now 
					courses[i].numOfStudents++;
				}
			}
		}
	}
}

/***********************************************************************************************
* Function Name:exStudent
* Input:course* courses, int* index, char* checkName
* Output:int 1 or 0
* Function Operation:the function checks if the student is already in at least one of the courses
*************************************************************************************************/
static int exStudent(course* courses, int* index, char* checkName)
{
	//go through the courses 
	for (int i = 0; i < *index; i++)
	{
		//go through the student in the course 
		for (int j = 0; j < courses[i].numOfStudents; j++)
		{
			//in case the student is in the course 
			if (!strcmp(courses[i].students[j].studentName, checkName))
			{
				return 1;
			}
		}
	}
	//in case the student is not in any course 
	return 0;
}

/************************************************************************************************************
* Function Name:checkValidNumberCourse
* Input:char userStr[],int i, course* courses, int* index
* Output:int 1 or 0
* Function Operation:the function checks if string of number courses are vailed(return 1) or not(return 0)
***************************************************************************************************************/
static int checkValidNumberCourse(char userStr[],int i, course* courses, int* index)
{
	//count the number of courses
	int counterCourse = 0;
	//skip spaces 
	while (userStr[i] == ' ')
	{
		i++;
	}
	//go through the numbers of the courses 
	while (userStr[i] != '\0')
	{
		//skip spaces 
		while (userStr[i] == ' ')
		{
			i++;
		}
		//array of the number of the course 
		char courseSt[6];
		for (int j = 0; j < 5; j++)
		{
			//check if the char is digit 
			if (userStr[i] < '0' || userStr[i]>'9')
			{
				printf("Error: invalid name or class number.\n");
				return 0;
			}
			courseSt[j] = userStr[i];
			++i;
		}
		++counterCourse;
		courseSt[5] = '\0';
		//check if the course is exsit
		if (!vaildInputCourses(courses, courseSt, index))
		{
			printf("Error: invalid name or class number.\n");
			return 0;
		}
		//check the validity of the string 
		if (userStr[i] != ',')
		{
			printf("Error: invalid name or class number.\n");
			return 0;
		}
		++i;
	}
	//in case there is no number of course in the string 
	if (counterCourse == 0)
	{
		printf("Error: invalid name or class number.\n");
		return 0;
	}
	//in case the string is vailid
	return 1;
}

/***********************************************************************************************
* Function Name:checkRemovingData
* Input:char userStr[], course* courses, int* index
* Output:int 1 or 0
* Function Operation:the function checks if string of student name and number of courses he need
*                    to be deleted from are vailed(return 1) or not(return 0)
*************************************************************************************************/
static int checkRemovingData(char userStr[], course* courses, int* index)
{
	//the index of the userStr
	int i = 0;
	//count the len of the name of the student 
	int counterName = 0;
	//array of the name of the student 
	char checkName[21];
	//go through the name of the student 
	while (userStr[i] != ':'&&counterName < 21)
	{
		//check the vailed of the student's name  
		if ((userStr[i] >= 'a' && userStr[i] <= 'z') ||
			(userStr[i] >= 'A' && userStr[i] <= 'Z') ||
			userStr[i] == ' ')
		{
			//put the chars of the name in "checkName"
			checkName[i] = userStr[i];
			++i;
			++counterName;
			continue;
		}
		else
		{
			printf("Error: invalid name or class number.\n");
			return 0;
		}
	}
	checkName[i] = '\0';
	//in case there is no name 
	if (counterName == 0)
	{
		printf("Error: invalid name or class number.\n");
		return 0;
	}
	//in case the student is not in any course 
	if (!exStudent(courses, index, checkName))
	{
		printf("Error: invalid name or class number.\n");
		return 0;
	}
	//check the validity of the string 
	if (userStr[i] != ':')
	{
		printf("Error: invalid name or class number.\n");
		return 0;
	}
	++i;
	//check if all the numbers courses are valid or not
	if (!checkValidNumberCourse(userStr, i, courses, index))
	{
		return 0;
	}
	//the userStr is valid
	return 1;
}

/*******************************************************************************************************
* Function Name:removingStudentData
* Input:char userStr[], char** name, char* numberCourse[]
* Output:int counter
* Function Operation:the function put the name of the studnet and all numbers of courses
*                    the user insert (in the userStr) into arrays and return the amount of the courses
*                    in the userStr
*********************************************************************************************************/
static int removingStudentData(char userStr[], char** name, char* numberCourse[])
{
	//count the amount of the courses
	int counter = 0;
	//the name of the student from the user string 
	char* nameData = strtok(userStr, ":");
	if (nameData != NULL)
	{
		//put the name in the array "name"
		*name = nameData;
	}
	//the index of the course
	int i = 0;
	//the max amount of courses
	int maxCourse = 5;
	while (i < maxCourse)
	{
		//the number of course from the user string 
		char* numberCourseData = strtok(NULL, " ,");
		if (numberCourseData != NULL)
		{
			//put the number of course in "numberCourse" array
			numberCourse[i] = numberCourseData;
			//count the amount of the courses
			++counter;
		}
		i++;
	}
	//return the amount of the courses the user insert
	return counter;
}

/*******************************************************************************************************
* Function Name:notMember
* Input:course* courses, int* index, char** name, char* numberCourse[], int count
* Output:1 or 0
* Function Operation:the function checks if a student is a member in all the courses and retrun 0
*                    else return 1
*********************************************************************************************************/
static int notMember(course* courses, int* index, char** name, char* numberCourse[], int count)
{
	//go through the array of the courses
	for (int i = 0; i < *index; i++)
	{
		//go through the number of courses the user insert
		for (int j = 0; j < count; j++)
		{
			if (!strcmp(courses[i].numberOfCourse, numberCourse[j]))
			{
				int member = 0;
				//go through the students in the course
				for (int k = 0; k < courses[i].numOfStudents; k++)
				{
					//check if the student is in the course
					if (!strcmp(courses[i].students[k].studentName, *name))
					{
						//the student is a member in the course
						++member;
					}
				}
				//in case the student is not a member in the course 
				if (member == 0)
				{
					printf("Error: student is not a member of class \"%s %s\".\n",
						courses[i].numberOfCourse, courses[i].nameCourse);
					return 1;
				}
			}
		}
	}
	//the student is member in all the inserts courses 
	return 0;
}

/*******************************************************************************************************
* Function Name:deleteFromAllCourses
* Input:course* courses, int* index, char** name, char* numberCourse[], int count
* Output:1 or 0
* Function Operation:the function checks if the user try to delete studnet from all his courses.
*                    if there is a least one course that the studnts is member of and will not be
*                    deleted from the function return 0 else the function print a message and return 1
*********************************************************************************************************/
static int deleteFromAllCourses(course* courses, int* index, char** name, char* numberCourse[], int count)
{
	//go through the courses
	for (int i = 0; i < *index; i++)
	{
		//go through the students in the course
		for (int k = 0; k < courses[i].numOfStudents; k++)
		{
			//checks if the name of the studnet is the same as the name the function got
			if (!strcmp(courses[i].students[k].studentName, *name))
			{
				//count the number of courses the studnet is need to be removed 
				int deleteAll = 0;
				//go through the number of the courses the student need to be removed
				for (int j = 0; j < count; j++)
				{
					//got to the course the student need to be removed
					if (!strcmp(courses[i].numberOfCourse, numberCourse[j]))
					{
						deleteAll++;
					}
				}
				//in case the student will not be removed from all the courses he member 
				if (!deleteAll)
				{
					return 0;
				}
			}
		}
	}
	//in case the student is need to be removed from all his courses 
	printf("Error: student cannot be removed from all classes.\n");
	return 1;
}

/*******************************************************************************************************
* Function Name:deleteStudent
* Input:course* courses, int* index, char** name, char* numberCourse[], int count
* Output:void
* Function Operation:the function gets name of student and array of number of courses and the function
*                    remove the student from theses courses
*********************************************************************************************************/
static void deleteStudent(course* courses, int* index, char** name, char* numberCourse[], int count)
{
	//go through the courses 
	for (int i = 0; i < *index; i++)
	{
		//go through the number of courses the studnet need to be removed
		for (int j = 0; j < count; j++)
		{
			//check if the course is one of the courses the student need to be removed 
			if (!strcmp(courses[i].numberOfCourse, numberCourse[j]))
			{
				//go through the students in the course 
				for (int k = 0; k < courses[i].numOfStudents; k++)
				{
					//check if got to the student 
					if (!strcmp(courses[i].students[k].studentName, *name))
					{

						/*
						array of the student that will be removed (only for print after
						delete the student)
						*/
						char nameDeleteStudent[21];
						//copy the name of the student into the array 
						strcpy(nameDeleteStudent, courses[i].students[k].studentName);
						//in case the studnet is the only one in the course 
						if (courses[i].numOfStudents == 1)
						{
							//remove the student from the course 
							free(courses[i].students);
							//now the number of the student in the course is 0
							courses[i].numOfStudents = 0;
							//print a message 
							printf("Student \"%s\" removed from class \"%s %s\".\n", nameDeleteStudent,
								courses[i].numberOfCourse, courses[i].nameCourse);
							break;
						}
						else
						{
							//go through the rest students in the course after him 
							for (int p = k; p < courses[i].numOfStudents; p++)
							{
								//in case got to the last student in the course 
								if (p == courses[i].numOfStudents - 1)
								{
									//reduce the number of studnet in the course by 1
									courses[i].students = (student*)realloc
									(courses[i].students, (courses[i].numOfStudents - 1) * sizeof(student));
									//in case realloc failed 
									if (courses[i].students == NULL)
									{
										freeAll(courses, index);
										exit(1);
									}
									//now there are less 1 student 
									--courses[i].numOfStudents;
									//print a message 
									printf("Student \"%s\" removed from class \"%s %s\".\n", nameDeleteStudent,
										courses[i].numberOfCourse,
										courses[i].nameCourse);
								}
								else
								{
									//copy the name of the next student to the current studnet 
									strcpy(courses[i].students[p].studentName, courses[i].students[p + 1].studentName);
									//copy the grade of the next student to the current studnet 
									strcpy(courses[i].students[p].grade, courses[i].students[p + 1].grade);
								}
							}
							break;
						}
					}
				}
			}
		}
	}
}

/*******************************************************************************************************
* Function Name:validPrint
* Input:char userStr[], course* courses, int* index
* Output:1 or 0
* Function Operation:the function checks if a the user insert vailed input of nunber of course if not
*                    vailed the function return 0 and print a message error else return 1
*********************************************************************************************************/
static int validPrint(char userStr[], course* courses, int* index)
{
	int lenOfNumberCourse = 5;
	//check if the len of the number course is valied
	if (strlen(userStr) != lenOfNumberCourse)
	{
		printf("Error: invalid class number.\n");
		return 0;
	}
	//check if the number of course consists only from digits 
	for (int i = 0; i < lenOfNumberCourse; i++)
	{
		if (userStr[i] < '0' || userStr[i]>'9')
		{
			printf("Error: invalid class number.\n");
			return 0;
		}
	}
	//check if the number of course is exsist 
	if (!vaildInputCourses(courses, userStr, index))
	{
		printf("Error: invalid class number.\n");
		return 0;
	}
	return 1;
}

/*******************************************************************************************************
* Function Name:empatyCourse
* Input:char numberCourse[], course* courses, int* index
* Output:1 or 0
* Function Operation:the function checks if a the user insert number of course which has no studnets
*                    at all.
*********************************************************************************************************/
static int empatyCourse(char numberCourse[], course* courses, int* index)
{
	//go through the courses
	for (int i = 0; i < *index; i++)
	{
		//check if got to the course that the function get
		if (!strcmp(courses[i].numberOfCourse, numberCourse))
		{
			//in case the course has no student
			if (courses[i].numOfStudents == 0)
			{
				printf("Class \"%s %s\" has no students.\n", courses[i].numberOfCourse, courses[i].nameCourse);
				return 1;
			}
		}
	}
	//the course is not empaty
	return 0;
}

/*******************************************************************************************************
* Function Name:swap
* Input:student *s1, student *s2
* Output:void
* Function Operation:the function gets 2 pointers to students and replace between them
*********************************************************************************************************/
static void swap(student *s1, student *s2)
{
	student temp = *s1;
	*s1 = *s2;
	*s2 = temp;
}

/*******************************************************************************************************
* Function Name:printTheNames
* Input:char numberCourse[], course* courses, int* index
* Output:void
* Function Operation:the function prints all the names of the studnet of the course the function got
*********************************************************************************************************/
static void printTheNames(char numberCourse[], course* courses, int* index)
{
	//go through the courses
	for (int i = 0; i < *index; i++)
	{
		//check if got to the course that the function get
		if (!strcmp(courses[i].numberOfCourse, numberCourse))
		{
			//go through the students in the course
			for (int k = 0; k < courses[i].numOfStudents - 1; k++)
			{
				//sort the names of the students in lexicographic order
				for (int j = 0; j < courses[i].numOfStudents - 1; j++)
				{
					if (strcmp(courses[i].students[j].studentName, courses[i].students[j + 1].studentName) > 0)
					{
						swap(&courses[i].students[j], &courses[i].students[j + 1]);
					}
				}
			}
			printf("Class \"%s %s\" students:\n", courses[i].numberOfCourse, courses[i].nameCourse);
			//print the names of the students 
			for (int p = 0; p < courses[i].numOfStudents; p++)
			{
				printf("%s, %s\n", courses[i].students[p].studentName, courses[i].students[p].grade);
			}
		}
	}
}

/*******************************************************************************************************
* Function Name:thereIsNoCourses
* Input:course* courses, int* index
* Output:void
* Function Operation:the function return 1 and print a message if there are courses exsists at all
*                    else the function return 0
*********************************************************************************************************/
static int thereIsNoCourses(course* courses, int* index)
{
	//if there are no courses 
	if (!*index)
	{
		printf("Error: there are no classes.\n");
		return 1;
	}
	//there is at least one course
	return 0;
}

/*******************************************************************************************************
* Function Name:thereIsNoStudents
* Input:course* courses, int* index
* Output:void
* Function Operation:the function return 1 and print a message if there are no students exsists at course
*                    the function got (the function gets the index of the course in the array of the
*                    courses) else the function return 0
*********************************************************************************************************/
static int thereIsNoStudents(course* courses, int index)
{
	//if there are no students in the course
	if (!courses[index].numOfStudents)
	{
		printf("Class \"%s %s\" has no students.\n", courses[index].numberOfCourse,
			courses[index].nameCourse);
		return 1;
	}
	//there is at least one student in the course
	return 0;
}

/*******************************************************************************************************
* Function Name:averageGrade
* Input:course* courses, int* index
* Output:void
* Function Operation:the function prints the avarage grade of each course
*********************************************************************************************************/
static void averageGrade(course* courses, int* index)
{
	//go through the courses 
	for (int i = 0; i < *index; i++)
	{
		//check if there are students in the coures
		if (!thereIsNoStudents(courses, i))
		{
			//sum the grades
			int sum = 0;
			//go through the studnets in the course
			for (int j = 0; j < courses[i].numOfStudents; j++)
			{
				//sum the grades
				sum += atoi(courses[i].students[j].grade);
			}
			//print a message with the avarge grade of the student in the current course
			printf("%s %s, %d\n", courses[i].numberOfCourse, courses[i].nameCourse,
				(sum / courses[i].numOfStudents));
		}
	}
}

/*******************************************************************************************************
* Function Name:sorted
* Input:course* courses, int* index
* Output:void
* Function Operation:the function sort the grades of the studnets in the course from the high to low
*********************************************************************************************************/
static void sorted(course* courses, int index)
{
	//the index of the course
	int i = index;
	//go through the grades of the students of the course and sorting them from high to low
	for (int k = 0; k < courses[i].numOfStudents - 1; k++)
	{
		for (int j = 0; j < courses[i].numOfStudents - 1; j++)
		{
			if (atoi(courses[i].students[j].grade) > atoi(courses[i].students[j + 1].grade))
			{
				swap(&courses[i].students[j], &courses[i].students[j + 1]);
			}
		}
	}
}

/*******************************************************************************************************
* Function Name:maximalGrade
* Input:course* courses, int* index
* Output:void
* Function Operation:the function prints the max grade of each course
*********************************************************************************************************/
static void maximalGrade(course* courses, int* index)
{
	//go through the courses
	for (int i = 0; i < *index; i++)
	{
		//check if there is no studnets in the course 
		if (!thereIsNoStudents(courses, i))
		{
			//sorted the grades of the studnts from the high to low
			sorted(courses, i);
			//print the max grade 
			printf("%s %s, %s\n", courses[i].numberOfCourse, courses[i].nameCourse,
				courses[i].students[courses[i].numOfStudents - 1].grade);
		}
	}
}

/*******************************************************************************************************
* Function Name:minimalGrade
* Input:course* courses, int* index
* Output:void
* Function Operation:the function prints the min grade of each course
*********************************************************************************************************/
static void minimalGrade(course* courses, int* index)
{
	//go through the courses
	for (int i = 0; i < *index; i++)
	{
		//check if there is no studnets in the course 
		if (!thereIsNoStudents(courses, i))
		{
			//sorted the grades of the studnts from the high to low
			sorted(courses, i);
			//print the min grade
			printf("%s %s, %s\n", courses[i].numberOfCourse, courses[i].nameCourse,
				courses[i].students[0].grade);
		}
	}
}

/*******************************************************************************************************
* Function Name:sumGrade
* Input:course* courses, int* index
* Output:void
* Function Operation:the function prints the sum of the grades in each course
*********************************************************************************************************/
static void sumGrade(course* courses, int* index)
{
	//go through the courses
	for (int i = 0; i < *index; i++)
	{
		//check if there is no studnets in the course 
		if (!thereIsNoStudents(courses, i))
		{
			int sum = 0;
			//sum the grades of the students
			for (int j = 0; j < courses[i].numOfStudents; j++)
			{
				sum = sum + atoi(courses[i].students[j].grade);
			}
			//prints the sum of the grades
			printf("%s %s, %d\n", courses[i].numberOfCourse, courses[i].nameCourse,
				sum);
		}
	}
}

/*******************************************************************************************************
* Function Name:countGrade
* Input:course* courses, int* index
* Output:void
* Function Operation:the function prints the number of studnts in each course
*********************************************************************************************************/
static void countGrade(course* courses, int* index)
{
	//go through the courses
	for (int i = 0; i < *index; i++)
	{
		//check if there is no studnets in the course 
		if (!thereIsNoStudents(courses, i))
		{
			//print the number of students 
			printf("%s %s, %d\n", courses[i].numberOfCourse, courses[i].nameCourse,
				courses[i].numOfStudents);
		}
	}
}

/*******************************************************************************************************
* Function Name:calculatedGrade
* Input:course* courses, int* index
* Output:void
* Function Operation:the function prints the min grade of each course
*********************************************************************************************************/
static void calculatedGrade(course* courses, int* index)
{
	//print the menu
	printf("Please select the aggregation method:\n\ta. Average Grade.\n\tb. Maximal Grade.\n\t"\
		"c. Minimal Grade.\n\td. Sum.\n\te. Count.\n\t0. Return to the main menu.\n");
	char userChoose;
	//get the choice from the user
	scanf(" %c", &userChoose);
	if (userChoose == '0')
	{
		return;
	}
	if (userChoose == 'a')
	{
		//prints the avarage grade of each course
		averageGrade(courses, index);
		return;
	}
	if (userChoose == 'b')
	{
		//prints the max grade of each course
		maximalGrade(courses, index);
		return;
	}
	if (userChoose == 'c')
	{
		//prints the min of each course
		minimalGrade(courses, index);
		return;
	}
	if (userChoose == 'd')
	{
		//prints the sum of all the grades of each course
		sumGrade(courses, index);
		return;
	}
	if (userChoose == 'e')
	{
		//prints the number of studnts of each course
		countGrade(courses, index);
		return;
	}
	return;
}

/***********************************************************************************
* Function Name:insertOrUpdateClass
* Input:course* courses, int* index
* Output:void
* Function Operation:the function gets from the user number and name of course
*                    the function checks by calling other functions if the number
*                    and the name of the course are vailed or not.if not the
*                    the function doesnt do anything but print a message error
*                    but if the string is vailed then the function add or update
*                    course by calling other functions.
*************************************************************************************/
void insertOrUpdateClass(course* courses, int* index)
{
	char userStr[200];
	//gets a string from the user
	scanf(" %[^\n]", userStr);
	int vaild = checkCourseData(userStr);
	//check if the string is vailed 
	if (!vaild)
	{
		return;
	}
	else
	{
		//the name of the course
		char name[31];
		//the number of the course
		char number[6];
		//put the number of the course in "number" array
		numberOfCourse(userStr, number);
		//put the name of the course in "name" array
		int len = nameOfCourse(userStr, name);
		//add/update the course
		addCourse(name, number, courses, index, len);
		return;
	}
}

/*************************************************************************************************************
* Function Name:insertOrUpdateStudent
* Input:course* courses, int* index
* Output:void
* Function Operation:the function gets from the user name of student,number of course,and the studnet's grade
*                    in the course.
*                    the function checks by calling other functions if the all string
*                    is vailed or not.if not the
*                    the function doesnt do anything but print a message error
*                    but if the string is vailed then the function call other functions
*                    that add/update the studnet in the chosen course
****************************************************************************************************************/
void insertOrUpdateStudent(course* courses, int* index)
{
	char userStr[200];
	//gets a string from the user
	scanf(" %[^\n]", userStr);
	//in case the string is invailed
	if (!checkStudentData(userStr, courses, index))
	{
		return;
	}
	else
	{
		//the name of the student 
		char* nameOfTheStudent[21];
		//all the number of the courses that the user inserts
		char* numberCourse[SIZE];
		//the grade of the courses that the user inserts
		char* grade[SIZE];
		//put all the data of the students in the arrays
		int counter = studentData(userStr, nameOfTheStudent, numberCourse, grade);
		//add or update 
		addAndUpdate(courses, index, nameOfTheStudent, numberCourse, grade, counter);
		return;
	}
}

/*****************************************************************************************************
* Function Name:removeStudentFromClasses
* Input:course* courses, int* index
* Output:void
* Function Operation:the function gets from the user name of student, and numbers of courses
*                    then the function delete the stundent from all these courses
*********************************************************************************************************/
void removeStudentFromClasses(course* courses, int* index)
{
	char userStr[200];
	//gets a string from the user
	scanf(" %[^\n]", userStr);
	//checks if the string is vailed 
	if (!checkRemovingData(userStr, courses, index))
	{
		return;
	}
	//the name of the student 
	char* nameOfTheStudent[21];
	//all the courses the student will be deleted from
	char* numberCourse[SIZE];
	//put all the data of the student in the varaibles
	int counter = removingStudentData(userStr, nameOfTheStudent, numberCourse);
	//in case the student is not a member of one or more of the courses that the user inserts
	if (notMember(courses, index, nameOfTheStudent, numberCourse, counter))
	{
		return;
	}
	//in case the user try to delete the student from all the courses
	if (deleteFromAllCourses(courses, index, nameOfTheStudent, numberCourse, counter))
	{
		return;
	}
	//delete the studnet from the chosen courses
	deleteStudent(courses, index, nameOfTheStudent, numberCourse, counter);
}

/*****************************************************************************************************
* Function Name:printClass
* Input:course* courses, int* index
* Output:void
* Function Operation:the function gets from the user number of course
*                    then the function call other function that prints the number and the name of the
*                    course and all the students in the course
*********************************************************************************************************/
void printClass(course* courses, int* index)
{
	char userStr[200];
	//gets a string from the user
	scanf(" %[^\n]", userStr);
	//check if the stirng is vailed
	if (!validPrint(userStr, courses, index))
	{
		return;
	}
	//checks if the course has no student at all
	if (empatyCourse(userStr, courses, index))
	{
		return;
	}
	/*
	print the names of the course,the number of the course and all the students
	that are in the course
	*/
	printTheNames(userStr, courses, index);
	return;
}

/*****************************************************************************************************
* Function Name:printComputedScores
* Input:course* courses, int* index
* Output:void
* Function Operation:
*********************************************************************************************************/
void printComputedScores(course* courses, int* index)
{
	//check if there are exsist courses 
	if (thereIsNoCourses(courses, index))
	{
		return;
	}
	//calculate the grade by the choice of the user
	calculatedGrade(courses, index);
	return;
}