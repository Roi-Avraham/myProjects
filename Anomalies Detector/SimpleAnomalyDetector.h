
/*
 * SimpleAnomalyDetector.h
 *
 * Author: Shira Goren 207814989 with Roi Avraham 318778081
 */

#ifndef SIMPLEANOMALYDETECTOR_H_
#define SIMPLEANOMALYDETECTOR_H_

#include "anomaly_detection_util.h"
#include "AnomalyDetector.h"
#include "minCircle.h"
#include <vector>
#include <algorithm>
#include <string.h>
#include <math.h>

/**
 * correlated features.
 * */
struct correlatedFeatures{
    Circle min_circle;
    string feature1;  // names of the correlated features
    string feature2;
    float corrlation;
    Line lin_reg;
    float threshold;

};


class SimpleAnomalyDetector:public TimeSeriesAnomalyDetector{
    vector<correlatedFeatures> cf;
    float minCorrelation;
public:
    SimpleAnomalyDetector();
    virtual ~SimpleAnomalyDetector();
    virtual void learnNormal(const TimeSeries& ts);
    correlatedFeatures findCorrelated(string &featureI, string &featureII);
    virtual vector<AnomalyReport> detect(const TimeSeries& ts);
    vector<correlatedFeatures> getNormalModel(){
        return cf;
    }
   virtual  void anomalyEquation(const vector<string>& namesOfFeatures, int i,
                                                TimeSeries ts, int size, float& m, int& c, float p, int j);
    virtual float getMinCorrelation()const{return minCorrelation;};
    virtual correlatedFeatures createCFObject(string &featureI, string &featureC,
                                      float m, float* i, float* c, int size);
    virtual void setMinCorrelation(float c){ minCorrelation = c;};
    virtual void checkPearson(int i,  vector<string> namesOfFeatures,const TimeSeries& ts, int size, float& m, int& c);
    virtual Point** arrayPoints(float* x, float* y, int size);
    virtual float findMaxDeviation(Point** arrayOfPoints, int size,
                                   const correlatedFeatures& correlated);
    virtual bool isAnomaly(correlatedFeatures c, Point p);
};



#endif /* SIMPLEANOMALYDETECTOR_H_ */
