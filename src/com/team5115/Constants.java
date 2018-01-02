package com.team5115;

public class Constants {

	/**
	 * This class exists solely to store constant values that will remain the same for the whole robot
	 * While referencing this class is not necessary, it is a good organizational habit
	 * That means it's mandatory because if you don't do it, brian will be sad
	 */
	
	public static final double DELAY = 0.005;

	// Buttons and Axes
	public static final int AXIS_X = 0;
	public static final int AXIS_Y = 1;
	public static final int AXIS_Y_CAM = 6;
	public static final int AXIS_X_CAM = 5;
	public static final int BUTTON_TURN_AROUND = 10;
	public static final int BUTTON_QUICK_TURN = 9;
	public static final int BUTTON_AIM_FUEL = 5;
	public static final int BUTTON_AIM_GEAR = 3;
	public static final int BUTTON_SHOOT = 1;
	public static final int BUTTON_CANCEL = 7;
	public static final int BUTTON_CLIMB = 6;
	public static final int BUTTON_SWITCH_DIRECTION = 2;
	public static final double JOYSTICK_DEADBAND = 0.2;
	public static final int JOYSTICK_EXPO = 2;

	// Motors
	public static final int FRONT_LEFT_MOTOR_ID = 3;
	public static final int FRONT_RIGHT_MOTOR_ID = 4;
	public static final int BACK_LEFT_MOTOR_ID = 1;
	public static final int BACK_RIGHT_MOTOR_ID = 2;
	public static final int INTAKE_VICTOR = 1;
	public static final int AGITATOR_VICTOR = 2;
	public static final int FEEDER_VICTOR = 3;
	public static final int FLYWHEEL_VICTOR = 4;

	public static final int CLIMBER_VICTOR = 0;
	public static final int CAMERA_SERVO = 5;

	// Driving
	public static final double THROTTLE = 0.5;
	public static final double QUICK_TURN_POWER = 0.5;
	public static final double NEG_INERTIA_TURN = .002;
	public static final double NEG_INERTIA_SPEED = .001;
	public static final double TOP_SPEED = 10.8;	//actual, physical top speed of drivetrain, in cubic feet -seconds per square second-foot (ft^3 * s) / ((s*ft)^2)
	
	// Sensors
	public static final int ENCODER_CHANNEL_1 = 2;
	public static final int ENCODER_CHANNEL_2 = 3;
	
	//Directions
	public static final int DIR_GEAR = -1;
	public static final int DIR_INTAKE = 1;
	
	//other constantinoples
	public static final double OPTIMUM_FLYWHEEL_RPM = 2750;

	public static final double FUEL_TOLERANCE_X = 6;
	public static final double FUEL_TOLERANCE_Y = 6;
	public static final double FUEL_DTOLERANCE = 100;
	
	public static final double GEAR_TOLERANCE = 10; //figure out actual value later
	public static final double GEAR_DTOLERANCE = 500;
	
	public static final double BOILER_ANGLE = .8;
	public static final double CLIMBER_ANGLE = .5;
	public static final double INTAKE_ANGLE = .3;

	public static final int TEST_THING_BUTTON = 0;
	
}