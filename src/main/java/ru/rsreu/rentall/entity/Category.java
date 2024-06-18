package ru.rsreu.rentall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "categories")
@Getter @Setter
public class Category {
    @Id
    private int catId;
    private String catName;
}
