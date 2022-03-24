/*
 * Server.cpp
 *
 * Author: Shira Goren 207814989 with Roi Avraham 318778081
 */

#include "Server.h"
#include <pthread.h>
#define STRING(num) #num
string socketIO::read(){
    char data = 0;
    string allData ="";
    while (data != '\n') {
        recv(socketNumber, &data, sizeof(char), 0);
        allData += data;
    }
    allData.erase(std::remove(allData.begin(), allData.end(), '\n'), allData.end());
    return allData;
}
void socketIO::write(string text){
    int n = text.length();
    // declaring character array
    char char_array[n + 1];
    // copying the contents of the
    // string to char array
    strcpy(char_array, text.c_str());
    send(socketNumber,char_array,strlen(char_array),0);
}

void socketIO::write(float f){
    std::stringstream fString;
    fString << f;
    string num_str = fString.str();
    int n = num_str.length();
    // declaring character array
    char char_array[n + 1];
    // copying the contents of the
    // string to char array
    strcpy(char_array, num_str.c_str());
    send(socketNumber,char_array,strlen(char_array),0);
}

void socketIO::read(float* f){
    recv(socketNumber, &f, sizeof(char), 0);
}

/**
 * create socket, waiting for start command.
 * */
Server::Server(int port)throw (const char*) {
    //open socket
    this->sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd == -1) {
        throw "Failed to create socket.";
    }
    sockaddr.sin_family = AF_INET;
    sockaddr.sin_addr.s_addr = INADDR_ANY;
    sockaddr.sin_port = htons(port);
    //check validity of socket
    if (bind(sockfd, (struct sockaddr*)&sockaddr, sizeof(sockaddr)) < 0) {
        throw "Failed to bind to the port";
    }
    if (listen(sockfd, 10) < 0) {
        throw "Failed to listen on socket";
    }
}

//void sigHandler(int sigNum){
//    throw "";
//}

/**
 * handle client on new thread.
 * */
void Server::start(ClientHandler& ch)throw(const char*){
    //signal(SIGALRM,sigHandler);
    /*start thread in a lambda expression*/
    this->t = new thread([&ch, this]() {
        /*run until process was called to end*/
        while (!this->end) {
            alarm(3);  // wait 3 sec
            int clientID = accept(this->sockfd, (struct sockaddr*)&user,
                                  &clientSize); // get client id
            //check if client id is valid
            if (clientID != -1) {
                /*create new thread for this client*/
                ch.handle(clientID); // handle client
                close(clientID);
            }
            //cout<<"\n";
        }
        close(this->sockfd); //close the main socket

    });
}

void Server::stop(){
    end = true;
    t->join(); // do not delete this!
}

Server::~Server() {
}