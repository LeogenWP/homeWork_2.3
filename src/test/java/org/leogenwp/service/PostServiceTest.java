package org.leogenwp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.leogenwp.model.Label;
import org.leogenwp.model.Post;
import org.leogenwp.repository.LabelRepository;
import org.leogenwp.repository.PostRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @Test
    void savePost_Success() {
        Post post = new Post("Content");
        PostRepository postRepository = mock(PostRepository.class);
        PostService postService_underTest = new PostService(postRepository);
        when(postRepository.save(post)).thenReturn(post);
        assertEquals(post,postService_underTest.save(post));
    }

    @Test
    void getAllPosts_Success() {
        List<Post> posts = getPostList();
        PostRepository postRepository = mock(PostRepository.class);
        PostService postService_underTest = new PostService(postRepository);
        when(postRepository.getAll()).thenReturn(posts);
        assertEquals(posts,postService_underTest.getAll());
    }

    @Test
    void deletePostById_Success() {
        PostService postService_underTest = mock(PostService.class);
        postService_underTest.deleteById(1);
        verify(postService_underTest).deleteById(1);
    }

    @Test
    void getPost_Success() {
        Post post = new Post("Content").setId(1);
        PostRepository postRepository = mock(PostRepository.class);
        PostService postService_underTest = new PostService(postRepository);
        when(postRepository.getById(1)).thenReturn(post);
        assertEquals(post,postService_underTest.getById(1));
    }

    @Test
    void updatePost_Success() {
        Post post = new Post("Content").setId(1);
        PostRepository postRepository = mock(PostRepository.class);
        PostService postService_underTest = new PostService(postRepository);
        when(postRepository.update(post)).thenReturn(post);
        assertEquals(post,postService_underTest.update(post));
    }

    private List<Post> getPostList() {
        List<Post> posts = Arrays.asList(
                new Post("Content1"),
                new Post("Content2"),
                new Post("Content3"));
        return posts;
    }
}