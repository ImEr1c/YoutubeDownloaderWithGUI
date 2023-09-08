package com.imer1c.utils;

import com.github.kiulian.downloader.model.videos.VideoDetails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ResourceLoader {

    public static final Image MUSIC_NOTE;

    static
    {
        try
        {
            MUSIC_NOTE = ImageIO.read(ResourceLoader.class.getClassLoader().getResourceAsStream("music_note.png"));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Image applyMusicNote(Image image, int width, int height)
    {
        BufferedImage bufImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufImg.getGraphics();

        graphics.drawImage(image, 0, 0, null);
        graphics.setColor(new Color(255, 255, 255, 200));
        graphics.fillRect(0, 0, width, height);

        int musicX = (width - MUSIC_NOTE.getWidth(null)) / 2;
        int musicY = (height - MUSIC_NOTE.getHeight(null)) / 2;

        graphics.drawImage(MUSIC_NOTE, musicX, musicY, null);

        return bufImg;
    }

    public static Image loadImage(VideoDetails details)
    {
        String link = details.thumbnails().get(0);

        try
        {
            URL url = new URL(link);
            URLConnection urlConnection = url.openConnection();

            InputStream inputStream = urlConnection.getInputStream();
            Image image = ImageIO.read(inputStream);
            inputStream.close();

            return image;
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
