package org.team5419.drivetrain.simulator

import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sign
import kotlin.math.sin

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


    var leftPercent: Double = 0.0
    var rightPercent: Double = 0.0
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

        leftPercent = left.coerceIn(-1.0, 1.0)
        rightPercent = right.coerceIn(-1.0, 1.0)

    }

    final fun update() {
        robotPeriodic()

        leftWheel += acceleration.coerceAtMost(abs(leftPercent - leftWheel)) * dt * sign(leftPercent - leftWheel)
        rightWheel += acceleration.coerceAtMost(abs(rightPercent - rightWheel)) * dt * sign(rightPercent - rightWheel)
        velocity = (leftWheel + rightWheel) / 2 * maxVelocity
        velocity = velocity.coerceIn(-maxVelocity, maxVelocity)

        if(leftWheel == rightWheel){
            theta += 0.0
        } else if(rightWheel == -leftWheel){
            theta += leftWheel * maxVelocity / wheelBase * dt
        } else {
            val r = wheelBase * (leftWheel + rightWheel) / 2 / abs(leftWheel - rightWheel) //turn radius
            val dist = velocity * dt
            theta += dist / r
            //val h = sqrt(r*r - dist*dist/4)
            //theta += 2 * atan(dist/2/h)
        }


        theta %= (Math.PI * 2)


        x += sin(theta) * velocity * dt
        y += cos(theta) * velocity * dt

        leftDist += velocity * dt
        rightDist += velocity * dt
    }

    abstract fun robotInit()

    abstract fun robotPeriodic()

    fun getLeftDistance(): Double = leftDist
    fun getRightDistance(): Double = rightDist
    fun getHeading(): Double = theta
    fun getHeadingInDegrees(): Double = theta / Math.PI * 180
}