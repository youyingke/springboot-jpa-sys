package com.hawk.demo.util;

import java.io.InputStream;

/**
 * Created by Lenovo on 2019-03-17.
 */
public class ImageHolder {

    private String imageName;
    private InputStream image;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }

    public ImageHolder(InputStream inputStream, String originalFilename) {
        this.image=inputStream;
        this.imageName=originalFilename;
    }
}
