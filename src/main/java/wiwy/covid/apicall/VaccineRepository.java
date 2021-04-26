package wiwy.covid.apicall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.apicall.vaccinedto.Vaccine;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
@Transactional(readOnly = true)
public class VaccineRepository {

    private final EntityManager em;

    @Transactional
    public void save(Vaccine vaccine) {
        em.persist(vaccine);
        log.info("Vaccine saved");
    }

    public List<Vaccine> findAll() {
        return em.createQuery("select v from Vaccine v", Vaccine.class)
                .getResultList();
    }

}
