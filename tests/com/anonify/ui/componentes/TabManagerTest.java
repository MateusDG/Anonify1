package com.anonify.ui.components;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.anonify.services.Services;

// Serviço dummy para TabManager
class DummyServicesForTab extends Services {
    // Implementação vazia para teste
}

public class TabManagerTest {
    private ChatPanel chatPanel;
    private DummyServicesForTab dummyServices;
    private TabManager tabManager;
    
    @BeforeEach
    public void setup() {
        chatPanel = new ChatPanel();
        dummyServices = new DummyServicesForTab();
        tabManager = new TabManager(dummyServices, chatPanel);
    }
    
    @Test
    public void testAddOrSwitchTabAddsTab() {
        JTabbedPane pane = tabManager.getTabbedPane();
        int initialCount = pane.getTabCount();
        
        JPanel testPanel = new JPanel();
        Icon testIcon = new ImageIcon();
        tabManager.addOrSwitchTab("TestTab", testPanel, testIcon);
        
        int newCount = pane.getTabCount();
        assertEquals(initialCount + 1, newCount, "Adding a new tab should increase tab count by 1");
        
        // Se adicionarmos a mesma aba novamente, o número de abas não deve aumentar
        tabManager.addOrSwitchTab("TestTab", testPanel, testIcon);
        assertEquals(newCount, pane.getTabCount(), "Adding an existing tab should not change tab count");
    }
    
    @Test
    public void testOpenSettingsTabAddsTab() {
        JTabbedPane pane = tabManager.getTabbedPane();
        int initialCount = pane.getTabCount();
        tabManager.openSettingsTab();
        assertTrue(pane.getTabCount() > initialCount, "openSettingsTab should add a new tab");
    }
}
