package org.vertexarmy.omniray.client;

import org.vertexarmy.omniray.client.components.OutputWindow;
import org.vertexarmy.omniray.client.laf.HalloweenLookAndFeel;

/**
 * User: Alex
 * Date: 11/3/13
 */
public class Launcher {
    public static void main(String[] argv){
        HalloweenLookAndFeel.install();

        OutputWindow window = new OutputWindow();
    }
}
