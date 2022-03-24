/*
 * HybridAnomalyDetector.h
 *
 * Author: Shira Goren 207814989 with Roi Avraham 318778081
 */

#ifndef HYBRIDANOMALYDETECTOR_H_
#define HYBRIDANOMALYDETECTOR_H_

#include "SimpleAnomalyDetector.h"
#include "minCircle.h"

class HybridAnomalyDetector:public SimpleAnomalyDetector {
    float bigThreshold;
public:
    HybridAnomalyDetector();
    virtual ~HybridAnomalyDetector();
    correlatedFeatures createCFObject(string &featureI, string &featureC,
                                              float m, float* i, float* c, int size);
    bool isAnomaly(correlatedFeatures c, Point p);
};

#endif /* HYBRIDANOMALYDETECTOR_H_ */
