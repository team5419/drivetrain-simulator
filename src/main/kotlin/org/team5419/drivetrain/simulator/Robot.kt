package org.team5419.drivetrain.simulator

object Robot : AbstractRobot(
    wheelBase = 20.0,
    maxVelocity = 30.0,
    acceleration = 5.0,
    startX = 300.0,
    startY = 300.0
) {

    //use setPercent(left: Double, right: Double) to set left and right motor power
    //use getLeftDistance(): Double to read the distance traveled by the left wheel
    //use getRightDistance(): Double to read the distance traveled by the left wheel
    //use getHeading(): Double to read the robot's heading in radians

    private val pid = PID(0.1, 0.0, 0.000)
    private const val target = Math.PI

    override fun robotInit() {
    }
    
    override fun robotPeriodic() {
        val output = pid.calculate(target - getHeading(), dt)
        setPercent(output, -output)
    }

}