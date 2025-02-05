package eu.curriedpython.aoc2024;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class CpuTest {

    @Test
    void test_bst() {
        Cpu cpu = new Cpu(List.of(2, 6), 0L, 0L, 9L);
        cpu.run();
        assertEquals(1, cpu.getRegB());
    }

    @Test
    void test_run_01() {
        Cpu cpu = new Cpu(List.of(5,0,5,1,5,4), 10L, 0L,0L);
        cpu.run();
        assertEquals(List.of(0, 1, 2), cpu.getOutput());
    }

    @Test
    void test_run_02() {
        Cpu cpu = new Cpu(List.of(0,1,5,4,3,0), 2024L, 0L,0L);
        cpu.run();
        assertEquals(List.of(4,2,5,6,7,7,7,7,3,1,0), cpu.getOutput());
        assertEquals(0, cpu.getRegA());
    }

    @Test
    void test_bxl() {
        Cpu cpu = new Cpu(List.of(1, 7), 0L, 29L, 0L);
        cpu.run();
        assertEquals(26, cpu.getRegB());
    }

    @Test
    void test_bxc() {
        Cpu cpu = new Cpu(List.of(4, 0), 0L, 2024L,43690L);
        cpu.run();
        assertEquals(44354, cpu.getRegB());
    }


}