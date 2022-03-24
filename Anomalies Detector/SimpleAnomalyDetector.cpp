/*
 * SimpleAnomalyDetector.cpp
 *
 * Author: Shira Goren 207814989 with Roi Avraham 318778081
 */

#include "SimpleAnomalyDetector.h"
#include "anomaly_detection_util.h"

SimpleAnomalyDetector::SimpleAnomalyDetector() {
    minCorrelation = 0.9;
}

SimpleAnomalyDetector::~SimpleAnomalyDetector() {
    // TODO Auto-generated destructor stub
}

/**
 * creates and returns array of all points.
 * @param x array of x values
 * @param y array of y values
 * @param size size of arrays x and y
 * @return array af points
 * */
Point** SimpleAnomalyDetector::arrayPoints(float* x, float* y, int size) {
    Point** points = new Point*[size];
    for (int i=0; i < size; i++) {
        Point* point = new Point(x[i], y[i]);
        points[i] = point;
    }
    return points;
}

/**
 * find max deviation.
 * @param size size of point array
 * @param arrayOfPoints point array
 * @param correlated a reference to a correlatedFeature object
 * @return max deviation
 * */
float SimpleAnomalyDetector::findMaxDeviation(Point** arrayOfPoints, int size,
                       const correlatedFeatures& correlated) {
    /*create deviations array*/
    float maxDeviation = 0.0;
    /*run through points and check deviation to normal*/
    for (int k=0;k< size;k++) {
        Point point(arrayOfPoints[k]->x, arrayOfPoints[k]->y);//create point
        float d =  (dev(point, correlated.lin_reg));//find deviation
        /*save max deviation*/
        if (maxDeviation < d) {
            maxDeviation = d;
        }
    }
    return (maxDeviation*1.1);
}

/**
 * creates cf object.
 * */
correlatedFeatures SimpleAnomalyDetector::createCFObject(string &featureI, string &featureC,
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
    /*create normal from points*/
    correlated.lin_reg = linear_reg(arrayOfPoints, size);
    /*save max deviations in object*/
    correlated.threshold = (findMaxDeviation(arrayOfPoints, size, correlated));
    correlated.min_circle = Circle(Point(0, 0),0);
    return correlated;
}

/**
 * find correlated couple in vector and return it.
 * */
correlatedFeatures SimpleAnomalyDetector::findCorrelated(string &featureI, string &featureII) {
    for (int i = 0; i < getNormalModel().size(); i++) {
        if (getNormalModel()[i].feature1 == (featureI) &&
             getNormalModel()[i].feature2 == (featureII)) {
            return getNormalModel()[i];
        }
    }
    correlatedFeatures empty;
    empty.feature1 = "Empty";
    return empty;
}


/**
 * finds correlated features by simple equation
 * @param namesOfFeatures vector of strng features
 * @param i index of line in file
 * @param ts timeseries obj we are using
 * @param size num of rows in file
 * @param m threshold of correlation
 * @param c index of correlated feature
 * @param p pearson of the two features
 * @param j index of feature currently at
 *
 * */
void SimpleAnomalyDetector::anomalyEquation(const vector<string>& namesOfFeatures, int i,
                                            TimeSeries ts, int size, float& m, int& c, float p, int j) {
    /*check if correlative*/
    if(p >= m || (-1*p >= m)) {
        /*save pearson as threshold and j as correlative index*/
        m = p;
        c = j;
    }
}

/**
 * find if pearson is correlated.
 * */
void SimpleAnomalyDetector::checkPearson(int i,  vector<string> namesOfFeatures,
                                           const TimeSeries& ts, int size, float& m, int& c) {
    /*run through all categories to come, and finds correlatives*/
    for (int j = i + 1;j < namesOfFeatures.size(); j++) {
        float *x = ts.dataByColumn(i);//all data of category i
        float *y = ts.dataByColumn(j);//all data of category j
        float p = pearson(x, y, size);//find pearson
        /*find correlated feature by equation*/
        anomalyEquation(namesOfFeatures, i, ts, size, m, c, p, j);
    }
}

/**
 * create a correlated features objects.
 * @param ts reference to a timeSeries object
 * */
void SimpleAnomalyDetector::learnNormal(const TimeSeries& ts){
    /*vector of all categories*/
    vector<string> namesOfFeatures = ts.countColumns();
    int size = ts.numOfRows() - 1;
    /*run through all categories and find all correlatives*/
    for (int i = 0; i < namesOfFeatures.size(); i++) {
        float m = getMinCorrelation(); //our correlation threshold
        int c = -1; //index of most correlative category
        /*run through all categories to come, and finds correlatives*/
        checkPearson(i, namesOfFeatures, ts, size, m, c);
        /*make sure a correlation was found*/
        if (c != -1) {
            /*create cf object*/
            correlatedFeatures correlated
                    = createCFObject(const_cast<string &>(namesOfFeatures[i]),
                                     const_cast<string &>(namesOfFeatures[c]),
                                     m, ts.dataByColumn(i), ts.dataByColumn(c),
                                     size);
            /*add correlated object to timeseries object if correlation is large enough*/
            cf.push_back(correlated);
        }
    }
}

bool SimpleAnomalyDetector::isAnomaly(correlatedFeatures c, Point p) {
    float d = dev(p, c.lin_reg);//find deviation
    if (d > c.threshold) {
        return true;
    }
    return false;
}

/**
 * sort anomalies by order of feature.
 * */
vector<AnomalyReport> SimpleAnomalyDetector::detect(const TimeSeries& ts){
    vector<vector<float>> data = ts.dataOfFeatures();//save all lines
    vector<AnomalyReport> allReports; //create report vector
    /*vector of all categories*/
    vector<string> namesOfFeatures = ts.countColumns();
    int size = ts.numOfRows();
    /*run through all features*/
    for (int i = 0; i < namesOfFeatures.size(); i++) {
        /*run through rest of the features in the line*/
        for (int k = i + 1; k <namesOfFeatures.size(); k++){
            /*run through all lines and check points of correlatives*/
            for (int j = 1; j < size; j++) {
                correlatedFeatures found = findCorrelated(namesOfFeatures[i], namesOfFeatures[k]);
                /*make sure correlation was found*/
                if (found.feature1 != "Empty") {
                    Point point(data[j][i], data[j][k]);//create point
                    /*add anomaly to vector if deviation is too large*/
                    if (isAnomaly(found, point)) {
                        AnomalyReport newReport(found.feature1 + "-" + found.feature2, j);
                        allReports.push_back(newReport);
                    }
                }
            }

        }
    }
    return allReports;
}