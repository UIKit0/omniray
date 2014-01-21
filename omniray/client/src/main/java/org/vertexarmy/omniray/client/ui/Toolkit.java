package org.vertexarmy.omniray.client.ui;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * User: Alex
 * Date: 1/16/14
 */
public class Toolkit {
    public static void centerFrame(JFrame frame) {
        final Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(
                (screenSize.width - frame.getSize().width) / 2,
                (screenSize.height - frame.getSize().height) / 2);
    }

    public static void attachFrame(JFrame leftFrame, JFrame rightFrame) {
        rightFrame.setBounds(leftFrame.getX() + leftFrame.getWidth() + 6, leftFrame.getY() - 5, rightFrame.getWidth(), rightFrame.getHeight());
    }

    public static long currentTime() {
        return (new Date()).getTime();
    }

    public static long diffTime(long time) {
        return currentTime() - time;
    }
}
