package ru.tinkoff.rentall.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "rent_history")
@Getter @Setter
public class RentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rentId;
    @OneToOne @JoinColumn(name = "adv_id", referencedColumnName = "adv_id")
    private Advertisement advertisement;
    @OneToOne @JoinColumn(name = "lessee_login", referencedColumnName = "login")
    private User user;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
