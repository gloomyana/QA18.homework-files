package ru.gloomyana;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonFileParsingTest {
    static private final ClassLoader cl = JsonFileParsingTest.class.getClassLoader();

    @Test
    void parseJsonTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = cl.getResourceAsStream("character.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            Character character = objectMapper.readValue(isr, Character.class);

            Assertions.assertEquals("Kira", character.name);
            Assertions.assertEquals("blood elf", character.race);
            Assertions.assertEquals("Hunter", character.classAndSpec.className);
            Assertions.assertEquals("Beast Mastery", character.classAndSpec.specName);
            Assertions.assertArrayEquals(new String[]{"Kill Command", "Cobra Shot", "Bestial Wrath"},
                    character.abilities);
            Assertions.assertTrue(character.isActive);
            Assertions.assertEquals(88, character.killsCount);
            Assertions.assertEquals(12, character.deathsCount);
            Assertions.assertEquals(130, character.completedQuestsCount);
        }
    }
}
