package org.team5419.drivetrain.gui

import org.team5419.drivetrain.simulator.Robot
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*

import javax.swing.border.EmptyBorder


class ControlPanel(renderer: Renderer): JPanel() {

    private val gbc = GridBagConstraints()
    private val xLabel: JLabel
    private val yLabel: JLabel
    private val velLabel: JLabel
    private val thetaLabel: JLabel
    private val leftLabel: JLabel
    private val rightLabel: JLabel

    private val zoomInButton: JButton = JButton("+")
    private val zoomOutButton: JButton = JButton("-")


    init{
        this.layout = GridLayout(20, 1, 5, 5);
        this.preferredSize = Dimension(180, 0)
        gbc.anchor = GridBagConstraints.NORTH;
        xLabel = addOutput("X: ")
        yLabel = addOutput("Y: ")
        velLabel = addOutput("Velocity: ")
        thetaLabel = addOutput("θ: ")
        leftLabel = addOutput("Left: ")
        rightLabel = addOutput("Right: ")

        this.add(JPanel().apply {
            layout = GridLayout(1,2, 10, 10)
            add(zoomInButton)
            add(zoomOutButton)
        })

        update()

        zoomOutButton.addActionListener {
            renderer.zoomOut()
        }

        zoomInButton.addActionListener {
            renderer.zoomIn()
        }

    }

    private fun addOutput(titleStr: String): JLabel{
        val label: JLabel = JLabel("")
        this.add( JPanel().apply{
            add(JLabel(titleStr), BorderLayout.LINE_START)
            add(label, BorderLayout.LINE_END)
            layout = GridLayout(1,2, 10, 10)
            border = EmptyBorder(0,15,0,10)
            // setLayOut(WrapLayout)
        })
        return label
    }

    fun update() {
        xLabel.setText("%.${3}f".format(Robot.x))
        yLabel.setText("%.${3}f".format(Robot.y))
        velLabel.setText("%.${3}f".format(Robot.velocity))
        thetaLabel.setText("%.${3}f".format(Robot.theta / Math.PI * 180))
        leftLabel.setText("%.${3}f".format(Robot.leftWheel))
        rightLabel.setText("%.${3}f".format(Robot.rightWheel))
    }
}