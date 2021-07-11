package org.leogenwp.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "labels",schema = "writer2")
public class Label {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "description")
    private String name;
    @ManyToMany (mappedBy = "labels")
    private List<Post> posts = new ArrayList<>();


    public Label setId(Integer id) {
        this.id = id;
        return this;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public Label setPosts(List<Post> posts) {
        this.posts = posts;
        return this;
    }

    public int getId() {
        return id;
    }

    @Column(name = "id")
    public Label setId(int id) {
        this.id = id;
        return this;
    }
    @Column(name = "description")
    public String getName() {
        return name;
    }

    public Label setName(String name) {
        this.name = name;
        return this;
    }

    public Label(String name) {
        this.name = name;
    }

    public Label(Integer id, String name) {
        this.name = name;
        this.id = id;
    }



    public Label() {
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
