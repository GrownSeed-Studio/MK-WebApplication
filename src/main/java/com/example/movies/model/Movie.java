package com.example.movies.model;

import java.time.LocalDate;
import java.util.List;

public class Movie {

    public long id;
    public String title;
    public LocalDate releaseDate;
    public String tagline;
    public String description;
    public Integer budget;
    public String status;
    public Double revenue;
    public String posterUrl;
    public List<Integer> genres;

    public Movie() {
    }

    public Movie(long id, String title, LocalDate releaseDate, String tagline, String description, Integer budget, String status, Double revenue, String posterUrl, List<Integer> genres) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.tagline = tagline;
        this.description = description;
        this.budget = budget;
        this.status = status;
        this.revenue = revenue;
        this.posterUrl = posterUrl;
        this.genres = genres;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenres(List<Integer> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", tagline='" + tagline + '\'' +
                ", description='" + description + '\'' +
                ", budget=" + budget +
                ", status='" + status + '\'' +
                ", revenue=" + revenue +
                ", posterUrl='" + posterUrl + '\'' +
                ", genres=" + genres +
                '}';
    }
}
