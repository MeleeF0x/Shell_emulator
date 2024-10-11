package app;

import java.nio.file.Files;
import java.nio.file.Path;


public class inputClass {

    //Метод проверки ввода команды ls
    public static boolean lsInputControl(String input_line){
        if(input_line.length() != 2){
            return true;
        }
        return false;
    }
    
    //Метод проверки ввода команды cd
    public static boolean cdInputControl(String input_line, Path CurrentDirectory){
        String[] command_words = input_line.split(" ");
            if(command_words.length != 2){
                return true;
            }
            CurrentDirectory = Path.of(CurrentDirectory.toString() + "\\" + command_words[1]).normalize().toAbsolutePath();
            if(!Files.exists(CurrentDirectory)){
                return true;
            }
            return false;
    }

    //Метода проверки ввода команды cat
    public static boolean catInputControl(String input_line, Path CurrentDirectory){
        String[] command_words = input_line.split(" ");
            if(command_words.length != 2){
                return true;
            }
            CurrentDirectory = Path.of(CurrentDirectory.toString() + "\\" + command_words[1]).normalize().toAbsolutePath();
            if(!Files.exists(CurrentDirectory)){
                return true;
            }
            return false;
    }

    //Метода проверки ввода команды exit
    public static boolean exitInputControl(String input_line){
        if(input_line.length() != 4){
            return true;
        }
        return false;
    }

    //Метода проверки ввода команды rev
    public static boolean revInputControl(String input_line, Path CurrentDirectory){
        String[] command_words = input_line.split(" ");
            if(command_words.length != 2){
                return true;
            }
            return false;
    }
    
}
