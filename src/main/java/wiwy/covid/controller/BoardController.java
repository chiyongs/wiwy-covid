package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.Post;
import wiwy.covid.service.BoardService;
import wiwy.covid.service.PostService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final PostService postService;

    // 모든 게시판 보기
    @GetMapping("/boards")
    public String viewAllBoards(Model model) {
        List<Board> allBoards = boardService.findAllBoards();
        model.addAttribute("allBoards", allBoards);
        return "boards";
    }

    @GetMapping("/boards/{boardId}")
    public String goBoard(@PathVariable Long boardId, Model model) {
        List<Post> posts = postService.findPostsByBoardId(boardId);
        Board board = boardService.findOne(boardId);
        model.addAttribute("board", board);
        model.addAttribute("posts",posts);
        return "board";
    }

    @GetMapping("/boards/add")
    public String makeBoard() {
        return "board/addForm";
    }

    @PostMapping("/boards/add")
    public String addBoard(Board board, RedirectAttributes redirectAttributes) {
        Long boardId = boardService.makeBoard(board);
        redirectAttributes.addAttribute("boardId",boardId);
        return "redirect:/board/{boardId}";
    }

}
