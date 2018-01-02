package com.team5115.systems;

import com.team5115.Constants;
import com.team5115.robot.Roobit;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;

public class Camera {
	
	Servo yServo;
	
	public Camera() {
		yServo = new Servo(Constants.CAMERA_SERVO);
	}
	
	public void setAngle(double angle) {
		yServo.set(angle);
	}
	
}