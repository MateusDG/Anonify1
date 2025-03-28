package com.anonify.ui.components;
import java.awt.Dimension;

import javax.swing.JButton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

class TestCustomScrollBarUI extends CustomScrollBarUI {
    public JButton testCreateDecreaseButton(int orientation) {
        return super.createDecreaseButton(orientation);
    }
    public JButton testCreateIncreaseButton(int orientation) {
        return super.createIncreaseButton(orientation);
    }
}

public class CustomScrollBarUITest {

    @Test
    public void testZeroButtonSize() {
        TestCustomScrollBarUI customUI = new TestCustomScrollBarUI();
        JButton decreaseButton = customUI.testCreateDecreaseButton(0);
        JButton increaseButton = customUI.testCreateIncreaseButton(0);
        
        assertNotNull(decreaseButton, "Decrease button should not be null");
        assertNotNull(increaseButton, "Increase button should not be null");
        
        assertEquals(new Dimension(0, 0), decreaseButton.getPreferredSize(), "Decrease button preferred size should be (0,0)");
        assertEquals(new Dimension(0, 0), increaseButton.getPreferredSize(), "Increase button preferred size should be (0,0)");
    }
}
