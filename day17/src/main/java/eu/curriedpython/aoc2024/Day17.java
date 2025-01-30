package eu.curriedpython.aoc2024;

import java.util.List;

public class Day17 {
    public static void main(String[] args) {

        int regA = 34615120;
        //int regA = 729;
        int regB = 0;
        int regC = 0;
        List<Integer> program = List.of(2,4,1,5,7,5,1,6,0,3,4,3,5,5,3,0);
        //List<Integer> program = List.of(0,1,5,4,3,0);

        Cpu cpu = new Cpu(program, regA, regB, regC);
        cpu.run();

        System.out.println("Done.");
    }
}