package ru.rsreu.rentall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "images")
@Getter @Setter
public class Image {
    @Id
    private int imgId;
    private byte[] catName;
}
