package com.anonify.ui;

import com.anonify.services.Services;
import com.anonify.ui.components.ChatPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class UI {
    private final Services services;
    private final ChatPanel chatPanel;

    public UI(Services services) {
        this.services  = services;
        this.chatPanel = new ChatPanel(); 
    }

    public void startUI(String title, int width, int height){
        // Define o Look-and-Feel Nimbus, se disponível
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // Se falhar, mantém o padrão
        }
        SwingUtilities.invokeLater(() -> new MainFrame(title, width, height, services, chatPanel));
    }

    public void sendMessage(String message, String sender){
        SwingUtilities.invokeLater(() -> chatPanel.addMessage(message, sender));
    }
}
