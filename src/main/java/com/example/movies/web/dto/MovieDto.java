package com.example.movies.web.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class MovieDto {

    private Long id;
    @NotEmpty
    @Length(min = 1, max = 400)
    private String title;
    private LocalDate releaseDate;
    private String tagline;
    private String description;
    private Integer budget;
    private String status;
    private double revenue;
    private String posterUrl;
    @NotEmpty
    private List<String> genres;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDto movieDto = (MovieDto) o;
        return Double.compare(movieDto.revenue, revenue) == 0 && Objects.equals(id, movieDto.id) && Objects.equals(title, movieDto.title) && Objects.equals(releaseDate, movieDto.releaseDate) && Objects.equals(tagline, movieDto.tagline) && Objects.equals(description, movieDto.description) && Objects.equals(budget, movieDto.budget) && Objects.equals(status, movieDto.status) && Objects.equals(posterUrl, movieDto.posterUrl) && Objects.equals(genres, movieDto.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, releaseDate, tagline, description, budget, status, revenue, posterUrl, genres);
    }

    @Override
    public String toString() {
        return "MovieDto{" +
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
