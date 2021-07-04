package org.leogenwp.service;

import org.leogenwp.model.Post;
import org.leogenwp.model.PostStatus;
import org.leogenwp.repository.PostRepository;
import org.leogenwp.repository.io.JavaIOLabelRepository;
import org.leogenwp.repository.io.JavaIOPostRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post save (Post post) {
        return postRepository.save(post);
    }

    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try {
            posts = postRepository.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public void deleteById( Integer id) {
        postRepository.deleteById(id);

    }

    public Post getById(Integer id) {
        return  postRepository.getById(id);
    }

    public Post update(Post post) {
        return  postRepository.update(post);
    }
}
