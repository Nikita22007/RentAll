package ru.rsreu.rentall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity(name = "categories")
@Getter @Setter @NoArgsConstructor
public class Category {
    @Id
    private int catId;
    private String catName;
}
