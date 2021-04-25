package wiwy.covid.apicall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import wiwy.covid.apicall.dismsgdto.DisMsg;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class DisMsgRepository {

    private final EntityManager em;

    public void save(List<DisMsg> rows) {
        for (DisMsg row : rows) {
            em.persist(row);
            log.info("DisMsg saved, create_date = {}", row.getCreate_date());
        }
    }

//    public
}
