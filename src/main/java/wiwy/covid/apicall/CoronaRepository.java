package wiwy.covid.apicall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import wiwy.covid.apicall.coronadto.CoronaDto;


import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Repository
public class CoronaRepository {

    private final EntityManager em;

    @Transactional
    public void save(List<CoronaDto> items) {
        for (CoronaDto item : items) {
            em.persist(item);
            log.info("CoronaDto saved, gubun = {}", item.getGubun());
        }
    }
}
