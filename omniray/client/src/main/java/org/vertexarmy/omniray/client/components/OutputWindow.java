package org.vertexarmy.omniray.client.components;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

/**
 * User: Alex
 * Date: 11/3/13
 */
public class OutputWindow extends JFrame {
    @Getter
    private Canvas canvas;
    private StatusBar statusBar;
    private JLabel statusLabel;

    public OutputWindow() {
        initComponents();
        initLayout();
        center();
    }

    private void initComponents() {
        canvas = new Canvas(800, 600);
        statusBar = new StatusBar();

        statusLabel = new JLabel("Idle");
        statusLabel.setSize(new Dimension(200, 16));
        statusLabel.getInsets().set(0, 0, 0, 0);
        statusLabel.setBorder(BorderFactory.createEmptyBorder());

        setTitle("Output");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initLayout() {
        getContentPane().setLayout(new BorderLayout());

        statusBar.add(statusLabel);

        getContentPane().add(canvas, BorderLayout.CENTER);
        getContentPane().add(statusBar, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    private void center(){
        setLocation((Toolkit.getDefaultToolkit().getScreenSize().width  - getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
    }
}
