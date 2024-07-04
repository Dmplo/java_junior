package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    @Column(nullable = false)
    String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    @JoinColumn(name = "post_id")
    private List<PostComment> postComments;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Post() {
    }

    public Post(String title, User user) {
        this.title = title;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", user=" + user +
               ", postComments=" + postComments +
               ", createdAt=" + createdAt +
               '}';
    }
}
