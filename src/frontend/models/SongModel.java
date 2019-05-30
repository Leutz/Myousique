package frontend.models;

public class SongModel {
    public String title;
    public String album;
    public String artist;

    public SongModel(String title, String artist, String album) {
        this.title = title;
        this.album = album;
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}