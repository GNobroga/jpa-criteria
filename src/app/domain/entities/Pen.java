package app.domain.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import app.domain.entities.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pen extends BaseEntity {

    private String name;

    private String color;

    @JoinColumn(name = "client_id")
    @ManyToOne
    private Client client;

    @Override 
    public String toString() {
        return name;
    }
}
