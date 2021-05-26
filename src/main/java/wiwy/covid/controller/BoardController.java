package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.Member;
import wiwy.covid.domain.Post;
import wiwy.covid.paging.BoardPaging;
import wiwy.covid.paging.Paging;
import wiwy.covid.service.BoardService;
import wiwy.covid.service.MemberService;
import wiwy.covid.service.PostService;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final PostService postService;
    private final BoardPaging boardPaging;

    @GetMapping("/co")
    public String viewAllBoards(Model model) {

        List<Board> boards = boardService.findAllBoards();
        model.addAttribute("boards", boards);

        return "board/community";
    }

    // 특정 게시판 보기
    @GetMapping("/{boardId}")
    public String viewOneBoard(@PathVariable Long boardId, Model model) {
        Board board = boardService.findOne(boardId);
        List<Post> posts = postService.pagingPosts(boardId, 0, 10);
        model.addAttribute("board",board);
        model.addAttribute("posts", posts);

        return "board/board_main";
    }

    // 게시판 페이징
    @GetMapping("/{boardId}/page/{pageNum}")
    public String viewOnePage(@PathVariable Long boardId, @PathVariable int pageNum, Model model) {
        List<Post> posts = postService.findPostsByBoardId(boardId);
        Integer totalCount = posts.size();

        BoardPaging bp = new BoardPaging();
        Paging paging = new Paging();
        paging.setPage(pageNum);

        bp.setPaging(paging);
        bp.setTotalCount(totalCount);

        List<Post> returnPosts = postService.pagingPosts(boardId, bp.getPaging().getPageStart(), 10);
        model.addAttribute("paging", paging);
        model.addAttribute("posts", returnPosts);

        return "/board/main";
    }

    @GetMapping("/addBoard")
    public String getEditBoard() {
        return "board/addBoardForm";
    }

    @PostMapping("/addBoard")
    public String postEditBoard(Board board, RedirectAttributes redirectAttributes) {
        try {
            Long boardId = boardService.makeBoard(board);
            redirectAttributes.addAttribute("boardId", boardId);
        } catch (IllegalStateException e) {
            redirectAttributes.addAttribute("dup",true);
            return "redirect:/addBoard";
        }

        return "redirect:/{boardId}";
    }


}
