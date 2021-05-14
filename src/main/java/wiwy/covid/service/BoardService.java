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

    public Long makeBoard(Board board) {
        boardRepository.save(board);
        return board.getId();
    }

    public Board findOne(Long boardId) {
        return boardRepository.findById(boardId);
    }

    public List<Board> findBoardByName(String boardName) {
        return boardRepository.findByName(boardName);
    }

    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }
}
