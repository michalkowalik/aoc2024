package eu.curriedpython.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Day12 {

    // the input data should be read to a 2d array
    // the regions can be represented in an <Integer, List<Point>> map
    private Plot[][] garden;


    private void readInput() {
        BufferedReader reader;
        int row = 0;
        try {
            reader = new BufferedReader(new FileReader("day12-input.txt"));
            String line = reader.readLine().strip();
            garden = new Plot[line.length()][line.length()];

            while (line != null) {
                garden[row++] = Arrays.stream(line.split(""))
                        .map(s -> new Plot(s.charAt(0), false))
                        .toArray(Plot[]::new);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setAsVisited(int row, int column) {
        garden[row][column] =
                new Plot(garden[row][column].plant(), true);
    }

    private List<PlotPoint> findNeighbours(int row, int column) {
        var n = List.of(
                new PlotPoint(row - 1, column, 'x'),
                new PlotPoint(row + 1, column, 'x'),
                new PlotPoint(row, column - 1, 'x'),
                new PlotPoint(row, column + 1, 'x'));

        return n.stream().filter(p -> p.column() >= 0 && p.column() < garden.length &&
                        p.row() >= 0 && p.row() < garden[row].length)
                .map(e -> new PlotPoint(e.row(), e.column(), garden[e.row()][e.column()].plant()))
                .toList();
    }

    // find region startin at point (row, column)
    private List<PlotPoint> findRegion(int row, int column) {
        Stack<PlotPoint> stack = new Stack<>();
        List<PlotPoint> plotsInRegion = new ArrayList<>();

        var startPlot = new PlotPoint(row, column, garden[row][column].plant());
        stack.push(startPlot);
        plotsInRegion.add(startPlot);
        setAsVisited(row, column);

        while (!stack.isEmpty()) {
            var plot = stack.pop();

            findNeighbours(plot.row(), plot.column()).stream()
                    .filter(p -> garden[p.row()][p.column()].plant() == plot.plant() &&
                            !garden[p.row()][p.column()].visited()).forEach(pp -> {
                        stack.push(pp);
                        plotsInRegion.add(pp);
                        setAsVisited(pp.row(), pp.column());
                    });
        }
        return plotsInRegion;
    }

    // returns number of neighbouring plots with different plants
    // and on the border
    private int calculateFence(PlotPoint point) {
        var n = List.of(
                new PlotPoint(point.row() - 1, point.column(), 'x'),
                new PlotPoint(point.row() + 1, point.column(), 'x'),
                new PlotPoint(point.row(), point.column() - 1, 'x'),
                new PlotPoint(point.row(), point.column() + 1, 'x'));

        return n.stream().filter(p -> (p.row() >= 0 && p.row() < garden.length &&
                        p.column() >= 0 && p.column() < garden.length &&
                        garden[p.row()][p.column()].plant() != point.plant()) ||
                        (p.row() < 0 || p.column() < 0 ||
                                p.row() >= garden.length || p.column() >= garden.length))
                .toList().size();
    }

    public void partOne(Map<Integer, List<PlotPoint>> regions) {
        AtomicLong fenceCost = new AtomicLong();
        regions.forEach((k, v) -> {
            var fenceSize = v.stream().map(this::calculateFence).mapToInt(Integer::intValue).sum();
            fenceCost.addAndGet((long) fenceSize * v.size());
        });
        System.out.println("Day 12, part One: " + fenceCost);
    }

    public void partTwo(Map<Integer, List<PlotPoint>> regions) {
        System.out.println("Day 12, part Two: ");
    }

    private Map<Integer, List<PlotPoint>> getRegions() {
        Map<Integer, List<PlotPoint>> regions = new IdentityHashMap<>();
        int regionId = 0;
        for (int row = 0; row < garden.length; row++) {
            for (int col = 0; col < garden.length; col++) {
                if (garden[row][col].visited()) {
                    continue;
                }
                // we haven't visited that particular plot yet.
                regions.put(regionId++, findRegion(row, col));
            }
        }
        return regions;
    }

    public static void main(String[] args) {
        Day12 day12 = new Day12();
        day12.readInput();
        var regions = day12.getRegions();
        day12.partOne(regions);
        day12.partTwo(regions);
    }
}
