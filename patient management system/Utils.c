#include <stdio.h>
#include "Utils.h"
#define COUNT 8

/****************************
//*Name Roi Avraham
//*ID 318778081
//*Group(01-cs,02-math) 01
//*Assignment Ex6
//***************************/

// Function to print Trinary tree in 2D 
// It does reverse inorder traversal 
void print2DUtil(Trin_Ari *root, int space)
{
	// Base case 
	if (root == NULL)
		return;

	// Increase distance between levels 
	space += COUNT;

	// Process right child first 
	print2DUtil(root->right, space);
	for (int i = COUNT; i < space; i++)
		printf(" ");
	printf("%s %d\n", root->name, root->id);
	// Process middle child 
	print2DUtil(root->middle, space);
	printf("\n");
	// Process left child 
	print2DUtil(root->left, space);
}