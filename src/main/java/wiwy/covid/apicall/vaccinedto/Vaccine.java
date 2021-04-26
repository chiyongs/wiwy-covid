package wiwy.covid.apicall.vaccinedto;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Vaccine {

    @Id @GeneratedValue
    @Column(name = "vaccine_id")
    private Long id;

    private String tpcd;
    private Integer firstCnt;
    private Integer secondCnt;
}
