package org.leogenwp.controller;

import org.leogenwp.repository.WriterRepository;
import org.leogenwp.repository.io.JavaIOPostRepository;
import org.leogenwp.repository.io.JavaIOWriterRepository;
import org.leogenwp.model.Writer;
import org.leogenwp.service.PostService;
import org.leogenwp.service.WriterService;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class WriterController {
    private final WriterService writerService = new WriterService(new JavaIOWriterRepository());
    private final PostService postService = new PostService(new JavaIOPostRepository());
    private final String updateText = "Next options are available for Writer: \n"
            + "type firsname for updating firs name\n"
            + "type lastname for updating last name\n"
            + "type addpost for adding post\n"
            + "type removepost for deleting post\n"
            + "type return for returning\n"
            + "type save for saving changes";

    public WriterController() {
    }

    public Writer save (String firstName, String lastName) {
        return writerService.save(new Writer(firstName, lastName));
    }

    public List<Writer> getAll() {
        for (Writer writer : writerService.getAll()) {
            System.out.println(writer.getId() + ";" + writer.getFirstName() +
                    ";" + writer.getLastName() + ";" +
                    writer.getPosts().toString());
        }
        return writerService.getAll();
    }

    public void deleteById( Integer id) {
        writerService.deleteById(id);

    }

    public Writer getById(Integer id) {
        try {
            Writer writer = writerService.getById(id);
            System.out.println(writer.getId() + ";" + writer.getFirstName() +
                    ";" + writer.getLastName() + ";" +
                   writer.getPosts().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writerService.getById(id);
    }

    public Writer updateById(BufferedReader reader) {
        System.out.println(updateText);
        String string;
        String postId;

        try {
            postId = reader.readLine();
            Writer writer = writerService.getById(Integer.parseInt(postId));
            while (true) {
                string = reader.readLine();
                if (string.equals("return")) {
                    return writer;
                } else if (string.equals("firsname")) {
                    System.out.println("Type new firsname");
                    writer.setFirstName(reader.readLine());
                } else if (string.equals("lastname")) {
                    System.out.println("Type new last name");
                    writer.setLastName(reader.readLine());
                } else if (string.equals("removepost")) {
                    System.out.println("write post id that you want to remove");
                    writer.removePost(Integer.parseInt(reader.readLine()));
                } else if (string.equals("addpost")) {
                    System.out.println("write post id that you want to add");
                    writer.addPost(postService.getById(Integer.parseInt(reader.readLine())));
                } else if (string.equals("save")) {
                    return writerService.update(writer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
