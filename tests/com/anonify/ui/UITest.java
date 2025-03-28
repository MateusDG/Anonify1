package com.anonify.ui;

import java.lang.reflect.Field;

import javax.swing.SwingUtilities;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.anonify.ui.components.ChatPanel;

public class UITest {

    @Test
    public void testStartUIAndSendMessage() throws Exception {
        // Use a classe dummy definida externamente
        DummyServicesForUI dummyServices = new DummyServicesForUI();
        UI ui = new UI(dummyServices);
        
        // Inicializa a UI (a criação da janela ocorre na EDT)
        SwingUtilities.invokeAndWait(() -> ui.startUI("Test UI", 800, 600));
        
        // Use reflection para acessar o ChatPanel privado da classe UI
        Field chatPanelField = UI.class.getDeclaredField("chatPanel");
        chatPanelField.setAccessible(true);
        ChatPanel chatPanel = (ChatPanel) chatPanelField.get(ui);
        assertNotNull(chatPanel, "ChatPanel should be initialized in UI");
        
        // Obtenha o painel interno que armazena as mensagens
        int initialCount = ((javax.swing.JPanel) chatPanel.getScrollPane().getViewport().getView()).getComponentCount();
        
        // Envia uma mensagem via UI
        ui.sendMessage("Hello from UI", "BOT");
        
        // Aguarda atualização na EDT
        SwingUtilities.invokeAndWait(() -> {});
        
        int newCount = ((javax.swing.JPanel) chatPanel.getScrollPane().getViewport().getView()).getComponentCount();
        assertTrue(newCount > initialCount, "After sending a message, the ChatPanel should have one more component");
    }
}
