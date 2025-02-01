package eu.curriedpython.aoc2024;

import java.util.List;
import java.util.stream.Collectors;

public class Day17 {

    private void printOutput(List<Integer> output) {
        System.out.println(output.stream().
            map(Object::toString).
            collect(Collectors.joining(",")));

    }

    private void part1() {
        int regA = 34615120;
        int regB = 0;
        int regC = 0;
        List<Integer> program = List.of(2,4,1,5,7,5,1,6,0,3,4,3,5,5,3,0);

        Cpu cpu = new Cpu(program, regA, regB, regC);
        cpu.run();
        printOutput(cpu.getOutput());

    }

    private void part2() {
        List<Integer> program = List.of(0,3,5,4,3,0);
        Cpu cpu = new Cpu(program, 0, 0, 0);

        cpu.setRegA(117440);
        cpu.run();
        printOutput(cpu.getOutput());

    }

    public static void main(String[] args) {
        var day17 = new Day17();
        day17.part1();
        day17.part2();

        System.out.println("Done.");
    }
}