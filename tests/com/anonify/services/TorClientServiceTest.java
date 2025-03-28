package com.anonify.services;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.verify;

import com.anonify.ui.components.ChatPanel;

public class TorClientServiceTest {

    @Test
    public void testTorClientServiceInvalidOnion() {
        ChatPanel mockPanel = org.mockito.Mockito.mock(ChatPanel.class);
        // Chamada com endereço .onion inválido (string vazia)
        TorClientService.main("", "127.0.0.1", 9050, 12345, mockPanel);
        // Verifica se a mensagem "Invalid .onion address. Exiting." foi enviada ao ChatPanel
        verify(mockPanel).addMessage(contains("Invalid .onion address. Exiting."), anyString());
    }
}
