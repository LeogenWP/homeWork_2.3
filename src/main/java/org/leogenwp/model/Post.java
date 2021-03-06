package org.leogenwp.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts",schema = "writer2")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "created")
    private String created;

    @Column(name = "updated")
    private String updated;

    @Column(name = "writer_id")
    private Integer writerId;

    @ManyToMany  (fetch = FetchType.LAZY , cascade = { CascadeType.ALL })
    @JoinTable(
            name = "posts_labels",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "label_id") }
    )
    private List<Label> labels = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "post_status")
    private PostStatus postStatus;

    public int getId() {
        return id;
    }

    public Post setId(int id) {
        this.id = id;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public Post setCreated(String created) {
        this.created = created;
        return this;
    }

    public String getUpdated() {
        return updated;
    }

    public Post setUpdated(String updated) {
        this.updated = updated;
        return this;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public Post() {

    }

    public Post(String content) {
        this.content = content;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public Post setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
        return this;
    }

    public Post setLabels(List<Label> labels) {
        this.labels = labels;
        return this;
    }

    public Post(Integer id, String content, String created, String updated, List<Label> labels, PostStatus postStatus) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.labels = labels;
        this.postStatus = postStatus;
    }

}
