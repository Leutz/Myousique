package frontend.controllers;

import backend.controllers.*;
import backend.controllers.FriendsController;
import backend.model.Album;
import backend.model.Artist;
import backend.model.Song;
import backend.model.User;
import frontend.models.AlbumModel;
import frontend.models.FriendModel;
import frontend.models.SongModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchController {

    @FXML TextField searchBar;
    public String querryStr = "";

    @FXML Label usernameLabel;

    backend.controllers.SearchController shc = new backend.controllers.SearchController();
    UserController uc = new UserController();
    SongsController sc = new SongsController();
    ArtistsController ac = new ArtistsController();
    SangByController sbc = new SangByController();
    AlbumsController alc = new AlbumsController();
    ContainsController cc = new ContainsController();

    List<Integer> usersIds = new ArrayList<>();
    List<User> users = new ArrayList<>();
    List<FriendModel> friendModels = new ArrayList<>();
    @FXML TableView<FriendModel> users_usersTable;
    @FXML TableColumn<FriendModel, String> users_nameCol;


    List<Integer> songsIds = new ArrayList<>();
    List<Song> songs = new ArrayList<>();
    List<SongModel> songModels = new ArrayList<>();
    @FXML TableView<SongModel> songs_songsTable;
    @FXML TableColumn<SongModel, String> songs_titleCol;
    @FXML TableColumn<SongModel, String> songs_artistCol;
    @FXML TableColumn<SongModel, String> songs_albumCol;


    List<Integer> artistsIds = new ArrayList<>();
    List<User> artists = new ArrayList<>();
    List<FriendModel> artistsModels = new ArrayList<>();
    @FXML TableView<FriendModel> artists_artistsTable;
    @FXML TableColumn<FriendModel, String> artists_nameCol;


    List<Integer> artistSongsIds = new ArrayList<>();
    List<User> artistSongs = new ArrayList<>();
    List<AlbumModel> artistSongModel = new ArrayList<>();
    @FXML TableView<AlbumModel> artists_albumsTable;
    @FXML TableColumn<AlbumModel, String> artists_albumCol;
    @FXML TableColumn<AlbumModel, String> artists_songCol;

    List<Integer> albumsIds = new ArrayList<>();
    List<Album> albums = new ArrayList<>();
    List<AlbumModel> albumsModel = new ArrayList<>();
    List<Integer> albumSongsIds = new ArrayList<>();
    List<Song> albumSongs = new ArrayList<>();
    List<AlbumModel> albumSongsModel = new ArrayList<>();

    @FXML TableView<AlbumModel> albums_albumsTable;
    @FXML TableView<AlbumModel> albums_songsTable;
    @FXML TableColumn<AlbumModel, String> albums_titleCol;
    @FXML TableColumn<AlbumModel, String> albums_artistCol;
    @FXML TableColumn<AlbumModel, String> albums_songsCol;

    public void initialize() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                searchBar.requestFocus();
            }
        });

        searchBar.setOnKeyReleased(keyEvent ->
        {
            querryStr = searchBar.getText();
            if(keyEvent.getCode().equals(KeyCode.ENTER) || querryStr.length() > 2) refreshResults(querryStr);
        });

        usernameLabel.setText(UserInfo.getfName() + " " + UserInfo.getlName());

        users_nameCol.setCellValueFactory(new PropertyValueFactory<FriendModel, String>("name"));

        songs_titleCol.setCellValueFactory(new PropertyValueFactory<SongModel, String>("title"));
        songs_artistCol.setCellValueFactory(new PropertyValueFactory<SongModel, String>("artist"));
        songs_albumCol.setCellValueFactory(new PropertyValueFactory<SongModel, String>("album"));

        artists_nameCol.setCellValueFactory(new PropertyValueFactory<FriendModel, String>("name"));

        artists_albumCol.setCellValueFactory(new PropertyValueFactory<AlbumModel, String>("name"));
        artists_songCol.setCellValueFactory(new PropertyValueFactory<AlbumModel, String>("song"));

        albums_titleCol.setCellValueFactory(new PropertyValueFactory<AlbumModel, String>("name"));
        albums_artistCol.setCellValueFactory(new PropertyValueFactory<AlbumModel, String>("song"));
        albums_songsCol.setCellValueFactory(new PropertyValueFactory<AlbumModel, String>("song"));
    }

    public void refreshResults(String q){
        refreshUsers(q);
        refreshSongs(q);
        refreshArtists(q);
        refreshAlbums(q);
    }

    public void refreshUsers(String q){
        users.clear();
        usersIds.clear();
        friendModels.clear();
        users_usersTable.getItems().clear();
        users_usersTable.refresh();
        try {
            usersIds = shc.searchUsers(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < usersIds.size(); i++){
            try {
                User usr = uc.findById(usersIds.get(i));
               // users.add(usr);
                friendModels.add(new FriendModel(usr.getFirstName() + " " + usr.getLastName(), usr.getId()));

            } catch (SQLException e) {
                e.printStackTrace();
            }
            //friendModels.add(new FriendModel(users.get(i).getFirstName() + " " + users.get(i).getLastName(), users.get(i).getId()));

        }
        users_usersTable.setItems(FXCollections.observableArrayList(friendModels));
    }

    public void refreshSongs(String q) {
        songs.clear();
        songsIds.clear();
        songModels.clear();
        songs_songsTable.getItems().clear();
        songs_songsTable.refresh();
        try {
            songsIds = shc.searchSongs(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < songsIds.size(); i++){
            Song sng = sc.getSongById(songsIds.get(i));

            songModels.add(new SongModel(sng.getTitle(), sng.getArtistName(), sng.getAlbumName()));

        }
        songs_songsTable.setItems(FXCollections.observableArrayList(songModels));
    }

    public void refreshArtists(String q) {
        artists.clear();
        artistsIds.clear();
        artistsModels.clear();
        artists_artistsTable.getItems().clear();
        artists_artistsTable.refresh();
        try {
            artistsIds = shc.searchArtist(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < artistsIds.size(); i++){
            Artist artist = ac.getArtistById(artistsIds.get(i));

            artistsModels.add(new FriendModel(artist.getName(), artist.getId()));

        }
        artists_artistsTable.setItems(FXCollections.observableArrayList(artistsModels));
        artists_artistsTable.setRowFactory(tv -> {
            TableRow<FriendModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    FriendModel rowData = row.getItem();
                    System.out.println("Double click on: " + rowData.getName());

                    artistSongs.clear();
                    artistSongsIds.clear();
                    artistSongModel.clear();
                    artists_albumsTable.getItems().clear();
                    artists_albumsTable.refresh();
                    artistSongsIds = sbc.getSongsByArtist(rowData.getId());
                    System.out.println(artistSongsIds.toString());
                    for (int i = 0; i < artistSongsIds.size(); i++){
                        Song sng = sc.getSongById(artistSongsIds.get(i));
                        artistSongModel.add(new AlbumModel(sng.getAlbumName(), sng.getTitle(), sng.getAlbumId()));
                    }
                    artists_albumsTable.setItems(FXCollections.observableArrayList(artistSongModel));
                }
            });
            return row ;
        });
    }

    public void refreshAlbums(String q) {
        albums.clear();
        albumsIds.clear();
        albumsModel.clear();
        albums_albumsTable.getItems().clear();
        albums_albumsTable.refresh();
        try {
            albumsIds = shc.searchAlbums(q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < albumsIds.size(); i++){
            Album album = alc.getAlbumById(albumsIds.get(i));
            albums.add(album);
            System.out.println(album.getArtistName());
            albumsModel.add(new AlbumModel(album.getName(), album.getArtistName(), album.getId()));

        }
        albums_albumsTable.setItems(FXCollections.observableArrayList(albumsModel));

        albums_albumsTable.setRowFactory(tv -> {
            TableRow<AlbumModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    AlbumModel rowData = row.getItem();
                    System.out.println("Double click on: " + rowData.getName());

                    albumSongs.clear();
                    albumSongsIds.clear();
                    albumSongsModel.clear();
                    albums_songsTable.getItems().clear();
                    albums_songsTable.refresh();
                    albumSongsIds = cc.getAlbumSongs(albums.get(row.getIndex()).getId());

                    for (int i = 0; i < albumSongsIds.size(); i++){
                        Song sng = sc.getSongById(albumSongsIds.get(i));
                        albumSongsModel.add(new AlbumModel(sng.getAlbumName(), sng.getTitle(), sng.getId()));
                    }
                    albums_songsTable.setItems(FXCollections.observableArrayList(albumSongsModel));
                }
            });
            return row ;
        });
    }


    @FXML void goToRecommended(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/recommended.fxml"));
            Stage stage = (Stage) searchBar.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    @FXML void goToWorld(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/world.fxml"));
            Stage stage = (Stage) searchBar.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    @FXML void goToHome(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/main.fxml"));
            Stage stage = (Stage) searchBar.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    @FXML void goToFriends(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/friends.fxml"));
            Stage stage = (Stage) searchBar.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    @FXML void logOut(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/login.fxml"));
            Stage stage = (Stage) searchBar.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
        }catch (IOException io){
            io.printStackTrace();
        }
        UserInfo.setlName("");
        UserInfo.setId(0);
        UserInfo.setfName("");
    }
}

