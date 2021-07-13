package org.leogenwp.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "writers",schema = "writer2")
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @OneToMany (mappedBy = "writerId", cascade = CascadeType.ALL)
    private List<Post> posts;

    public int getId() {
        return id;
    }

    public Writer setId(int id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public Writer setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public Writer setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Writer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        posts = new ArrayList<>();
    }

    public Writer() {
    }

    public void addPost(Post post){
        this.posts.add(post);
    }
    public void removePost(Integer id) {
        for (int i = 0; i < this.posts.size(); i++) {
            if (id == this.posts.get(i).getId()) {
                this.posts.remove(i);
                break;
            }
        }
    }

    public Writer(Integer id, String firstName, String lastName, List<Post> posts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
    }

    public Writer setPosts(List<Post> posts) {
        this.posts = posts;
        return this;
    }
}
