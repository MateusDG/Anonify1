package com.anonify.ui;

import com.anonify.services.Services;
import com.anonify.ui.components.ChatPanel;
import com.anonify.ui.components.InputPanel;
import com.anonify.ui.components.LogoPanel;
import com.anonify.ui.components.TabManager;
import java.awt.*;
import javax.swing.*;

class MainFrame extends JFrame {
    MainFrame(String title, int width, int height, Services torService, ChatPanel chatPanel) {
        setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setLayout(new BorderLayout());

        // Carrega o ícone original
        ImageIcon originalIcon = new ImageIcon("src/res/bg_illustation.png");
        
        // Ajuste o fator de escala conforme necessário (aqui usamos 4x)
        double scaleFactor = 4.0;
        int scaledWidth = (int) (originalIcon.getIconWidth() * scaleFactor);
        int scaledHeight = (int) (originalIcon.getIconHeight() * scaleFactor);

        // Cria a imagem em escala
        Image scaledImage = originalIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        
        // Define como ícone da janela
        setIconImage(scaledImage);

        // Configura o restante do layout
        TabManager tabManager = new TabManager(torService, chatPanel);
        add(new LogoPanel(chatPanel, torService, tabManager), BorderLayout.NORTH);
        add(tabManager.getTabbedPane(), BorderLayout.CENTER);
        add(new InputPanel(chatPanel, torService), BorderLayout.SOUTH);

        setVisible(true);
    }
}
