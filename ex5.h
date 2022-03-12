#ifndef MAIN_C_EX5_H
#define MAIN_C_EX5_H
#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
//the max number of course that can be 
#define SIZE 5
//the max len of the grade
#define LEN_GRADE 3

/*
the struct of the student.
every student has name and grade 
*/
struct Student
{
	//the name of the student 
	char studentName[21];
	//the grade of the student 
	char grade[4];
};
typedef struct Student student;

/*
the struct of the course.every course has:
1-name 
2-number
3-number of students which are are in the course 
4-array of students in the course 
*/
struct Course
{
	char nameCourse[31];
	char numberOfCourse[6];
	//the number of the student in the course 
	int numOfStudents;
	//Dynamic array of the students in the course 
	student* students;
};
typedef struct Course course;

void printShortMenu();
void printMenu();
void insertOrUpdateClass(course* courses, int* index);
void insertOrUpdateStudent(course* courses, int* index);
void removeStudentFromClasses(course* courses, int* index);
void printClass(course* courses, int* index);
void printComputedScores(course* courses, int* index);
void freeAll(course* courses, int* index);
#endif 
