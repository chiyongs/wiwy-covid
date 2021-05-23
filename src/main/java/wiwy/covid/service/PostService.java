package wiwy.covid.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.Member;
import wiwy.covid.domain.Post;
import wiwy.covid.repository.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberService memberService;
    private final BoardService boardService;

    @Transactional
    public Long post(Long memberId, Long boardId, String title, String content) {

        Member member = memberService.findOne(memberId);
        Board board = boardService.findOne(boardId);

        Post post = Post.makePost(board, member, title, content);
        postRepository.save(post);
        return post.getId();
    }

    public List<Post> findPostsByName(String postName) {
        return postRepository.findByName(postName);
    }

    public List<Post> findPostsByBoardId(Long boardId) {
        return postRepository.findPostsByBoardId(boardId);
    }

    public List<Post> findByMember(Long memberId) {
        return postRepository.findByMemberId(memberId);
    }

    public Post findOne(Long postId) {
        return postRepository.findById(postId);
    }

    public List<Post> findBoards() {
        return postRepository.findAll();
    }

    public Long deletePost (Long postId) {
        return postRepository.delete(postId);
    }


}
