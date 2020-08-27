package org.team5419.drivetrain.gui

import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.ImageIcon


import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Color
import java.awt.BasicStroke
import java.awt.RenderingHints
import java.awt.Dimension


import org.team5419.drivetrain.simulator.Robot

class Renderer(): JPanel() {

    var radius = 5

    val pastPoints = mutableListOf<Pair<Int, Int>>()
    val framesPerPoint = 10
    var framesSincePoint = 0 
    val maxPastPoints = 300


    public var zoomLevel = 1.0
        set(value) {
            field = value;
            repaint();
        }

    init{
        // println("init")
        // val listener = object: ActionListener {
        //     override fun actionPerformed(evt: ActionEvent){
        //         update()
        //     }
        // }

        // val timer = Timer(1000 * Robot.dt, listener)
        // timer.start()
    }

    public fun update(){
        Robot.update()
        repaint()
    }

    override fun paintComponent(g: Graphics) {

        super.paintComponent(g);

        if (g is Graphics2D) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.stroke = BasicStroke(2f)
            g.color = Color.WHITE
            g.fillRect(0, 0, width, height)

            // gameField.renderScale = (backgroundImage.getWidth() * zoomLevel) / gameField.imageWidth;
            framesSincePoint++

            if(framesSincePoint == framesPerPoint){
                pastPoints.add(Pair(Robot.x.toInt(), Robot.y.toInt()))
                framesSincePoint = 0

                if(pastPoints.size >= maxPastPoints){
                    pastPoints.removeAt(0)
                }
            }

            g.color = Color.decode("#008000")
            pastPoints.zipWithNext { a,b -> g.drawLine(a.first, a.second, b.first, b.second) }

            g.color = Color.decode("#990000")
            

            g.fillArc(Robot.x.toInt()-radius, Robot.y.toInt()-radius, radius*2, radius*2, 0, 360)
            g.drawLine(
                Robot.x.toInt(),
                Robot.y.toInt(),
                (Robot.x + Math.sin(Robot.theta) * 20).toInt(),
                (Robot.y + Math.cos(Robot.theta) * 20).toInt()
            )


        }
    }

}