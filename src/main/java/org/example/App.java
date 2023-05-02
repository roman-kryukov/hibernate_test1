package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.MainController;
import org.example.dao.PersonDao;
import org.example.model.Car;
import org.example.model.Person;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App extends Application
{
    Configuration configuration = new Configuration()
            .addAnnotatedClass(Person.class)
            .addAnnotatedClass(Car.class); // Добавление сущностей

    SessionFactory sessionFactory = configuration.buildSessionFactory();
    
    PersonDao personDao = new PersonDao(sessionFactory);

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/main.fxml"));
        // Назначание контроллера через код, чтобы можно было внедрить 
        // sessionFactory и personDao (DAO - data access object, объект, который работает исключительно с базой данных, не затрагивая бизнес-логику).
        // Я сознательно не делаю sessionFactory и personDao статичными, чтобы к ним нельзя было обращаться
        // через Main.sessionFactory и Main.personDao, потому что я считаю этот подход идиотским, так как
        // контроллер будет зависеть от Main класса. При внедрении sessionFactory контроллер ни от
        // кого не зависит.
        loader.setController(new MainController(sessionFactory, personDao));
        Parent parent = loader.load();
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
}
