package com.anonify.ui.components;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JTextField;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.anonify.services.Services;

/**
 * Classe dummy para simular o comportamento de Services no teste.
 */
class DummyServicesForInputTest extends Services {
    public String lastMessageSent = null;
    
    @Override
    public void sendMessageToServer(String message) {
        lastMessageSent = message;
    }
}

public class InputPanelTest {
    private ChatPanel chatPanel;
    private DummyServicesForInputTest dummyServices;
    private InputPanel inputPanel;
    
    @BeforeEach
    public void setUp() {
        chatPanel = new ChatPanel();
        dummyServices = new DummyServicesForInputTest();
        inputPanel = new InputPanel(chatPanel, dummyServices);
    }
    
    /**
     * Método auxiliar que busca recursivamente um JTextField dentro de um Container.
     */
    private JTextField findTextField(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JTextField) {
                return (JTextField) comp;
            }
            if (comp instanceof Container) {
                JTextField result = findTextField((Container) comp);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
    
    @Test
    public void testSendMessageClearsTextFieldAndCallsService() {
        // Procura pelo JTextField no InputPanel
        JTextField textField = findTextField(inputPanel);
        assertNotNull(textField, "Text field should be found in InputPanel");
        
        String testMessage = "Hello";
        textField.setText(testMessage);
        
        // Simula o acionamento do actionPerformed (como se o usuário pressionasse Enter)
        for (java.awt.event.ActionListener al : textField.getActionListeners()) {
            al.actionPerformed(null);
        }
        
        // Verifica se o campo de texto foi limpo
        assertEquals("", textField.getText(), "Text field should be cleared after sending message");
        // Verifica se o serviço dummy recebeu a mensagem
        assertEquals(testMessage, dummyServices.lastMessageSent, "Dummy service should receive the message");
    }
}
