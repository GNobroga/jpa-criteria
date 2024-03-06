package app.domain.entities.abstraction;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import app.domain.entities.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "people")
@DiscriminatorColumn(name = "type")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public abstract class Person extends BaseEntity {
    
    protected String name;

    protected Integer age;

    @Column(name = "birth_date", columnDefinition = "DATE")
    private Date birthDate;
}
