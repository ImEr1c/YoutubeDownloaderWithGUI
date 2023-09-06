package com.imer1c.gui;

import com.imer1c.Client;
import com.imer1c.downloading.DownloadSystem;
import com.imer1c.downloading.VideoType;
import com.imer1c.gui.components.PlaceholderTextField;

import javax.swing.*;
import java.awt.*;

public class MainGUI extends JPanel {

    private final PlaceholderTextField linkField;
    private final JButton add;
    private final VideosListComponent list;

    public MainGUI(Client client)
    {
        this.setPreferredSize(new Dimension(1280, 720));

        this.linkField = new PlaceholderTextField("Link");
        this.add = new JButton("+");
        this.list = new VideosListComponent();
        JComboBox<VideoType> types = new JComboBox<>(VideoType.values());

        GroupLayout layout = new GroupLayout(this);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(linkField)
                        .addComponent(types, 80, 80, 80)
                        .addComponent(add, 40, 40, 40)
                )
                .addComponent(this.list)
        );

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(linkField, 40, 40, 40)
                        .addComponent(types, 40, 40, 40)
                        .addComponent(add, 40, 40, 40)
                )
                .addComponent(this.list)
        );

        DownloadSystem downloadSystem = client.getDownloadSystem();

        this.add.addActionListener(e -> {
            downloadSystem.parseAndStart(this.linkField.getText(), types.getSelectedItem() == VideoType.MP3);

            this.linkField.setText("");
        });

        this.setLayout(layout);

        downloadSystem.setGuiCallback(this.list);
    }
}
