package com.anonify.ui.components;

import com.anonify.services.Services;
import com.anonify.utils.Constants;
import java.awt.*;
import javax.swing.*;

public class InputPanel extends JPanel {
    private static Services torSendMessage;

    public InputPanel(ChatPanel chatPanel, Services torService) {
        setLayout(new BorderLayout());
        setBackground(Constants.LIGHTER_GRAY);

        torSendMessage = torService;

        // Cria um JTextField com placeholder "Mensagem" via subclasse anônima
        JTextField messageField = new JTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if(getText().isEmpty() && !isFocusOwner()){
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setFont(getFont().deriveFont(Font.ITALIC));
                    g2.setColor(Color.GRAY);
                    Insets insets = getInsets();
                    int padding = 5;
                    g2.drawString("Mensagem", insets.left + padding, getHeight() / 2 + getFont().getSize() / 2 - 2);
                    g2.dispose();
                }
            }
        };
        messageField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        messageField.setBackground(Constants.LIGHTER_GRAY);
        messageField.setForeground(Constants.LIGHT_GRAY);
        messageField.setCaretColor(Constants.LIGHT_GRAY);
        messageField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Botão de envio com ícone
        ImageIcon sendIcon = new ImageIcon("src/res/icons/16x16/send.png");
        Image scaledIcon = sendIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        JButton sendButton = new JButton(new ImageIcon(scaledIcon));
        sendButton.setToolTipText("Send");
        sendButton.setBackground(Constants.DARK_PURPLE);
        sendButton.setForeground(Constants.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Ações para envio de mensagem
        sendButton.addActionListener(e -> sendMessage(chatPanel, messageField));
        messageField.addActionListener(e -> sendMessage(chatPanel, messageField));

        add(messageField, BorderLayout.CENTER);
        add(sendButton, BorderLayout.EAST);
    }

    private void sendMessage(ChatPanel chatPanel, JTextField messageField) {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            chatPanel.addMessage(message, "YOU");
            messageField.setText("");
            torSendMessage.sendMessageToServer(message);
        }
    }
}
