package app;

import java.io.IOException;
import java.nio.file.Path;

import emulator.*;

public class inputClass {

    //Метод проверки ввода команды ls
    public static boolean lsInputControl(String input_line){
        if(input_line.length() != 2){
            return false;
        }
        return true;
    }
    
    //Метод проверки ввода команды cd
    public static boolean cdInputControl(String input_line, String CurrentDirectory, Emulator emulator) throws IOException{
        String[] command_words = input_line.split(" ");
            if(command_words.length != 2){
                return false;
            }
            if(CurrentDirectory == ""){
                CurrentDirectory = Path.of(CurrentDirectory + command_words[1]).normalize().toString();
            }
            else{
                CurrentDirectory = Path.of(CurrentDirectory + "/" + command_words[1]).normalize().toString();
                CurrentDirectory = CurrentDirectory.replace("\\", "/");
            }
            if(CurrentDirectory.compareTo("") == 0){
                return true;
            }
            else if(!emulator.isPathExist(CurrentDirectory)){
                return false;
            }
            return true;
    }

    //Метода проверки ввода команды cat
    public static boolean catInputControl(String input_line, String CurrentDirectory, Emulator emulator) throws IOException{
        String[] command_words = input_line.split(" ");
            if(command_words.length != 2){
                return false;
            }
            CurrentDirectory = Path.of(CurrentDirectory + "/" + command_words[1]).normalize().toString();
            CurrentDirectory = CurrentDirectory.replace("\\", "/").replaceFirst("/","");
            System.out.println(CurrentDirectory);
            if(!emulator.isPathExist(CurrentDirectory)){
                return false;
            }
            return true;
    }

    //Метода проверки ввода команды exit
    public static boolean exitInputControl(String input_line){
        if(input_line.length() != 4){
            return false;
        }
        return true;
    }

    //Метода проверки ввода команды rev
    public static boolean revInputControl(String input_line, Path CurrentDirectory){
        String[] command_words = input_line.split(" ");
            if(command_words.length != 2){
                return false;
            }
            return true;
    }
    
}
