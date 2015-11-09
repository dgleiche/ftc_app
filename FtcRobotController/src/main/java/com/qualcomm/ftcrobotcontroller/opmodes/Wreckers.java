package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

public class Wreckers extends OpMode {
    DcMotor motorFL, motorFR, motorBL, motorBR;

    public Wreckers() {

    }

    @Override
    public void init() {
        motorFL = hardwareMap.dcMotor.get("motorFL");
        motorFR = hardwareMap.dcMotor.get("motorFR");

        motorBL = hardwareMap.dcMotor.get("motorBL");
        motorBR = hardwareMap.dcMotor.get("motorBR");

        //motorFL.setDirection(DcMotor.Direction.REVERSE);
        //motorFR.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {
        //Send data back to driver
        telemetry.addData("Text", "*** Robot Data***");

        if (gamepad1.dpad_left) {
            setMotorVals(-0.43, 0.43);
        } else if (gamepad1.dpad_down)  {
            setMotorVals(-0.43, -0.43);
        } else if (gamepad1.dpad_right) {
            setMotorVals(0.43, -0.43);
        } else if (gamepad1.dpad_up) {
            setMotorVals(0.43, 0.43);
        } else {
            //Joystick control
            float throttle = -gamepad1.left_stick_y;
            float direction = gamepad1.right_stick_x;
            float right = throttle - direction;
            float left = throttle + direction;

            // clip the right/left values so that the values never exceed +/- 1
            right = Range.clip(right, -1, 1);
            left = Range.clip(left, -1, 1);

            // scale the joystick value to make it easier to control
            // the robot more precisely at slower speeds.
            right = (float)scaleInput(right);
            left =  (float)scaleInput(left);

            // write the values to the motors
            motorFR.setPower(right);
            motorFL.setPower(left);

            motorBL.setPower(left);
            motorBR.setPower(right);

            telemetry.addData("left tgt pwr", "left  pwr: " + String.format("%.2f", left));
            telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
        }
    }

    @Override
    public void stop() {

    }

    private void setMotorVals(double l, double r) {
        motorFL.setPower(l);
        motorFR.setPower(r);

        motorBL.setPower(l);
        motorBR.setPower(r);
    }

    /*
	 * This method scales the joystick input so for low joystick values, the
	 * scaled value is less than linear.  This is to make it easier to drive
	 * the robot more precisely at slower speeds.
	 */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }
}
