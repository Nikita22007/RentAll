package ru.tinkoff.rentall.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "categories")
@Getter @Setter
public class Category {
    @Id
    private String catName;
}
