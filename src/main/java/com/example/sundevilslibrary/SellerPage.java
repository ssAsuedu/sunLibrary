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

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import java.net.URL;
public class SellerPage extends HBox{ // extends Parent

    Label TF = new Label("Seller Page");
    public SellerPage(){
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));

        vbox.getChildren().add(TF);

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(vbox);

    }





}