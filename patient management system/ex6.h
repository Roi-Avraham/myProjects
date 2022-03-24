#ifndef _EX6_H_
#define _EX6_H_

/****************************
//*Name Roi Avraham
//*ID 318778081
//*Group(01-cs,02-math) 01
//*Assignment Ex6
//***************************/

//the family 
struct Trin_Ari {
	int id;
	char * name;
	struct Trin_Ari *left;
	struct Trin_Ari *middle;
	struct Trin_Ari *right;
};

typedef struct Trin_Ari Trin_Ari;

void printMenu();
void printShortMenu();
Trin_Ari* buildFamily(Trin_Ari* root);
void addMeeting(Trin_Ari* root);
void printTreeFamily(Trin_Ari* root);
void insulation(Trin_Ari* root);
Trin_Ari* vaccine(Trin_Ari* root);
void freeAll(Trin_Ari* root);
void superSpreader(Trin_Ari* root);
#endif

