package com.imer1c.gui;

import com.imer1c.downloading.Downloader;
import com.imer1c.gui.components.VerticalLayout;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;

public class VideosListComponent extends JScrollPane {

    private final JPanel panel;

    public VideosListComponent()
    {
        VerticalLayout layout = new VerticalLayout();
        layout.setFillHorizontal(true);

        this.panel = new JPanel(layout);

        this.setViewportView(this.panel);
    }

    public void addToList(Downloader downloader)
    {
        String title = downloader.getTitle();

        VideoComponent component = new VideoComponent(title, this);
        downloader.setComponentCallback(component);

        System.out.println("Added");

        this.panel.add(component, 0);
        this.panel.revalidate();
        this.revalidate();
    }
}
