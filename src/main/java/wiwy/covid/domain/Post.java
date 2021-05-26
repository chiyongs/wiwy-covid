package wiwy.covid.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String postName;
    private String content;

    private LocalDateTime createTime;

    private int viewCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();


    //연관관계 메서드
    public void addComments(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    private Post() {
    }

    public Post(String postName, String content) {
        this.postName = postName;
        this.content = content;
    }

    public static Post makePost(Member member, String postName, String content) {
        Post post = new Post();
        post.setPostName(postName);
        post.setContent(content);
        post.setMember(member);

        return post;
    }

    public static Post makePost(Board board, String postName, String content) {
        Post post = new Post();
        post.setBoard(board);
        post.setPostName(postName);
        post.setContent(content);

        return post;
    }



}
