package com.team5115;

public class ForwardProfile {
	
	/**
	 * This is a motion profile
	 * This profile maps out the velocity so that it accelerates to a certain maximum velocity and decelerates in a smooth motion
	 * The velocity vs time graph should make a trapezoid
	 * You can use the motion profile to give you velocity and acceleration at a certain time t
	 */
	
	double maxVelocity;
	double maxAcceleration;
	double distance;
	double t1;		// This is the time for the acceleration and decceleration portions of the profile
	double t2;		// This is the time for when the profile is at a constant velocity
	double time;	// This is the total time
	
	public ForwardProfile(double maxv, double maxa, double dist) {
		maxVelocity = maxv;
		maxAcceleration = maxa;
		distance = dist;
		
		t1 = maxVelocity / maxAcceleration;
		t2 = (Math.abs(distance) - (t1 * maxVelocity)) / maxVelocity;
		time = (2 * t1) + t2;
		
		maxVelocity *= Math.signum(distance);
		maxAcceleration *= Math.signum(distance);
	}
	
	public double getVelocity(double t) {
		if (t < t1) {
			return t * maxAcceleration;
		} else if (t < t1 + t2) {
			return maxVelocity;
		} else if (t < time){
			System.out.println((maxVelocity - ((t - t2 - t1) * maxAcceleration)));
			return (maxVelocity - ((t - t2 - t1) * maxAcceleration));
		} else {
			return 0;
		}
	}
	
	public double getAcceleration(double t) {
		if (distance == 0) {
			return 0;
		} else if (t < t1) {
			return maxAcceleration;
		} else if (t < t1 + t2) {
			return 0;
		} else if (t < time) {
			return -maxAcceleration;
		} else {
			return 0;
		}
	}
}