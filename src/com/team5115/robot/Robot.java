package com.team5115.robot;

import com.team5115.Constants;
import com.team5115.PID;
import com.team5115.statemachines.AgitatorManager;
import com.team5115.statemachines.AutoDrive;
import com.team5115.statemachines.CameraManager;
import com.team5115.statemachines.ClimberManager;
import com.team5115.statemachines.Drive;
import com.team5115.statemachines.FuelManipulatorManager;
import com.team5115.statemachines.IntakeManager;
import com.team5115.statemachines.FlywheelManager;
import com.team5115.statemachines.SwitchDirection;
import com.team5115.systems.Agitator;
import com.team5115.systems.Camera;
import com.team5115.systems.Climber;
import com.team5115.systems.DriveTrain;
import com.team5115.systems.Intake;
import com.team5115.systems.Flywheel;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
	
/**
 * The Robot class handles the basic functions of the robot
 * Subsystems and State Machines are created and run during the various stages of operation
 * Robot has 3 distinct phases, autonomous, teleop, and disabled
 * Autonomous - Robot runs by itself
 * Teleop - Robot is controlled by the driver
 * Disabled - Robot is off 
 * (Note: if you have no clue as to what your doing, there is a Google slides presentation on the shared FRC files in your drive named "How To code" )
 */
	
	/**
	 * First, we define all of the subsystems, state machines, and other variables
	 * To define an object or variable, follow this format:
	 * public static class name;
	 * e.g. public static DriveTrain dt;
	 * The name can be anything you want
	 * Don't forget to end your code with a ;
	 */
	
	// Define subsystems
	public static DriveTrain drivetrain;
	public static Intake intake;
	public static Flywheel flywheel;
	public static Agitator agitator;
	public static Climber climber;
	public static Camera camera;
	
	// Define state machines
	public static Drive hd;
	public static FuelManipulatorManager fuel;
	public static ClimberManager cm;
	public static CameraManager cam;
	public static SwitchDirection sd;
	public static AutoDrive ad;
	
	// Define other stuff
	public static NetworkTable nt;
	public static double xOffset;
	public static double gearOffset;
	public static double yOffset;
	public static int team;
	public static int action;
	public static int position;
	public static SendableChooser teamChooser;
	public static SendableChooser positionChooser;
	public static SendableChooser actionChooser;
	

	// Initialization phase of the robot class
	// This method runs once when the robot turns on and does not run again until the robot reboots
	public void robotInit() {
	// Initialize objects and variables here
		
		/**
		 * Constructing an object runs the initialization method of the class
		 * To construct an object, follow this format:
		 * name-of-object = new class();
		 * e.g. dt = new DriveTrain();
		 * To construct a variable, give it a default value
		 * e.g. var = 0;
		 * In this case, 0 is the default value that you set it to initially
		 * You can also give the constructor variables like you would any other method
		 * e.g. object = new Class(20);
		 * Make sure that the value you give it is consistent with what the constructor method in the class asks for
		 */
		
		// Initialize subsystems
		drivetrain = new DriveTrain();
		intake = new Intake();
		flywheel =  new Flywheel();
		agitator = new Agitator();
		climber = new Climber();
		camera = new Camera();
		
		// Initialize state machines
		hd = new Drive();
		fuel = new FuelManipulatorManager();
		cm = new ClimberManager();
		cam = new CameraManager();
		sd = new SwitchDirection();
		
		// Initialize variables
		xOffset = 0;
		yOffset = 0;
		
		// Initialize network tables and SmartDashboard choosers for autonomous
		nt = NetworkTable.getTable("pi");
    	nt.putNumber("rioStatus", 0);    	
    	positionChooser = new SendableChooser();
    	actionChooser = new SendableChooser();
    	teamChooser = new SendableChooser();
    	teamChooser.addDefault("Red", 1);
    	teamChooser.addObject("Blue", 2);
    	actionChooser.addDefault("Gear", 1);
    	actionChooser.addObject("Fuel", 2);
    	positionChooser.addDefault("Left", 3);
    	positionChooser.addObject("Center", 4);
    	positionChooser.addObject("Right", 5);
    	SmartDashboard.putData("Action", actionChooser);
    	SmartDashboard.putData("Position", positionChooser);
    	SmartDashboard.putData("Team", teamChooser);
	}

	// Runs once when the autonomous phase of the game starts
	public void autonomousInit() {
	// Initialize autonomous state machines here
		
		// Gets the selected values from the SmartDashboard
//		team = (int) teamChooser.getSelected();
//		position = (int) positionChooser.getSelected();
//		action = (int) actionChooser.getSelected();
		
		/**
		 * To initialize a state machine, follow this format:
		 * object.setState(initialState)
		 * e.g. dt.setState(DriveTrain.START);
		 * Per this example, the initialState value should come from the class of the object
		 * You could just give it a normal integer like 0 or 1, but this is nicer organizationally
		 */
		
		
		fuel.setState(FuelManipulatorManager.INTAKE);
	}

	//Runs periodically while the game is in the autonomous phase
	public void autonomousPeriodic() {
		
		// Gets values from the raspberry pi
		nt.putNumber("riostatus", 1);
		xOffset = nt.getNumber("xOffset", 0);
		yOffset = nt.getNumber("yOffset", 0);
		gearOffset = nt.getNumber("gearOffset", 0);
		
		// Puts values onto the SmartDashboard
		SmartDashboard.putNumber("X Offset", xOffset);
		SmartDashboard.putNumber("Y Offset", yOffset);
		SmartDashboard.putNumber("left accel", drivetrain.leftAcceleration());
		SmartDashboard.putNumber("right accel", drivetrain.rightAcceleration());
		SmartDashboard.putNumber("left speed", drivetrain.leftSpeed());
		SmartDashboard.putNumber("right speed", drivetrain.rightSpeed());
		
		/**
		 * Updating the state machine runs all of the stuff in it
		 * To update a state machine, simply follow this format:
		 * object.update();
		 * e.g. dt.update();
		 */
		
		// Update state machines here
		ad.update();
		fuel.update();
		
		Timer.delay(Constants.DELAY);
	}

	// Runs once when the game enters the driver operated stage
	public void teleopInit() {
	// Initialize state machines here for teleop
		
		hd.setState(Drive.DRIVING);
		fuel.setState(FuelManipulatorManager.INTAKE);
		cm.setState(ClimberManager.STOP);
		cam.setState(CameraManager.INTAKE);

	}

	// Runs periodically when the game is in the driver operated stage
	public void teleopPeriodic() {
		
		// Put update commands for state machines here
		hd.update();
		fuel.update();
		cm.update();
		cam.update();
		sd.update();

		Timer.delay(Constants.DELAY);		
		SmartDashboard.putString("Direction", drivetrain.direction == Constants.DIR_ARM ? "ARM" : "BALL");
		
	}
	
	// Runs when the robot is disabled
	public void disabledInit() {
	// Set all state machines that run during teleop to the off state
		hd.setState(Drive.STOP);
		fuel.setState(FuelManipulatorManager.STOP);
	}

	// Runs periodically while the robot is disabled
	public void disabledPeriodic() {
		Timer.delay(Constants.DELAY);
	}

}

// This year's code brought to you by:
// Rohan and Takeshi

                                                                                                                                                             
                                           //..``               `'#@@@@@@@+`                                                                                                                  
                                     //`'@@@@@@@@@@@@#`       @@@@#:``  `;@@@'`                                                                                                               
                                  //`;@@#.          ;@@@'   @@+             .@@@                                                                                                              
                                 //+@@                 :@@+@'                  @@+                                                                                                            
                              //.@@                     :@#                    `#@                                                                                                           
                             //@@`                        @@                     @@                                                                                                          
                           //`@#                   ;@@@@@@@@#:            .''''''.@@                                                                                                         
                          //.@,                 +@@@:``` `@@@@@@@+.  `#@@@@@@@#@@@@@@@@@,                                                                                                    
                        //`@,               .@@+    .@@@@;```` `:@@@@+`         ;#@@@@@@@@@.                                 `.'@@@@@@@@@@@@@@#:                                            
                         //@.              :@@,    #@@;             +@`      :@@@#'``   ` ,#@@ `                           ;@@@@#'`        ```.#@@@'`                                        
                        //@'              @@.   `@@'                 +@  ``#@@,              ;@#                        `#@@:                     :@@+                                       
                       //#@             ;#+    #@.      `.+@@@@@@@@@#,@ .@@+       ,++@@@@++,  @@                      #@#                          `@@                                      
                       //@             +@     @#      #@@;           #@@@.     .+@@@,,` ``,#@@@.@`                   .@@                              @@`                                    
                      //##            @@    :@`    .@@,                #@   ;@@#:             '@@'                  ,@;                                @@`                                   
                    //:@            +@    :@     @@                    '@;@@                  `+@.                 @'                                  @@                                   
                    //@:           '@    `@    ;#;                      '@                      '@,               #@                                   .@                                   
                   //,@            @   ` @    @@                         @@                      +@               @;     `     '       #@+  @@@+        @:                                  
                  //@'           @'    @    @'     +@@#:                 @`   `@@@@@+            @@             '@      :     '.   @ @@#@''@  @.       +@                                  
                  //@            @    @.   @#    @@@@@@@@                @@  '@@@@@@@@           .@             @@      #`    ;'   +@@   @@   ;#       .@                                  
                 //;@           +.    #   @@    #@@@@@@@@@               ,@ :@@@@@@@@@@           @.            @:      #,    .#   ;@`   @@    @        @                                  
                //`@#           @    @   ;@    @@@@@@@@:#@@               @ @@@@@@@@@@@:          @#            @`      ;;     @    @    #@    @        @                                  
                 //@;          ,         @`   ,@@@@@@ `  #@               @:@@@@@@   ;@@          @@            @       ,'     @    @    '#    @        @                                  
                 //@,          ;        @@    @@@@@@+    .@               @#@@@@@     @@          @#            @`      `@    @@    @    ;@    @        @,     `                           
                 //@                    @     @@@@@@`    '@.              @#@@@@#`    @'          @#            @'       @@, #@@`   @,   .@    @        #@@@@@@@@@@@@:                     
                 //@                   @+     @@@@@@,    @@               @+@@@@@    #@           @;          '@@@        +@@@`@'   #;`   ``   '                    #@@@                   
                //;@,                  @      @@@@@@@#+@@@@              ;@:@@@@@@@@@@@           @          @@,                `                                      @@@                 
              //#@@@`               '@@;      @@@@@@@@@@@@,              @, @@@@@@@@@@;          :@        :@@                                                          .@@                
             //;@#  @#             +@:         :@@@@@@@@@@#              .@  ;@@@@@@@@#           @+       ,@'`                                                            @@               
            //@@     +            '#            @@@@@@@@@,               @.   ;@@@@@@.           '@       .@'  `                                                            @@              
            //@+                   '@:   +        :@@@@@'                ##      ;+'             ;@        @#                                                                 @#             
          //;@,                     +@@@@#@`                            ,@@                     #@`       +@                                               ;                  ;@             
        // '@`                            ;@#                         `@+ @                   ,@@@        @'         #      +                              @                   @@            
       //: @`                               @@:                      #@``@#                 :@@..@       '@          @      @                              +;             `    ;@            
       //@,                             `   .@@#                 ;@@,  ;@@@            '@@@@,  @+       @#          @      :'                             ;#           .       @+            
      //@+                              +@.  `.#@@+`         `+@@+    ##  @@@@@@@@@@@@@@;     @@  `    `@`          @       @             ``              .@    @#  .@@@@      #@           
     //:@                                `@@`    `@@@@@#@@@@@@;     ,@+   `@'               #@;        '@           @       @           `@@@+       ,,     @   @@   @,  @+     .@           
     //@:                                  +@@,      `.;,.`       ,@@      `@@       `  .#@#@#         @@           @       @           #. .@.  ;+,@@@@    #  @@    @           @,          
    //:@                                     :@@@'             :@@@         `@@@@@@@@@@@@;  @`         @@           @       #. @@@+   `#'   ;@  `@@:  @    @`@+  `  @#          @@          
    //@:                                        ,@@@@@@@@#@@@@@@+@            #@+          @@          #@         ;+@@@@@   ;+@@`:@       :,.@   @@   @.   @#@#      @@@        @@          
    //@                                              ,.,,::   `+@               #@'       @@@+`        #@         :+@;#;, `  @@   ##    #@@@@@   @:   ##   @: #,      ,@@       @@          
   //@@                                                      `@@                  :`    ;@;  @@        @@           @        @#   :@   .@   @@   @,   +@   @' ,@        @       @@          
   //@,                                                     @@:                        @@     @@    `  :@           @.       @,   `@   ;'    @   @:   ,@   :+  @@  ``   @.      @'          
   //@                                                   ;@@'                                  #@@@@    @,          +:       @,    @   :@   .@   @,   ,@   ;@  `@.  @   @       @.          
   //@                                    @@@@+;:,:`,+@@@@.                                    .@ ,@@   #@          ,@   ,;  @:    @    @@@@@@   @;    @   ,@   ;;  #@@@@      ;@           
  //,@                                   `   .+@@@@@@+;                                       +@.   @:  :@`          @   @,  @:`   @     `+; @   #+    @              ,.       @#           
  //#@                                     `                                                `@@     @#   @@          @@@@@                             ,                      ,@            
  //@+                                                                                     @@;`     @,    @'          +#.`                                                    @#            
  //@+                                                                                  ,@@'       :@     '@,                                                               `#@             
  //@+                                                                              `;@@@:         @@      #@#                                                              ;@              
  //@+                            ```                                        ` `'#@@@#.   `      `@@        :@@                                                            #@,              
  //@;                        +#@@@@@@@@@@@',,                          ,+#@#@@@+.               #@           @@#                                                        .@@                
  //@,                      ,@@`         `;+@@@@@@@@@@@+@@@##@@@@@@@@@@@@+,                     @@            `,@@@+.                                                  :@#;                 
  //@:                      @`                     ..,,,,,::,,,,        `                     +@#                 #@@@@@@'',..    `                                .+@@@:                   
  //@'                     @'                                               `              `#@@'@;                     :##@@@@@@@@@@@@@              `,'''@@@@@@@@@@@:                      
  //#@                    ;@                                                             '@@@`  .@'                                  @@            `@@@@###'+:;`                            
  //.@                    @;                                                         `;@@@@      :@                                 @@          ` `@@                                       
    //@`                   @          :@##@@##;#,:`                         ``::'#@@@@@@; `@       @;                              `@@           `;@@                                        
     //@+                   @         @@'+'+++#@@@@@#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@,.       @.      ##                             +@@            @@+                                         
      //'@                   #         #          `+@@:               `           +@         @       +@                           :@@.           `@@.                                          
       //@;                  @,        @@@#:         +@@                          @@        @@       @+                        .@@@:            #@@                                            
       //:@                  @@          ;@@@@',       #@@                        @'      '@@        @`     #@@''``      `.'#@@@#             :#@.                                             
        //@@                  @'            `;@@@@@'` ` `@@'                  ``;@@:;'@@@@@;        :@      @@@:#@@@@@@@@@@@;,              ,@@'                                               
         //@,                 ;@+`               .+@@@@@@@@@@@,.;:@'##@@@@@@@@@@@@@@@@'.            @'       `@@@`     `                  +@#;                                                 
         //#@                  ;@@,                    ..'#@@@@@#@@@'+':.`                         @@          `@@@;                  .@@@@,                                                   
          //@@                   @@@,                                                            `@@              +@@@@+,        .'#@@@@,                                                      
          //`@#                    #@@#,                                                       :@@#`                 ,'#@#@@@@@@@@@+,                                                          
           //`@@                     '@#@@#:                                              :+@@@@'                                                                                              
             //.@+                       `'@@@@@@+;.                          .;;@@@@@@@@@@@@@'                                                                                                 
             //;@+                         ` ..@@@@@@@#;`        ``:;'@@@@@@@@@@@#;:,``   @@                                                                                                   
            //` .@'                                `;#@@@@@@@@@@@@@@@@@':,              '@+                                                                                                    
               //`@@                                                                  ;@@                                                                                                      
                  //@@,                                                              ;@@`                                                                                                                //+@#                                                           @@@.                                                                                                         
                     //`@@:                                                     ` +#@'                                                                                                            
                       //;@@:                                                 ;@@@,                                                                                                              
                         //'@@;                                            ,@@@,                                                                                                                 
                           //,@@#,                                      .@@@.                                                                                                                    
                            //`#@@#.                               `:@@@'                                                                                                                       
                                 //+@@@@+,`                     ,+@@@#,                                                                                                                          
                                      //:#@@@@@@@@+,.+:''++@@@@@@#'.                                                                                                                              
                                              //,:'#@@@##@#';`                                                                                                                                     