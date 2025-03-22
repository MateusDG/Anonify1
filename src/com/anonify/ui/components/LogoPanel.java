package com.anonify.ui.components;

import com.anonify.services.Services;
import com.anonify.utils.Constants;
import java.awt.*;
import javax.swing.*;

public class LogoPanel extends JPanel {
    public LogoPanel(ChatPanel chatPanel, Services torService, TabManager tabManager) {
        setBackground(Constants.DARK_GRAY);
        setPreferredSize(new Dimension(0, 50));
        setLayout(new BorderLayout());

        JLabel logoLabel = createLogoLabel();
        JPanel centeredLogoPanel = createCenteredPanel(logoLabel);
        add(centeredLogoPanel, BorderLayout.CENTER);

        JPanel iconsPanel = createIconsPanel(chatPanel, tabManager);
        add(iconsPanel, BorderLayout.EAST);
    }

    private JLabel createLogoLabel() {
        ImageIcon logoIcon = new ImageIcon("src/res/logo-no-bg-centralized.png");
        Image scaledLogo = logoIcon.getImage().getScaledInstance(120, 40, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(scaledLogo), SwingConstants.CENTER);
    }

    private JPanel createCenteredPanel(JLabel logoLabel) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Constants.DARK_GRAY);
        panel.add(logoLabel);
        return panel;
    }

    private JPanel createIconsPanel(ChatPanel chatPanel, TabManager tabManager) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panel.setBackground(Constants.DARK_GRAY);
    
        JButton settingsButton = createIconButton("src/res/settings-icon-white.png", "Settings");
        JButton helpButton = createIconButton("src/res/help-icon-white.png", "Help");
        JButton clearButton = createIconButton("src/res/clear.png", "Limpar Chat");
    
        settingsButton.addActionListener(e -> tabManager.openSettingsTab());
        helpButton.addActionListener(e -> chatPanel.showHelp());
        clearButton.addActionListener(e -> chatPanel.clearMessages());
    
        panel.add(settingsButton);
        panel.add(helpButton);
        panel.add(clearButton);
        return panel;
    }
    

    private JButton createIconButton(String iconPath, String tooltip) {
        // Carrega o ícone
        ImageIcon icon = new ImageIcon(iconPath);
        Image scaledImage = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    
        JButton button = new JButton(new ImageIcon(scaledImage));
        button.setToolTipText(tooltip);
    
        // Definir um tamanho fixo para não encolher no hover
        button.setPreferredSize(new Dimension(40, 40));
    
        // Define cor de fundo e outras propriedades para o estado "normal"
        button.setBackground(new Color(45, 45, 60));  // Ajuste conforme desejar
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        // Borda invisível (transparente)
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 2));
    
        // Listener para efeito de hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                // Ao entrar com o mouse, muda a cor de fundo e exibe a borda
                button.setBackground(new Color(70, 70, 90));
            }
    
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                // Ao sair com o mouse, retorna à cor e borda originais
                button.setBackground(new Color(45, 45, 60));
                button.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 2));
            }
        });
    
        return button;
    }
    
    
}
