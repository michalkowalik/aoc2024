package eu.curriedpython.aoc2024;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day17 {

    public static void main(String[] args) {
        var day17 = new Day17();
        day17.part1();
        day17.part2();

        System.out.println("Done.");
    }

    private void printOutput(List<Integer> output) {
        System.out.println(output.stream().
                map(Object::toString).
                collect(Collectors.joining(",")));

    }

    /*
     *   analysing the program we get the following:
     *   2,4: BST(A) -> copy 3 lowest bit of RegA to RegB
     *   1,5: BXL 5  -> B XOR 0101
     *   7,5: CDV(B) -> C = A >> B
     *   1,6: BXL 6  -> B XOR 0110
     *   0,3: ADV 3  -> A = A >> 3  ! here we shift regA 3 bits to the left
     *   4,3: BXC 3  -> B = B XOR C
     *   5,5: OUT(B) => that's what we print
     *   3,0: JNZ 0  -> loop back if A != 0
     *
     *   As we need to know the 0..7 _older_ bits of A to calculate the output,
     *   we will work our way down by reversing the program & shifting RegA 3 bits to the right before
     *   appending next 3 bits.
     *
     *   we need to track _multiple_ partial values of A.
     *   not all of them lead to a happy ending -> use recursion
     */
    private void findA(List<Integer> program, List<Integer> temp, long a, List<Long> candidates) {
        List<Long> partAs = findNextBits(program, a, temp);
        if (program.size() == temp.size()) {
            candidates.addAll(partAs);
            return;
        }

        List<Integer> nextTemp = new ArrayList<>(temp);
        nextTemp.addFirst(program.reversed().get(temp.size()));
        partAs.forEach(nextA -> findA(program, nextTemp, nextA, candidates));
    }

    private List<Long> findNextBits(List<Integer> program, long a, List<Integer> temp) {
        var nextBits = new ArrayList<Long>();
        Cpu cpu = new Cpu(program, 0L, 0L, 0L);

        for (int i = 0; i <= 7; i++) {
            long tempA = (a << 3) | i;
            cpu.reset();
            cpu.setRegA(tempA);
            cpu.run();
            if (cpu.getOutput().equals(temp)) {
                nextBits.add(tempA);
            }
        }
        return nextBits;
    }

    private void part1() {
        long regA = 34615120;
        long regB = 0;
        long regC = 0;
        List<Integer> program = List.of(2, 4, 1, 5, 7, 5, 1, 6, 0, 3, 4, 3, 5, 5, 3, 0);

        Cpu cpu = new Cpu(program, regA, regB, regC);
        cpu.run();
        System.out.print("Part 1: ");
        printOutput(cpu.getOutput());

    }

    private void part2() {
        List<Integer> program = List.of(2, 4, 1, 5, 7, 5, 1, 6, 0, 3, 4, 3, 5, 5, 3, 0);
        List<Long> candidates = new ArrayList<>();

        findA(program,
                List.of(program.getLast()),
                0,
                candidates);

        var min = candidates.stream().min(Long::compareTo).orElseThrow();
        System.out.println("Part 2: " + min);
    }
}