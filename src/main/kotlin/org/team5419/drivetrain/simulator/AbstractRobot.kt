package org.team5419.drivetrain.simulator

import kotlin.math.*

abstract class AbstractRobot (
    val wheelBase: Double, //pixels
    val maxVelocity: Double, //pixels/s
    val acceleration: Double, //pixels/s/s
    startX: Double = 0.0,
    startY: Double = 0.0,
    startTheta: Double = 0.0
) {
    var x: Double = startX
    var y: Double = startY
    var theta: Double = startTheta

    private var dtheta: Double = 0.0
    private var targetVelocity: Double = 0.0
    var velocity: Double = 0.0

    var leftWheel: Double = 0.0
    var rightWheel: Double = 0.0

    val dt = 0.05

    private var leftDist = 0.0
    private var rightDist = 0.0

    init{
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
            val r = wheelBase * (leftWheel + rightWheel) / 2 / abs(leftWheel - rightWheel) //turn radius
            val dist = targetVelocity * dt
            val h = sqrt(r*r - dist*dist/4)
            dtheta = 2 * atan(dist/2/h)

        }
    }

    final fun update() {
        robotPeriodic()

        theta += dtheta
        theta %= (Math.PI * 2)

        velocity += acceleration.coerceAtMost(abs(targetVelocity - velocity)) * dt * sign(targetVelocity - velocity)
        velocity = velocity.coerceIn(-maxVelocity, maxVelocity)

        x += sin(theta) * velocity * dt
        y += cos(theta) * velocity * dt

        leftDist += velocity * dt
        rightDist += velocity * dt
    }

    abstract fun robotInit()

    abstract fun robotPeriodic()

    protected fun getLeftDistance(): Double = leftDist
    protected fun getRightDistance(): Double = rightDist
    protected fun getHeading(): Double = theta
}