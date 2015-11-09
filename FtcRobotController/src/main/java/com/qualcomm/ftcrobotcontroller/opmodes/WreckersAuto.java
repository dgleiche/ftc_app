package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.util.Range;

public class WreckersAuto extends OpMode {
    //Motors n sensors n stuff
    DcMotor motorFL, motorFR, motorBL, motorBR;

    ColorSensor colorSensor;
    GyroSensor gyroSensor;

    Servo yellowManStake;

    private int autoState = 0;

    final int yellowManBuried = 100;
    final int yellowManNotBuried = 0;

    //Current x-gyro heading
    float headingX = 0;

    public WreckersAuto() {

    }

    @Override
    public void init() {
        motorFL = hardwareMap.dcMotor.get("motorFL");
        motorFR = hardwareMap.dcMotor.get("motorFR");

        motorBL = hardwareMap.dcMotor.get("motorBL");
        motorBR = hardwareMap.dcMotor.get("motorBR");

        yellowManStake = hardwareMap.servo.get("yellowMan");

        colorSensor = hardwareMap.colorSensor.get("color");
        gyroSensor = hardwareMap.gyroSensor.get("gyro");

        gyroSensor.calibrate();

        //motorFL.setDirection(DcMotor.Direction.REVERSE);
        //motorFR.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {
        switch (autoState) {
            case 0:
                break;
            default:
                break;
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

    /*void updateHeadingX()
    {
        float rotSpeed = HTGYROreadRot(gyroX);
        float dt = time1[T1];//Update time that has passed
        if((rotSpeed != initial_rotSpeedX) || dt >= 20)
        {
            headingX += (initial_rotSpeedX * (dt/1000)); //updates the heading
            resetHeadingX();
        }
    }*/
}
