package org.team5419.drivetrain.simulator

import kotlin.math.sign

abstract class AbstractRobot (
    val wheelBase: Double, //pixels
    val maxVelocity: Double, //pixels/s
    val acceleration: Double, //pixels/s/s
    startX: Double = 0.0,
    startY: Double = 0.0,
    startTheta: Double = 0.0
) {
    public var x: Double
    public var y: Double

    public var theta: Double
    private var dtheta: Double = 0.0
    private var targetVelocity: Double = 0.0
    public var velocity: Double = 0.0

    public var leftWheel: Double = 0.0
    public var rightWheel: Double = 0.0


    public val dt = 0.05

    protected var leftDist = 0.0
    protected var rightDist = 0.0

    init{
        x = startX
        y = startY
        theta = startTheta
        robotInit()
    }

    protected final fun setPercent(left: Double, right: Double){

        leftWheel = left.coerceIn(-1.0, 1.0)
        rightWheel = right.coerceIn(-1.0, 1.0)

        targetVelocity = (leftWheel + rightWheel) / 2 * maxVelocity

        if(leftWheel == rightWheel) {
            dtheta = 0.0
        } else if(rightWheel == -leftWheel) {
            dtheta = leftWheel * maxVelocity / wheelBase * dt
        } else if(leftWheel != rightWheel) {
            val r = wheelBase * (leftWheel + rightWheel) / 2 / Math.abs(leftWheel - rightWheel) //turn radius
            val dist = targetVelocity * dt
            val h = Math.sqrt(r*r - dist*dist/4)
            dtheta = 2 * Math.atan(dist/2/h)

        }
    }

    public final fun update() {
        robotPeriodic()

        theta += dtheta
        theta = theta % (Math.PI * 2)
        println("%.${3}f %.${3}f".format(targetVelocity, velocity))

        velocity += Math.min(acceleration, Math.abs(targetVelocity - velocity)) * dt * sign(targetVelocity - velocity)
        velocity = velocity.coerceIn(-maxVelocity, maxVelocity)

        x += Math.sin(theta) * velocity * dt
        y += Math.cos(theta) * velocity * dt

        leftDist += velocity * dt
        rightDist += velocity * dt
    }

    final private fun getLeftDistance(): Double = leftDist
    final private fun getRightDistance(): Double = rightDist

    abstract fun robotInit()

    abstract fun robotPeriodic()

    protected fun getLeftDistance(): Double = leftDist
    protected fun getRightDistance(): Double = rightDist
}