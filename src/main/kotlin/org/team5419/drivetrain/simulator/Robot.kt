package org.team5419.drivetrain.simulator

object Robot : AbstractRobot(
    wheelBase = 20.0,
    maxVelocity = 30.0,
    acceleration = 5.0,
    startX = 300.0,
    startY = 300.0
) {

    //use setPercent(left: Double, right: Double) to set left and right motor power
    //use getLeftDistance(): Double to read the distance travled by the left wheel
    //use getRightDistance(): Double to read the distance travled by the left wheel

    override fun robotInit() {
    }
    
    override fun robotPeriodic() {
    }

}