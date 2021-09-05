package com.jwhh.stiawareness.database;

import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseManagerTest {

    @Test
    public void deleteDoctor() {
        DatabaseManager databaseManager = null;
        boolean result = databaseManager.deleteDoctor(0);
        AssertThat(result).isFalse();
    }
}