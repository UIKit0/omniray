package org.vertexarmy.omniray.client.components;

import javax.swing.*;
import java.awt.*;

/**
 * User: Alex
 * Date: 11/3/13
 */
public class StatusBar extends JPanel {
    private static final int HEIGHT = 28;

    public StatusBar(){
        getInsets().set(0, 0, 0, 0);
        setBackground(new Color(0x3c3f41));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0x282828)),
                BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(0x555555))));
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(0, HEIGHT);
    }
}
