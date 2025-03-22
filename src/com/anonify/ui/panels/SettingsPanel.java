package com.anonify.ui.panels;

import com.anonify.services.Services;
import com.anonify.ui.components.TabManager;
import java.awt.*;
import javax.swing.*;

public class SettingsPanel extends JPanel {

    public SettingsPanel(TabManager tabManager, Services torService) {
        // Configura o layout e o espaçamento interno
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        // Tornar o painel não opaco para que o paintComponent customizado seja exibido
        setOpaque(false);

        // Configura os constraints do GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        // Título do painel
        JLabel titleLabel = new JLabel("Settings");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, gbc);

        // Reseta gridwidth e avança para a próxima linha
        gbc.gridwidth = 1;
        gbc.gridy++;

        // Botão para configurar o Onion Server
        JButton configureOnionButton = createStyledButton("Configure Onion Server", "src/res/icons/16x16/onion-alt.png");
        configureOnionButton.addActionListener(e -> tabManager.openConfigureOnionTab());
        gbc.gridx = 0;
        add(configureOnionButton, gbc);

        // Botão para conectar a um Onion Server
        JButton connectOnionButton = createStyledButton("Connect to an Onion Server", "src/res/icons/16x16/exit.png");
        connectOnionButton.addActionListener(e -> tabManager.openConnectOnionTab());
        gbc.gridx = 1;
        add(connectOnionButton, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Não chama super.paintComponent(g) para evitar o fundo padrão (geralmente branco)
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define uma leve transparência (opcional)
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.95f));

        // Cria um gradiente vertical em tons escuros
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(20, 20, 35),
            0, getHeight(), new Color(5, 5, 20)
        );
        g2d.setPaint(gradient);

        // Desenha um retângulo arredondado que ocupa todo o painel
        int arc = 30; // raios para os cantos arredondados
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        // Desenha uma borda sutil e translúcida
        g2d.setColor(new Color(255, 255, 255, 50));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, arc, arc);

        g2d.dispose();
    }

    /**
     * Cria um JButton estilizado com fonte em negrito, fundo adequado ao tema dark e ícone opcional.
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
        // Fundo com tom discreto que complementa o tema dark
        button.setBackground(new Color(40, 70, 40));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }
}
