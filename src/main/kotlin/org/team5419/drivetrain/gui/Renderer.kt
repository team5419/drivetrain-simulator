package org.team5419.drivetrain.gui


import org.team5419.drivetrain.simulator.Robot
import java.awt.*
import java.awt.event.*
import javax.swing.JPanel
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class Renderer: JPanel(), MouseListener, MouseMotionListener, MouseWheelListener {

    private var radius = 5

    private val pastPoints = mutableListOf<Pair<Double, Double>>()
    private val maxPastPoints = 3000
    private val framesPerPoint = 1
    private var framesSincePoint = 0


    private var zoomLevel = 1.0
    private val zoomIncrement = 0.85f

    private var yOffset = 0.0
    private var xOffset = 0.0
    private var startDrag: Point? = null


    init{
        addMouseMotionListener(this)
        addMouseListener(this)
        addMouseWheelListener(this);
    }

    fun update(){
        Robot.update()
        repaint()
    }

    fun zoomIn(times: Int = 1){
        zoomLevel /= zoomIncrement.pow(times)
        repaint()
    }
    fun zoomOut(times: Int = 1) {
        zoomLevel *= zoomIncrement.pow(times)
        repaint()
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        if (g !is Graphics2D) return

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        g.stroke = BasicStroke(zoomLevel.toFloat() * 2f)
        g.color = Color.WHITE
        g.fillRect(0, 0, width, height)

        drawTrail(g)
        drawRobot(g)
    }

    private fun drawTrail(g: Graphics2D){
        framesSincePoint++

        if(framesSincePoint == framesPerPoint){
            pastPoints.add(Robot.x to Robot.y)
            framesSincePoint = 0

            if(pastPoints.size >= maxPastPoints) pastPoints.removeAt(0)
        }

        g.color = Color.decode("#008000")
        pastPoints.zipWithNext { a,b -> g.drawLine(
            ((a.first + xOffset) * zoomLevel).toInt(),
            ((a.second + yOffset) * zoomLevel).toInt(),
            ((b.first + xOffset) * zoomLevel).toInt(),
            ((b.second + yOffset) * zoomLevel).toInt()
        )}
    }

    private fun drawRobot(g: Graphics2D){
        g.color = Color.decode("#990000")

        g.fillArc(
                ((Robot.x - radius + xOffset) * zoomLevel).toInt(),
                ((Robot.y - radius + yOffset) * zoomLevel).toInt(),
                (radius*2*zoomLevel).toInt(), (radius*2*zoomLevel).toInt(),
                0, 360
        )
        g.drawLine(
                ((Robot.x + xOffset) * zoomLevel).toInt(),
                ((Robot.y + yOffset) * zoomLevel).toInt(),
                ((Robot.x + xOffset + sin(Robot.theta) * 20)*zoomLevel).toInt(),
                ((Robot.y + yOffset + cos(Robot.theta) * 20)*zoomLevel).toInt()
        )

    }

    override fun mouseMoved(e: MouseEvent){}
    override fun mouseEntered(e: MouseEvent){}
    override fun mouseClicked(e: MouseEvent){}
    override fun mouseExited(e: MouseEvent){}
    override fun mousePressed(e: MouseEvent) {
        startDrag = e.getPoint()
    }
    override fun mouseDragged(e: MouseEvent) {
        xOffset += ((e.point.x - startDrag!!.x) / zoomLevel).toInt()
        yOffset += ((e.point.y - startDrag!!.y) / zoomLevel).toInt()
        startDrag = e.point
        repaint()
    }
    override fun mouseReleased(e: MouseEvent){
        startDrag = null
    }

    override fun mouseWheelMoved(e: MouseWheelEvent?) {
        zoomOut(e!!.wheelRotation)
    }
}
