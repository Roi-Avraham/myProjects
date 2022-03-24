/*
 * Server.h
 *
 * Author: Shira Goren 207814989 with Roi Avraham 318778081
 */

#ifndef SERVER_H_
#define SERVER_H_


#include <thread>
#include <pthread.h>
#include "commands.h"
#include "CLI.h"

#include <cstdlib> // For exit() and EXIT_FAILURE
#include <iostream> // For cout

#include <sys/socket.h> // For socket functions
#include <netinet/in.h> // For sockaddr_in
#include <cstdlib> // For exit() and EXIT_FAILURE
#include <iostream> // For cout
#include <unistd.h> // For read
#include <stdio.h>
#include <stdlib.h>
#include <signal.h>


using namespace std;

// edit your ClientHandler interface here:
class ClientHandler{
public:
    virtual void handle(int clientID)=0;
};


// you can add helper classes here and implement on the cpp file
class socketIO:public DefaultIO{
    int socketNumber;
public:
    socketIO(int clientID):socketNumber(clientID){}
    virtual string read();
    virtual void write(string text);
    virtual void write(float f);
    virtual void read(float* f);

};
// edit your AnomalyDetectionHandler class here
class AnomalyDetectionHandler:public ClientHandler{
public:
    virtual void handle(int clientID){
        socketIO socketIo(clientID); // create socket for client
        CLI cli(&socketIo);
        cli.start();
    }
};


// implement on Server.cpp
class Server {
    thread* t; // the thread to run the start() method in
    int sockfd; //main socket number
    sockaddr_in user; // start user socket
    socklen_t clientSize=sizeof(user); //save size of client
    sockaddr_in sockaddr;
    volatile bool end = false;
public:
    Server(int port) throw (const char*);
    virtual ~Server();
    void start(ClientHandler& ch) throw(const char*);
    void stop();
};

#endif /* SERVER_H_ */