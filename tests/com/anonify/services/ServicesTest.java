import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.anonify.services.Services;
import com.anonify.ui.components.ChatPanel;

public class ServicesTest {

    @Test
    public void testStartOnionServerInvalidPort() {
        ChatPanel mockPanel = mock(ChatPanel.class);
        Services services = new Services();
        // Passa uma porta inválida
        services.startOnionServer("fakePath", "invalidPort", mockPanel);
        // Verifica se a mensagem de erro foi adicionada
        verify(mockPanel).addMessage(contains("Invalid port number"), anyString());
    }

    @Test
    public void testConnectToOnionServerInvalidPort() {
        ChatPanel mockPanel = mock(ChatPanel.class);
        Services services = new Services();
        services.connectToOnionServer("abcdef.onion", "127.0.0.1", "notANumber", "12345", mockPanel);
        verify(mockPanel).addMessage(contains("Invalid port number"), anyString());
    }
}
