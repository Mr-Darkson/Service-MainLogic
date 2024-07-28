package me.absolute.businessapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Username not should be empty")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Telephone Number not should be empty")
    @Size(min = 10, max = 10)
    @Column(name = "tel_number")
    private String telephoneNumber;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdDate;


    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Ticket> tickets = new ArrayList<>();


    public Person(String username, String telephoneNumber, LocalDateTime createdDate) {
        this.username = username;
        this.telephoneNumber = telephoneNumber;
        this.createdDate = createdDate;
    }

}
