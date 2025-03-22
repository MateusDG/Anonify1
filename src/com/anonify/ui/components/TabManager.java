package com.anonify.ui.components;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.anonify.services.Services;
import com.anonify.ui.panels.ConnectOnionPanel;
import com.anonify.ui.panels.ConfigureOnionPanel;
import com.anonify.ui.panels.SettingsPanel;

public class TabManager {
    private final JTabbedPane tabbedPane;
    private final Services torService;
    private final ChatPanel chatPanel;

    public TabManager(Services torService, ChatPanel chatPanel) {
        this.torService = torService;
        this.chatPanel = chatPanel;
        this.tabbedPane = createTabbedPane();
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    /**
     * Adiciona ou alterna para uma aba, configurando o painel, o ícone e o componente customizado (título + botão "X").
     */
    public void addOrSwitchTab(String title, JPanel panel, Icon icon) {
        int index = tabbedPane.indexOfTab(title);
        if (index >= 0) {
            tabbedPane.setSelectedIndex(index);
        } else {
            tabbedPane.addTab(title, icon, panel);
            int newIndex = tabbedPane.getTabCount() - 1;
            tabbedPane.setTabComponentAt(newIndex, new CloseableTabComponent(tabbedPane, title, icon));
            tabbedPane.setSelectedIndex(newIndex);
        }
    }

    /**
     * Carrega um ícone a partir de um caminho.
     */
    private Icon getIconFromPath(String path) {
        return new ImageIcon(path);
    }

    /**
     * Cria o JTabbedPane, aplica o UI customizado e adiciona a aba "Chat" por padrão.
     */
    private JTabbedPane createTabbedPane() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setUI(new ModernTabbedPaneUI());
        tabs.setBackground(new Color(40, 40, 55));
        tabs.setForeground(Color.LIGHT_GRAY);
        tabs.setOpaque(true); // garante que não seja transparente

        // Aba "Chat" inicial
        Icon relayIcon = getIconFromPath("src/res/icons/16x16/relay.png");
        tabs.addTab("Chat", relayIcon, chatPanel.getScrollPane());
        int index = tabs.getTabCount() - 1;
        tabs.setTabComponentAt(index, new CloseableTabComponent(tabs, "Chat", relayIcon));

        return tabs;
    }

    public void openSettingsTab() {
        addOrSwitchTab("Settings", new SettingsPanel(this, torService), 
            getIconFromPath("src/res/icons/16x16/onion-alt.png"));
    }

    public void openConfigureOnionTab() {
        addOrSwitchTab("Configure Onion Server", new ConfigureOnionPanel(torService, chatPanel),
            getIconFromPath("src/res/icons/16x16/running.png"));
    }

    public void openConnectOnionTab() {
        addOrSwitchTab("Connect to Onion Server", new ConnectOnionPanel(torService, chatPanel),
            getIconFromPath("src/res/icons/16x16/exit.png"));
    }

    /**
     * UI customizada para o JTabbedPane, com visual flat e sem bordas.
     */
    private static class ModernTabbedPaneUI extends BasicTabbedPaneUI {
        private final Color unselectedColor = new Color(60, 60, 80);
        private final Color selectedColor   = new Color(80, 80, 100);
        private final Color underlineColor  = new Color(120, 200, 120);

        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                                          int x, int y, int w, int h, boolean isSelected) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(isSelected ? selectedColor : unselectedColor);
            g2.fillRect(x, y, w, h);
            if (isSelected) {
                g2.setColor(underlineColor);
                g2.fillRect(x, y + h - 3, w, 3);
            }
            g2.dispose();
        }

        @Override
        protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex,
                                      int x, int y, int w, int h, boolean isSelected) {
            // Sem borda para visual flat
        }

        @Override
        protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
            // Sem borda no conteúdo
        }

        @Override
        protected void installDefaults() {
            super.installDefaults();
            tabInsets = new Insets(2, 10, 2, 10);
            tabAreaInsets = new Insets(3, 3, 0, 3);
        }
    }

    /**
     * Componente customizado para cada aba: exibe título, ícone e um botão "X".
     */
    private static class CloseableTabComponent extends JPanel {
        public CloseableTabComponent(JTabbedPane pane, String title, Icon icon) {
            // Torna o componente opaco e define um fundo consistente
            setOpaque(true);
            setBackground(new Color(60, 60, 80));
            setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

            // Label com título e ícone
            JLabel titleLabel = new JLabel(title);
            titleLabel.setIcon(icon);
            titleLabel.setForeground(Color.LIGHT_GRAY);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            add(titleLabel);

            // Botão "X" usando caractere Unicode (✖)
            JButton closeButton = new JButton("\u2716");
            closeButton.setFont(new Font("SansSerif", Font.BOLD, 12));
            closeButton.setForeground(Color.LIGHT_GRAY);
            closeButton.setContentAreaFilled(false);
            closeButton.setBorderPainted(false);
            closeButton.setFocusPainted(false);
            closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            closeButton.setMargin(new Insets(0, 0, 0, 0));
            closeButton.setPreferredSize(new Dimension(16, 16));
            closeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int i = pane.indexOfTabComponent(CloseableTabComponent.this);
                    if (i != -1) {
                        pane.remove(i);
                    }
                }
            });

            add(Box.createHorizontalStrut(5));
            add(closeButton);
        }
    }
}
