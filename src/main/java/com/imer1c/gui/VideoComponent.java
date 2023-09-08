package com.imer1c.gui;

import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.imer1c.downloading.Downloader;
import com.imer1c.gui.components.ImageComponent;
import com.imer1c.utils.ResourceLoader;
import com.imer1c.utils.Utils;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class VideoComponent extends JPanel {

    private final JProgressBar bar;
    private final VideosListComponent list;
    private final JButton show, open;

    public VideoComponent(Downloader downloader, VideosListComponent list)
    {
        VideoDetails details = downloader.getDetails();

        JLabel label = new JLabel(details.title());
        label.setForeground(Color.WHITE);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 30f));

        JPanel centeredTitle = Utils.center(label);
        centeredTitle.setBackground(Color.GRAY);

        this.bar = new JProgressBar(0, 100);
        this.list = list;

        int imgWidth = 100;
        int imgHeight = 100;

        Image img = ResourceLoader.loadImage(details);

        ImageComponent image = new ImageComponent(downloader.isAudio() ? ResourceLoader.applyMusicNote(img, imgWidth, imgHeight) : img);

        JButton delButton = new JButton("Sterge");
        delButton.setBackground(Color.RED);
        delButton.setForeground(Color.WHITE);

        this.show = new JButton("Deschide dosarul");
        this.open = new JButton("Deschide");

        show.setEnabled(false);
        open.setEnabled(false);

        delButton.addActionListener(e -> {
            downloader.del();
            list.remove(this);
        });

        show.addActionListener(e -> {
            File output = downloader.getOutput();
            File directory = output.getParentFile();

            try
            {
                Desktop.getDesktop().open(directory);
            }
            catch (IOException ex)
            {
                throw new RuntimeException(ex);
            }
        });

        open.addActionListener(e -> {
            File output = downloader.getOutput();

            try
            {
                Desktop.getDesktop().open(output);
            }
            catch (IOException ex)
            {
                throw new RuntimeException(ex);
            }
        });

        GroupLayout layout = new GroupLayout(this);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(image, imgWidth, imgWidth, imgWidth)
                            .addGap(50)
                            .addGroup(layout.createParallelGroup()
                                .addComponent(centeredTitle)
                                .addComponent(this.bar)
                            )
                            .addGap(50)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(delButton)
                                .addComponent(show)
                                .addComponent(open))
                )
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addComponent(image, imgHeight, imgHeight, imgHeight)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(centeredTitle)
                                .addGap(5)
                                .addComponent(this.bar)
                                .addGap(20)))
                .addGap(2)
                .addGroup(layout.createParallelGroup()
                        .addComponent(delButton)
                        .addComponent(show)
                        .addComponent(open))
                .addGap(5)
        );

        this.setLayout(layout);
        this.setBackground(Color.GRAY);
        this.setBorder(new MatteBorder(1, 0, 1, 0, Color.WHITE));
    }

    public void setProgress(int progress)
    {
        this.bar.setValue(progress);
    }

    public void finished()
    {
        this.show.setEnabled(true);
        this.open.setEnabled(true);
    }
}
