/* Created Sat Jan 23 12:56:39 EST 2016 */
package org.usfirst.frc322.FRCTeam0322Strongback2016;

import org.strongback.Strongback;
import org.strongback.components.Motor;
import org.strongback.components.ui.ContinuousRange;
import org.strongback.components.ui.FlightStick;
import org.strongback.drive.TankDrive;
import org.strongback.hardware.Hardware;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	private static final int JOYSTICK_PORT = 1;
	private static final int LF_MOTOR_PORT = 1;
	private static final int LR_MOTOR_PORT = 2;
	private static final int RF_MOTOR_PORT = 3;
	private static final int RR_MOTOR_PORT = 4;

	private TankDrive drive;
	private ContinuousRange driveSpeed;
	private ContinuousRange turnSpeed;
	
    @Override
    public void robotInit() {
    	Motor left = Motor.compose(Hardware.Motors.talon(LF_MOTOR_PORT),
    								Hardware.Motors.talon(LR_MOTOR_PORT));
    	Motor right = Motor.compose(Hardware.Motors.talon(RF_MOTOR_PORT),
									Hardware.Motors.talon(RR_MOTOR_PORT)).invert();
    	drive = new TankDrive(left, right);
    	
    	FlightStick driveStick = Hardware.HumanInterfaceDevices.logitechAttack3D(JOYSTICK_PORT);
    	driveSpeed = driveStick.getPitch();
    	turnSpeed = driveStick.getRoll().invert();
    	
    	Strongback.configure().recordNoEvents().recordNoData().initialize();

    }

    @Override
    public void teleopInit() {
        // Start Strongback functions ...
        Strongback.start();
    }

    @Override
    public void teleopPeriodic() {
    	drive.arcade(driveSpeed.read(), turnSpeed.read());
    }

    @Override
    public void disabledInit() {
    	drive.stop();
        // Tell Strongback that the robot is disabled so it can flush and kill commands.
        Strongback.disable();
    }

}
