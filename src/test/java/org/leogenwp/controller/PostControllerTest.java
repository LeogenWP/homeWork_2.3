package org.leogenwp.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.leogenwp.model.Post;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class PostControllerTest {

    @Test
    void savePost_Success() {
        PostController postController_underTest = mock(PostController.class);
        Post post = new Post().setContent("Tom");
        when(postController_underTest.save("Tom")).thenReturn(post);
        assertEquals(post,postController_underTest.save("Tom"));

    }

    @Test
    void getAllPosts_Success() {
        PostController postController_underTest = mock(PostController.class);
        List<Post> posts = getPostList();
        when(postController_underTest.getAll()).thenReturn(posts);
        assertEquals(posts,postController_underTest.getAll());
    }

    @Test
    void deletePostById_Success() {
        PostController postController_underTest = mock(PostController.class);
        postController_underTest.deleteById(1);
        verify(postController_underTest).deleteById(1);
    }

    @Test
    void getPostById_Success() {
        PostController postController_underTest = mock(PostController.class);
        Post post = new Post().setId(1);
        when(postController_underTest.getById(1)).thenReturn(post);
        assertEquals(post,postController_underTest.getById(1));
    }

    @Test
    @Disabled
    void updatePostById() {
        PostController postController_underTest = mock(PostController.class);
        postController_underTest.updateById( new BufferedReader(new InputStreamReader(System.in)));
        verify(postController_underTest).updateById(new BufferedReader(new InputStreamReader(System.in)));
    }

    private List<Post> getPostList() {
        List<Post> posts = Arrays.asList(
                new Post(),
                new Post()
        );
        return posts;
    }
}