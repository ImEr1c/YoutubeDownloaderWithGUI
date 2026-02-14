package com.imer1c.gui;

import com.github.kiulian.downloader.model.Extension;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;
import com.github.kiulian.downloader.model.videos.quality.VideoQuality;
import com.imer1c.Client;
import com.imer1c.downloading.DownloadSystem;
import com.imer1c.downloading.Downloader;
import com.imer1c.downloading.VideoType;
import com.imer1c.gui.components.PlaceholderTextField;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

public class MainGUI extends JPanel {

    private final PlaceholderTextField linkField;
    private final JButton add;
    private final VideosListComponent list;
    private Downloader downloader;

    public MainGUI(Client client)
    {
        this.setPreferredSize(new Dimension(1280, 720));
        this.setBackground(Color.GRAY);

        this.linkField = new PlaceholderTextField("Link");
        this.add = new JButton("+");

        if (System.getProperty("os.name").contains("win"))
        {
            this.add.setBorder(((BorderUIResource.CompoundBorderUIResource) this.add.getBorder()).getOutsideBorder());
        }

        this.list = new VideosListComponent();
        JComboBox<VideoType> types = new JComboBox<>(VideoType.values());
        JComboBox<VideoQuality> qualities = new JComboBox<>();

        GroupLayout layout = new GroupLayout(this);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addGap(5)
                        .addComponent(linkField)
                        .addComponent(types, 80, 80, 80)
                        .addComponent(qualities, 80, 80, 80)
                        .addComponent(add, 30, 30, 30)
                        .addGap(5)
                )
                .addComponent(this.list)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addComponent(linkField, 30, 30, 30)
                        .addComponent(types, 30, 30, 30)
                        .addComponent(qualities, 30, 30, 30)
                        .addComponent(add, 30, 30, 30)
                )
                .addGap(4)
                .addComponent(this.list)
        );

        DownloadSystem downloadSystem = client.getDownloadSystem();

        this.add.addActionListener(e -> {
            VideoInfo info = downloadSystem.parse(this.linkField.getText(), types.getSelectedItem() == VideoType.AUDIO);

            downloadSystem.startLast();
            this.linkField.setText("");


        });

        this.setLayout(layout);

        downloadSystem.setGuiCallback(this.list);
    }
}
