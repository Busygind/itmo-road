package com.busygind.lab3.beans;

import lombok.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@ManagedBean(name = "hitBean")
@RequestScoped
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HitBean implements Serializable {
    private Double x;
    private Double y;
    private Double r;
    private Instant timezone;
    
    @ManagedProperty(name = "service", value = "#{serviceBean}")
    private Service service = new Service();
    @ManagedProperty(value = "#{hitsContainer}")
    private SessionsHitsContainer container = new SessionsHitsContainer();

    public void processRequest() {
        System.out.println(this);
        this.timezone = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        service.addNewDot(this);
    }
}
