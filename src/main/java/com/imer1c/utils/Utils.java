package com.imer1c.utils;

import java.io.File;

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
