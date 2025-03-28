package com.anonify.ui.panels;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JLabel;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.anonify.services.Services;
import com.anonify.ui.components.ChatPanel;

public class ConfigureOnionPanelTest {

    private ChatPanel chatPanel;
    private Services dummyServices;
    private ConfigureOnionPanel configureOnionPanel;

    @BeforeEach
    public void setUp() {
        chatPanel = new ChatPanel();
        dummyServices = new DummyServicesForPanels();
        configureOnionPanel = new ConfigureOnionPanel(dummyServices, chatPanel);
    }

    @Test
    public void testContainsTitleLabel() {
        boolean found = containsText(configureOnionPanel, "Configure Onion Server");
        assertTrue(found, "ConfigureOnionPanel should contain a label with 'Configure Onion Server'");
    }

    /**
     * Método auxiliar para percorrer recursivamente a árvore de componentes
     * e verificar se algum JLabel contém o texto esperado.
     */
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
