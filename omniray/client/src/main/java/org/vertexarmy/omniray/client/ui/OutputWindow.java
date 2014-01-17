package org.vertexarmy.omniray.client.ui;

import lombok.Getter;
import org.vertexarmy.omniray.client.ui.components.StatusBar;

import javax.swing.*;
import java.awt.*;

/**
 * User: Alex
 * Date: 11/3/13
 */
public class OutputWindow extends JFrame {
    @Getter
    private org.vertexarmy.omniray.client.ui.components.Canvas canvas;
    private StatusBar statusBar;
    private JLabel statusLabel;

    public OutputWindow() {
        initWindow();
        initComponents();
        initLayout();

        setVisible(true);
        Toolkit.centerFrame(this);
    }

    private void initWindow() {
        setTitle("Output");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initComponents() {
        canvas = new org.vertexarmy.omniray.client.ui.components.Canvas(800, 600);
        statusBar = new StatusBar();

        statusLabel = new JLabel("Idle");
        statusLabel.setSize(new Dimension(200, 16));
        statusLabel.getInsets().set(0, 0, 0, 0);
        statusLabel.setBorder(BorderFactory.createEmptyBorder());
    }

    private void initLayout() {
        getContentPane().setLayout(new BorderLayout());

        statusBar.add(statusLabel);

        getContentPane().add(canvas, BorderLayout.CENTER);
        getContentPane().add(statusBar, BorderLayout.SOUTH);
        pack();
    }
}
