package com.anonify.ui.components;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JTextField;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.anonify.services.Services;

// Serviço dummy para capturar a mensagem enviada
class DummyServices extends Services {
    public String lastMessageSent = null;
    
    @Override
    public void sendMessageToServer(String message) {
        lastMessageSent = message;
    }
}

public class InputPanelTest {

    private ChatPanel chatPanel;
    private DummyServices dummyServices;
    private InputPanel inputPanel;
    
    @BeforeEach
    public void setup() {
        chatPanel = new ChatPanel();
        dummyServices = new DummyServices();
        inputPanel = new InputPanel(chatPanel, dummyServices);
    }
    
    /**
     * Método auxiliar para buscar um componente do tipo especificado dentro de um container.
     */
    private <T> T findComponent(Container container, Class<T> clazz) {
        for (Component comp : container.getComponents()) {
            if (clazz.isInstance(comp)) {
                return clazz.cast(comp);
            } else if (comp instanceof Container) {
                T result = findComponent((Container) comp, clazz);
                if (result != null) return result;
            }
        }
        return null;
    }
    
    @Test
    public void testSendMessageClearsTextFieldAndCallsService() {
        // Busca o JTextField dentro do InputPanel
        JTextField textField = findComponent(inputPanel, JTextField.class);
        assertNotNull(textField, "Text field should be found");
        
        String testMessage = "Hello";
        textField.setText(testMessage);
        
        // Simula o acionamento do actionPerformed (por exemplo, pressionando Enter)
        for (java.awt.event.ActionListener al : textField.getActionListeners()) {
            al.actionPerformed(null);
        }
        
        // Verifica se o campo de texto foi limpo
        assertEquals("", textField.getText(), "Text field should be cleared after sending");
        
        // Verifica se o serviço dummy recebeu a mensagem
        assertEquals(testMessage, dummyServices.lastMessageSent, "DummyServices should have received the message");
    }
}
