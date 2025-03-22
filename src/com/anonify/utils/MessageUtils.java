package com.anonify.utils;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MessageUtils {

    public static JPanel createMessagePanel(String message, String sender) {
        boolean isUser = sender.equalsIgnoreCase("YOU");

        // Painel (container) para alinhar a bolha à esquerda (BOT) ou à direita (YOU)
        JPanel container = new JPanel(new FlowLayout(isUser ? FlowLayout.RIGHT : FlowLayout.LEFT));
        container.setOpaque(false); // <--- Importante para não ter fundo branco

        // Painel que vai desenhar a bolha em si
        JPanel bubblePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Como setOpaque(false), o Swing não pinta fundo branco aqui
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width  = getWidth();
                int height = getHeight();

                // 1) Desenha uma sombra sutil (opcional)
                int shadowSize = 4;
                Color shadowColor = new Color(0, 0, 0, 60); // preto translúcido
                RoundRectangle2D shadowRect = new RoundRectangle2D.Float(
                    shadowSize, // deslocamento X
                    shadowSize, // deslocamento Y
                    width - shadowSize * 2,
                    height - shadowSize * 2,
                    15, 15
                );
                g2.setColor(shadowColor);
                g2.fill(shadowRect);

                // 2) Define gradiente para a bolha (cores diferentes p/ YOU vs BOT)
                Color bubbleColor1, bubbleColor2;
                if (isUser) {
                    // Exemplo: tons de verde-escuro
                    bubbleColor1 = new Color(50, 80, 50);
                    bubbleColor2 = new Color(40, 70, 40);
                } else {
                    // Exemplo: tons de roxo-escuro
                    bubbleColor1 = new Color(65, 50, 80);
                    bubbleColor2 = new Color(55, 40, 70);
                }

                GradientPaint gp = new GradientPaint(
                    0, 0, bubbleColor1,
                    0, height, bubbleColor2
                );
                g2.setPaint(gp);

                // 3) Desenha a bolha arredondada
                RoundRectangle2D bubbleRect = new RoundRectangle2D.Float(
                    0, 0,
                    width - shadowSize * 2,
                    height - shadowSize * 2,
                    15, 15
                );
                g2.fill(bubbleRect);

                // 4) Borda sutil (opcional)
                g2.setColor(new Color(255, 255, 255, 50)); // branco semi-transparente
                g2.setStroke(new BasicStroke(1f));
                g2.draw(bubbleRect);

                g2.dispose();
            }
        };

        // Tornamos o bubblePanel transparente para ver o gradiente do ChatPanel ao fundo
        bubblePanel.setOpaque(false);

        // Layout vertical para empilhar texto e horário
        bubblePanel.setLayout(new BoxLayout(bubblePanel, BoxLayout.Y_AXIS));
        // Espaço interno da bolha
        bubblePanel.setBorder(new EmptyBorder(8, 12, 8, 12));

        // Texto da mensagem (HTML para quebra de linha)
        String styledText = "<html><body style='width:300px;'>" + message + "</body></html>";
        JLabel messageLabel = new JLabel(styledText);
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Horário
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        JLabel timeLabel = new JLabel("<html><div style='text-align: right;'>" + time + "</div></html>");
        timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        timeLabel.setForeground(Color.LIGHT_GRAY);
        timeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // Adiciona labels no bubblePanel
        bubblePanel.add(messageLabel);
        bubblePanel.add(Box.createVerticalStrut(4));
        bubblePanel.add(timeLabel);

        // Adiciona a bolha no container
        container.add(bubblePanel);

        return container;
    }
}
