package org.vertexarmy.omniray.client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Alex
 * Date: 1/17/14
 */
public class ConfigurationWindow extends JFrame {
    private JButton renderButton;
    private JButton clearButton;

    private JComboBox<String> samplingTechniqueComboBox;
    private JComboBox<String> sampleCountComboBox;

    private final List<Listener> listeners = new ArrayList<Listener>();

    public ConfigurationWindow() {
        initComponents();
        initLayout();
        initListeners();
        initFrame();
    }

    private void initListeners() {
        renderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyRenderRequested();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notifyClearRequested();
            }
        });
    }

    private void initFrame() {
        setTitle("Config");
        pack();
        setSize(200, 200);
        setVisible(true);
    }

    private void initComponents() {
        renderButton = new JButton("Render");
        renderButton.setFocusable(false);

        clearButton = new JButton("Clear");
        clearButton.setFocusable(false);

        samplingTechniqueComboBox = new JComboBox<String>(new String[]{"None", "Random"/*, "Multi-Jittered"*/});
        samplingTechniqueComboBox.setFocusable(false);

        sampleCountComboBox = new JComboBox<String>(new String[]{"4", "16", "32", "64", "128"});
        samplingTechniqueComboBox.setFocusable(false);
    }

    private void initLayout() {
        setLayout(new BorderLayout());

        JLabel samplingLabel = new JLabel("Sampling Technique");
        samplingLabel.setForeground(Color.BLACK);

        JLabel sampleCountLabel = new JLabel("Sample Count");
        sampleCountLabel.setForeground(Color.BLACK);

        JPanel configurationPanel = new JPanel();
        configurationPanel.setLayout(new GridLayout(4, 1));
        configurationPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        configurationPanel.add(samplingLabel);
        configurationPanel.add(samplingTechniqueComboBox);
        configurationPanel.add(sampleCountLabel);
        configurationPanel.add(sampleCountComboBox);

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new GridLayout(1, 2));

        actionPanel.add(renderButton);
        actionPanel.add(clearButton);

        add(configurationPanel, BorderLayout.NORTH);
        add(actionPanel, BorderLayout.SOUTH);
    }

    public void registerListener(Listener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(Listener listener) {
        listeners.remove(listener);
    }

    private void notifyRenderRequested() {
        for (Listener listener : listeners) {
            listener.onRenderRequested();
        }
    }

    private void notifyClearRequested() {
        for (Listener listener : listeners) {
            listener.onClearRequested();
        }
    }

    public interface Listener {
        void onRenderRequested();

        void onClearRequested();
    }
}