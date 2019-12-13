package com.github.juandavidgc.scalableweb.external.memory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Jgutierrez on 13/12/2019.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {MemoryStoreManagerTests.Config.class})
public class MemoryStoreManagerTests {

    @Autowired
    private MemoryStoreManager memoryStoreManager;

    @Configuration
    public static class Config {

        @Bean
        public MemoryStoreManager getMemoryStoreManager() {
            return new MemoryStoreManager();
        }

    }

    @Test
    public void storeRightPart() {
        memoryStoreManager.storeRightPart("storeRightPart", "the_right_json");

        assertEquals("the_right_json", memoryStoreManager.retrievePartsById("storeRightPart").getRight());
        assertNull(memoryStoreManager.retrievePartsById("storeRightPart").getLeft());
    }

    @Test
    public void storeLeftPart() {
        memoryStoreManager.storeLeftPart("storeLeftPart", "the_left_json");

        assertEquals("the_left_json", memoryStoreManager.retrievePartsById("storeLeftPart").getLeft());
        assertNull(memoryStoreManager.retrievePartsById("storeLeftPart").getRight());
    }

    @Test
    public void storeBothParts() {
        memoryStoreManager.storeRightPart("storeBothParts", "the_right_json");
        memoryStoreManager.storeLeftPart("storeBothParts", "the_left_json");

        assertEquals("the_right_json", memoryStoreManager.retrievePartsById("storeBothParts").getRight());
        assertEquals("the_left_json", memoryStoreManager.retrievePartsById("storeBothParts").getLeft());
    }

}
