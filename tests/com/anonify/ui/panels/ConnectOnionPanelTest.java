package com.anonify.ui.panels;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JLabel;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.anonify.services.Services;
import com.anonify.ui.components.ChatPanel;

public class ConnectOnionPanelTest {

    private ChatPanel chatPanel;
    private Services dummyServices;
    private ConnectOnionPanel connectOnionPanel;

    @BeforeEach
    public void setUp() {
        chatPanel = new ChatPanel();
        dummyServices = new DummyServicesForPanels();
        connectOnionPanel = new ConnectOnionPanel(dummyServices, chatPanel);
    }

    @Test
    public void testContainsTitleLabel() {
        boolean found = containsText(connectOnionPanel, "Connect to Onion Server");
        assertTrue(found, "ConnectOnionPanel should contain a label with 'Connect to Onion Server'");
    }

    private boolean containsText(Component comp, String text) {
        if (comp instanceof JLabel) {
            String labelText = ((JLabel) comp).getText();
            if (labelText != null && labelText.contains(text)) {
                return true;
            }
        }
        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                if (containsText(child, text)) {
                    return true;
                }
            }
        }
        return false;
    }
}
