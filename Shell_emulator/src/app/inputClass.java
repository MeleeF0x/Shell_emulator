package app;

import java.nio.file.Files;
import java.nio.file.Path;

public class inputClass {
    public static boolean inputControl(String input_line, String command_type, Path CurrentDirectory){
        if(command_type == "ls"){
            if(input_line.length() != 2){
                return true;
            }
            return false;
        }

        else if(command_type == "cd"){
            String[] command_words = input_line.split(" ");
            if(command_words.length != 2){
                return true;
            }
            String[] command_directory = input_line.split("\"");
            CurrentDirectory = Path.of(CurrentDirectory.toString() + command_directory[1]).normalize().toAbsolutePath();
            if(!Files.exists(CurrentDirectory)){
                return true;
            }
            return false;
        }
        else if(command_type == "cat"){
            String[] command_words = input_line.split(" ");
            if(command_words.length != 2){
                return true;
            }
            CurrentDirectory = Path.of(CurrentDirectory.toString() + command_words[1]).normalize().toAbsolutePath();
            if(!Files.exists(CurrentDirectory)){
                return true;
            }
            return false;
        }
        else if(command_type == "exit"){
            if(input_line.length() != 4){
                return true;
            }
            return false;
        }
        else if(command_type == "rev"){
            String[] command_words = input_line.split(" ");
            if(command_words.length != 2){
                return true;
            }
            return false;
        }
        else{
            return false;
        }
    }
}
