package app.domain.entities;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import app.domain.entities.base.BaseEntity;
import app.domain.entities.embeddables.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "companies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Company extends BaseEntity {
    
    private String name;

    @Embedded
    private Address address;
}
