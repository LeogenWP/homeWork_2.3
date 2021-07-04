package org.leogenwp.view;

import org.leogenwp.controller.PostController;

import java.io.BufferedReader;
import java.io.IOException;

public class PostView {
    private final PostController postController;

    public PostView(){
        postController = new PostController();
    }

    public void getString(BufferedReader reader) {
        String command;
        while (true) {
            System.out.println("You are in a Post menu");
            System.out.println("Type one of  next commands: save,update,getall,delete,get");
            System.out.println("For example type \"save\"  if you want to create a new Post");
            System.out.println("Type \"return\"  if you want to return");
            try {
                command = reader.readLine();
                if (command.equals("return")) {
                    System.out.println("Returning to main menu");
                    return;
                } else if (command.equals("save")) {
                    System.out.println("Please type a content for a Post");
                    System.out.println("If you want to return type: return");
                    String postContent = reader.readLine();
                    if (postContent.equals("return")) {
                        return;
                    } else {
                        postController.save(postContent);
                        System.out.println("Content has been saved");
                    }
                } else if (command.equals("getall")) {
                    postController.getAll();
                } else if (command.equals("delete")) {
                    System.out.println("Please, write the ID of the Post which should be deleted");
                    postController.deleteById(Integer.parseInt(reader.readLine()));
                    System.out.println("Label has been deleted");
                } else if (command.equals("get")) {
                    System.out.println("Please, write the ID of the Post which should be found");
                    postController.getById(Integer.parseInt(reader.readLine()));
                } else if (command.equals("update")) {
                    System.out.println("Please, write the ID of the Post which should be updated");
                    postController.updateById(reader);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
