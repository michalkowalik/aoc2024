package eu.curriedpython.aoc2024;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Cpu {
    private Integer regA;
    private Integer regB;
    private Integer regC;

    List<Integer> output = new ArrayList<>();

    private int pc; // program counter
    private List<Integer> program;

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

    private int comboOperand(int op) {
        return switch (op & 7) {
            case 0, 1, 2, 3 -> op;
            case 4 -> regA;
            case 5 -> regB;
            case 6 -> regC;
            default -> throw new IndexOutOfBoundsException();
        };
    }

    // opcodes
    private void adv(int operand) {
        double nom = (double) regA;
        double denom = (double) (2 << (comboOperand(operand)-1));
        regA = (int) Math.floor(nom / denom);
    }

    private void bxl(int operand) {
        regB = regB ^ (operand & 7);
    }

    private void bst(int operand) {
        regB = comboOperand(operand) & 7;
    }

    private void jnz(int operand) {
        if (regA == 0) {
            return;
        }
        pc = operand;
    }

    private void bxc(int operand) {
        regB = regB ^ regC;
    }

    private void out(int operand) {
        output.add(comboOperand(operand) & 7);
    }

    private void bdv(int operand) {
        double nom = (double) regA;
        double denom = (double) (2 << comboOperand(operand)-1);
        regB = (int) Math.floor(nom / denom);
    }

    private void cdv(int operand) {
        double nom = (double) regA;
        double denom = (double) (2 << comboOperand(operand)-1);
        regC = (int) Math.floor(nom / denom);
    }

}
