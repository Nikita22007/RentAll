package ru.rsreu.rentall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "images")
public class Image {
    @Id
    private int imgId;

    public byte[] getCatName() {
        return catName;
    }

    public void setCatName(byte[] catName) {
        this.catName = catName;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    private byte[] catName;

    public Image() {}
}
