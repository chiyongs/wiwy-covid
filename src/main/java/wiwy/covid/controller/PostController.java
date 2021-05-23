package wiwy.covid.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.Post;
import wiwy.covid.service.BoardService;
import wiwy.covid.service.PostService;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final BoardService boardService;
    private final PostService postService;

    @GetMapping("/board/{boardId}/{postId}}")
    @ResponseBody
    public String post(@PathVariable Long boardId, @PathVariable Long postId, Model model) {
        Board board = boardService.findOne(boardId);
        Post post = postService.findOne(postId);

        model.addAttribute("post", post);
        return "ok";
    }

    @GetMapping("/board/{boardId}/add")
    public String makePost() {
        return "post/addForm";
    }

    @PostMapping("/board/{boardId}/add")
    public String addPost(Post post, RedirectAttributes redirectAttributes) {
        return "ok";
    }

//    @GetMapping("/board/{boardId}/{postId}/delete")
//    public String deletePost(@PathVariable Long postId) {}

    @GetMapping("/board/{boardId}/edit")
    public String editPost(@PathVariable Long boardId, Model model) {
        Post post = postService.findOne(boardId);
        model.addAttribute("post", post);
        return "basic/editForm";
    }

//    @PostMapping("/{itemId}/edit")
//    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
//        itemRepository.update(itemId, item);
//        return "redirect:/basic/items/{itemId}";
//    }


}
