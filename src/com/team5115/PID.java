package com.team5115;

import com.team5115.robot.Roobit;

public class PID {
	
	/**
	 * What is PID?
	 * PID stands for Proportional-Integral-Derivative
	 * It is a type of feedback loop that takes in an error value and tries to compensate for the error
	 * Proportional control changes the motor input as a function of some fraction of the error in speed
	 * Integral control changes the motor input as a function of some fraction of the error in distance travelled(or distance left to travel)
	 * Derivative control changes the rate of change of the motor input as a function of some fraction of the error in the motor's acceleration
	 */
	
	public double error;
	double last_error = 0;
	public double error_accum = 0;
	public double derror;
	double output;
	
	double active_range = 0;
	
	/**
	 * Sets range in which PID loop will work. Range is the setpoint +/- r. Values outside the range will return 0 for output. 
	 * The default range is 0, in which case the PID loop always runs.
	 * @param r
	 */
	public void setActiveRange(double r) {
		active_range = r;
	}

	/**
	 * Runs the PID loop. Must be run every Constants.DELAY seconds.
	 * @param setpoint
	 * @param actual
	 * @param kp
	 * @param ki
	 * @param kd
	 * @return output of PID controller
	 */
	public double getPID(double setpoint, double actual, double kp, double ki, double kd) {
		error = setpoint - actual;
		
//		if (Math.abs(error) < active_range || active_range == 0) {
			// Calculation of the integral of the error
			error_accum += error * Constants.DELAY;
			
			// Calculation of the derivative of the error
			if (last_error == 0)
				derror = 0;
			else
				derror = (error - last_error) / Constants.DELAY;
			
			last_error = error;
			
			output = ((kp * error) + (ki * error_accum) + (kd * derror)) ;
//		} else {
//			output = 0;
//		}
//		
		return output;
	}
	
	/**
	 * Returns true if PID loop has settled. Takes tolerance in input units and dtolerance in input units per second.
	 * @param tolerance in input units
	 * @param dtolerance in input units per second
	 * @return true if PID loop has settled to within tolerances, false otherwise
	 */
	public boolean isFinished(double tolerance, double dtolerance) {
		return (Math.abs(error) < tolerance && Math.abs(derror) < dtolerance);
	}
}