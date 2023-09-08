package com.imer1c.downloading;

public enum VideoType {
    VIDEO,
    AUDIO;

    @Override
    public String toString()
    {
        return this.name().toLowerCase();
    }
}
