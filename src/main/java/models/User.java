package models;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numero_tessera", nullable = false)
    private Long numeroTessera;

    private String name;
    private String cognome;
    @Column(name = "birth_date")
    private String birthDate;
    @OneToMany(mappedBy = "user")
    private List<Loan> list;
}
