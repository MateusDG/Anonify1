package com.anonify.ui.components;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JLabel;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.anonify.services.Services;

// Serviço dummy para LogoPanel
class DummyServicesForLogo extends Services {
    // Implementação vazia para teste
}

public class LogoPanelTest {
    
    private ChatPanel chatPanel;
    private DummyServicesForLogo dummyServices;
    private TabManager tabManager;
    private LogoPanel logoPanel;
    
    @BeforeEach
    public void setup() {
        chatPanel = new ChatPanel();
        dummyServices = new DummyServicesForLogo();
        tabManager = new TabManager(dummyServices, chatPanel);
        logoPanel = new LogoPanel(chatPanel, dummyServices, tabManager);
    }
    
    private boolean containsLabel(Component comp) {
        if (comp instanceof JLabel) {
            return true;
        }
        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                if (containsLabel(child)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Test
    public void testLogoPanelComponentsNotEmpty() {
        assertTrue(logoPanel.getComponentCount() > 0, "LogoPanel should have components");
        assertTrue(containsLabel(logoPanel), "LogoPanel should contain a JLabel (logo)");
    }
}
