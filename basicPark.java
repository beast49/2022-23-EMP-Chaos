package org.firstinspires.ftc.teamcode.Autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name = "basicPark", group = "Auto2022")
public class basicPark extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();


    // CRServo leftTop;

    static final double COUNTS_PER_MOTOR_REV = 537.7;    // eg: GoBilda 5203 Motor Encoder
    static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.5;
    static final double TURN_SPEED = 0.3;

    DcMotor leftFront;
    DcMotor rightFront;
    DcMotor leftBack;
    DcMotor rightBack;
    DcMotor turnTable;

    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Assign variables to hardware map
        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        // rightTop = hardwareMap.get(CRServo.class, "rightTop");

        //set the direction of the motors, left goes forward, right goes backward
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.FORWARD);


        //From Pushbot
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set all motors to zero power
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
//        leftServo1.setPower(0);
//        rightServo1.setPower(0);
//        leftServo2.setPower(0);
//        rightServo2.setPower(0);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Running Servos");
        telemetry.update();

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0", "Starting at %7d :%7d",
                leftFront.getCurrentPosition(),
                rightFront.getCurrentPosition());

        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        //actual motion of robot
        encoderDrive(DRIVE_SPEED, 2, 2, 2, 2, 2.0); // go forward 2 inches

        telemetry.addData("Path", "Complete");
        telemetry.update();

    }

    public void encoderDrive(double speed,
                             double left1Inches, double right1Inches, double left2Inches, double right2Inches,
                             double timeoutS) {
        int newLeftTarget;
        int newRightTarget;
        int newLeftBTarget;
        int newRightBTarget;


        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newLeftTarget = leftFront.getCurrentPosition() + (int) (left1Inches * COUNTS_PER_INCH);
            newRightTarget = rightFront.getCurrentPosition() + (int) (right1Inches * COUNTS_PER_INCH);
            newLeftBTarget = leftBack.getCurrentPosition() + (int) (left2Inches * COUNTS_PER_INCH);
            newRightBTarget = rightBack.getCurrentPosition() + (int) (right2Inches * COUNTS_PER_INCH);


            leftFront.setTargetPosition(newLeftTarget);
            rightFront.setTargetPosition(newRightTarget);
            leftBack.setTargetPosition(newLeftBTarget);
            rightBack.setTargetPosition(newRightBTarget);

            // Turn On RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();

            leftFront.setPower(Math.abs(speed));
            rightFront.setPower(Math.abs(speed));
            leftBack.setPower(Math.abs(speed));
            rightBack.setPower(Math.abs(speed));

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (leftFront.isBusy() && rightFront.isBusy()) && (leftBack.isBusy() && rightBack.isBusy())) {
                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget, newRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d",
                        leftFront.getCurrentPosition(),
                        rightFront.getCurrentPosition(),
                        leftBack.getCurrentPosition(),
                        rightBack.getCurrentPosition());
                telemetry.update();
            }
            // Stop all motion;
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftBack.setPower(0);
            rightBack.setPower(0);

            // Turn off RUN_TO_POSITION
            leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
}

