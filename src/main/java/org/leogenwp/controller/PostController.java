package org.leogenwp.controller;

import org.leogenwp.model.Label;
import org.leogenwp.model.Post;
import org.leogenwp.model.PostStatus;
import org.leogenwp.repository.PostRepository;
import org.leogenwp.repository.io.JavaIOLabelRepository;
import org.leogenwp.repository.io.JavaIOPostRepository;
import org.leogenwp.service.LabelService;
import org.leogenwp.service.PostService;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostController {
    private final PostService postService = new PostService(new JavaIOPostRepository());
    private final LabelService labelService = new LabelService(new JavaIOLabelRepository());
    private final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final String updateText = "Next options are available for Post: \n"
            + "type content for updating content\n"
            + "type addlabels for adding labels\n"
            + "type removelabels for deleting labels\n"
            + "type status for updating status\n"
            + "type return for returning\n"
            + "type save for saving changes\n";

    public PostController(){

    }

    public Post save (String postContent) {
        return postService.save(new Post(postContent).setLabels(new ArrayList<Label>()));
    }

    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try {
            posts = postService.getAll();
            for (Post post : posts) {
                System.out.println(post.getId() + ";" + post.getContent() +
                        ";" + post.getCreated() + ";" + post.getUpdated() + ";" +
                        post.getLabels().toString() + ";" + post.getPostStatus());

            }

            //return postRepository.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public void deleteById( Integer id) {
        postService.deleteById(id);

    }

    public Post getById(Integer id) {
        try {
            Post post = postService.getById(id);
            System.out.println(post.getId() + ";" + post.getContent() +
                    ";" + post.getCreated() + ";" + post.getUpdated() + ";" +
                    post.getLabels().toString() + ";" + post.getPostStatus() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  postService.getById(id);
    }

    public Post updateById(BufferedReader reader) {

        System.out.println(updateText);
        String string;
        String postId;
        try {
            postId = reader.readLine();
            Post post = postService.getById(Integer.parseInt(postId));
            while (true) {
                System.out.println("Type command");
                string = reader.readLine();
                if (string.equals("return")) {
                    return post;
                } else if (string.equals("content")) {
                    System.out.println("Type new content");
                    post.setContent(reader.readLine());
                } else if (string.equals("addlabels")) {
                    System.out.println("add label id that you want to add");
                    post.addLabel(labelService.getById(Integer.parseInt(reader.readLine())));
                } else if (string.equals("removelabels")) {
                    System.out.println("add label id that you want to remove");
                    post.removeLabel(Integer.parseInt(reader.readLine()));
                } else if (string.equals("status")) {
                    System.out.println("type  active/review/deleted");
                    String status = reader.readLine();
                    if (status.equals("active")) {
                        post.setPostStatus(PostStatus.ACTIVE);
                    } else if (status.equals("review")) {
                        post.setPostStatus(PostStatus.UNDER_REVIEW);
                    } else if (status.equals("deleted")) {
                        post.setPostStatus(PostStatus.DELETED);
                    }
                } else if (string.equals("save")) {
                    Date now = new Date();
                    post.setUpdated(sdfDate.format(now));
                    return  postService.update(post);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
