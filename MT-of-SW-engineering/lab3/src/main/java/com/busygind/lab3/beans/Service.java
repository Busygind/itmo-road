package com.busygind.lab3.beans;

import com.busygind.lab3.database.DatabaseController;
import com.busygind.lab3.entities.Hit;
import com.busygind.lab3.utilities.AreaChecker;
import com.busygind.lab3.utilities.HitValidator;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.Date;

@ManagedBean(name = "serviceBean")
@ApplicationScoped
@Getter
@Setter
public class Service {

    private final AreaChecker areaChecker;
    private final DatabaseController databaseController;
    private final HitValidator hitValidator;

    public Service() {
        this.areaChecker = new AreaChecker();
        this.databaseController = new DatabaseController();
        this.hitValidator = new HitValidator();
    }

    public void addNewDot(HitBean hitBean) {
        if (hitValidator.hitIsValid(hitBean)) {
            Hit hit = new Hit();
            hit.setX(hitBean.getX());
            hit.setY(hitBean.getY());
            hit.setR(hitBean.getR());
            hit.setTimestamp(Date.from(hitBean.getTimezone()));
            hit.setSuccess(areaChecker.checkHit(hit));
            hit.setSessionId(hitBean.getContainer().getSessionId());
            hitBean.getContainer().getCurSessionDots().add(hit);
            databaseController.uploadHitToDatabase(hit);
        }
    }
}
