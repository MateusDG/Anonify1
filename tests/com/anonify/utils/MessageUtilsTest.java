package com.anonify.utils;

import java.awt.Component;
import java.awt.Container;

import javax.swing.JLabel;
import javax.swing.JPanel;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class MessageUtilsTest {

    @Test
    public void testCreateMessagePanelContainsMessage() {
        String message = "Teste de mensagem";
        String sender = "YOU";
        JPanel panel = MessageUtils.createMessagePanel(message, sender);
        
        // Busca recursivamente por um JLabel que contenha o texto da mensagem
        boolean found = containsText(panel, message);
        assertTrue(found, "O painel de mensagem deve conter o texto informado.");
    }
    
    /**
     * Método auxiliar para percorrer a árvore de componentes e verificar se algum JLabel contém o texto esperado.
     */
    private boolean containsText(Component comp, String text) {
        if (comp instanceof JLabel) {
            String labelText = ((JLabel) comp).getText();
            if (labelText != null && labelText.contains(text)) {
                return true;
            }
        } else if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                if (containsText(child, text)) {
                    return true;
                }
            }
        }
        return false;
    }
}
