package wiwy.covid.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.domain.Post;
import wiwy.covid.repository.PostRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public Long post(Post board) {
        postRepository.save(board);
        return board.getId();
    }

    public List<Post> findBoardsByName(String boardName) {
        return postRepository.findByName(boardName);
    }

    public List<Post> findByMember(Long memberId) {
        return postRepository.findByMemberId(memberId);
    }

    public Post findOne(Long boardId) {
        return postRepository.findById(boardId);
    }

    public List<Post> findBoards() {
        return postRepository.findAll();
    }


}
