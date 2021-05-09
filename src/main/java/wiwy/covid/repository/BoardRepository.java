package wiwy.covid.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public void save(Board board) {
        em.persist(board);
    }

    public Board findById(Long boardId) {
        return em.find(Board.class, boardId);
    }

    public List<Board> findByMemberId(Long memberId) {
        return em.createQuery("select b from Board b join b.member m where m.id = :memberId", Board.class)
                .setParameter("memberId",memberId)
                .getResultList();
    }

    public List<Board> findByName(String boardName) {
        return em.createQuery("select b from Board b where b.boardName = :boardName", Board.class)
                .setParameter("boardName", boardName)
                .getResultList();
    }

    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }
}
