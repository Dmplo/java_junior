package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PostComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public PostComment() {
    }

    public PostComment(String text, User user, Post post) {
        this.text = text;
        this.user = user;
        this.post = post;
    }

    @Override
    public String toString() {
        return "PostComment{" +
               "id=" + id +
               ", text='" + text + '\'' +
               ", userId=" + user.getId() +
               ", postId=" + post.getId() +
               ", createdAt=" + createdAt +
               '}';
    }
}
