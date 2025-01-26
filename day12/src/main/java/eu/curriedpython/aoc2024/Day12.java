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

    // find region starting at point (row, column)
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
        regions.forEach((_, v) -> {
            var fenceSize = v.stream().map(this::calculateFence).mapToInt(Integer::intValue).sum();
            fenceCost.addAndGet((long) fenceSize * v.size());
        });
        System.out.println("Day 12, part One: " + fenceCost);
    }


    private boolean isOutsideGarden(PlotPoint p) {
        return p.row() < 0 || p.row() >= garden.length || p.column() < 0 || p.column() >= garden.length;
    }

    private char gardenPlant(PlotPoint p) {
        if (isOutsideGarden(p)) {
            return 'x';
        }
        return this.garden[p.row()][p.column()].plant();
    }

    /**
     * we take the three L-shaped plots.
     * 3x the same Plant -> check diagonal for the concave corner
     * 2x the same Plant -> no corner
     * 1x the same Plant -> corner
     * outside the garden is to be treated as a different plant
     * <p>
     * rotate L four times to cover all corners.
     * <p>
     * it's an ugly code tbh
     *
     * @param plot plot
     * @return count of corners
     */
    private int countCorners(PlotPoint plot) {

        var els = List.of(
                List.of(new PlotPoint(plot.row(), plot.column() - 1, 'x'), new PlotPoint(plot.row() - 1, plot.column(), 'x')),
                List.of(new PlotPoint(plot.row(), plot.column() - 1, 'x'), new PlotPoint(plot.row() + 1, plot.column(), 'x')),
                List.of(new PlotPoint(plot.row() + 1, plot.column(), 'x'), new PlotPoint(plot.row(), plot.column() + 1, 'x')),
                List.of(new PlotPoint(plot.row() - 1, plot.column(), 'x'), new PlotPoint(plot.row(), plot.column() + 1, 'x')));

        // now, let's filter that
        return els.stream().map(p -> {
            var x = p.stream().filter(pp -> isOutsideGarden(pp) || gardenPlant(pp) != plot.plant()).count();
            return switch ((int) x) {
                case 1 -> 0; // no corner
                case 2 -> 1; // corner
                default -> {  // can be only 2 (concave). TODO: finish
                    var row = p.stream().filter(pp -> !Objects.equals(pp.row(), plot.row())).findFirst().orElseThrow();
                    var col = p.stream().filter(pp -> !Objects.equals(pp.column(), plot.column())).findFirst().orElseThrow();
                    yield garden[row.row()][col.column()].plant() == plot.plant() ? 0 : 1;
                }
            };
        }).mapToInt(Integer::intValue).sum();

    }


    public void partTwo(Map<Integer, List<PlotPoint>> regions) {
        // let's find all the corners
        AtomicLong fenceCost = new AtomicLong();
        regions.forEach((k, v) -> {
            var corners = v.stream().map(this::countCorners).mapToInt(Integer::intValue).sum();
            fenceCost.addAndGet((long) corners * v.size());
        });
        System.out.println("Day 12, part Two: " + fenceCost);
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
