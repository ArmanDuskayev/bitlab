package kz.bitlab.hotels.db;

import java.util.Date;

public class Hotel {

    private Long id;
    private String name;
    private User author;
    private int stars;
    private String description;
    private Date addedDate;
    private int price;
    private int likes;

    public Hotel(Long id, String name, User author, int stars, String description, Date addedDate, int price) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.stars = stars;
        this.description = description;
        this.addedDate = addedDate;
        this.price = price;
    }

    public Hotel(Long id, String name, User author, int stars, String description, Date addedDate, int price, int likes) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.stars = stars;
        this.description = description;
        this.addedDate = addedDate;
        this.price = price;
        this.likes = likes;
    }

    public Hotel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
