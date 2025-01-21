package eu.curriedpython.aoc2024;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("ALL")
public class Day12 {


    // we have to think a second how do we want to present the regions.
    // PLOT: should have x,y, plant name
    // and should be able to be "colored" as already visited

    // the input data should be read to a 2d array
    // the regions can be represented in an <Integer, List<Point>> map

    private Plot[][] garden;
    private Map<Integer, List<PlotPoint>> regions;

    public Day12(Map<Integer, List<PlotPoint>> regions) {
        this.regions = regions;
    }

    private void readInput() {
        BufferedReader reader;
        int row = 0;
        try {
            reader = new BufferedReader(new FileReader("day12-input-test-1.txt"));
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

    // find region startin at point (row, column)
    private List<PlotPoint> findRegion(int row, int column) {
        Stack<PlotPoint> stack = new Stack<>();
        List<PlotPoint> plotsInRegion = new ArrayList<>();

        // we're searching regions starting from the top-left.
        // therefore there's no need to check what's up/left from the starting point
        // Go right -> down.
        // that way, each plot has only 2 neighbours that need to be checked
        var startPlot = new PlotPoint(row, column, garden[row][column].plant());
        stack.push(startPlot);
        plotsInRegion.add(startPlot);
        setAsVisited(row, column);

        while (!stack.isEmpty()) {
            var plot = stack.pop();

            // check right
            if ((plot.column() + 1) < garden.length &&
                    garden[plot.row()][plot.column() + 1].plant() == plot.plant() &&
                    !garden[plot.row()][plot.column() + 1].visited()) {
                var pp = new PlotPoint(plot.row(), plot.column() + 1, plot.plant());
                stack.push(pp);
                plotsInRegion.add(pp);
                setAsVisited(plot.row(), plot.column() + 1);
            }
            // check down
            if ((plot.row() + 1) < garden.length &&
                    garden[plot.row() + 1][plot.column()].plant() == plot.plant() &&
                    !garden[plot.row() + 1][plot.column()].visited()) {
                var pp = new PlotPoint(plot.row() + 1, plot.column(), plot.plant());
                stack.push(pp);
                plotsInRegion.add(pp);
                setAsVisited(plot.row() + 1, plot.column());
            }
        }
        return plotsInRegion;
    }

    public void partOne() {
        int regionId = 0;
        for (int row = 0; row < garden.length; row++) {
            for (int col = 0; col < garden.length; col++) {
                if (garden[row][col].visited()) {
                    continue;
                }
                // we haven't visited that particular plot yet.
                // that means, we need to do the search and find all adjacent plots with the same plant growing on them
                // and save them in a map
                regions.put(regionId++, findRegion(row, col));
            }
        }

        // once we have defined the regions, we can calculate the cost of the fence for each region:
        System.out.println("Day 12, part One: ");
    }


    public static void main(String[] args) {
        Day12 day12 = new Day12(new IdentityHashMap<>());
        day12.readInput();
        day12.partOne();
    }
}
