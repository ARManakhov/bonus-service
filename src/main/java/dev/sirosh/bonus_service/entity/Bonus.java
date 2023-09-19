package dev.sirosh.bonus_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table()
public class Bonus {
    @Id
    @GeneratedValue
    private Long id;
    @Column(columnDefinition = "integer default 0 check (count >=0)")
    public Integer count = 0;
    @OneToOne
    User user;
}
