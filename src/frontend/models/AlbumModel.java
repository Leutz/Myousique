package frontend.models;

import java.util.List;

public class AlbumModel {
    String name;
    String song;
    String artistName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;

    public AlbumModel(String name, String song, int id) {
        this.name = name;
        this.song = song;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    @Override
    public String toString() {
        return "AlbumModel{" +
                "name='" + name + '\'' +
                ", song='" + song + '\'' +
                ", artistName='" + artistName + '\'' +
                ", id=" + id +
                '}';
    }
}
