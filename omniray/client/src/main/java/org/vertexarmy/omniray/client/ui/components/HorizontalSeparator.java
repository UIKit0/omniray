package org.vertexarmy.omniray.client.ui.components;

import javax.swing.*;
import java.awt.*;

/**
 * User: Alex
 * Date: 1/20/14
 */
public class HorizontalSeparator extends JPanel {
    private int height;

    public HorizontalSeparator(int height) {
        this.height = height;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(0, height);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(52, 52, 52));
        g.fillRect(0, getHeight() / 2 - height / 2, getWidth(), height);
    }
}
