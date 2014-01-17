package org.vertexarmy.omniray.client.ui.laf;

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
        component.setForeground(new Color(0xC1C1C1));
        component.setFont(new javax.swing.plaf.FontUIResource("Sans", Font.PLAIN, 10));
    }
}
