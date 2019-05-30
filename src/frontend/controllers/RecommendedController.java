package frontend.controllers;

import backend.controllers.*;
import backend.model.Song;
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

public class RecommendedController {
    List<Integer> recommendedSongsId = new ArrayList<>();
    List<Song> recommendedSongs = new ArrayList<>();
    List<SongModel> recommendedSongModels = new ArrayList<>();

    @FXML TableView<SongModel> recommendedSongsTable;
    @FXML TableColumn<SongModel, String> titleCol;
    @FXML TableColumn<SongModel, String> artistCol;
    @FXML TableColumn<SongModel, String> albumCol;

    @FXML Label usernameLabel;

    @FXML TextField searchBar;

    @FXML Button goHomeButton;
    @FXML Button goToFriendsButton;

    /* Database controllers */
    UserController uc = new UserController();
    ListenedController lc = new ListenedController();
    ContainsController coc = new ContainsController();
    CategorizedController cac = new CategorizedController();
    SangByController sbc = new SangByController();
    SongsController sc = new SongsController();
    ArtistsController ac = new ArtistsController();

    @FXML
    public void initialize() {
        usernameLabel.setText(UserInfo.getfName() + " " + UserInfo.getlName());

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

        titleCol.setCellValueFactory(new PropertyValueFactory<SongModel, String>("title"));
        artistCol.setCellValueFactory(new PropertyValueFactory<SongModel, String>("artist"));
        albumCol.setCellValueFactory(new PropertyValueFactory<SongModel, String>("album"));

        System.out.println("Getting data");
        try {
            recommendedSongsId = uc.recommend(UserInfo.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < recommendedSongsId.size(); i++){
            recommendedSongs.add(sc.getSongById(recommendedSongsId.get(i)));
            recommendedSongModels.add(new SongModel(recommendedSongs.get(i).getTitle(), recommendedSongs.get(i).getArtistName(), recommendedSongs.get(i).getAlbumName()));
        }
        recommendedSongsTable.setItems(FXCollections.observableArrayList(recommendedSongModels));
        recommendedSongsTable.setRowFactory(tv -> {
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
