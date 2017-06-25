package com.example.pankaj.top10downloader;

/**
 * Created by pankaj on 6/24/2017.
 */

public class Application {

    //name, artist, release date
    private String title;
    private String rights;
    private String releaseDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
