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

    public static final String THE_RIGHT_JSON = "the_right_json";
    public static final String THE_LEFT_JSON = "the_left_json";

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
        memoryStoreManager.storeRightPart("storeRightPart", THE_RIGHT_JSON);

        assertEquals(THE_RIGHT_JSON, memoryStoreManager.retrievePartsById("storeRightPart").getRight());
        assertNull(memoryStoreManager.retrievePartsById("storeRightPart").getLeft());
    }

    @Test
    public void storeLeftPart() {
        memoryStoreManager.storeLeftPart("storeLeftPart", THE_LEFT_JSON);

        assertEquals(THE_LEFT_JSON, memoryStoreManager.retrievePartsById("storeLeftPart").getLeft());
        assertNull(memoryStoreManager.retrievePartsById("storeLeftPart").getRight());
    }

    @Test
    public void storeBothParts() {
        memoryStoreManager.storeRightPart("storeBothParts", THE_RIGHT_JSON);
        memoryStoreManager.storeLeftPart("storeBothParts", THE_LEFT_JSON);

        assertEquals(THE_RIGHT_JSON, memoryStoreManager.retrievePartsById("storeBothParts").getRight());
        assertEquals(THE_LEFT_JSON, memoryStoreManager.retrievePartsById("storeBothParts").getLeft());
    }

}
