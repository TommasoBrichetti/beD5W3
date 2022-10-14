package models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "data_prestito")
    private LocalDate dataPrestito = LocalDate.now();;

    @Column(name = "termine_prestito")
    private LocalDate terminePrestito = LocalDate.now().plusMonths(1);

    @ManyToOne
    @JoinColumn(name = "numero_tessera")
    private User user;

    @ManyToOne
    @JoinColumn(name = "isbn")
    private Item item;
}
