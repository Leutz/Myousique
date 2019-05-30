package backend.model;

import java.util.ArrayList;
import java.util.List;

public class Artist {
    int id;
    String name;
    List<Integer> albumIds;
    List<String> albumNames;

    public List<String> getAlbumNames() {
        return albumNames;
    }

    public void setAlbumNames(List<String> albumNames) {
        this.albumNames = albumNames;
    }

    public void addAlbum(int id){
        if(albumIds == null)
            albumIds = new ArrayList<>();

        albumIds.add(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getAlbumIds() {
        return albumIds;
    }

    public void setAlbumIds(List<Integer> albumIds) {
        this.albumIds = albumIds;
    }

    public Artist(){};
}
