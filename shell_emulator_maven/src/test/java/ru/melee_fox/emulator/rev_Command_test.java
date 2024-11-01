package ru.melee_fox.emulator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class rev_Command_test {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
 
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void rev_Command_Test_File_read() throws IOException{
        Emulator emulator = new Emulator();        
        emulator.cd_Command("cd home");
        emulator.rev_Command("rev test.txt");
        Assertions.assertEquals("tset tset tset", outputStreamCaptor.toString().trim());
    }

    @Test
    void rev_Command_Test_Input_String() throws IOException{
        Emulator emulator = new Emulator();
        emulator.rev_Command("rev test_test_test");
        Assertions.assertEquals("tset_tset_tset", outputStreamCaptor.toString().trim());
    }

    @Test
    void rev_Command_Test_With_Error() throws IOException{
        Emulator emulator = new Emulator();
        emulator.rev_Command("rv test_test_test");
        Assertions.assertEquals("command rv test_test_test not found", outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown(){
        System.setOut(standardOut);
    }
}
