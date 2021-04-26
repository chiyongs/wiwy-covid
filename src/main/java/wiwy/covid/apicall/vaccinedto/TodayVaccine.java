package wiwy.covid.apicall.vaccinedto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
public class TodayVaccine extends Vaccine {

}
