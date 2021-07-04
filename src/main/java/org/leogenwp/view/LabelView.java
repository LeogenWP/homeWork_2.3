package org.leogenwp.view;

import org.leogenwp.controller.LabelController;

import java.io.BufferedReader;
import java.io.IOException;

public class LabelView {
    private final LabelController labelController;

    public LabelView() {
        labelController = new LabelController();
    }


    public void getString(BufferedReader reader) {
        String command;
        while (true) {
            System.out.println("You are in a Label menu");
            System.out.println("Type one of  next commands: save,update,getall,delete,get");
            System.out.println("For example type \"save\"  if you want to create a new Label");
            System.out.println("Type \"return\"  if you want to return");
            try {
                command = reader.readLine();
                if (command.equals("return")) {
                    System.out.println("Returning to main menu");
                    return;
                } else if (command.equals("save")) {
                    System.out.println("Please type a name for a label");
                    System.out.println("If you want to return type: return");
                    String labelName = reader.readLine();
                    if (labelName.equals("return")) {
                        return;
                    } else if (labelName.isEmpty()) {
                        System.out.println("sorry, invalid name");
                        return;
                    } else {
                        labelController.save(labelName);
                        System.out.println("Label has been saved");
                    }
                } else if (command.equals("getall")) {
                    labelController.getAll();
                } else if (command.equals("delete")) {
                    System.out.println("Please, write the ID of the label which should be deleted");
                    labelController.deleteById(Integer.parseInt(reader.readLine()));
                    System.out.println("Label has been deleted");
                } else if (command.equals("get")) {
                    System.out.println("Please, write the ID of the label which should be found");
                    labelController.getById(Integer.parseInt(reader.readLine()));
                } else if (command.equals("update")) {
                    System.out.println("Please, write the ID of the label which should be updated");
                    int labelId = Integer.parseInt(reader.readLine());
                    labelController.getById(labelId);
                    System.out.println("Please write a new name for the label");
                    String labelName = reader.readLine();
                    labelController.updateById(labelId,labelName);
                    System.out.println("Label has been updated");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}
