package mk.ukim.finki.blogbusterbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "blog_images")
@RequiredArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//    @Lob
    @Column(name = "picture", nullable = true)
    private byte[] picture;

    @OneToOne
    private Post post;

    @ManyToOne
    private User author;

    public Image(byte[] picture,User author,Post post) {
        this.picture = picture;
        this.author=author;
        this.post=post;
    }

}
