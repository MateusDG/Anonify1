package com.anonify.utils;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class ConstantsTest {

    @Test
    public void testMaxMessageString() {
        assertEquals(200, Constants.MAX_MESSAGE_STRING, "MAX_MESSAGE_STRING deve ser 200");
    }
    
    @Test
    public void testColorValues() {
        assertEquals(new Color(125, 70, 152), Constants.PURPLE, "A cor PURPLE está incorreta");
        assertEquals(new Color(89, 49, 107), Constants.DARK_PURPLE, "A cor DARK_PURPLE está incorreta");
        assertEquals(new Color(104, 176, 48), Constants.GREEN, "A cor GREEN está incorreta");
        assertEquals(new Color(248, 249, 250), Constants.GREY, "A cor GREY está incorreta");
        assertEquals(new Color(51, 58, 65), Constants.DARK_GRAY, "A cor DARK_GRAY está incorreta");
        assertEquals(new Color(68, 70, 84), Constants.LIGHTER_GRAY, "A cor LIGHTER_GRAY está incorreta");
        assertEquals(new Color(153, 174, 195), Constants.LIGHT_GRAY, "A cor LIGHT_GRAY está incorreta");
        assertEquals(new Color(255, 255, 255), Constants.WHITE, "A cor WHITE está incorreta");
        // Verifica se a cor do remetente é a mesma que DARK_PURPLE
        assertEquals(Constants.DARK_PURPLE, Constants.SENDER_COLOR, "A cor SENDER_COLOR deve ser DARK_PURPLE");
    }
}
