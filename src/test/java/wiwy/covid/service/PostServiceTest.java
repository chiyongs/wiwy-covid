package wiwy.covid.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.Post;
import wiwy.covid.domain.Member;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostServiceTest {


    @Autowired
    PostService postService;
    @Autowired MemberService memberService;
    @Autowired BoardService boardService;

    @Test
    void 저장() {
        Post board = new Post("testBoard", "hello");
        postService.post(board);

        Post findBoard = postService.findOne(board.getId());

        assertThat(findBoard.getPostName()).isEqualTo(board.getPostName());
    }

    @Test
    void 전부조회() {
        Post board1 = new Post("AA","hi");
        Post board2 = new Post("BB", "hello");

        postService.post(board1);
        postService.post(board2);

        List<Post> boards = postService.findBoards();

        assertThat(boards.size()).isEqualTo(2);
        assertThat(boards).contains(board1,board2);
    }

    @Test
    void 회원등록게시글조회() {
        Member member = new Member();
        member.setUserName("shin");
        member.setAge(25);
        memberService.join(member);

        Post board1 = Post.makePost(member,"sample","hello");
        Post board2 = Post.makePost(member, "BBB", "hi");
        postService.post(board1);
        postService.post(board2);

        List<Post> boards = postService.findByMember(member.getId());
//        List<Board> boards = boardService.findBoards();

        assertThat(boards.size()).isEqualTo(2);
        Assertions.assertThat(boards).contains(board1,board2);

    }

    @Test
    void 게시글작성회원조회() {
        Member member = new Member();
        member.setAge(25);
        member.setUserName("shin");
        memberService.join(member);

        Post board = Post.makePost(member, "aaa", "bbb");
        postService.post(board);

        Member findMember = memberService.findOne(board.getMember().getId());

        assertThat(findMember).isEqualTo(member);
    }

    @Test
    void 게시글의해당게시판조회() {
        Member member = new Member();
        member.setAge(25);
        member.setUserName("shin");
        memberService.join(member);

        Board board = new Board();
        board.setBoardName("자유게시판");
        boardService.makeBoard(board);

        Post post = Post.makePost(board, member,"예시","안녕");
        postService.post(post);

        Post findPost = postService.findOne(post.getId());

        assertThat(findPost.getBoard().getBoardName()).isEqualTo(board.getBoardName());

    }
}