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

    @GetMapping("/")
    public String viewAllBoards(Model model) {
        List<Board> boards = boardService.findAllBoards();
        model.addAttribute("boards", boards);
        return "board/main";
    }

    // 특정 게시판 보기
    @GetMapping("/{boardId}")
    public String viewOneBoard(@PathVariable Long boardId, Model model) {
        Board board = boardService.findOne(boardId);
        List<Post> posts = postService.findPostsByBoardId(boardId);
        model.addAttribute("board",board);
        model.addAttribute("posts", posts);

        return "board/{boardId}";
    }

    @GetMapping("/addBoard")
    public String getEditBoard() {
        return "board/addForm";
    }

    @PostMapping("/addBoard")
    public String postEditBoard(Board board, RedirectAttributes redirectAttributes) {
        Long boardId = boardService.makeBoard(board);
        redirectAttributes.addAttribute("boardId", boardId);
        return "redirect:/board/{boardId}";
    }

}
