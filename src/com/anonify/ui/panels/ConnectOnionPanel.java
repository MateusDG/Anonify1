package com.anonify.ui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.anonify.services.Services;
import com.anonify.ui.components.ChatPanel;

public class ConnectOnionPanel extends JPanel {

    public ConnectOnionPanel(Services torService, ChatPanel chatPanel) {
        // Configura o layout e o espaçamento interno
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // Para que o fundo customizado apareça, definimos o painel como não opaco
        setOpaque(false);

        // Configura os constraints do GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Título do painel
        JLabel titleLabel = new JLabel("Connect to Onion Server");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Reseta gridwidth e avança para a próxima linha
        gbc.gridwidth = 1;
        gbc.gridy++;

        // Onion Address
        JLabel onionAddressLabel = new JLabel("Onion Address:");
        onionAddressLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        onionAddressLabel.setForeground(Color.WHITE);
        add(onionAddressLabel, gbc);

        gbc.gridx = 1;
        JTextField onionAddressField = createStyledTextField("");
        add(onionAddressField, gbc);

        // Tor Proxy Host
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel proxyHostLabel = new JLabel("Tor Proxy Host:");
        proxyHostLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        proxyHostLabel.setForeground(Color.WHITE);
        add(proxyHostLabel, gbc);

        gbc.gridx = 1;
        JTextField proxyHostField = createStyledTextField("127.0.0.1");
        add(proxyHostField, gbc);

        // Tor Proxy Port
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel proxyPortLabel = new JLabel("Tor Proxy Port:");
        proxyPortLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        proxyPortLabel.setForeground(Color.WHITE);
        add(proxyPortLabel, gbc);

        gbc.gridx = 1;
        JTextField proxyPortField = createStyledTextField("9050");
        add(proxyPortField, gbc);

        // Server Port
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel serverPortLabel = new JLabel("Server Port:");
        serverPortLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        serverPortLabel.setForeground(Color.WHITE);
        add(serverPortLabel, gbc);

        gbc.gridx = 1;
        JTextField serverPortField = createStyledTextField("12345");
        add(serverPortField, gbc);

        // Botão Connect, usando o mesmo método de criação do SettingsPanel
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton connectButton = createStyledButton("Connect", "src/res/icons/16x16/connect.png");
        add(connectButton, gbc);

        // Ação do botão Connect
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer(torService, chatPanel, onionAddressField, proxyHostField, proxyPortField, serverPortField);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Fundo com gradiente dark, igual ao SettingsPanel
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(20, 20, 35),
            0, getHeight(), new Color(5, 5, 20)
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }

    private JTextField createStyledTextField(String defaultText) {
        JTextField textField = new JTextField(defaultText, 20);
        textField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textField.setBackground(new Color(60, 60, 80));
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(120, 120, 150), 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return textField;
    }

    private JButton createStyledButton(String text, String iconPath) {
        JButton button;
        if (iconPath != null && !iconPath.isEmpty()) {
            ImageIcon icon = new ImageIcon(iconPath);
            button = new JButton(text, icon);
        } else {
            button = new JButton(text);
        }
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        // Mesma cor de fundo dos botões do SettingsPanel
        button.setBackground(new Color(40, 70, 40));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Utiliza uma borda interna para garantir espaçamento consistente
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    private void connectToServer(Services torService, ChatPanel chatPanel,
                                 JTextField onionAddressField, JTextField proxyHostField,
                                 JTextField proxyPortField, JTextField serverPortField) {
        String onionAddress = onionAddressField.getText();
        String proxyHost = proxyHostField.getText();
        String proxyPort = proxyPortField.getText();
        String serverPort = serverPortField.getText();

        new Thread(() -> {
            try {
                torService.connectToOnionServer(onionAddress, proxyHost, proxyPort, serverPort, chatPanel);
                SwingUtilities.invokeLater(() -> chatPanel.addMessage("Connected to server!", "BOT"));
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> chatPanel.addMessage("Connection failed: " + ex.getMessage(), "BOT"));
            }
        }).start();
    }
}
