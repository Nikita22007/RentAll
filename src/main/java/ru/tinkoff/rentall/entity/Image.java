package ru.tinkoff.rentall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "images")
@Getter @Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imgId;
    private byte[] imgName;
}
