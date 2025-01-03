#!/usr/bin/env python


def pair_in_range(pair: (int, int), size: int) -> bool:
    return 0 <= pair[0] < size and 0 <= pair[1] < size


def solve_part_1(antennas: dict[str, list[tuple[int, int]]], map_size: int) -> int:
    antinodes: set[tuple[int, int]] = set()

    # find pairs of antennas of the same frequency
    for antenna in antennas:
        for a in range(len(antennas[antenna])-1):
            for b in range(a+1, len(antennas[antenna])):
                pair = (antennas[antenna][a], antennas[antenna][b])
                # calculate the vector
                dx = pair[1][0] - pair[0][0]
                dy = pair[1][1] - pair[0][1]

                antinodes.add((pair[0][0]-dx, pair[0][1]-dy))
                antinodes.add((pair[1][0]+dx, pair[1][1]+dy))
    return len([x for x in antinodes if pair_in_range(x, map_size)])


def solve_part_2(antennas, map_size) -> int:
    antinodes: set[tuple[int, int]] = set()

    # add antennas to the antinodes:
    for antenna in antennas:
        if len(antennas[antenna]) > 1:
            for position in antennas[antenna]:
                antinodes.add(position)

    for antenna in antennas:
        for a in range(len(antennas[antenna])-1):
            for b in range(a+1, len(antennas[antenna])):
                pair = (antennas[antenna][a], antennas[antenna][b])
                # calculate the vector
                dx = pair[1][0] - pair[0][0]
                dy = pair[1][1] - pair[0][1]

                factor = 1
                while pair_in_range((pair[0][0]-dx*factor, pair[0][1]-dy*factor), map_size):
                    antinodes.add((pair[0][0]-dx*factor, pair[0][1]-dy*factor))
                    factor += 1

                factor = 1
                while pair_in_range((pair[1][0]+dx*factor, pair[1][1]+dy*factor), map_size):
                    antinodes.add((pair[1][0]+dx*factor, pair[1][1]+dy*factor))
                    factor += 1

    return len(antinodes)


def main():
    frequency_map = []
    antennas = {}
    with open('inputs/input-day8.txt') as f:
        for line in f:
            frequency_map.append(list(line.strip()))

    # sort antennas by frequency
    for x in range(len(frequency_map)):
        for y in range(len(frequency_map[0])):
            if frequency_map[x][y] not in ['.']:
                if frequency_map[x][y] in antennas:
                    antennas[frequency_map[x][y]].append((x, y))
                else:
                    antennas[frequency_map[x][y]] = [(x, y)]

    print(f'Part1: {solve_part_1(antennas, len(frequency_map))}')
    print(f'Part2: {solve_part_2(antennas, len(frequency_map))}')


if __name__ == '__main__':
    main()
