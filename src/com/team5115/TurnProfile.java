package com.team5115;

public class TurnProfile {
	
	/**
	 * This state machine is basically the same as the forward profile, except that it takes an angle
	 * It also replaces distance from forward profile and uses radius * angle
	 * In order to make use of this profile, you should add it to the forward profile for one side of the robot,
	 * and subtract it from the forward profile for the other side
	 * For example, in your Autonomous Drive class, you might put something like this:
	 * e.g. vel_left = forward.getVelocity(t) + turn.getVelocity(t);
	 * vel_right = forward.getVelocity(t) - turn.getVelocity(t);
	 */
	
	double maxVelocity;
	double maxAcceleration;
	double x = 0;
	double angle = 0;
	public double velocity = 0;
	public double acceleration = 0;
	double radius = 1;
	double t1;
	double t2;
	double time;
	
	public TurnProfile(double maxv, double maxa, double a) {
		maxVelocity = maxv;
		maxAcceleration = maxa;
		angle = Math.toRadians(a);
		
		t1 = maxVelocity / maxAcceleration;
		t2 = ((radius * Math.abs(angle)) - (t1 * maxVelocity / 2)) / maxVelocity;
		time = (2 * t1) + t2;
		
		maxVelocity *= Math.signum(angle);
		maxAcceleration *= Math.signum(angle);
	}
	
	public double getVelocity(double t) {
		if (t < t1) {
			return t * maxAcceleration;
		} else if (t < t1 + t2) {
			return maxVelocity;
		} else if (t < 2 * t1 + t2){
			return maxVelocity - ((t - t2 - t1) * maxAcceleration);
		} else {
			return 0;
		}
	}
	
	public double getAcceleration(double t) {
		if (t < t1) {
			return maxAcceleration;
		} else if (t < t1 + t2) {
			return 0;
		} else if (t < 2 * t1 + t2){
			return -maxAcceleration;
		} else {
			return 0;
		}
	}
}