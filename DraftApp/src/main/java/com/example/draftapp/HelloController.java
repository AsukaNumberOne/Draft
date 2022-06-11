package com.example.draftapp;


import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addNewFolder;

    @FXML
    private Button saveFolder;

    @FXML
    private Button deleteFolder;

    @FXML
    private Button englishLanguage;

    @FXML
    private Text name;

    @FXML
    private TextField nameFolder;


    @FXML
    private Button russianLanguage;


    @FXML
    private TextArea textArea;

    @FXML
    private Button upgradeButton;

    static ObservableList<String> rowList = FXCollections.observableArrayList();
    @FXML
    private static ListView<String> allFolders = new ListView<>(rowList);


    private static final String PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\txt-folders";

    public static void getAllPath(String path) {
        File dir = new File(path);
        if (dir.listFiles() != null) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                rowList.add(file.getName());
            }
            allFolders.setItems(rowList);
        }
    }

    public boolean checkNameFolder(String e) {
        String s = "";
        File dir = new File(PATH);
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isFile() && file.getName().equals(e))
                s = file.getName();
        }
        return s.equals(e);
    }


    @FXML
    void initialize() {
        //===================================================
        upgradeButton.setOnAction(actionEvent -> {
            getAllPath(PATH);
        });
        //===================================================
        Alert alert = new Alert(Alert.AlertType.INFORMATION);//---
        alert.setTitle("Ошибка в названии");                 //--- Вывод информации
        alert.setHeaderText(null);                           //---
        alert.setContentText("Вы не ввели название  файла"); //---
        //===================================================
        Alert wrongSave = new Alert(Alert.AlertType.INFORMATION);
        wrongSave.setTitle("Ошибка в названии");
        wrongSave.setHeaderText(null);
        wrongSave.setContentText("Вы не можете сохранить не существующую папку");
        //===================================================
        addNewFolder.setOnAction(actionEvent -> {
            if (!nameFolder.getText().equals("")) {

                String s = nameFolder.getText() + ".txt";

                //--------------------------------------
                if (checkNameFolder(s)) {
                    Path reader = Path.of(PATH + "\\" + nameFolder.getText() + ".txt");
                    try {
                        List<String> list = Files.readAllLines(reader);
                        StringBuilder allLines = new StringBuilder();
                        for (String str : list) {
                            allLines.append(str).append("\n");
                        }
                        textArea.setText(allLines.toString());

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }


                } else {

                    try {
                        FileWriter myFile = new FileWriter(PATH + "\\" + nameFolder.getText() + ".txt");
                        myFile.write(textArea.getText().trim());
                        myFile.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    //rowList.clear();
                    rowList.add(nameFolder.getText());
                    allFolders.setItems(rowList);

                }
            } else {
                alert.showAndWait();
                System.out.println("Пользователь не ввёл название!");
            }

        });
        saveFolder.setOnAction(actionEvent1 -> {
            String s = nameFolder.getText() + ".txt";

            if (!nameFolder.getText().equals("")) {
                if (checkNameFolder(s)) {
                    try {
                        FileWriter writer = new FileWriter(PATH + "\\" + nameFolder.getText() + ".txt");
                        writer.write(textArea.getText().trim());
                        writer.close();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    wrongSave.showAndWait();
                    System.out.println("Пользователь ввёл не существующее название !");
                }
            } else {
                alert.showAndWait();
                System.out.println("Пользователь не ввёл название!");
            }
        });
        deleteFolder.setOnAction(actionEvent -> {
            String str = nameFolder.getText();
            if (!str.contains(".txt")) {
                str += ".txt";
            }
            try {
                Files.delete(Paths.get(PATH + "\\" + str));
            } catch (IOException e) {
                e.printStackTrace();
            }
            textArea.setText("");
            nameFolder.setText("");
        });
        russianLanguage.setOnAction(actionEvent -> {
            alert.setTitle("Ошибка в названии");
            alert.setHeaderText(null);
            alert.setContentText("Вы не ввели название файла");
            //--------------------------------
            name.setText("Название");
            addNewFolder.setText("Добавить");
            saveFolder.setText("Сохранить");
            deleteFolder.setText("Удалить");
        });
        englishLanguage.setOnAction(actionEvent -> {
            alert.setTitle("Name error");
            alert.setHeaderText(null);
            alert.setContentText("You did not enter a file name");
            //--------------------------------
            name.setText("Name");
            addNewFolder.setText("Add");
            saveFolder.setText("Save");
            deleteFolder.setText("Delete");
        });
    }

}