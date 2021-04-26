package wiwy.covid.apicall.vaccinedto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter @Setter
public class CumulativeVaccine extends Vaccine{
}
