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

public class WorldController {
    List<Integer> topSongsId = new ArrayList<>();
    List<Song> topSongs = new ArrayList<>();
    List<SongModel> topSongModels = new ArrayList<>();

    private int pageNumber = 1;

    @FXML TableView<SongModel> topSongsTable;
    @FXML TableColumn<SongModel, String> titleCol;
    @FXML TableColumn<SongModel, String> artistCol;
    @FXML TableColumn<SongModel, String> albumCol;

    @FXML Label usernameLabel;
    @FXML Label pageNumberLabel;
    /* Database controllers */
    ListenedController lc = new ListenedController();
    SongsController sc = new SongsController();

    @FXML TextField searchBar;
    @FXML Button prevButton;

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
        refresh();
    }

    @FXML void nextPage(){
        if(pageNumber*10 >= 10000) return;
        pageNumber++;
        refresh();
    }

    @FXML void previousPage(){
        if(pageNumber <= 1) return;
        pageNumber--;
        refresh();
    }

    public void refresh(){
        if(pageNumber == 1){
            prevButton.setDisable(true);
        } else {
            prevButton.setDisable(false);
        }
        pageNumberLabel.setText(Integer.toString(pageNumber));
        topSongs.clear();
        topSongsId.clear();
        topSongModels.clear();
        topSongsTable.getItems().clear();
        topSongsTable.refresh();
        try {
            topSongsId = sc.mostListened(pageNumber, 10);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                System.out.println("Double click on: " + rowData.getTitle());
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
