package org.team5419.drivetrain.simulator

object Robot : AbstractRobot(
    wheelBase = 20.0,
    maxVelocity = 30.0,
    startX = 300.0,
    startY = 300.0
) {

    override fun robotInit() {
        setPercent(0.5, -0.5)
    }
    
    override fun robotPeriodic() {

    }

}