package models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQuery;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@NamedQuery(name = "libroByAuthor", query = "select l from Book l where upper(l.author) like upper(concat('%',:author, '%'))")
public class Book extends Item {

    private String author;

    @Enumerated(EnumType.STRING)
    private Genres genre;

}