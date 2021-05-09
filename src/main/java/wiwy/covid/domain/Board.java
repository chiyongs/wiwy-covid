package wiwy.covid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String boardName;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Board() {
    }

    public Board(String boardName, String content) {
        this.member = member;
        this.boardName = boardName;
        this.content = content;
    }

    public static Board createBoard(Member member, String boardName, String content) {
        Board board = new Board();
        board.setBoardName(boardName);
        board.setContent(content);
        board.setMember(member);

        return board;
    }

}
