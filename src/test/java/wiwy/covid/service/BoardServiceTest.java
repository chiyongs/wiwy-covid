package wiwy.covid.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class BoardServiceTest {


    @Autowired BoardService boardService;
    @Autowired MemberService memberService;

    @Test
    void 저장() {
        Board board = new Board("testBoard", "hello");
        boardService.post(board);

        Board findBoard = boardService.findOne(board.getId());

        assertThat(findBoard.getBoardName()).isEqualTo(board.getBoardName());
    }

    @Test
    void 전부조회() {
        Board board1 = new Board("AA","hi");
        Board board2 = new Board("BB", "hello");

        boardService.post(board1);
        boardService.post(board2);

        List<Board> boards = boardService.findBoards();

        assertThat(boards.size()).isEqualTo(2);
        assertThat(boards).contains(board1,board2);
    }

    @Test
    void 회원등록게시글조회() {
        Member member = new Member();
        member.setUserName("shin");
        member.setAge(25);
        memberService.join(member);

        Board board1 = Board.createBoard(member,"sample","hello");
        Board board2 = Board.createBoard(member, "BBB", "hi");
        boardService.post(board1);
        boardService.post(board2);

        List<Board> boards = boardService.findByMember(member.getId());
//        List<Board> boards = boardService.findBoards();

        assertThat(boards.size()).isEqualTo(2);
        Assertions.assertThat(boards).contains(board1,board2);


    }
}