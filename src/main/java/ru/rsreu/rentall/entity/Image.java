package ru.rsreu.rentall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "images")
@Getter @Setter @NoArgsConstructor
public class Image {
    @Id
    private int imgId;
    private byte[] catName;
}
