package org.leogenwp.view;

import org.leogenwp.controller.WriterController;

import java.io.BufferedReader;
import java.io.IOException;

public class WriterView {
    private final WriterController writerController;

    public WriterView() {
        writerController = new WriterController();
    }

    public void getString(BufferedReader reader) {
        String command;
        while (true) {
            System.out.println("You are in a Writer menu");
            System.out.println("Type one of  next commands: save,update,getall,delete,get");
            System.out.println("For example type \"save\"  if you want to create a new Writer");
            System.out.println("Type \"return\"  if you want to return");
            try {
                command = reader.readLine();
                if (command.equals("return")) {
                    System.out.println("Returning to main menu");
                    return;
                } else if (command.equals("save")) {
                    System.out.println("Please type a First name for a Writer");
                    String firstName = reader.readLine();
                    System.out.println("Please type a Last name for a Writer");
                    String lastName = reader.readLine();
                    System.out.println("If you want to return type: return");
                    String postContent = reader.readLine();
                    if (postContent.equals("return")) {
                        return;
                    } else {
                        writerController.save(firstName,lastName);
                        System.out.println("Content has been saved");
                    }
                } else if (command.equals("getall")) {
                    writerController.getAll();
                } else if (command.equals("delete")) {
                    System.out.println("Please, write the ID of the Writer which should be deleted");
                    writerController.deleteById(Integer.parseInt(reader.readLine()));
                    System.out.println("Writer has been deleted");
                } else if (command.equals("get")) {
                    System.out.println("Please, write the ID of the Writer which should be found");
                    writerController.getById(Integer.parseInt(reader.readLine()));
                } else if (command.equals("update")) {
                    System.out.println("Please, write the ID of the Writer which should be updated");
                    writerController.updateById(reader);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
