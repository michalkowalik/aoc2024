package eu.curriedpython.aoc2024;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day17 {

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
     *   we will work our way down by reversing the program & shifting A 3 bits to the right before
     *   appending next 3 bits.
     *
     *   we need to track _multiple_ partial values of A.
     *   not all of them lead to a happy ending -> use recursion
     */
    private int findA(List<Integer> program) {
        Integer a = 0;
        List<Integer> temp = new ArrayList<>();
        for(int b : program.reversed()) {
            temp.addFirst(b);
            System.out.println("adding " + b);
            var nextBits = findNextBits(program, a, temp);
            System.out.println("next bits " + nextBits);

        }
            return a;
    }


    private List<Integer> findNextBits(List<Integer> program, Integer a, List<Integer> temp) {
        var nextBits = new ArrayList<Integer>();
        Cpu cpu = new Cpu(program, 0, 0, 0);

            for (int i = 0; i <= 7; i++) {
                Integer tempA = (a << 3) | i;
                cpu.setRegA(tempA);
                cpu.run();
                printOutput(cpu.getOutput());
                if (cpu.getOutput().equals(temp)) {
                    nextBits.add(tempA);
                }
            }
        return nextBits;
    }

    private void part1() {
        int regA = 34615120;
        int regB = 0;
        int regC = 0;
        List<Integer> program = List.of(2,4,1,5,7,5,1,6,0,3,4,3,5,5,3,0);

        Cpu cpu = new Cpu(program, regA, regB, regC);
        cpu.run();
        System.out.print("Part 1: ");
        printOutput(cpu.getOutput());

    }

    private void part2() {
        List<Integer> program = List.of(2,4,1,5,7,5,1,6,0,3,4,3,5,5,3,0); //List.of(0,3,5,4,3,0);
        System.out.println("Part 2: A = " + findA(program));
    }

    public static void main(String[] args) {
        var day17 = new Day17();
        day17.part1();
        day17.part2();

        System.out.println("Done.");
    }
}