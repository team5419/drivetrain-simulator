package org.team5419.drivetrain.gui

import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JButton

import java.awt.BorderLayout
import java.awt.event.ActionListener
import java.awt.event.ActionEvent

import org.team5419.drivetrain.gui.Renderer

class FeildPanel(): JPanel() {
    /**
     * The path renderer being rendered.
     */
    public val pathRenderer = Renderer();
    val scrollPane = JScrollPane();
    val bottomPane = JPanel();

    // val zoomInBtn = JButton("+")
    // val zoomOutBtn = JButton("-")

    val zoomIncriment = 0.5f;

    init {
        this.layout = BorderLayout();

        scrollPane.setViewportView(pathRenderer);
        this.add(scrollPane, BorderLayout.CENTER)

        // zoomOutBtn.addActionListener(object: ActionListener {
        //     override fun actionPerformed(e: ActionEvent) {
        //         pathRenderer.zoomLevel *= zoomIncriment;
        //         scrollPane.revalidate()
        //     }
        // })

        // zoomInBtn.addActionListener(object: ActionListener {
        //     override fun actionPerformed(e: ActionEvent) {
        //         pathRenderer.zoomLevel /= zoomIncriment;
        //         scrollPane.revalidate()
        //     }
        // })

        // bottomPane.add(zoomInBtn)
        // bottomPane.add(zoomOutBtn)
        // this.add(bottomPane, BorderLayout.SOUTH)
    }

}