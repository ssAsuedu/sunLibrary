package com.example.sundevilslibrary;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BuyerPage extends HBox{ // extends Parent


    Label TF = new Label("Search the Library");
    Font font = Font.font("Helvetica", FontWeight.BOLD, FontPosture.REGULAR, 36);

//    URL url = getClass().getResource("/Books/Books.txt");
    public BuyerPage(ArrayList<Book> books) throws IOException{
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20));
        TF.setFont(font);
        TF.getStyleClass().add("title");
        vbox.getChildren().add(TF);

        for (Book book: books){
            HBox bookItem = new HBox(40);
            VBox leftPane = new VBox(20);
            VBox rightPane = new VBox(20);

            Label title = new Label(book.getTitle());
            Label condition = new Label(book.getCondition());
            Label category = new Label(book.getCategory());
            Label price = new Label("$" + book.getPrice().toString());

            leftPane.getChildren().addAll(title, condition, category);
            Button addToCart = new Button("Add to Cart");
            addToCart.getStyleClass().add("addToCart");
            Checkbox checkbox = new Checkbox();
            rightPane.getChildren().addAll(price, addToCart);

            bookItem.getChildren().addAll(leftPane, rightPane);
            bookItem.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            bookItem.setPadding(new Insets(10,10,10,10));

            bookItem.getStyleClass().add("bookCard");
            vbox.getChildren().add(bookItem);


        }

        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(vbox);




    }





}
