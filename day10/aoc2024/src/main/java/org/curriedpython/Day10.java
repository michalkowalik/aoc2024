package org.curriedpython;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("CallToPrintStackTrace")
public class Day10 {

    static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private int[][] map;

    private void readInput() {
        BufferedReader reader;
        int i = 0;

        try {
            reader = new BufferedReader(new FileReader("day10-test.txt"));
            String line = reader.readLine();
            this.map = new int[line.length()][line.length()];

            while (line != null) {
                map[i++] = Arrays.stream(line.split(""))
                        .map(Integer::parseInt)
                        .mapToInt(j -> j)
                        .toArray();
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // find all trailheads, return position as an array of 2: row, column
    private List<int[]> findTrailheads() {
        List<int[]> trailheads = new ArrayList<>();

        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map.length; j++) {
                if (map[i][j] == 0) {
                    trailheads.add(new int[]{i, j});
                }
            }
        }
        return trailheads;
    }

    // TODO: check upped bound of the array!
    private List<int[]> findNext(int[] position) {
        List<int[]> positions = List.of(
                new int[]{position[0] - 1, position[1]},
                new int[]{position[0] + 1, position[1]},
                new int[]{position[0], position[1] - 1},
                new int[]{position[0], position[1] + 1}
        );

        return positions.stream()
                .filter(x -> x[0] >=0
                        && x[1] >= 0
                        && this.map[x[0]][x[1]] == this.map[position[0]][position[1]] + 1)
                .collect(Collectors.toList());

    }

    // here starts the
    private void findPaths(int[] trailhead, Set<Point> nines) {
        if (this.map[trailhead[0]][trailhead[1]] == 9) {
            nines.add(new Point(trailhead[0], trailhead[1]));
        }

        var nextSteps = findNext(trailhead);
        if (!nextSteps.isEmpty()) {
            nextSteps.forEach(x -> findPaths(x, nines));
        }
    }


    public int trailHeadScore(int[] trailhead) {
        Set<Point> nines = new HashSet<>();
        findPaths(trailhead, nines);
        return nines.size();
    }

    public void solvePartOne() {
        // find zeros:
        var trailHeads = findTrailheads();

        // find paths for each trailHead:
        // trailHeads.stream().forEach(this::findPaths);
        // here starts the recursive magic
        System.out.println("Trailhead has score of " + trailHeadScore(trailHeads.getFirst()) );

    }


    public static void main(String[] args) {
        var day10 = new Day10();

        day10.readInput();
        day10.solvePartOne();
    }
}