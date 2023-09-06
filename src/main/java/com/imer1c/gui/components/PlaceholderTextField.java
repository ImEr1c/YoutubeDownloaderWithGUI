package com.imer1c.gui.components;

import javax.swing.*;
import java.awt.*;

public class PlaceholderTextField extends JTextField {
    private final String placeholder;

    public PlaceholderTextField(String placeholder)
    {
        this.placeholder = placeholder;
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        if (!this.getText().isEmpty())
        {
            return;
        }

        g.setColor(getDisabledTextColor());

        FontMetrics fm = g.getFontMetrics();
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

        g.drawString(this.placeholder, getInsets().left, y);
    }
}
