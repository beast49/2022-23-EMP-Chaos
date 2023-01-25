package org.firstinspires.ftc.teamcode.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name = "teleop2022", group = "Linear Opmode")

public class teleop2022 extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;
    private DcMotor raiseLeft = null;
    private DcMotor raiseRight = null;
    private Servo grip = null;
    private Servo flip = null;

    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        rightBack = hardwareMap.get(DcMotor.class, "rightBack");
        raiseLeft = hardwareMap.get(DcMotor.class, "raiseLeft");
        raiseRight = hardwareMap.get(DcMotor.class, "raiseRight");
        grip = hardwareMap.get(Servo.class, "grip");
        flip = hardwareMap.get(Servo.class, "flip");

        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        raiseLeft.setDirection(DcMotor.Direction.REVERSE);
        raiseRight.setDirection(DcMotor.Direction.REVERSE);
        grip.setDirection(Servo.Direction.FORWARD);
        flip.setDirection(Servo.Direction.FORWARD);

        waitForStart();
        runtime.reset();

//        gripLeft.setPosition(0.2);
//        gripRight.setPosition(0.2);

        while (opModeIsActive()) {
            double Threshold = .25;
            double playerOneSpeed = 1;
            double playerTwoSpeed = 1;
            double defaultPosition = 0.65;
            flip.setPosition(0.05);

            //speed adjustments
            if(gamepad1.b){
                playerOneSpeed = 1;
            }
            if(gamepad1.a){
                playerOneSpeed = 0.8;
            }
            if(gamepad1.x){
                playerOneSpeed = 0.6;
            }

            if(gamepad2.b){
                playerTwoSpeed = 1;
            }
            if(gamepad2.a){
                playerTwoSpeed = 0.8;
            }
            if(gamepad2.x){
                playerTwoSpeed = 0.6;
            }

            //Gamepad 1 controls
            if (Math.abs(gamepad1.left_stick_y) > Threshold) {
                leftFront.setPower((gamepad1.left_stick_y) * playerOneSpeed);
                leftBack.setPower((gamepad1.left_stick_y) * playerOneSpeed);
            }
            if (Math.abs(gamepad1.right_stick_y) > Threshold) {
                rightFront.setPower((gamepad1.right_stick_y) * playerOneSpeed);
                rightBack.setPower((gamepad1.right_stick_y) * playerOneSpeed);
            }
            if (Math.abs(gamepad1.right_stick_x) > 0.5) {
                leftFront.setPower((-gamepad1.left_stick_x) * playerOneSpeed);
                rightFront.setPower((gamepad1.left_stick_x) * playerOneSpeed);
            }
            if (Math.abs(gamepad1.left_stick_x) > 0.5) {
                leftBack.setPower((gamepad1.left_stick_x) * playerOneSpeed);
                rightBack.setPower((-gamepad1.left_stick_x) * playerOneSpeed);
            } else {
                leftFront.setPower(0);
                rightFront.setPower(0);
                leftBack.setPower(0);
                rightBack.setPower(0);
            }

            //Gamepad 2 controls
            if (Math.abs(gamepad2.left_stick_y) > Threshold) {
                raiseLeft.setPower((gamepad2.left_stick_y) * playerTwoSpeed);
                raiseRight.setPower((gamepad2.left_stick_y) * playerTwoSpeed);
            }
            else{
                raiseLeft.setPower(0);
                raiseRight.setPower(0);
            }

            if (gamepad2.right_trigger>0) {
                grip.setPosition(1);
            }
            if (gamepad2.left_trigger > 0) {
                grip.setPosition(defaultPosition);
            }


            if(gamepad2.dpad_right)
            {
                grip.setPosition((grip.getPosition())+0.01);
            }
            if(gamepad2.dpad_left)
            {
                grip.setPosition((grip.getPosition())-0.01);
            }
            if(gamepad2.dpad_up){
                grip.setPosition(defaultPosition);
            }

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)");
            telemetry.addData("Servos:", grip.getPosition());
            telemetry.update();
        }
    }
}

