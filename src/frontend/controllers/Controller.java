package frontend.controllers;

import backend.controllers.*;
import backend.model.Song;
import backend.model.User;
import frontend.models.SongModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.jws.soap.SOAPBinding;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller{
    List<Integer> topSongsId = new ArrayList<>();
    List<Song> topSongs = new ArrayList<>();
    List<SongModel> topSongModels = new ArrayList<>();

    @FXML TableView<SongModel> topSongsTable;
    @FXML TableColumn<SongModel, String> titleCol;
    @FXML TableColumn<SongModel, String> artistCol;
    @FXML TableColumn<SongModel, String> albumCol;

    @FXML Label usernameLabel;

    /* Database controllers */
    UserController uc = new UserController();
    ListenedController lc = new ListenedController();
    ContainsController coc = new ContainsController();
    CategorizedController cac = new CategorizedController();
    SangByController sbc = new SangByController();
    SongsController sc = new SongsController();
    ArtistsController ac = new ArtistsController();

    @FXML TextField searchBar;

    @FXML
    public void initialize() {
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


        usernameLabel.setText(UserInfo.getfName() + " " + UserInfo.getlName());

        titleCol.setCellValueFactory(new PropertyValueFactory<SongModel, String>("title"));
        artistCol.setCellValueFactory(new PropertyValueFactory<SongModel, String>("artist"));
        albumCol.setCellValueFactory(new PropertyValueFactory<SongModel, String>("album"));

        System.out.println("Getting data");
        topSongsId = lc.topListened(500, 10);
        for (int i = 0; i < topSongsId.size(); i++){
            topSongs.add(sc.getSongById(topSongsId.get(i)));
            topSongModels.add(new SongModel(topSongs.get(i).getTitle(), topSongs.get(i).getArtistName(), topSongs.get(i).getAlbumName()));
        }
        topSongsTable.setItems(FXCollections.observableArrayList(topSongModels));
        topSongsTable.setRowFactory(tv -> {
            TableRow<SongModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    SongModel rowData = row.getItem();
                    System.out.println("Double click on: "+rowData.getTitle());
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
