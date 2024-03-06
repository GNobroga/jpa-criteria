package app.domain.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import app.domain.entities.abstraction.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "workers")
@DiscriminatorValue("WORKER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Worker extends Person {
    
    private BigDecimal salary;

    public Worker(String name, Integer age, Date birthDate, BigDecimal salary) {
        super(name, age, birthDate);
        this.salary = salary;
    }
}
