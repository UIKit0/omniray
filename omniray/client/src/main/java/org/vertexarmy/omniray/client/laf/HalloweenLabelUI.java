package org.vertexarmy.omniray.client.laf;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;

/**
 * User: Alex
 * Date: 11/3/13
 */
public class HalloweenLabelUI extends BasicLabelUI {

    private static final HalloweenLabelUI INSTANCE = new HalloweenLabelUI();

    public static ComponentUI createUI(JComponent c) {
        return INSTANCE;
    }

    @Override
    public void installUI(JComponent component) {
        super.installUI(component);
        component.setForeground(new Color(0xB8B8B8));
        component.getInsets().set(0, 0, 0, 0);
        component.setOpaque(true);
    }
}
