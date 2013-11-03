package org.vertexarmy.omniray.client.laf;

import javax.swing.*;

/**
 * User: Alex
 * Date: 11/3/13
 */
public class HalloweenLookAndFeel {
    public static void install(){
        UIManager.put("LabelUI", HalloweenLabelUI.class.getName());
    }
}
