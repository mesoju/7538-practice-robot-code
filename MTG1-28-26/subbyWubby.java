// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.MotorConstantsKitBot;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class subbyWubby extends SubsystemBase {
  private SparkMax mlpoopie; // left
  private SparkMax mlpookie; // right
  private DifferentialDrive drive_train;

  /** Creates a new ExampleSubsystem. */
  public subbyWubby() {
    SparkMaxConfig defaultSparkConfig = new SparkMaxConfig();

    defaultSparkConfig
      .smartCurrentLimit(50)
      .idleMode(IdleMode.kBrake);

    mlpoopie = new SparkMax(MotorConstantsKitBot.TANK_DRIVE_LEFT_CAN_ID, MotorType.kBrushed);
    mlpookie = new SparkMax(MotorConstantsKitBot.TANK_DRIVE_RIGHT_CAN_ID, MotorType.kBrushed);

    mlpoopie.configure(defaultSparkConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    mlpookie.configure(defaultSparkConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    drive_train = new DifferentialDrive(mlpoopie, mlpookie);
  }

  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void spinnywinny(double left_speed, double right_speed) {
    drive_train.tankDrive(left_speed, right_speed);
  }
}
