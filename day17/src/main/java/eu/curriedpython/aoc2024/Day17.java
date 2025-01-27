package eu.curriedpython.aoc2024;

import java.util.List;

public class Day17 {
    public static void main(String[] args) {

        int regA = 729;
        int regB = 0;
        int regC = 0;
        List<Integer> program = List.of(0, 1, 5, 4, 3, 0);

        Cpu cpu = new Cpu(program, regA, regB, regC);
        cpu.run();

        System.out.println("Done.");
    }
}