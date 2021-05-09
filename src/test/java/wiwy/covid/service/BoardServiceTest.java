package wiwy.covid.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.domain.Board;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired BoardService boardService;

    @Test
    void 게시판등록() {
        Board board = new Board();
        board.setBoardName("자유게시판");

        boardService.makeBoard(board);

        Board one = boardService.findOne(board.getId());

        assertThat(one).isEqualTo(board);
        assertThat(one.getBoardName()).isEqualTo(board.getBoardName());
    }

}