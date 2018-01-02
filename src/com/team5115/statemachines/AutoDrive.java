package com.team5115.statemachines;

import com.team5115.ForwardProfile;
import com.team5115.PID;
import com.team5115.TurnProfile;
import com.team5115.robot.Roobit;
import com.team5115.statemachines.StateMachineBase;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoDrive extends StateMachineBase {

	/**
	 * This state machine converts the velocity and acceleration we get from the
	 * forward profile into values the robot can use It should be used in other
	 * classes which run the autonomous routines
	 */

	public static final int STOP = 0;
	public static final int START = 1;
	public static final int DRIVING = 2;

	ForwardProfile fp;
	TurnProfile tp;

	PID pidControllerLeft;
	PID pidControllerRight;

	double turnStartTime;

	double t;
	double startTime;
	double error_left;
	double error_right;
	double v_left;
	double v_right;
	double a;

	// These values are constants that should be tuned in order to make the
	// control smoother
	double kv = .1;
	double ka = 0;
	double kp = 0.05;
	double ki = 1.5;
	double kd = 0;

	// public boolean doneDriving;

	public AutoDrive(double maxv, double maxa, double distance, double angle, double delay) {
		fp = new ForwardProfile(maxv, maxa, distance);
		tp = new TurnProfile(maxv, maxa, angle);
		pidControllerLeft = new PID();
		pidControllerRight = new PID();

		a = angle;
		turnStartTime = delay;
	}

	public void update() {
		switch (state) {
		case START:
			pidControllerLeft.setActiveRange(0);
			pidControllerRight.setActiveRange(0);
			startTime = Timer.getFPGATimestamp();
			setState(DRIVING);
			break;

		case DRIVING:

			t = Timer.getFPGATimestamp() - startTime;

			double accel_left = fp.getAcceleration(t);
			double vel_left = fp.getVelocity(t);// * Roobit.drivetrain.direction;
			double accel_right = fp.getAcceleration(t);
			double vel_right = fp.getVelocity(t);// * Roobit.drivetrain.direction;

			if ((t >= turnStartTime) && (a != 0)) {
				accel_left += tp.getAcceleration(t - turnStartTime);
				vel_left += tp.getVelocity(t - turnStartTime);

				accel_right -= tp.getAcceleration(t - turnStartTime);
				vel_right -= tp.getVelocity(t - turnStartTime);
			}

			v_left = kv * vel_left + ka * accel_left /* + (pidControllerLeft.getPID(vel_left, Roobit.drivetrain.leftSpeed(), kp, ki, kd)) */;
			v_right = kv * vel_right + ka * accel_right /* + (pidControllerRight.getPID(vel_right, Roobit.drivetrain.rightSpeed(), kp, ki, kd)) */;
			
			if (vel_left == 0 && vel_right == 0)
				Roobit.drivetrain.drive(0, 0, 1);
			else
				Roobit.drivetrain.drive(v_left, v_right, 1);

			SmartDashboard.putNumber("Left Speed", Roobit.drivetrain.leftSpeed());
			SmartDashboard.putNumber("Right Speed", Roobit.drivetrain.rightSpeed());
			SmartDashboard.putNumber("Left V", v_left);
			SmartDashboard.putNumber("Right V", v_right);

			break;

		}
	}

	public boolean isFinished() {
		return fp.getAcceleration(t) == 0 && fp.getVelocity(t) == 0; // Will it work? Nobody knows...
	}
}