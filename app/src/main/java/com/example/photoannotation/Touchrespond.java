package com.example.photoannotation;

import java.nio.file.Path;

public class Touchrespond {

    public int color;
    public boolean emboss,blur;
    public int strokeWidth;
    public Path path;

    public Touchrespond(int color, boolean emboss, boolean blur, int strokeWidth, Path path) {
        this.color = color;
        this.emboss = emboss;
        this.blur = blur;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }





}
