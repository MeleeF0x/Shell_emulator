package app;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import emulator.Emulator;

public class Main {
    public static void main(String[] args) throws IOException{
        boolean program_work_tester = false;
        Emulator emulator = new Emulator();
        while(!program_work_tester){
            program_work_tester = emulator.command_Reader();
        }
    }
}
