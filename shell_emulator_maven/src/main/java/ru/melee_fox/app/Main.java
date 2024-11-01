package ru.melee_fox.app;

import java.io.IOException;

import ru.melee_fox.emulator.Emulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class ReadXmlFile {
    public static String readXmlFile() {
        StringBuilder xmlContent = new StringBuilder();
        
        try {
            File xmlFile = new File("src/main/java/ru/melee_fox/config.xml");
            Scanner scanner = new Scanner(xmlFile);
            
            while (scanner.hasNextLine()) {
                xmlContent.append(scanner.nextLine()).append("\n");
                String xmlString = xmlContent.toString();
                if(xmlString.contains("<Virtual_File_System_Path>") && xmlString.contains("<Virtual_File_System_Path/>")){
                    xmlString = xmlString.replace("<Virtual_File_System_Path>", "").replace("<Virtual_File_System_Path/>", "").trim();
                    System.out.println(xmlString);
                    scanner.close();
                    return xmlString;
                    }
                }
                scanner.close();
            } 
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
public class Main {
    public static void main(String[] args) throws IOException{
        String path_to_virtual_system = ReadXmlFile.readXmlFile();
        boolean program_work_tester = false;
        Emulator emulator = new Emulator(path_to_virtual_system);
        while(!program_work_tester){
            program_work_tester = emulator.command_Reader();
        }
    }
}
