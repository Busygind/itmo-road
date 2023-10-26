package com.busygind.lab3.beans;

import com.busygind.lab3.entities.Hit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@ManagedBean(name = "hitsContainer")
@SessionScoped
@Getter
@Setter
public class SessionsHitsContainer implements Serializable {

    @ManagedProperty(value = "#{serviceBean}")
    private Service service;
    private final String sessionId;
    private final List<Hit> curSessionDots = new LinkedList<>();

    public SessionsHitsContainer() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        this.sessionId = session.getId();
//        this.curSessionDots = new LinkedList<>();
    }

    public void removeUserHits() {
        curSessionDots.clear();
        service.getDatabaseController().deleteHitsBySessionId(sessionId);
    }
}
