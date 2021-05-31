# Command Based Swerve Robot Example

This example demonstrates basic controls for a swerve drive using WPILIB's command-based
programming framework.

## Controls

The code used an XBox gamepad for controls, with the following mapping:

 * Left stick Y - Steer the Y direction and speed of the robot.
 * Right stick X - Steer the X direction and speed of the robot.
 * Right trigger - Spin Robot clockwise
 * Left trigger - Spin Robot counter-clockwise
 * Y button - Zeros the gyro so that the robot is facing forwards. Only works while disabled.

## Important files

Differences between the regular command example

* Use a different Joystick setup as mentioned above.
* Doesn't use a ManualDrive or SetGyroYaw command instead does inline and in subsystem.
* Expanded constants using subsystem constants
* All Joystick operations are done in RobotContainer
* Uses the 6391Xbox class to make joystick less verbose and handle deadband
* Doesn't include option to switch to robot centric will add if needed.
