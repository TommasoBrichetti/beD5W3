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
@NamedQueries({
        @NamedQuery(name = "findByTitle", query = "select c from Item c where" +
                " upper(c.title) like upper(concat('%', :title, '%'))"),
        @NamedQuery(name = "findByYear", query = "select c from Item c where c.year = :anno")
})
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
    @ToString.Exclude
    private List<Loan> loaned;
}