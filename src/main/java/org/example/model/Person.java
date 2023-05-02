package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // mappedBy = "person": Значение "person" - это название ПОЛЯ в КЛАССЕ Car, не в базе данных.
    // fetch = FetchType.LAZY писать необязательно, потому что оно задано по умолчанию,
    // позволяет инициализировать список только по обращению к нему.
    // fetch = FetchType.EAGER инициализирует список сразу
    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private List<Car> cars;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

}
