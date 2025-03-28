package com.anonify.ui.components;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChatPanelTest {

    private ChatPanel chatPanel;

    @BeforeEach
    public void setup() {
        chatPanel = new ChatPanel();
    }
    
    @Test
    public void testAddMessageIncreasesComponentCount() {
        // Obtém o painel interno onde as mensagens são adicionadas
        JScrollPane scrollPane = chatPanel.getScrollPane();
        JPanel internalPanel = (JPanel) scrollPane.getViewport().getView();
        int initialCount = internalPanel.getComponentCount();
        
        chatPanel.addMessage("Hello World", "YOU");
        
        int newCount = internalPanel.getComponentCount();
        assertEquals(initialCount + 1, newCount, "Adding a message should increase the component count by 1");
    }
    
    @Test
    public void testClearMessagesEmptiesPanel() {
        chatPanel.addMessage("Message 1", "YOU");
        chatPanel.addMessage("Message 2", "BOT");
        
        JScrollPane scrollPane = chatPanel.getScrollPane();
        JPanel internalPanel = (JPanel) scrollPane.getViewport().getView();
        assertTrue(internalPanel.getComponentCount() > 0, "Panel should have messages before clearing");
        
        chatPanel.clearMessages();
        
        assertEquals(0, internalPanel.getComponentCount(), "Clearing messages should result in zero components");
    }
    
    @Test
    public void testShowHelpAddsMessages() {
        JScrollPane scrollPane = chatPanel.getScrollPane();
        JPanel internalPanel = (JPanel) scrollPane.getViewport().getView();
        int countBefore = internalPanel.getComponentCount();
        
        chatPanel.showHelp();
        
        int countAfter = internalPanel.getComponentCount();
        assertTrue(countAfter > countBefore, "showHelp should add multiple messages to the panel");
    }
}
