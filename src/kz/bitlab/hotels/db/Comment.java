package kz.bitlab.hotels.db;

import java.util.Date;

public class Comment {

    private Long id;
    private Hotel hotel;
    private User user;
    private String comment;
    private Date addedDate;

    public Comment(Long id, Hotel hotel, User user, String comment, Date addedDate) {
        this.id = id;
        this.hotel = hotel;
        this.user = user;
        this.comment = comment;
        this.addedDate = addedDate;
    }

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }
}
