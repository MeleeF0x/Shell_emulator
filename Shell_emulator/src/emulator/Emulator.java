package emulator;

import java.util.Scanner;
import app.inputClass;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.zip.*;

public class Emulator {
    //Поля класса
    //Поле - путь к tar архиву с виртуальной файловой системой
    private String pathToVirtualFileSystem;
    //Поле - путь к текущей директории
    private String CurrentDirectory;
    
    //конструктор
    public Emulator(){
        pathToVirtualFileSystem = Path.of("src\\Virtual_File_System.zip").toAbsolutePath().toString();
        CurrentDirectory = "";
    }

    //Метод получения файла
    public ZipEntry getFile(String path) throws IOException{
        ZipInputStream is_path_zin = new ZipInputStream(new FileInputStream(pathToVirtualFileSystem.toString()));
        ZipEntry entry;
        entry = is_path_zin.getNextEntry();
        while(entry.getName().compareTo(path) != 0){
            entry = is_path_zin.getNextEntry();
        }     
        is_path_zin.close();
        return entry;   
    }
    //Метод проверки корректности пути файла
    public boolean isPathExist(String path) throws IOException{
        ZipInputStream is_path_zin = new ZipInputStream(new FileInputStream(pathToVirtualFileSystem.toString()));
        ZipEntry entry;
        while((entry = is_path_zin.getNextEntry()) != null){
            if(entry.isDirectory()){
                if(path.compareTo(entry.getName()) == 0 || entry.getName().compareTo(path + "/") == 0){
                    is_path_zin.close();
                    return true;
                }
            }
            else{
                if(path.compareTo(entry.getName()) == 0 || entry.getName().compareTo(path) == 0){
                    is_path_zin.close();
                    return true;
                }
            }
            
        }
        is_path_zin.close();
        return false;
        
    }

    //реализация комманды ls
    public void ls_Command(String input_line) throws IOException{
        System.out.println(CurrentDirectory);
        if(!inputClass.lsInputControl(input_line)){
            System.out.println("command " + input_line + " not found");
            return;
        }
        String[] curr_dir_arr = CurrentDirectory.split("/");
        ZipInputStream zin = new ZipInputStream(new FileInputStream(pathToVirtualFileSystem.toString()));
        ZipEntry entry = zin.getNextEntry();
        if(CurrentDirectory.compareTo("") == 0){
            while(entry.getName().split("/").length ==  1){
                System.out.println(entry.getName());
                entry = zin.getNextEntry();
            }
        }
        else{
            while((entry = zin.getNextEntry()) != null){
                if(entry.getName().startsWith(CurrentDirectory) && entry.getName().split("/").length == curr_dir_arr.length + 1){
                    if(entry.isDirectory()){
                        String[] file_path = entry.getName().split("/");
                        System.out.println(file_path[file_path.length - 1] + "/");
                    }
                    else{
                        String[] file_path = entry.getName().split("/");
                        System.out.println(file_path[file_path.length - 1]);
                    }
                }
            }
        }
        zin.close();
    }

    //реализация комманды cd
    public void cd_Command(String input_line) throws IOException{
        String[] command_directory = input_line.split(" ");
        if(command_directory[1].compareTo("~") == 0 || command_directory[1].compareTo("root") == 0){
            CurrentDirectory = "";
            return;
        }
        else if(!inputClass.cdInputControl(input_line, CurrentDirectory, this)){
            System.out.println("command " + input_line + " not found");
            return;
        }
        else{
            if(CurrentDirectory == ""){
                CurrentDirectory = Path.of(CurrentDirectory + command_directory[1]).normalize().toString();
            }
            else{
                CurrentDirectory = Path.of(CurrentDirectory + "/" + command_directory[1]).normalize().toString();
                CurrentDirectory = CurrentDirectory.replace("\\", "/");
            }
        }
    }

    //реализация комманды exit
    public boolean exit_Command(String input_line){
        if(!inputClass.exitInputControl(input_line)){
            System.out.println("command " + input_line + " not found");
            return false;
        }
        else{
            return true;
        }
    }

    //реализация комманды cat
    public void cat_Command(String input_line) throws IOException{
        System.out.println(CurrentDirectory);
        if(!inputClass.catInputControl(input_line, CurrentDirectory, this)){
            System.out.println("command " + input_line + " not found");
            return;
        }
        String[] command_directory = input_line.split(" ");
        String readeble_directory = CurrentDirectory;
        if(readeble_directory == ""){
            readeble_directory = Path.of(readeble_directory + command_directory[1]).normalize().toString();
        }
        else{
            readeble_directory = Path.of(readeble_directory + "/" + command_directory[1]).normalize().toString();
            readeble_directory = readeble_directory.replace("\\", "/");
        }
        ZipFile zipFile = new ZipFile(pathToVirtualFileSystem);
        ZipEntry entry = getFile(readeble_directory);
        InputStream inputStream = zipFile.getInputStream(entry);
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
        }
    }

    //реализация комманды rev
    public void rev_Command(String input_line) throws IOException{
        if(!inputClass.revInputControl(input_line, Path.of(CurrentDirectory))){
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
