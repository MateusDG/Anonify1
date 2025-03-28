package com.anonify.services;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.verify;

import com.anonify.ui.components.ChatPanel;

public class TorServerServiceTest {

    @Test
    public void testTorServerServiceInvalidFile() {
        ChatPanel mockPanel = org.mockito.Mockito.mock(ChatPanel.class);
        // Passa um caminho de arquivo inexistente para o hostname do serviço oculto.
        TorServerService.main("nonexistent_file.txt", 12345, mockPanel);
        // Verifica se a mensagem de erro referente à leitura do arquivo foi enviada.
        verify(mockPanel).addMessage(contains("Could not read the .onion address"), anyString());
    }
}
