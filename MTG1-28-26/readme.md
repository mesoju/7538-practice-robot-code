# Guide to Creating a Subsystem

Have questions? Ask me directly on these platforms!

### Discord:

xyz.mega - Vincent Tong

FRC 7538 : This meeting took place on - 1/28/26 

## Importing Libraries
> [!TIP]
> Using WPILib's Visual Studio Code and installing the needed vendor dependencies, most import methods are auto-completed when you write code and autocomplete them by pressing Tab.

Libraries are essentially collections of many classes, often written by other developers and the manufacturers of hardware that require software libraries to operate. *For our KitBot's tank drive, we will need
to import all 'SparkMax' classes from the 'spark' library provided by REV Robotics. You may find yourself using other MotorControllers, and as such should import the necessary libraries for them.
```java
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
```
The imports should all be located at the top of the file. As you auto-complete code, the imports may also be generated automatically for you.

## Creating your Constants
The idea of creating a Constants file is to organize all of your values into one single file as a 'source of truth'. Sometimes, coding with a lot of numbers and values that otherwise remain static (never
changing) inside of the code can be overwhelming and sacrifice readability. For our KitBot, we have 4 different MotorControllers to ID and manage, so we will need to create a `Constants` under the `robot`
folder and store these IDs. As good coding practice, always make sure your names for variables are concise to save on time and management.

```java
  public static final class MotorConstants {
    public static final int TANK_DRIVE_RIGHT_CAN_ID = 1;
    public static final int TANK_DRIVE_LEFT_CAN_ID = 2;
    public static final int INTAKE_CAN_ID = 3;
    public static final int OUTTAKE_CAN_ID = 4;
  }
```

When you are generating your template robot, a `Constants` file should already be provided for you, and you can just copy the class above into your `Constants` file under the class.

## Creating Instance Variables for your Subsystem
Directly below the subsystem's class, you should set up the variables that your subsystem will need to utilize. 

```java
private SparkMax mlpoopie; // left
private SparkMax mlpookie; // right
private DifferentialDrive drive_train;
```

We won't assign them values yet because that job is reserved for the constructor method, but it does an important role by:
1. Setting the variables to `private` introduces encapsulation; only the subsystem can interact with the variables and other classes can not. This provides reliability and prevents unintended results.
2. The second keywords (i.e. `SparkMax` and `DifferentialDrive`) introduces type-safety; the general idea is that they indicate what the variable should be. For example, the variable `mlpoopie` can not be
   given a value like a `number` or `String`, and it guarantees that the variable HAS to be a `SparkMax` class / object.
3. Keeping your variables in one place keeps it organized, and is an easy place to reference the variables you need and create.

## Initializing your Variables
The variables won't have any data associated with them for now, they're simply `NULL` or nothing. This is where the constructor method comes in.

In Java, a constructor method essentially creates an 'object' of that class that has its own values and methods associated with it based on the class itself. Imagine a `Car` class that you can 'construct' for
each car you want to manufacture, where each `Car` object has its methods and variables like `carColor` inherited from the `Car` class. With each object, you can set values you want for that specific object
to describe it, and also run methods on that object to interact with it. [If you're interested, GeeksforGeeks does a great job of explaining this.](https://www.geeksforgeeks.org/java/object-oriented-programming-oops-concept-in-java/)

```java
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
```

This is our constructor method. Generally, all constructors should be `public` so other classes can access it, and the name of the constructor should ALWAYS be the same as the file's name.
In the lines 2-5, we create a new `SparkMaxConfig` that provides default configuration values for the motor to operate normally. 

In the lines 7-8, we create two new motors using the variables we created in the last step. We use `new` to indicate that we're creating a new object, and then `SparkMax()` to indicate the class we're creating,
Inside the parenthesis, we will provide the parameters that 'describe' the MotorController and Motor, where we provide the appropriate CAN_ID to identify and operate the intended motor, then set the MotorType
second parameter to `MotorType.kBrushed` since our CIM motors are brushed. *I am not entirely sure of the difference between Brushed and Brushless motors, but they can not operate with the same code / 
techniques as it would cause damage due to differences in voltage and rotation. You are entirely free to research the motors for what Brushed and Brushless means, but its not needed for coding the robot 
other than to know what type the motor is.

In lines 10-11, we then apply the configurations to the `SparkMax` MotorControllers we created. Pass through the `defaultSparkConfig`, then pass along `ResetMode` and `PersistMode` data. Essentially, the last
two parameters are indicating that in the case of a short or issue with the motors, the parameters are preserved, ...at least I think so, but again not explicitly important for coding.

In line 13, we create a `DifferentialDrive` object that is provided by WPILib and takes two motors and moves them through the provided MotorControllers using the `tankDrive` method associated with it.

## Creating your Subsystem Method
Finally, you'll create a subsystem method, which is essentially how commands interact with subsystems in order to interact with motors or components in a specified way through the method by passing in
parameters such as a `speed` percentage.

```java
  public void spinnywinny(double left_speed, double right_speed) {
    drive_train.tankDrive(left_speed, right_speed);
  }
```

The method will be given the `public` access modifier so that other classes like `Commands` to be able to access the method. 

The `void` keyword indicates that the method does not return any value.

You can name the method anything, again it's recommended to choose a coherent name so that you'll know easily what method to use as you need to use it, but `spinnywinny` is pretty funny too.

i don't see why anyone should code if they're not having fun ykyk.

Line 2 is really where the method does something. Essentially, it is taking the object `drive_train` which is of class `DifferentialDrive`. You then use the method `tankDrive` of the class, and provide
speed parameters passed from the `spinnywinny` method into the `tankDrive` method. The parameters are of type `double` indicating that they are numbers with decimals (i.e. 0.0, 5.2, 2.3453). For the robot,
the speed values should only be between -1 to 1 as it represents the percentage of a joysticks yaw or pitch (up down and left right), and the motors also only accept values in these ranges and uses them as
percentages to apply the proper voltage for the desired speed. A speed value of absolute value (ignoring if there is a negative) 1 indicates that the motor will run at the maximum speed, 0.5 indicates half speed,
and 0 just means to stop. 

```java
public void stop() {
        drive_train.tankDrive(0.0, 0.0);
    }
```

The code above is not implemented in the .java file, but may be important for you to have. It works similarly to `spinnywinny` but instead of taking any parameters, it immediately halts the motors by 
setting their speeds to 0. pretty self-explanatory, right?

***As always, if you have questions, feel free to ask me! I will help you whenever I am free, good luck to you on your journey as an FRC programmer.***
