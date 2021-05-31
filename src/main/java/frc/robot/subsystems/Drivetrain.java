// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.team88.swerve.SwerveController;

/**
 * It is tradition on team 88 for every subsystem to be accompanied by a haiku.
 *
 * <p>The drive subsystem <br>
 * contains the swerve controller <br>
 * and calls its methods.
 */
public class Drivetrain extends SubsystemBase {

  // The controller for the swerve drive.
  private SwerveController swerve;

  // The path to the swerve config file in the deploy directory.
  private static final String SWERVE_CONFIG = "swerve.toml";

  /** Constructor. */
  public Drivetrain() {
    this.swerve = new SwerveController(SWERVE_CONFIG);
    this.setYaw(0.);
  }

  // This is the default command that normally operates the drivetrain via the XBox controller
  public void manualDrive(double yDirection, double xDirection, double rotation) {
    rotation *= DriveConstants.MAX_ROTATION;
    var translationSpeed = Math.max(yDirection, xDirection) * DriveConstants.MAX_SPEED;
    setVelocity(translationSpeed, rotation);
    setTranslationDirection(calculateTranslationDirection(xDirection, yDirection), true);
  }

  /** Updates the swerve controller. Should be called at the end of each program loop. */
  public void update() {
    this.swerve.update();
  }

  /**
   * Sets the current yaw to be read by the gyro.
   *
   * @param yaw The yaw to be set as the current heading of the robot.
   */
  public void setYaw(double yaw) {
    this.swerve.setGyroYaw(yaw);
  }

  /** Sets the translation and rotation speeds to zero and holds the module steering in place. */
  public void holdDirection() {
    this.swerve.holdDirection();
  }

  /**
   * Sets the translation speed and rotation velocity of the robot, without modifying the direction
   * of translation.
   *
   * @param translationSpeed The speed for translation, in feet per second.
   * @param rotationVelocity The velocity for rotation, in degrees per second.
   */
  public void setVelocity(double translationSpeed, double rotationVelocity) {
    this.swerve.setVelocity(translationSpeed, rotationVelocity);
  }

  /**
   * Sets the direction of translation as either a field-centric or robot-centric angle.
   *
   * @param translationDirection The direction to translation, in degrees increasing
   *     counterclockwise with forwards at 0.
   * @param fieldCentric If true, the direction will be intrepretted relative to the gyro's zero
   *     point. If false, it will be interpretted relative to the front of the robot.
   */
  public void setTranslationDirection(double translationDirection, boolean fieldCentric) {
    this.swerve.setTranslationDirection(translationDirection, fieldCentric);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // update() is not called here because it would occur before commands are run.
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  /**
   * Calculates the angle of translation set by the left stick.
   *
   * @return The angle of translation, in degrees. 0 corresponds to forwards, and positive
   *     corresponds to counterclockwise.
   */
  private double calculateTranslationDirection(double x, double y) {
    // Calculate the angle.
    return Math.toDegrees(Math.atan2(y, x));
  }

  /**
   * Determines if the left stick is pressed out far enough to merit changing the translation
   * direction. If the joystick is close to the center, it is too difficult to control the
   * direction.
   *
   * @return True if the current translation direction should be changed, false if it should stay
   *     the same.
   */
  private boolean shouldChangeDirection(double x, double y) {
    // Calculate the magnitude of the joystick position and use it as the threshold.
    return Math.sqrt(x * x + y * y) >= DriveConstants.CHANGE_DIRECTION_THRESHOLD;
  }
}
