package ru.melee_fox.emulator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class cat_Command_test {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
 
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void cat_Command_Test_File_read() throws IOException{
        Emulator emulator = new Emulator("src\\\\main\\\\java\\\\ru\\\\melee_fox\\\\Virtual_File_System.zip");        
        emulator.cd_Command("cd home");
        emulator.cat_Command("cat test.txt");
        Assertions.assertEquals("test test test", outputStreamCaptor.toString().trim());
    }

    @Test
    void cat_Command_Test_Wrong_Argument() throws IOException{
        Emulator emulator = new Emulator("src\\\\main\\\\java\\\\ru\\\\melee_fox\\\\Virtual_File_System.zip");
        emulator.cat_Command("cat test_wrong_argument.txt");
        Assertions.assertEquals("command cat test_wrong_argument.txt not found", outputStreamCaptor.toString().trim());
    }

    @Test
    void cat_Command_Test_With_Error() throws IOException{
        Emulator emulator = new Emulator("src\\\\main\\\\java\\\\ru\\\\melee_fox\\\\Virtual_File_System.zip");        
        emulator.cd_Command("cd home");
        emulator.cat_Command("ct test.txt");
        Assertions.assertEquals("command ct test.txt not found", outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown(){
        System.setOut(standardOut);
    }
}