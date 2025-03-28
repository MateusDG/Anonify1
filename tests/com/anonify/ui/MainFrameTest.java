package com.anonify.ui;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.anonify.ui.components.ChatPanel;

public class MainFrameTest {

    @Test
    public void testMainFrameProperties() throws Exception {
        final MainFrame[] frameHolder = new MainFrame[1];
        // Use a classe dummy previamente definida
        DummyServicesForUI dummyServices = new DummyServicesForUI();
        ChatPanel chatPanel = new ChatPanel();
        
        // Cria a janela na EDT
        SwingUtilities.invokeAndWait(() -> {
            frameHolder[0] = new MainFrame("Test Frame", 800, 600, dummyServices, chatPanel);
        });
        
        MainFrame frame = frameHolder[0];
        assertNotNull(frame, "MainFrame should be instantiated");
        assertEquals("Test Frame", frame.getTitle(), "The title of MainFrame should be 'Test Frame'");
        
        Dimension size = frame.getSize();
        assertEquals(800, size.width, "Frame width should be 800");
        assertEquals(600, size.height, "Frame height should be 600");
        assertEquals(JFrame.EXIT_ON_CLOSE, frame.getDefaultCloseOperation(), "Default close operation should be EXIT_ON_CLOSE");
        
        Container content = frame.getContentPane();
        assertNotNull(content, "Content pane should not be null");
        assertTrue(content.getComponentCount() > 0, "Content pane should have components");
        
        // Dispose a janela para encerrar o teste
        SwingUtilities.invokeLater(() -> frame.dispose());
    }
}
