package org.leogenwp.model;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private Integer id;
    private String content;
    private String created;
    private String updated;
    private List<Label> labels;
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
        this.labels = new ArrayList<>();
    }

    public Post(String content) {
        this.content = content;
        this.labels = new ArrayList<>();
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public Post setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;

        return this;
    }

    public void addLabel(Label label){
        this.labels.add(label);
    }
    public void removeLabel(Integer id){
        for (int i = 0; i < this.labels.size(); i++){
            if(id == this.labels.get(i).getId()){
                this.labels.remove(i);
                break;
            }
        }
    }
}
