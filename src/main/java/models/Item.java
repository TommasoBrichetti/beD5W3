package models;

import lombok.*;
import models.DAO.ItemsDAO;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "isbn", nullable = false)
    private Long isbn;
    private String title;
    private int year;
    private int pageNumber;
    private Availability availability= Availability.DISPONIBILE;
    @OneToMany(mappedBy = "item")
    private List<Loan> loaned;
}