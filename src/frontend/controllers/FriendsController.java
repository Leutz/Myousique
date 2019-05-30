package frontend.controllers;

import backend.controllers.*;
import backend.model.Song;
import backend.model.User;
import frontend.models.FriendModel;
import frontend.models.SongModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FriendsController {
    List<Integer> friendsIds = new ArrayList<>();
    List<User> friends = new ArrayList<>();
    List<FriendModel> friendModels = new ArrayList<>();

    @FXML TableView<FriendModel> friendsTable;
    @FXML TableColumn<FriendModel, String> nameCol;

    List<Integer> topSongsId = new ArrayList<>();
    List<Song> topSongs = new ArrayList<>();
    List<SongModel> topSongModels = new ArrayList<>();

    @FXML TableView<SongModel> friendsSongsTable;
    @FXML TableColumn<SongModel, String> titleCol;
    @FXML TableColumn<SongModel, String> artistCol;
    @FXML TableColumn<SongModel, String> albumCol;

    @FXML TextField searchBar;

    @FXML Label usernameLabel;



    @FXML Button goToRecommendedButton;
    @FXML Button goToMenuButton;

    /* Database controllers */
    SongsController sc = new SongsController();
    backend.controllers.FriendsController fc = new backend.controllers.FriendsController();
    ListenedController lc = new ListenedController();
    backend.controllers.SearchController shc = new backend.controllers.SearchController();
    @FXML
    public void initialize() {
        usernameLabel.setText(UserInfo.getfName() + " " + UserInfo.getlName());
        nameCol.setCellValueFactory(new PropertyValueFactory<FriendModel, String>("name"));

        searchBar.setOnMouseClicked(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/search.fxml"));
                Stage stage = (Stage) searchBar.getScene().getWindow();
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
            } catch (IOException io) {
                io.printStackTrace();
            }
        });
        //friendsIds = shc.searchUsers()



        titleCol.setCellValueFactory(new PropertyValueFactory<SongModel, String>("title"));
        artistCol.setCellValueFactory(new PropertyValueFactory<SongModel, String>("artist"));
        albumCol.setCellValueFactory(new PropertyValueFactory<SongModel, String>("album"));

        try {
            friends = fc.getFriends(UserInfo.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < friends.size(); i++){
            friendModels.add(new FriendModel(friends.get(i).getFirstName() + " " + friends.get(i).getLastName(), friends.get(i).getId()));
        }
        friendsTable.setItems(FXCollections.observableArrayList(friendModels));
        friendsTable.setRowFactory(tv -> {
            TableRow<FriendModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    FriendModel rowData = row.getItem();
                    System.out.println("Double click on: "+rowData.getName());

                    topSongsId = lc.topListened(rowData.getId(),10);
                    friendsSongsTable.getItems().clear();
                    friendsSongsTable.refresh();
                    topSongModels.clear();
                    topSongs.clear();

                    for (int i = 0; i < topSongsId.size(); i++){
                        topSongs.add(sc.getSongById(topSongsId.get(i)));
                        topSongModels.add(new SongModel(topSongs.get(i).getTitle(), topSongs.get(i).getArtistName(), topSongs.get(i).getAlbumName()));
                    }
                    friendsSongsTable.setItems(FXCollections.observableArrayList(topSongModels));
                    friendsSongsTable.setRowFactory(tv2 -> {
                        TableRow<SongModel> row2 = new TableRow<>();
                        row2.setOnMouseClicked(event2 -> {
                            if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                                SongModel rowDatat = row2.getItem();
                                System.out.println("Double click on: " + rowDatat.getTitle());
                            }
                        });
                        return row2 ;
                    });
                }
            });
            return row ;
        });
    }

    @FXML void clear(){
        friendsSongsTable.getItems().clear();
        friendsSongsTable.refresh();
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
