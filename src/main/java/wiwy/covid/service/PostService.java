package wiwy.covid.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.Post;
import wiwy.covid.repository.PostRepository;
import wiwy.covid.repository.PostRepositoryImpl;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long post(Optional<Board> board, Post p) {
        Post newPost = Post.makePost(board.get(), p.getPostName(), p.getContent());
        postRepository.save(newPost);
        return newPost.getId();
    }
//    @Transactional
//    public Long post(Long memberId, Long boardId, String title, String content) {
//
//        Member member = memberService.findOne(memberId);
//        Board board = boardService.findOne(boardId);
//
//        Post post = Post.makePost(board, member, title, content);
//        postRepository.save(post);
//        return post.getId();
//    }

    public List<Post> findPostsByName(String postName) {
        return postRepository.findByPostName(postName);
    }

    public List<Post> findPostsByBoardId(Long boardId) {
        return postRepository.findByBoardId(boardId);
    }

    public List<Post> findByMember(Long memberId) {
        return postRepository.findByMemberId(memberId);
    }

    public Optional<Post> findOne(Long postId) {
        return postRepository.findById(postId);
    }

    public List<Post> findBoards() {
        return postRepository.findAll();
    }

    public Long deletePost (Long postId) {
        postRepository.deleteById(postId);
        return postId;
    }

    public Page<Post> pagingPosts (Long boardId, int page, int perPageNum) {
        PageRequest request = PageRequest.of(page, perPageNum);
        return postRepository.findByBoardId(boardId, request);
    }


}
