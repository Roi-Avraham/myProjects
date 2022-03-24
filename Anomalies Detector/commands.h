/*
 * commands.h
 *
 * Author: Shira Goren 207814989 with Roi Avraham 318778081
 */
#ifndef COMMANDS_H_
#define COMMANDS_H_

#include<iostream>
#include <string.h>

#include <fstream>
#include <vector>
#include "HybridAnomalyDetector.h"

using namespace std;

class DefaultIO{
public:
    virtual string read()=0;
    virtual void write(string text)=0;
    virtual void write(float f)=0;
    virtual void read(float* f)=0;
    virtual ~DefaultIO(){}

    // you may add additional methods here

    /**
     * read file till word "done".
     * */
    void uploadFile(string nameOfFile){
        ofstream output(nameOfFile);
        string data="";
        while((data=read())!="done"){
            output<<data<<endl;
        }
        output.close();
    }
};

// you may add here helper classes
/**
 * sequence of anomalies.
 * */
struct AnomalyFindings {
    long start;
    long end;
    string description;
    bool truePositive;
};

/**
 * current status of anomalies.
 * */
struct CurrentDetect{
    float threshold=0.9;
    vector<AnomalyReport> report;
    vector<AnomalyFindings> findings;
    int numOfRows;
};

// you may edit this class
class Command{
protected:
    DefaultIO* dio;
public:
    const string description;
    Command(DefaultIO* dio, const string description):dio(dio), description(description){};
    virtual void execute(CurrentDetect* currentDetect)=0;
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
    virtual ~Command(){}
};

// implement here your command classes
/**
 * upload files.
 * */
class Upload: public Command {
public:
    Upload(DefaultIO* dio):Command(dio, "upload a time series csv file"){}
    virtual void execute(CurrentDetect* currentDetect) {
        dio->write("Please upload your local train CSV file.\n");
        dio->uploadFile("anomalyTrain.csv");
        dio->write("Upload complete.\n");
        dio->write("Please upload your local test CSV file.\n");
        dio->uploadFile("anomalyTest.csv");
        dio->write("Upload complete.\n");
    }
};
/**
 * Algorithm settings.
 * */
class AlgorithmSettings: public Command {
public:
    AlgorithmSettings(DefaultIO* dio):Command(dio,"algorithm settings"){}
    virtual void execute(CurrentDetect* currentDetect){
        bool rightThreshold=false;
        while(!rightThreshold){
            dio->write("The current correlation threshold is ");
            dio->write(currentDetect->threshold);
            dio->write("\nType a new threshold\n");
            float threshold;
            dio->read(&threshold);
            if(threshold <= 0 && threshold > 1) {
                dio->write("please choose a value between 0 and 1.\n");
            }
            else {
                currentDetect->threshold=threshold;
                rightThreshold=true;
            }

        }
    }
};

/**
 * Detect Anomalies.
 * */
class  DetectAnomalies: public Command {
public:
    DetectAnomalies(DefaultIO* dio):Command(dio, "detect anomalies"){}
    virtual void execute(CurrentDetect* currentDetect){
        /*create train and test timeseries*/
        TimeSeries train("anomalyTrain.csv");
        TimeSeries test("anomalyTest.csv");
        /*save num of all rows in test file*/
        currentDetect->numOfRows = test.numOfRows();
        HybridAnomalyDetector hybridAnomalyDetector;
        //hybridAnomalyDetector.setMinCorrelation(currentDetect->threshold); //set threshold
        /*create correlated Features from train file*/
        hybridAnomalyDetector.learnNormal(train);
        /*save anomalies found in test file*/
        currentDetect->report = hybridAnomalyDetector.detect(test);
        /*initialize anomaly findings for test file*/
        AnomalyFindings anomalyFindings;
        anomalyFindings.start = currentDetect->report[0].timeStep;
        anomalyFindings.description = currentDetect->report[0].description;
        /*add anomaly findings to currentDetect object*/
        currentDetect->findings.push_back(anomalyFindings);
        int index = 0;
        long end =  anomalyFindings.start;
        /*run through all anomalies detected for test file*/
        for (int i = 1; i < currentDetect->report.size(); i++) {
            /*check if report was presumed*/
            if (currentDetect->report[i].description == currentDetect->findings[index].description) {
                end = currentDetect->report[i].timeStep;
                currentDetect->findings[index].end = end;
            } else {
                AnomalyFindings newAnomalyFindings;
                newAnomalyFindings.start = currentDetect->report[i].timeStep;
                newAnomalyFindings.description = currentDetect->report[i].description;
                currentDetect->findings.push_back(newAnomalyFindings);
                index += 1;
            }
        }
        dio->write("anomaly detection complete.\n");
    }
};

class DisplayResults: public Command {
public:
    DisplayResults(DefaultIO* dio):Command(dio,"display results"){}
    virtual void execute(CurrentDetect* currentDetect){
        for (int i = 0; i < currentDetect->report.size(); i++) {
            dio->write(currentDetect->report[i].timeStep);
            dio->write(" \t"+currentDetect->report[i].description+"\n");
        }
        dio->write("Done.\n");
    }
};

/**
 * returns lines that have anomalies.
 * */
class UploadAnomaliesAndAnalyzeResults: public Command {
public:
    UploadAnomaliesAndAnalyzeResults(DefaultIO* dio):Command(dio, "upload anomalies and analyze results"){}

    /**
     * upload files.
     * */
    virtual void execute(CurrentDetect* currentDetect) {
        dio->write("Please upload your local anomalies file.\n");
        /*initialize all truePositive flags to false*/
        for (int i=0; i < currentDetect->findings.size(); i++) {
            currentDetect->findings[i].truePositive = false;
        }
        /*initialize names of anomalies and num of anomalies detected*/
        string localAnomalies="";
        int P=0; //all positives
        int truePositive = 0;
        int falsePositive = currentDetect->findings.size();
        int numberOfAnomalies = 0;
        /*uploads file till end word 'done'*/
        while((localAnomalies=dio->read())!="done"){
            P++;
            std::size_t comma = localAnomalies.find(","); //parse by comma
            std::string start = localAnomalies.substr (0, comma);
            start.erase(std::remove(start.begin(), start.end(), '\n'), start.end());
            std::string end = localAnomalies.substr (comma + 1);
            end.erase(std::remove(end.begin(), end.end(), '\n'), end.end());
            if (isDigit(start)) {
                int startAnomaly = stoi(start);
                if (isDigit(end)) {
                    int endAnomaly = stoi(end);
                    numberOfAnomalies += (endAnomaly - startAnomaly + 1);
                    for(int i=0;i<currentDetect->findings.size();i++){
                        if(currentDetect->findings[i].end >= startAnomaly
                            && endAnomaly >= currentDetect->findings[i].start) {
                            currentDetect->findings[i].truePositive=true;
                            truePositive++;
                            falsePositive--;
                        }
                    }
                }
            }
        }
        dio->write("Upload complete.\n");
        int N = currentDetect->numOfRows - numberOfAnomalies;
        float tp = floorf(((float)truePositive / (float)P)*1000)/1000;
        float fp = floorf(((float)falsePositive / (float)N)*1000)/1000;
        dio->write("True Positive Rate: ");
        dio->write(tp);
        dio->write("\nFalse Positive Rate: ");
        dio->write(fp);
        dio->write("\n");
    }
};


class Exit: public Command {
public:
    Exit(DefaultIO* dio):Command(dio, "exit"){}
    virtual void execute(CurrentDetect* currentDetect) {
    }
};

#endif /* COMMANDS_H_ */
