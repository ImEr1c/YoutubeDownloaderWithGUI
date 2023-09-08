package com.imer1c.gui.components;

import javax.swing.*;
import java.awt.*;

public class ImageComponent extends JComponent {

    private Image image;

    public ImageComponent(Image image)
    {
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if (this.image.getWidth(null) > this.getWidth() || this.image.getHeight(null) > this.getHeight())
        {
            this.image = this.image.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        }

        g.drawImage(this.image, this.getX(), this.getY(), null);
    }
}
