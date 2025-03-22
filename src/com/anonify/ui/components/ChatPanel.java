package com.anonify.ui.components;

import com.anonify.utils.Constants;
import com.anonify.utils.MessageUtils;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class ChatPanel {
    private final JPanel chatPanel;
    private final JScrollPane scrollPane;
    private int messageIndex = 0;

    // Imagens para gradiente + textura
    private Image backgroundImage;
    private Image cryptoPatternImage;

    public ChatPanel() {
        // Carrega as imagens
        loadBackgroundImage();
        loadCryptoPatternImage();

        // Painel principal, onde serão adicionadas as “bolhas” de mensagem
        chatPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // 1) Gradiente de fundo
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(20, 20, 35),    // cor topo
                    0, getHeight(), new Color(5, 5, 20) // cor base
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // 2) Textura “criptografia” em baixa opacidade (opcional)
                if (cryptoPatternImage != null) {
                    int pWidth  = cryptoPatternImage.getWidth(this);
                    int pHeight = cryptoPatternImage.getHeight(this);

                    // Define opacidade de ~8%
                    float alpha = 0.08f;
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

                    for (int y = 0; y < getHeight(); y += pHeight) {
                        for (int x = 0; x < getWidth(); x += pWidth) {
                            g2d.drawImage(cryptoPatternImage, x, y, this);
                        }
                    }
                    // Restaura opacidade total para próximos desenhos
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }

                // 3) Logo central, se desejar manter
                if (backgroundImage != null) {
                    int panelWidth  = getWidth();
                    int panelHeight = getHeight();
                    int imgW = backgroundImage.getWidth(this);
                    int imgH = backgroundImage.getHeight(this);

                    // Ajuste para caber ~25% da largura
                    double maxImageWidth = panelWidth * 0.25;
                    double scaleFactor   = Math.min(maxImageWidth / imgW, 1.0);
                    int targetW = (int) (imgW * scaleFactor);
                    int targetH = (int) (imgH * scaleFactor);

                    int x = (panelWidth - targetW) / 2;
                    int y = (panelHeight - targetH) / 2;

                    g2d.drawImage(backgroundImage, x, y, targetW, targetH, this);
                }

                g2d.dispose();
            }
        };

        // Importante: defina o painel como opaco para que paintComponent seja visível
        chatPanel.setOpaque(true);

        // Cria o JScrollPane que contém o chatPanel
        scrollPane = new JScrollPane(chatPanel);
        // Para o gradiente aparecer, deixamos o scrollPane e o viewport transparentes
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Ajusta políticas de scroll
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        // Personaliza a barra de rolagem
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setBackground(Constants.LIGHTER_GRAY);
        verticalBar.setUI(new CustomScrollBarUI());

        // Ajusta largura das bolhas de mensagem quando redimensionar
        scrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustMessageWidths();
                SwingUtilities.invokeLater(ChatPanel.this::scrollToBottom);
            }
        });
    }

    /**
     * Carrega a imagem de fundo (logo) central.
     * Ajuste o caminho conforme sua estrutura de pastas.
     */
    private void loadBackgroundImage() {
        ImageIcon icon = new ImageIcon("src/res/bg_illustation.png");
        backgroundImage = icon.getImage();
    }

    /**
     * Carrega a imagem de textura para “criptografia”.
     * Crie o arquivo `crypto_pattern.png` em `src/res/` ou mude o caminho.
     */
    private void loadCryptoPatternImage() {
        // Se não existir, cryptoPatternImage será null e não desenharemos nada
        ImageIcon patternIcon = new ImageIcon("src/res/crypto_pattern.png");
        if (patternIcon.getIconWidth() > 0 && patternIcon.getIconHeight() > 0) {
            cryptoPatternImage = patternIcon.getImage();
        } else {
            // Caso não encontre, cryptoPatternImage fica null
            cryptoPatternImage = null;
        }
    }

    /**
     * Retorna o JScrollPane para uso em outros componentes (ex.: TabManager).
     */
    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    /**
     * Adiciona uma nova mensagem no painel.
     */
    public void addMessage(String message, String sender) {
        JPanel messageContainer = MessageUtils.createMessagePanel(message, sender);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx    = 0;
        gbc.gridy    = messageIndex++;
        gbc.weightx  = 1.0;
        gbc.fill     = GridBagConstraints.HORIZONTAL;
        gbc.insets   = new Insets(5, 5, 5, 5);
        gbc.anchor   = GridBagConstraints.WEST;

        chatPanel.add(messageContainer, gbc);

        chatPanel.revalidate();
        chatPanel.repaint();

        SwingUtilities.invokeLater(this::scrollToBottom);
    }

    /**
     * Ajusta a largura das “bolhas” de mensagem para acompanhar o redimensionamento.
     */
    void adjustMessageWidths() {
        int newWidth = scrollPane.getViewport().getWidth() - 20;
        for (Component component : chatPanel.getComponents()) {
            if (component instanceof JPanel panel) {
                panel.setPreferredSize(new Dimension(
                    newWidth,
                    panel.getPreferredSize().height
                ));
                panel.setMaximumSize(new Dimension(newWidth, Integer.MAX_VALUE));
                panel.setMinimumSize(new Dimension(newWidth, panel.getMinimumSize().height));
            }
        }
        chatPanel.revalidate();
        chatPanel.repaint();
    }

    /**
     * Faz o scroll “descer” até o final do chat.
     */
    void scrollToBottom() {
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }

    public void clearMessages() {
        chatPanel.removeAll();
        messageIndex = 0;
        chatPanel.revalidate();
        chatPanel.repaint();
    }
    

    /**
     * Exibe mensagens de ajuda no chat.
     */
    public void showHelp() {
        addMessage("What is Anonify?", "YOU");
        addMessage("Anonify is a quick chat application that works exclusively over the Tor network, connecting clients to a .onion address.", "BOT");
        addMessage("How do I use it?", "YOU");
        addMessage("To use Anonify, you must have Tor configured. This can be done by running the setup script located at scripts/tor.", "BOT");
        addMessage("Once configured, provide your RSA private key and your peer's RSA public key to establish a secure connection.", "BOT");
    }
}
