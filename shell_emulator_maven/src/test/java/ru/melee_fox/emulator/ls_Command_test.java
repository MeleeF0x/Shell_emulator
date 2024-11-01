package ru.melee_fox.emulator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ls_Command_test {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
 
    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void ls_Command_Test_normal() throws IOException{
        Emulator emulator = new Emulator();
        emulator.ls_Command("ls");
        Assertions.assertEquals("bin/\r\n" + "dev/\r\n" + "etc/\r\n" + "home/\r\n" + "lib/\r\n" + "media/\r\n" + "mnt/", outputStreamCaptor.toString().trim());
    }

    @Test
    void ls_Command_Test_With_Argument() throws IOException{
        Emulator emulator = new Emulator();
        emulator.ls_Command("ls ls");
        Assertions.assertEquals("command ls ls not found", outputStreamCaptor.toString().trim());
    }

    @Test
    void ls_Command_Test_With_Error() throws IOException{
        Emulator emulator = new Emulator();
        emulator.ls_Command("lsd");
        Assertions.assertEquals("command lsd not found", outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void tearDown(){
        System.setOut(standardOut);
    }
}
