/*
 * CLI.cpp
 *
 * Author: Shira Goren 207814989 with Roi Avraham 318778081
 */
#include "CLI.h"

CLI::CLI(DefaultIO* dio) {
    this->dio=dio;
    commands.push_back(new Upload(dio));
    commands.push_back(new AlgorithmSettings(dio));
    commands.push_back(new DetectAnomalies(dio));
    commands.push_back(new DisplayResults(dio));
    commands.push_back(new UploadAnomaliesAndAnalyzeResults(dio));
    commands.push_back(new Exit(dio));
}

bool isDigit(string s) {
    if(s.size() == 0) {
        return false;
    }
    for(int i = 0; i < s.length(); i++) {
        if(s[i] < '0' || s[i] > '9') {
            return false;
        }
    }
    return true;
}

void menu(DefaultIO* dio, vector<Command*> commands) {
    dio->write("Welcome to the Anomaly Detection Server.\n");
    dio->write("Please choose an option:\n");
    for (int i = 0; i < commands.size(); i++) {
        std::string s = std::to_string(i + 1);
        s = s + ".";
        dio->write(s);
        dio->write(commands[i]->description+"\n");
    }
}

void CLI::start(){
    CurrentDetect currentDetect;
    int choose = 0;
    while (choose != 6) { //print out menu
        menu(this->dio, this->commands);
        string input = dio->read();
        input.erase(std::remove(input.begin(), input.end(), '\n'), input.end());
        if (isDigit(input)){
            choose = stoi(input);
        }else {
            choose = 0;
        }
        if(choose >= 1 && choose <= 6)
            commands[choose - 1]->execute(&currentDetect);
    }
}


CLI::~CLI() {
    for (int i = 0; i < commands.size(); i++) {
        delete commands[i];
    }
}


