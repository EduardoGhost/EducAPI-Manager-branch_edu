package br.ufpb.dcx.apps4society.educapimanager.model.bean;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

import br.ufpb.dcx.apps4society.educapimanager.model.dto.UserDTO;

public class Session implements Serializable {

    private UserDTO creator;

    public Session(UserDTO creator) {
        this.creator = creator;
    }

    public Session() {
    }

    public UserDTO getCreator() {
        return creator;
    }

    public void setCreator(UserDTO creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Session{" +
                "creator=" + creator +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return creator.equals(session.creator);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
