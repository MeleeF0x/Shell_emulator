package ru.melee_fox.emulator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class exit_Command_test {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
 
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void exit_Command_Test_normal() throws IOException{
        Emulator emulator = new Emulator("src\\\\main\\\\java\\\\ru\\\\melee_fox\\\\Virtual_File_System.zip");
        boolean result = emulator.exit_Command("exit");
        Assertions.assertEquals(true, result);
    }

    @Test
    void exit_Command_Test_With_Argument() throws IOException{
        Emulator emulator = new Emulator("src\\\\main\\\\java\\\\ru\\\\melee_fox\\\\Virtual_File_System.zip");
        boolean result = emulator.exit_Command("exit program");
        Assertions.assertEquals(false, result);
        Assertions.assertEquals("command exit program not found", outputStreamCaptor.toString().trim());
    }

    @Test
    void exit_Command_Test_With_Error() throws IOException{
        Emulator emulator = new Emulator("src\\\\main\\\\java\\\\ru\\\\melee_fox\\\\Virtual_File_System.zip");
        boolean result = emulator.exit_Command("ext");
        Assertions.assertEquals(false, result);
        Assertions.assertEquals("command ext not found", outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown(){
        System.setOut(standardOut);
    }
}
