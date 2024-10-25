package com.example.sundevilslibrary;

import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainApplication extends Application {
//    @Override
    private final IntegerProperty loggedInAs = new SimpleIntegerProperty(-1);
    public void start(Stage stage) throws IOException {
        // load data from text files to initialize buyers. sellers, and admins
        URL url = getClass().getResource("/users/Buyers.txt");
        URL urlSellers = getClass().getResource("/users/Sellers.txt");
        URL urlAdmins = getClass().getResource("/users/Admins.txt");
        List<Buyer> buyers = LoadBuyers.readBuyersFromFile(url.getPath());
        List<Seller> sellers = LoadSellers.readSellersFromFile(urlSellers.getPath());
        List<Admin> admins = LoadAdmins.readAdminsFromFile(urlAdmins.getPath());

        // set up scene
        StackPane root = new StackPane();
        LoginPane loginPane = new LoginPane(LoadBuyers.getBuyers(), LoadAdmins.getAdmins(), LoadSellers.getSellers());
        root.getChildren().add(loginPane);
        root.setStyle("-fx-background-color: white;");
        Scene scene = new Scene(root, 800, 800);
        scene.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());

        stage.setTitle("Login");
        stage.setScene(scene);
        // binds loggedInAs variable to loginPane's variable, allows main to detect when to switch scenes
        loggedInAs.bind(loginPane.getLoggedInAs());
        // listener event awaits change in loggedInAs variable, switches scenes accordingly
        loggedInAs.addListener((observable, oldValue, newValue) -> {
            System.out.println(oldValue);
            System.out.println(newValue);
            if (newValue.intValue() == 1) { // Switch to BuyerPage when logged in
                BuyerPage buyerPage = null;
                try {
                    URL urlBooks = getClass().getResource("/Books/Books.txt");
                    List<Book> books = LoadBooks.readBooksFromFile(urlBooks.getPath());

                    buyerPage = new BuyerPage(LoadBooks.getBooks());


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                StackPane newRoot = new StackPane();
                newRoot.setStyle("-fx-background-color: white;");
                newRoot.getChildren().add(buyerPage);
                Scene buyerScene = new Scene(newRoot, 800, 800);
                buyerScene.getStylesheets().add(getClass().getResource("/styles/buyers.css").toExternalForm());
                stage.setTitle("Buyer Search");
                stage.setScene(buyerScene);
            } else if (newValue.intValue() ==2){
                SellerPage sellerPage = new SellerPage();
                StackPane newRoot = new StackPane();
                newRoot.setStyle("-fx-background-color: white;");
                newRoot.getChildren().add(sellerPage);
                Scene sellerScene = new Scene(newRoot, 800, 800);
                stage.setTitle("Seller Page");
                stage.setScene(sellerScene);
            }else if (newValue.intValue() == 3){
                AdminPage adminPage = new AdminPage();
                StackPane newRoot = new StackPane();
                newRoot.setStyle("-fx-background-color: white;");
                newRoot.getChildren().add(adminPage);
                Scene adminScene = new Scene(newRoot, 800, 800);
                stage.setTitle("Admin Page");
                stage.setScene(adminScene);
            }
        });

        stage.show();



    }

    public static void main(String[] args) {
        launch(args);
    }
}