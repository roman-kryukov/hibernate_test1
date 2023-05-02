package org.example.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.dao.CarDao;
import org.example.dao.PersonDao;
import org.example.model.Car;
import org.example.model.Person;
import org.hibernate.Hibernate;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringJoiner;

public class MainController implements Initializable {

    @FXML
    private TableView<Person> table;

    @FXML
    private TableColumn<Person, String> firstName;

    @FXML
    private TableColumn<Person, String> lastName;

    private final PersonDao personDao;
    private final CarDao carDao;

    public MainController(PersonDao personDao, CarDao carDao) {
        this.personDao = personDao;
        this.carDao = carDao;
    }

    public void initialize(URL location, ResourceBundle resources) {
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        // Назначение элементов через DAO
        table.setItems(FXCollections.observableArrayList(personDao.findAll()));
    }


    @FXML
    void tableOnClicked(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            VBox vBox = new VBox();
            vBox.setSpacing(10);
            vBox.setPadding(new Insets(20));

            Person person = table.getSelectionModel().getSelectedItem();

            vBox.getChildren().add(new Label(
                    new StringJoiner(" ")
                            .add("Машины человека:")
                            .add(person.getFirstName())
                            .add(person.getLastName())
                            .toString()
            ));

            // Получение списка машин человека через CarDao нужно для того
            // чтобы проинициализировать этот список, ведь используется ленивая загрузка
            for (Car car : carDao.getCarsByPerson(person)) {
                Label label = new Label(car.getName());
                label.setStyle("-fx-font-size: 20;");
                vBox.getChildren().add(label);
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(vBox));
            stage.show();
        }
    }

}
