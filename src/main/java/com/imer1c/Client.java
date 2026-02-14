package com.imer1c;

import com.github.kiulian.downloader.Config;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.imer1c.downloading.DownloadSystem;
import com.imer1c.gui.MainGUI;

import javax.swing.*;

public class Client {

    private static Client instance = new Client();

    public static Client getInstance()
    {
        return instance;
    }

    private final JFrame frame;
    private final DownloadSystem downloadSystem;

    public Client()
    {
        this.downloadSystem = new DownloadSystem(this);
        this.frame = new JFrame();
        this.frame.setContentPane(new MainGUI(this));
        this.frame.pack();
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.frame.setVisible(true);
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public DownloadSystem getDownloadSystem()
    {
        return downloadSystem;
    }
}
