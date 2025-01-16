package eu.curriedpython.aoc2024;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class Day11 {

    private List<Long> inputStones = List.of(41078L, 18L, 7L, 0L, 4785508L, 535256L, 8154L, 447L);

    private List<Long> applyRule(Long stone) {
        if (stone == 0) {
            return List.of(1L);
        } else if (String.valueOf(stone).length() % 2 == 0) {
            int l = String.valueOf(stone).length();
            return List.of(
                    Long.valueOf(String.valueOf(stone).substring(0, l/2)),
                    Long.valueOf(String.valueOf(stone).substring(l/2, l)));
        } else {
            return List.of(stone * 2024L);
        }
    }

    public void solvePartOne() {
        int blinksPartOne = 25;
        for (int blink = 0; blink < blinksPartOne; blink++) {
            inputStones = inputStones.parallelStream()
                    .map(this::applyRule)
                    .flatMap(Collection::stream)
                    .toList();
        }
        System.out.println("Day11, Part One: " + inputStones.size() + " stones");
    }

    // we can't do the brute force as for the first part - this is getting too expensive
    // the order of the stones doesn't matter really. they don't interact.
    // so as well we can have a map, where the key is the number engraved on a stone,
    // and the value is the count of stones with this value.
    // this way, we can apply the rule only once and update the count of the newly created stones.
    public void solvePartTwo() {
        Map<Long, Long> stones = new IdentityHashMap<>();
        inputStones.forEach(stone -> stones.put(stone, 1L));

        for (int blink = 0; blink < 75; blink++) {
            // nothing yet
        }

        // count stones
        AtomicLong totalCount = new AtomicLong();
        stones.forEach((k, v) -> totalCount.addAndGet(v));
        System.out.println("Day11, Part Two: " + totalCount + " stones");
    }

    public static void main(String[] args) {
        Day11 day11 = new Day11();
        //day11.solvePartOne();
        day11.solvePartTwo();
    }
}