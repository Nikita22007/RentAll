package ru.rsreu.rentall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity(name = "Advertisements")
public class Advertisement {
    @Id
    private int advId;
    private String advName;
    private String description;
    private int timeUnit;
    private int rentTime;
    private boolean isBarterAllowed;
    private String advPrice;
    private int userId;
    private int imageId;
    private int categoryId;
    private Timestamp creationTime;


    public Advertisement() {}

    public int getId(){
        return advId;
    }

    public void setId(int id){
        this.advId = id;
    }

    public String getName(){
        return advName;
    }

    public void setName(String name){
        this.advName = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public int getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(int time_unit) {
        this.timeUnit = time_unit;
    }

    public int getRentTime() {
        return rentTime;
    }

    public void setRentTime(int rent_time) {
        this.rentTime = rent_time;
    }

    public boolean isBarterAllowed() {
        return isBarterAllowed;
    }

    public void setBarterAllowed(boolean is_barter_allowed) {
        this.isBarterAllowed = is_barter_allowed;
    }

    public String getAdvPrice() {
        return advPrice;
    }

    public void setAdvPrice(String adv_price) {
        this.advPrice = adv_price;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user_id) {
        this.userId = user_id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int image_id) {
        this.imageId = image_id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int category_id) {
        this.categoryId = category_id;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creation_time) {
        this.creationTime = creation_time;
    }
}
