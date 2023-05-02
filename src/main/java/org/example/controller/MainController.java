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
import org.example.dao.PersonDao;
import org.example.model.Car;
import org.example.model.Person;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

    private final SessionFactory sessionFactory;
    private final PersonDao personDao;

    public MainController(SessionFactory sessionFactory, PersonDao personDao) {
        this.sessionFactory = sessionFactory;

        this.personDao = personDao;
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

            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction(); // Если у связного один ко многим поля fetch = FetchType.LAZY,
            // тогда нужно открыть тразнкцию для инициализации списка машин

            // Чтобы список инициализировать сразу, не открывая транзанцию, при получении людей,
            // можно использовать fetch = FetchType.EAGER, но не рекомендуется

            // session.merge(Object object) - привязка объекта Person к сессии, чтобы
            // он стал persistent, иначе список машин невозможно будет инициализировать
            Person person = (Person) session.merge(table.getSelectionModel().getSelectedItem());

            vBox.getChildren().add(new Label(
                    new StringJoiner(" ")
                            .add("Машины человека:")
                            .add(person.getFirstName())
                            .add(person.getLastName())
                            .toString()
            ));


            for (Car car : person.getCars()) {
                Label label = new Label(car.getName());
                label.setStyle("-fx-font-size: 20;");
                vBox.getChildren().add(label);
            }

            session.getTransaction().commit();

            Stage stage = new Stage();
            stage.setScene(new Scene(vBox));
            stage.show();
        }
    }

}
