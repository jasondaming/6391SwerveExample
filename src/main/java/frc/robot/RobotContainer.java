// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.OIConstants;
import frc.robot.UA6391.Xbox6391;
import frc.robot.subsystems.Drivetrain;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drivetrain = new Drivetrain();

  private final WaitCommand m_autoCommand = new WaitCommand(1);

  // The drive XBox controller.
  Xbox6391 drv = new Xbox6391(OIConstants.GAMEPAD_PORT, OIConstants.JOYSTICK_DEADBAND);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Set default drivetrain command.
    CommandScheduler.getInstance()
        .setDefaultCommand(m_drivetrain, new InstantCommand(() -> m_drivetrain.manualDrive(drv.JoystickLY(), drv.JoystickRX(), drv.TriggerCombined())));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // If disabled, zero the gyro when the Y button on the gamepad is pressed.
    drv.YButton.whenPressed(
        new ConditionalCommand(
            new InstantCommand(() -> m_drivetrain.setYaw(0)),
            new WaitCommand(0),
            DriverStation.getInstance()::isDisabled));
  }

  /** Calls update on the drivetrain subsystem. Should be called after the scheduler run. */
  public void updateDrivetrain() {
    m_drivetrain.update();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
