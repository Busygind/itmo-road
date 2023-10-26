package com.busygind.lab3.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hits")
public class Hit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double x;
    private Double y;
    private Double r;
    private boolean success;
    @Column(name = "time")
    private Date timestamp;
    @Column(name = "session_id")
    private String sessionId;

    public Hit(Double x, Double y, Double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
}
