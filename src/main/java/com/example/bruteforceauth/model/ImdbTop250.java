package com.example.bruteforceauth.model;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "imdb_top")
public class ImdbTop250 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rank;
    private String title;
    private String year;
    private String rating;

    public ImdbTop250() {
    }
    public ImdbTop250(int rank, String title, String year, String rating) {
        this.rank = rank;
        this.title = title;
        this.year = year;
        this.rating = rating;
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getRank() { return rank; }
    public void setRank(int rank) { this.rank = rank; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }
}
