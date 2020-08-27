package org.team5419.drivetrain.simulator

abstract class AbstractRobot (
    val wheelBase: Double, //pixels
    val maxVelocity: Double, //pixels/s
    startX: Double = 0.0,
    startY: Double = 0.0,
    startTheta: Double = 0.0
) {
    public var x: Double
    public var y: Double

    public var theta: Double
    private var dtheta: Double = 0.0
    private var dv: Double = 0.0

    public var leftWheel: Double = 0.0
    public var rightWheel: Double = 0.0

    public val dt = 0.01

    private var leftDist = 0.0
    private var rightDist = 0.0

    init{
        x = startX
        y = startY
        theta = startTheta
        robotInit()
    }

    protected final fun setPercent(left: Double, right: Double){
        leftWheel = left
        rightWheel = right

        dv = (left + right) / 2 * maxVelocity

        if(left == right) {
            dtheta = 0.0
        } else if(right == -left) {
            dtheta = left * maxVelocity / wheelBase * dt
        } else if(left != right) {
            val r = wheelBase * (left + right) / 2 / Math.abs(left - right) //turn radius
            val dist = dv * 0.01
            val h = Math.sqrt(r*r - dist*dist/4)
            dtheta = 2 * Math.atan(dist/2/h)

        }
    }

    public final fun update() {
        robotPeriodic()

        theta += dtheta
        theta = theta % (Math.PI * 2)
        x += Math.sin(theta) * dv * dt
        y += Math.cos(theta) * dv * dt

        leftDist += leftWheel * maxVelocity * dt
        rightDist += rightWheel * maxVelocity * dt
    }

    final private fun getLeftDistance(): Double = leftDist
    final private fun getRightDistance(): Double = rightDist

    abstract fun robotInit()

    abstract fun robotPeriodic()
}