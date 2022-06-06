package arani.abdollahzade.mohammad.backend.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    String ruleName;
    String market;
    String currentValue;
    String date;
}
