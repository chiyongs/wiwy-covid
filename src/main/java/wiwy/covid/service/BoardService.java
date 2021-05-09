package wiwy.covid.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.domain.Board;
import wiwy.covid.repository.BoardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long post(Board board) {
        boardRepository.save(board);
        return board.getId();
    }

    public List<Board> findBoardsByName(String boardName) {
        return boardRepository.findByName(boardName);
    }

    public List<Board> findByMember(Long memberId) {
        return boardRepository.findByMemberId(memberId);
    }

    public Board findOne(Long boardId) {
        return boardRepository.findById(boardId);
    }

    public List<Board> findBoards() {
        return boardRepository.findAll();
    }


}
