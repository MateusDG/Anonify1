package com.anonify.ui.panels;

import java.awt.Component;
import java.awt.Container;

import javax.swing.AbstractButton;
import javax.swing.JLabel;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.anonify.services.Services;
import com.anonify.ui.components.ChatPanel;
import com.anonify.ui.components.TabManager;

public class SettingsPanelTest {

    private Services dummyServices;
    private TabManager tabManager;
    private SettingsPanel settingsPanel;

    @BeforeEach
    public void setUp() {
        dummyServices = new DummyServicesForPanels();
        ChatPanel chatPanel = new ChatPanel();
        tabManager = new TabManager(dummyServices, chatPanel);
        settingsPanel = new SettingsPanel(tabManager, dummyServices);
    }

    @Test
    public void testContainsTitleLabel() {
        boolean found = containsText(settingsPanel, "Settings");
        assertTrue(found, "SettingsPanel should contain a label with 'Settings'");
    }

    @Test
    public void testContainsButtons() {
        boolean foundConfigure = containsText(settingsPanel, "Configure Onion Server");
        boolean foundConnect = containsText(settingsPanel, "Connect to an Onion Server");
        assertTrue(foundConfigure, "SettingsPanel should contain a button for 'Configure Onion Server'");
        assertTrue(foundConnect, "SettingsPanel should contain a button for 'Connect to an Onion Server'");
    }

    private boolean containsText(Component comp, String text) {
        if (comp instanceof JLabel) {
            String labelText = ((JLabel) comp).getText();
            if (labelText != null && labelText.contains(text)) {
                return true;
            }
        }
        if (comp instanceof AbstractButton) {
            String buttonText = ((AbstractButton) comp).getText();
            if (buttonText != null && buttonText.contains(text)) {
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
