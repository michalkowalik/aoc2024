package eu.curriedpython.aoc2024;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Cpu {
    private Integer RegA;
    private Integer RegB;
    private Integer RegC;

    private int pc; // program counter
    private List<Integer> program;

    public Cpu(List<Integer> program, int regA, int regB, int regC) {
        this.program = program;
        this.RegA = regA;
        this.RegB = regB;
        this.RegC = regC;
    }

    public void run() {
        while (pc < program.size()) {
            step();
        }
    }

    private int fetch() {
        if (pc >= program.size()) {
            throw new IndexOutOfBoundsException();
        }
        return program.get(pc++);
    }

    public void step() {
        int opcode = fetch();
        int operand = fetch();

        switch (opcode & 7) {
            case 0 -> adv(operand);
            case 1 -> bxl(operand);
            case 2 -> bst(operand);
            case 3 -> jnz(operand);
            case 4 -> bxc(operand);
            case 5 -> out(operand);
            case 6 -> bdv(operand);
            case 7 -> cdv(operand);
        }
    }

    // opcodes
    private void adv(int operand) {

    }

    private void bxl(int operand) {
    }

    private void bst(int operand) {
    }

    private void jnz(int operand) {
    }

    private void bxc(int operand) {
    }

    private void out(int operand) {
    }

    private void bdv(int operand) {
    }

    private void cdv(int operand) {
    }

}
