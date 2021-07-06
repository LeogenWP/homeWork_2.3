package org.leogenwp.model;

import javax.persistence.*;

@Entity
@Table(name = "labels",schema = "writer2")
public class Label {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "description")
    private String name;

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
