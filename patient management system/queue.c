#include "queue.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

/****************************
//*Name Roi Avraham
//*ID 318778081
//*Group(01-cs,02-math) 01
//*Assignment Ex6
//***************************/

/****************************************************
* Function Name:createQueue
* Input:
* Output:Queue*
* Function Operation:the function create a Queue
*****************************************************/
Queue* createQueue()
{
	//alloc the queue
	Queue* q = (Queue*)malloc(sizeof(Queue));
	//in case the malloc failed 
	if (q == NULL)
	{
		printf("Malloc Failed!\n");
		//realse memory
		free(q);
		exit(1);
	}
	//initialize the queue
	q->front = NULL;
	q->rear = q->front;
	return q;
}

/***************************************************************
* Function Name:enQueue
* Input:Queue* q, void* k
* Output:void
* Function Operation:the function enter value into the queue
****************************************************************/
int enQueue(Queue* q, void* k)
{
	//alloc the node in the queue
	struct QNode* node = (struct QNode*)malloc(sizeof(struct QNode));
	//in case the malloc failed 
	if (node == NULL)
	{
		printf("Malloc Failed!\n");
		destroyQueue(q);
		return 1;
	}
	//put the value the function got to the queue
	node->key = k;
	node->next = NULL;
	if (q->rear == NULL) 
	{
		q->front = q->rear = node;
		return 0;
	}

	// Add the new node at the end of queue and change rear 
	q->rear->next = node;
	q->rear = node;
}

/***************************************************************
* Function Name:deQueue
* Input:Queue* q, void* k
* Output:void
* Function Operation:the function remove value into the queue
****************************************************************/
void deQueue(Queue* q)
{
	// If queue is empty, return NULL. 
	if (q->front == NULL)
		return;

	// Store previous front and move front one node ahead 
	struct QNode* temp = q->front;

	q->front = q->front->next;

	// If front becomes NULL, then change rear also as NULL 
	if (q->front == NULL)
		q->rear = NULL;

	free(temp);
}

/***************************************************************
* Function Name:isEmpty
* Input:Queue* q, void* k
* Output:int 1 or 0
* Function Operation:the function return 1 if the queue is empty
*                    else the function return 0
****************************************************************/
int isEmpty(Queue* q)
{
	//in case the queue is empty
	if (q->rear == NULL)
	{
		return 1;
	}
	return 0;
}

/*******************************************************************************
* Function Name:destroyQueueItem
* Input:QNode* node
* Output:void
* Function Operation:the function releases all alloc of the nodes in the queue
********************************************************************************/
void destroyQueueItem(QNode* node)
{
	if (node != NULL)
	{
		free(node->key);
		destroyQueueItem(node->next);
	}
	//free the node 
	free(node);
}

/*******************************************************************************
* Function Name:destroyQueue
* Input:Queue* q
* Output:void
* Function Operation:the function releases the alloc of the queue
********************************************************************************/
void destroyQueue(Queue* q)
{
	//releases the allocs of the nodes
	destroyQueueItem(q->front);
	////releases the allocs of the queue
	free(q);
}
