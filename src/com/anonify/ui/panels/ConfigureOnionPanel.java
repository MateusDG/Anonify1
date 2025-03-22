package com.anonify.ui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.anonify.services.Services;
import com.anonify.ui.components.ChatPanel;

public class ConfigureOnionPanel extends JPanel {

    public ConfigureOnionPanel(Services torService, ChatPanel chatPanel) {
        // Define o layout e a borda para espaçamento interno
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // Torna o painel não opaco para que o paintComponent customizado seja exibido
        setOpaque(false);

        // Configuração dos constraints para o GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        // Título do painel
        JLabel titleLabel = new JLabel("Configure Onion Server");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, gbc);

        // Reseta gridwidth e avança para a próxima linha
        gbc.gridwidth = 1;
        gbc.gridy++;

        // Rótulo "Port:"
        JLabel portLabel = new JLabel("Port:");
        portLabel.setForeground(Color.WHITE);
        portLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        add(portLabel, gbc);

        // Campo de texto para a porta
        gbc.gridx = 1;
        JTextField portField = createStyledTextField("12345");
        add(portField, gbc);

        // Próxima linha para "Hidden Service Hostname File:"
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel hiddenServiceLabel = new JLabel("Hidden Service Hostname File:");
        hiddenServiceLabel.setForeground(Color.WHITE);
        hiddenServiceLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        add(hiddenServiceLabel, gbc);

        gbc.gridx = 1;
        JTextField hiddenServiceField = createStyledTextField("/var/lib/tor/hidden_service/hostname");
        add(hiddenServiceField, gbc);

        // Linha para o botão "Start Server"
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton startButton = createStyledButton("Start Server", "src/res/icons/16x16/start.png");
        add(startButton, gbc);

        // Ação do botão
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startOnionServer(torService, chatPanel, portField, hiddenServiceField);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Custom paint: fundo com gradiente, retângulo arredondado e borda sutil
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Define leve transparência
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));

        int arc = 30; // Raios dos cantos arredondados
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(20, 20, 35),
            0, getHeight(), new Color(5, 5, 20)
        );
        g2d.setPaint(gradient);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        // Desenha uma borda sutil e translúcida
        g2d.setColor(new Color(255, 255, 255, 50));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, arc, arc);
        g2d.dispose();
    }

    /**
     * Cria um JTextField estilizado com fundo escuro, texto branco e borda com padding.
     */
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

    /**
     * Cria um JButton estilizado com fonte em negrito, fundo de acordo com o tema dark e ícone opcional.
     * Aqui foi atualizado para usar a mesma cor de fundo dos botões no SettingsPanel.
     */
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
        // Usamos a mesma cor do SettingsPanel para consistência
        button.setBackground(new Color(40, 70, 40));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    /**
     * Inicia o servidor Onion e exibe a janela de status.
     */
    private void startOnionServer(Services torService, ChatPanel chatPanel,
                                  JTextField portField, JTextField hiddenServiceField) {
        String port = portField.getText();
        String hiddenServicePath = hiddenServiceField.getText();
        new Thread(() -> {
            try {
                torService.startOnionServer(hiddenServicePath, port, chatPanel);
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() ->
                    chatPanel.addMessage("Failed to start server: " + ex.getMessage(), "BOT")
                );
            }
        }).start();

        SwingUtilities.invokeLater(() -> {
            // Fecha todas as janelas abertas antes de mostrar a nova
            for (Window window : Window.getWindows()) {
                window.dispose();
            }
            ServerStatusWindow statusWindow = new ServerStatusWindow(torService.getOnionAddress());
            statusWindow.setVisible(true);
        });
    }
}

/**
 * Classe auxiliar para exibir uma janela com o status do servidor.
 */
class ServerStatusWindow extends JFrame {
    public ServerStatusWindow(String onionAddress) {
        setTitle("Server Status");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(40, 40, 55));

        JLabel statusLabel = new JLabel("Server Running");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoLabel = new JLabel("Onion Address: " + onionAddress);
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        infoLabel.setForeground(Color.WHITE);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(statusLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(infoLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        add(mainPanel, BorderLayout.CENTER);
    }
}
