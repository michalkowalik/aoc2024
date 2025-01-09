package eu.curriedpython.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

@SuppressWarnings("CallToPrintStackTrace")
public class Day25 {

    private List<char[][]> inputData;
    List<List<Integer>> keys = new ArrayList<>();
    List<List<Integer>> locks = new ArrayList<>();

    public Day25() {
        inputData = this.readInput();
    }

    private List<char[][]> readInput() {
        inputData = new ArrayList<>();
        BufferedReader reader;
        int lineInDiagram = 0;
        char[][] diagram = new char[7][5];

        // we've got a group of diagrams separated with an empty line.
        try {
            reader = new BufferedReader(new FileReader("day25-input.txt"));
            String line = reader.readLine().strip();

            while (line != null) {
                if (line.isBlank()) {
                    inputData.add(diagram);
                    lineInDiagram = 0;
                    line = reader.readLine().strip();
                }

                if (lineInDiagram == 0) {
                    diagram = new char[7][5];
                }
                diagram[lineInDiagram++] = line.toCharArray();


                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
            // add last diagram
            inputData.add(diagram);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputData;
    }

    List<Integer> decode(char[][] diagram) {
        List<Integer> pins = new ArrayList<>();

        for (int column = 0; column < 5; column++) {
            int pinCount = 0;
            for (int row = 0; row < 7; row++) {
                if (diagram[row][column] == '#') {
                    pinCount++;
                }
            }
            pins.add(pinCount);
        }
        return pins;
    }

    private void decodeAll() {
        inputData.forEach(x -> {
            if (x[0][0] == '#') {
                this.keys.add(decode(x));
            } else {
                this.locks.add(decode(x));
            }
        });
    }

    private void solvePart1() {
        var x = this.keys.stream().
                map(k -> {
                    return this.locks.stream().map(l -> lockMatchKey(l, k)).toList();
                }).
                flatMap(Collection::stream).
                filter(z -> z).
                toList();
        System.out.println("Part One: there are " + x.size() + " matching combinations");
    }

    private boolean lockMatchKey(List<Integer> lock, List<Integer> key) {
        return IntStream.range(0, Math.min(lock.size(), key.size()))
                .mapToObj(i -> lock.get(i) + key.get(i))
                .filter(x -> x > 7).findAny().isEmpty();
    }

    public static void main(String[] args) {
        Day25 day25 = new Day25();
        day25.decodeAll();
        day25.solvePart1();
    }
}