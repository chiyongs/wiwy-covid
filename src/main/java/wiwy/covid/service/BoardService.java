package wiwy.covid.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wiwy.covid.domain.Board;
import wiwy.covid.domain.Member;
import wiwy.covid.repository.BoardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long makeBoard(Board board) {
        validateDuplicateBoard(board);
        boardRepository.save(board);
        return board.getId();
    }

    // 중복 게시판 검증
    private void validateDuplicateBoard(Board board) {
        List<Board> findBoards = boardRepository.findByName(board.getBoardName());
        if(!findBoards.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 게시판입니다.");
        }
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

    public Long deleteBoard(Long boardId) {
        return boardRepository.delete(boardId);
    }
}
