package com.imer1c.utils;

import com.github.kiulian.downloader.model.videos.VideoDetails;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Utils {
    public static String decodeVideoId(String link)
    {
        if (link.contains("watch?v="))
        {
            return link.split("v=")[1];
        }
        else if (link.contains("youtu.be"))
        {
            return link.split("youtu.be/")[1].split("si=")[0];
        }
        else
        {
            throw new RuntimeException("Video Id not recognized: " + link);
        }
    }

    public static JPanel center(JComponent... components)
    {
        JPanel panel = new JPanel(new FlowLayout());

        for (JComponent component : components)
        {
            panel.add(component);
        }

        return panel;
    }

    public static JPanel end(JComponent... components)
    {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        for (JComponent component : components)
        {
            panel.add(component);
        }

        return panel;
    }

    public static File getDownloadsFolder()
    {
        String os = System.getProperty("os.name").toLowerCase();
        String home = System.getProperty("user.home");

        String path;
        if (os.contains("windows"))
        {
            path = home + "\\Downloads";
        }
        else
        {
            path = home + "/Downloads";
        }

        return new File(path);
    }
}
