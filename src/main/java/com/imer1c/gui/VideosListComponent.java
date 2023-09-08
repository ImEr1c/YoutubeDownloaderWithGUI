package com.imer1c.gui;

import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.imer1c.downloading.Downloader;
import com.imer1c.gui.components.VerticalLayout;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class VideosListComponent extends JScrollPane {

    private final JPanel panel;

    public VideosListComponent()
    {
        VerticalLayout layout = new VerticalLayout();
        layout.setGap(5);
        layout.setFillHorizontal(true);

        this.panel = new JPanel(layout);
        this.panel.setBackground(Color.GRAY);

        this.setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK));
        this.setViewportView(this.panel);
    }

    public void addToList(Downloader downloader)
    {
        VideoComponent component = new VideoComponent(downloader, this);
        downloader.setComponentCallback(component);

        this.panel.add(component, 0);
        this.panel.revalidate();
        this.revalidate();
    }

    public void remove(VideoComponent component)
    {
        this.panel.remove(component);
        this.panel.revalidate();
        this.revalidate();
        this.repaint();
    }
}
