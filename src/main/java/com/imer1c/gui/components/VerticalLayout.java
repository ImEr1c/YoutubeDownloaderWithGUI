package com.imer1c.gui.components;

import java.awt.*;

public class VerticalLayout implements LayoutManager {

    private boolean fillHorizontal, centerHorizontal, centerVertical;
    private int centerHeight;

    public void setFillHorizontal(boolean fillHorizontal)
    {
        this.fillHorizontal = fillHorizontal;
    }

    public void setCenterHorizontal(boolean centerHorizontal)
    {
        this.centerHorizontal = centerHorizontal;
    }

    public void setCenterVertical(boolean centerVertical)
    {
        this.centerVertical = centerVertical;
    }

    @Override
    public void addLayoutComponent(String name, Component comp)
    {
        if (this.centerVertical)
        {
            this.centerHeight += comp.getHeight();
        }
    }

    @Override
    public void removeLayoutComponent(Component comp)
    {
        if (this.centerVertical)
        {
            this.centerHeight -= comp.getHeight();
        }
    }

    @Override
    public Dimension preferredLayoutSize(Container parent)
    {
        return this.size(parent);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent)
    {
        return this.size(parent);
    }

    private Dimension size(Container parent)
    {
        Dimension dim = new Dimension();

        if (this.fillHorizontal)
        {
            dim.width = parent.getWidth();
        }

        for (Component component : parent.getComponents())
        {
            if (component.isVisible())
            {
                if (!this.fillHorizontal)
                {
                    dim.width = Math.max(dim.width, component.getPreferredSize().width);
                }

                dim.height += component.getHeight();
            }
        }

        return dim;
    }

    @Override
    public void layoutContainer(Container parent)
    {
        int y = 0;

        if (centerVertical)
        {
            y = (parent.getHeight() - this.centerHeight) / 2;
        }

        for (Component component : parent.getComponents())
        {
            int x = 0;

            if (fillHorizontal)
            {
                component.setSize(parent.getWidth(), component.getPreferredSize().height);
            }
            else
            {
                component.setSize(component.getPreferredSize());
            }

            if (centerHorizontal)
            {
                x = (parent.getWidth() - component.getWidth()) / 2;
            }

            System.out.println(x);
            System.out.println(y);
            component.setLocation(x, y);

            y += component.getHeight();
        }
    }
}
