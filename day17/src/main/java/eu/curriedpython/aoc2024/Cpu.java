package eu.curriedpython.aoc2024;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cpu {
    @Getter List<Integer> output = new ArrayList<>();
    @Getter private int regA;
    @Getter private int regB;
    @Getter private int regC;
    private int pc; // program counter
    @Getter List<Integer> program;

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
        System.out.println(output.stream().
                map(Object::toString).
                collect(Collectors.joining(",")));
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
                    regA = dv(operand);
            case 1 -> // BLX
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
                    regB = dv(operand);
            case 7 -> // CDV
                    regC = dv(operand);
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

    private int dv(int operand) {
        return (int) Math.floor((double) regA / (1 << comboOperand(operand)));
    }
}
