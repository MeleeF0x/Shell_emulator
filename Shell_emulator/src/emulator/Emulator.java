package emulator;

import java.util.Scanner;
import app.inputClass;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Emulator {
    //Поля класса
    //Поле - путь к tar архиву с виртуальной файловой системой
    Path pathToVirtualFileSystem;
    //Поле - путь к текущей директории
    Path CurrentDirectory;
    
    //конструктор
    public Emulator(){
        pathToVirtualFileSystem = Path.of("src\\Virtual_File_System.tar");
        CurrentDirectory = Path.of("src").toAbsolutePath();
    }

    //реализация комманды ls
    public void ls_Command(String input_line) throws IOException{
        if(inputClass.inputControl(input_line, "ls", CurrentDirectory)){
            System.out.println("command " + input_line + " not found");
            return;
        }
        DirectoryStream<Path> files = Files.newDirectoryStream(CurrentDirectory);
         for (Path path : files){
            System.out.println(path.getFileName());
         }
        files.close();
    }

    //реализация комманды cd
    public void cd_Command(String input_line){
        if(inputClass.inputControl(input_line, "cd", CurrentDirectory)){
            System.out.println("command " + input_line + " not found");
            return;
        }
        String[] command_directory = input_line.split(" ");
        CurrentDirectory = Path.of(CurrentDirectory.toString() + "\\" + command_directory[1]).normalize().toAbsolutePath();
    }

    //реализация комманды exit
    public boolean exit_Command(String input_line){
        if(inputClass.inputControl(input_line, "exit", CurrentDirectory)){
            System.out.println("command " + input_line + " not found");
            return false;
        }
        else{
            return true;
        }
    }

    //реализация комманды cat
    public void cat_Command(String input_line){

    }

    //реализация комманды rev
    public void rev_Command(String input_line){

    }

    //реализация обработки комманд
    public boolean command_Reader() throws IOException{
        Scanner scan = new Scanner(System.in);
        String input_line = scan.nextLine();
        if(input_line.startsWith("test")){
            System.out.println(CurrentDirectory.toString());
            return false;
        }
        else if(input_line.startsWith("ls") ){
            this.ls_Command(input_line);
            return false;
        }
        else if(input_line.startsWith("cd")){
            this.cd_Command(input_line);
            return false;
        }
        else if(input_line.startsWith("exit")){
            return this.exit_Command(input_line);
        }
        else if(input_line.startsWith("cat")){
            this.cat_Command(input_line);
            return false;
        }
        else if(input_line.startsWith("rev")){
            this.rev_Command(input_line);
            return false;
        }
        else{
            System.out.println("command not found");
            return false;
        }
    }
}
