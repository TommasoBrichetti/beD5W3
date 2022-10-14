package models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Book extends Item {

    private String author;

    @Enumerated(EnumType.STRING)
    private Genres genre;

}