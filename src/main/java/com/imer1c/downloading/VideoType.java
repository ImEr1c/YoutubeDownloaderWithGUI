package com.imer1c.downloading;

public enum VideoType {
    MP4,
    MP3;

    @Override
    public String toString()
    {
        return this.name().toLowerCase();
    }
}
