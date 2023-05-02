package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.MainController;
import org.example.dao.CarDao;
import org.example.dao.PersonDao;
import org.example.model.Car;
import org.example.model.Person;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App extends Application
{
    private Configuration configuration = new Configuration()
            .addAnnotatedClass(Person.class)
            .addAnnotatedClass(Car.class); // Добавление сущностей

    private SessionFactory sessionFactory = configuration.buildSessionFactory();
    
    private PersonDao personDao = new PersonDao(sessionFactory);
    private CarDao carDao = new CarDao(sessionFactory);

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
        // Либо можно создать статический класс HibernateUtils (допуспим) 
        // и там создать статические поля sessionFactory и personDao, обращаться к ним
        // в контроллере, но я бы так не делал.
        loader.setController(new MainController(personDao, carDao));
        Parent parent = loader.load();
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
}
