package eu.curriedpython.aoc2024;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Cpu {
    @Getter
    List<Integer> output = new ArrayList<>();
    @Setter
    @Getter
    List<Integer> program;
    @Setter
    @Getter
    private int regA;
    @Setter
    @Getter
    private int regB;
    @Setter
    @Getter
    private int regC;
    private int pc; // program counter


    public Cpu(List<Integer> program, int regA, int regB, int regC) {
        this.program = program;
        this.regA = regA;
        this.regB = regB;
        this.regC = regC;
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
        if (pc % 2 == 1) {
            throw new IllegalArgumentException("Thou shalt not use odd addresses.");
        }
        int opcode = fetch();
        int operand = fetch();

        switch (opcode & 7) {
            case 0 -> // ADV
                    regA = regA >> (comboOperand(operand) & 7);
            case 1 -> // BXL
                    regB = regB ^ (operand & 7);
            case 2 -> // BST
                    regB = comboOperand(operand) & 7;
            case 3 -> // JNZ
                    pc = (regA == 0) ? pc : operand;
            case 4 -> // BXC
                    regB = regB ^ regC;
            case 5 -> // OUT
                    output.add(comboOperand(operand) & 7);
            case 6 -> //BDV
                    regB = regA >> (comboOperand(operand) & 7);
            default -> // CDV
                    regC = regA >> (comboOperand(operand) & 7);
        }
    }

    private int comboOperand(int op) {
        return switch (op & 7) {
            case 0, 1, 2, 3 -> op;
            case 4 -> regA;
            case 5 -> regB;
            case 6 -> regC;
            default -> throw new IndexOutOfBoundsException();
        };
    }
}
