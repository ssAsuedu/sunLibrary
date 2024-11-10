package com.example.sundevilslibrary;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.io.IOException;
import java.net.URL;
import java.net.URL;
/*public class SellerPage extends HBox{ // extends Parent

    Label TF = new Label("Seller Page");
    public SellerPage(){
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        vbox.getChildren().add(TF);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(vbox);

    }
}*/


import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;


public class SellerPage extends BorderPane {
    private ComboBox<String> categoryComboBox;
    private ComboBox<String> conditionComboBox;
    private TextField originalPriceTextField;
    private Label receiptLabel;

    public SellerPage() {
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20));

        // Store logo and name
        URL url = getClass().getResource("/images/library_logo.jpg");
        Image storeLogoImage = new Image(url.toString());
        ImageView storeLogo = new ImageView(storeLogoImage);
        storeLogo.setFitWidth(175);
        storeLogo.setFitHeight(175);
        storeLogo.setPreserveRatio(true);

        Circle circleClip = new Circle(storeLogo.getFitWidth() / 2);
        circleClip.setCenterX(storeLogo.getFitWidth() / 2);
        circleClip.setCenterY(storeLogo.getFitHeight() / 2);
        storeLogo.setClip(circleClip);

        Label message = new Label("Please select the category & condition of the book you wish to sell:");
        message.setStyle("-fx-font-size: 12pt;");

        // Dropdown for book category
        categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Fiction", "Non-fiction", "Textbook", "Science", "Biography", "Mystery", "Romance", "Thriller", "History");
        categoryComboBox.setStyle("-fx-font-size: 12pt;");
        categoryComboBox.setPromptText("Select Book Category");

        // Dropdown for book condition
        conditionComboBox = new ComboBox<>();
        conditionComboBox.getItems().addAll("Like New", "Good", "Moderate", "Poor");
        conditionComboBox.setStyle("-fx-font-size: 12pt;");
        conditionComboBox.setPromptText("Select Book Condition");

        // Textbox for original price input
        originalPriceTextField = new TextField();
        originalPriceTextField.setMaxWidth(200);
        originalPriceTextField.setPromptText("Enter Original Price (e.g., 29.99)");

        // Buttons
        Button logoutButton = new Button("Log Out");
        logoutButton.setStyle("-fx-font-size: 14pt; -fx-padding: 10;");
        logoutButton.setOnAction(e -> {
            Stage primaryStage = (Stage) logoutButton.getScene().getWindow();
            MainApplication mainApp = new MainApplication();
            try {
                mainApp.start(primaryStage);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            // Redirect to login page (implement actual navigation logic here)
            System.out.println("Logged out and redirected to login page");
        });

        Button generatePriceButton = new Button("Generate Offer");
        generatePriceButton.setStyle("-fx-font-size: 12pt;");
        generatePriceButton.setOnAction(e -> showPricePopup());

        // Receipt label
        receiptLabel = new Label();

        // Adding elements to VBox
        vbox.getChildren().addAll(
                storeLogo,
                message,
                categoryComboBox,
                conditionComboBox,
                originalPriceTextField,
                generatePriceButton,
                receiptLabel
        );

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setPadding(new Insets(10));
        topBar.getChildren().add(logoutButton);

        this.setTop(topBar);
        this.setCenter(vbox);
    }

    private void showPricePopup() {
        String category = categoryComboBox.getValue();
        String condition = conditionComboBox.getValue();
        String originalPriceStr = originalPriceTextField.getText();

        if (category == null || condition == null || originalPriceStr.isEmpty()) {
            receiptLabel.setText("All fields must be filled in to generate a buyout price.");
            receiptLabel.setStyle("-fx-font-size: 14pt;");
            return;
        }

        try {
            float originalPrice = Float.parseFloat(originalPriceStr);
            float buyoutPrice = calculateBuyoutPrice(originalPrice, condition);

            // Create a pop-up window
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Buyout Price Offer");

            Label priceLabel = new Label("Offered Buyout Price: $" + String.format("%.2f", buyoutPrice));

            Button acceptButton = new Button("Accept");
            acceptButton.setStyle("-fx-font-size: 14pt;");
            acceptButton.setOnAction(e -> {
                receiptLabel.setText("Book sold successfully! Receipt: Buyout Price - $" + String.format("%.2f", buyoutPrice));
                receiptLabel.setStyle("-fx-font-size: 14pt;");
                storeBookData(category, condition, buyoutPrice);
                popupStage.close();
            });

            Button rejectButton = new Button("Reject");
            rejectButton.setStyle("-fx-font-size: 14pt;");
            rejectButton.setOnAction(e -> {
                receiptLabel.setText("Sale Canceled");
                receiptLabel.setStyle("-fx-font-size: 14pt;");
                popupStage.close();
            });

            HBox buttonBox = new HBox(10, acceptButton, rejectButton);
            buttonBox.setAlignment(Pos.CENTER);

            VBox popupVBox = new VBox(10, priceLabel, buttonBox);
            popupVBox.setAlignment(Pos.CENTER);
            popupVBox.setPadding(new Insets(20));

            Scene popupScene = new Scene(popupVBox, 300, 150);
            popupStage.setScene(popupScene);
            popupStage.showAndWait();

        } catch (NumberFormatException e) {
            receiptLabel.setText("Invalid price format. Please enter a valid float value.");
        }
    }

    private float calculateBuyoutPrice(float originalPrice, String condition) {
        float conditionMultiplier;
        switch (condition) {
            case "Like New":
                conditionMultiplier = 0.8f;
                break;
            case "Good":
                conditionMultiplier = 0.7f;
                break;
            case "Moderate":
                conditionMultiplier = 0.5f;
                break;
            case "Poor":
                conditionMultiplier = 0.3f;
                break;
            default:
                conditionMultiplier = 0.0f;
                break;
        }
        return originalPrice * conditionMultiplier;
    }

    private void storeBookData(String category, String condition, float price) {
        // Store the book data for future reference
    }
}
