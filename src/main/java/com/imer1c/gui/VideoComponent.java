package com.imer1c.gui;

import javax.swing.*;
import java.awt.*;

public class VideoComponent extends JPanel {

    private final JProgressBar bar;
    private final VideosListComponent list;

    public VideoComponent(String title, VideosListComponent list)
    {
        JLabel label = new JLabel(title);
        this.bar = new JProgressBar(0, 100);
        this.list = list;

        GroupLayout layout = new GroupLayout(this);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addComponent(label)
                .addComponent(bar));

        layout.setVerticalGroup(layout.createParallelGroup()
                .addComponent(label)
                .addComponent(bar));

        this.setLayout(layout);
    }

    public void setProgress(int progress)
    {
        this.bar.setValue(progress);
    }

    public void finished()
    {
    }
}
