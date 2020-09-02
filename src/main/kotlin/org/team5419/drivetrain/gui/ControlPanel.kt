package org.team5419.drivetrain.gui

import org.team5419.drivetrain.simulator.Robot
import java.awt.*

import javax.swing.JPanel
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.JFormattedTextField
import javax.swing.InputVerifier
import javax.swing.JComponent

import javax.swing.border.EmptyBorder


class ControlPanel(): JPanel() {

    val gbc = GridBagConstraints()
    val xLabel: JLabel
    val yLabel: JLabel
    val velLabel: JLabel
    val thetaLabel: JLabel
    val leftLabel: JLabel
    val rightLabel: JLabel


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

        update()

    }

    fun addOutput(titleStr: String): JLabel{
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