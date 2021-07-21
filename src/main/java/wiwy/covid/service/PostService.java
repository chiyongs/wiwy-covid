package wiwy.covid.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.Member;
import wiwy.covid.domain.Post;
import wiwy.covid.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long post(Optional<Board> board, Post p) {
        Post newPost = Post.makePost(board, p.getPostName(), p.getContent());
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

    public List<Post> pagingPosts (Long boardId, int page, int perPageNum) {
        return postRepository.pagingPosts(boardId, page, perPageNum);
    }


}
