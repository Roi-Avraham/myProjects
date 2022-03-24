/*
 * HybridAnomalyDetector.cpp
 *
 * Author: Shira Goren 207814989 with Roi Avraham 318778081
 */

#include "HybridAnomalyDetector.h"


HybridAnomalyDetector::HybridAnomalyDetector() {
    bigThreshold = getMinCorrelation();
    SimpleAnomalyDetector::setMinCorrelation(0.5);
}

HybridAnomalyDetector::~HybridAnomalyDetector() {
    // TODO Auto-generated destructor stub
}

/**
 * creates cf object based on hybrid method.
 * */
correlatedFeatures HybridAnomalyDetector::createCFObject(string &featureI, string &featureC,
                                          float m, float* i, float* c, int size) {
    /*create correlated object*/
    correlatedFeatures correlated;
    correlated.feature1 = featureI;//namesOfFeatures[i];
    correlated.feature2 = featureC;//namesOfFeatures[c];
    correlated.corrlation = m;
    /*save all data of i, c categories*/
    float* x = i;//ts.dataByColumn(i);
    float* y = c;//ts.dataByColumn(c);
    /*create array of points*/
    Point** arrayOfPoints = arrayPoints(x, y, size);
    /*create normal or circle from points*/
    if (correlated.corrlation < bigThreshold && correlated.corrlation > SimpleAnomalyDetector::getMinCorrelation()) {
        correlated.lin_reg = Line(0, 0);
        correlated.min_circle = findMinCircle(arrayOfPoints, size);
        /*save max deviations in object*/
        correlated.threshold = (correlated.min_circle.radius * 1.1);
    } else {
        correlated.lin_reg = linear_reg(arrayOfPoints, size);
        /*save max deviations in object*/
        correlated.threshold = (findMaxDeviation(arrayOfPoints, size, correlated));
        correlated.min_circle = Circle(Point(0, 0),0);
    }
    return correlated;
}

/**
 * checks if point as an anomaly * */
bool HybridAnomalyDetector::isAnomaly(correlatedFeatures c, Point p) {
    if (c.corrlation < bigThreshold && c.corrlation > getMinCorrelation()) {
        float dis = distance(c.min_circle.center, p);
        if(dis >  c.threshold) {
            return true;
        }
    } else if (c.corrlation >= bigThreshold) {
       return SimpleAnomalyDetector::isAnomaly(c, p);
    }
    return false;
}

