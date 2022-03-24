/*
 * minCircle.h
 *
 * Author: Shira Goren 207814989 with Roi Avraham 318778081
 */

#ifndef MINCIRCLE_H_
#define MINCIRCLE_H_

#include <iostream>
#include <vector>
#include <stdlib.h>     /* srand, rand */
#include <time.h>       /* time */
#include "anomaly_detection_util.h"

using namespace std;


// ------------ DO NOT CHANGE -----------

class Circle{
public:
	Point center;
	float radius;
	Circle(Point c,float r):center(c),radius(r){};
    Circle():center(Point(0, 0)),radius(0){};
};
// --------------------------------------
Circle findMinCircle(Point** points,size_t size);
double distance(const Point& a, const Point& b);
// you can add here additional methods

#endif /* MINCIRCLE_H_ */
