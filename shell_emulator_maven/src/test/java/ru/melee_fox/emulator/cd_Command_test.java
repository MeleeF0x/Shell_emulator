package ru.melee_fox.emulator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class cd_Command_test {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
 
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void cd_Command_Test_normal() throws IOException{
        Emulator emulator = new Emulator("src\\\\main\\\\java\\\\ru\\\\melee_fox\\\\Virtual_File_System.zip");
        emulator.cd_Command("cd bin");
        String result = emulator.getCurrentDirectory();
        Assertions.assertEquals("bin", result);
    }
    
    @Test
    void cd_Command_Test_With_Wrong_Argument() throws IOException{
        Emulator emulator = new Emulator("src\\\\main\\\\java\\\\ru\\\\melee_fox\\\\Virtual_File_System.zip");
        emulator.cd_Command("cd wrong_argument");
        String result = emulator.getCurrentDirectory();
        Assertions.assertEquals("", result);
        Assertions.assertEquals("command cd wrong_argument not found", outputStreamCaptor.toString().trim());

    }

    @Test
    void cd_Command_Test_With_Error() throws IOException{
        Emulator emulator = new Emulator("src\\\\main\\\\java\\\\ru\\\\melee_fox\\\\Virtual_File_System.zip");
        emulator.cd_Command("cdd bin");
        String result = emulator.getCurrentDirectory();
        Assertions.assertEquals("", result);
        Assertions.assertEquals("command cdd bin not found", outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown(){
        System.setOut(standardOut);
    }
}
