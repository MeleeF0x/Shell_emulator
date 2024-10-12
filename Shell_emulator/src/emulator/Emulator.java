package emulator;

import java.util.Scanner;
import app.inputClass;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.zip.*;

public class Emulator {
    //Поля класса
    //Поле - путь к tar архиву с виртуальной файловой системой
    File pathToVirtualFileSystem;
    //Поле - путь к текущей директории
    Path CurrentDirectory;
    
    //конструктор
    public Emulator(){
        CurrentDirectory = Path.of("src\\Virtual_File_System.zip").toAbsolutePath();
        pathToVirtualFileSystem = CurrentDirectory.toFile();
    }

    //реализация комманды ls
    public void ls_Command(String input_line) throws IOException{
        if(inputClass.lsInputControl(input_line)){
            System.out.println("command " + input_line + " not found");
            return;
        }
        ZipInputStream zin = new ZipInputStream(new FileInputStream(pathToVirtualFileSystem));
        ZipEntry entry;
        while((entry = zin.getNextEntry())!= null){
            System.out.println(entry.getName());
        }
        zin.close();
        /* 
        DirectoryStream<Path> files = Files.newDirectoryStream(CurrentDirectory);
         for (Path path : files){
            if(Files.isDirectory(path)){
                System.out.println(path.getFileName() + "/");
            }
            else{
                System.out.println(path.getFileName());
            }
         }
        files.close();
        */
    }

    //реализация комманды cd
    public void cd_Command(String input_line){
        if(inputClass.cdInputControl(input_line, CurrentDirectory)){
            System.out.println("command " + input_line + " not found");
            return;
        }
        String[] command_directory = input_line.split(" ");
        CurrentDirectory = Path.of(CurrentDirectory.toString() + "\\" + command_directory[1]).normalize().toAbsolutePath();
        return;
    }

    //реализация комманды exit
    public boolean exit_Command(String input_line){
        if(inputClass.exitInputControl(input_line)){
            System.out.println("command " + input_line + " not found");
            return false;
        }
        else{
            return true;
        }
    }

    //реализация комманды cat
    public void cat_Command(String input_line) throws IOException{
        if(inputClass.catInputControl(input_line, CurrentDirectory)){
            System.out.println("command " + input_line + " not found");
            return;
        }
        String[] command_directory = input_line.split(" ");
        Path CurrentFile = Path.of(CurrentDirectory.toString() + "\\" + command_directory[1]).normalize().toAbsolutePath();
        String result = Files.readString(CurrentFile);
        System.out.println(result);
    }

    //реализация комманды rev
    public void rev_Command(String input_line) throws IOException{
        if(inputClass.revInputControl(input_line, CurrentDirectory)){
            System.out.println("command " + input_line + " not found");
            return;
        }
        String[] command_line = input_line.split(" ");
        Path CurrentFile = Path.of(CurrentDirectory.toString() + "\\" + command_line[1]).normalize().toAbsolutePath();
        if(Files.exists(CurrentFile)){
            String result = Files.readString(CurrentFile);
            String reverse_line = new StringBuilder(result).reverse().toString();
            System.out.println(reverse_line);
        }
        else{
            String reverse_line = new StringBuilder(command_line[1]).reverse().toString();
            System.out.println(reverse_line);
        }
    }
    //реализация обработки комманд
    public boolean command_Reader() throws IOException{
        Scanner scan = new Scanner(System.in);
        String input_line = scan.nextLine();
        if(input_line.startsWith("ls") ){
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
            System.out.println("command " + input_line + " not found");
            return false;
        }
    }
}
