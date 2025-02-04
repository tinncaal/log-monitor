package org.cata.lseg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TestMain {

    @Test
    void testRun() {
        String[] args = new String[] { "src/test/resources/logs.log" };
        Main.main(args);
    }

    @Test
    void testNoArguments() {
        String[] args = new String[] {};
        assertThrows(IllegalStateException.class, () -> Main.main(args));
    }

    @Test
    void testFileNotFound() {
        String[] args = new String[] { "dummy.log" };
        assertThrows(IllegalStateException.class, () -> Main.main(args));
    }

}
