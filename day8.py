#!/usr/bin/env python


def solve_part_1(frequency_map: [list[str]]) -> int:
    antennas = {}
    antinodes = set()

    # populate antinodes
    for x in range(len(antinodes)):
        for y in range(len(antinodes)):
            if frequency_map[x][y] not in ['.']:
                if frequency_map[x][y] in antennas:
                    antennas[frequency_map[x][y]].append((x, y))
                else:
                    antennas[frequency_map[x][y]] = [(x, y)]

    print(antennas)

    # find pairs of antennas
    # calculate the vector
    # expand vector
    # mark antinode

    return 0


def main():
    frequency_map = []
    with open('day8-test.txt') as f:
        for line in f:
            frequency_map.append(list(line.strip()))

    antinode_count = solve_part_1(frequency_map)
    # show the map
    for line in frequency_map:
        print(''.join(line))

    print(antinode_count)


if __name__ == '__main__':
    main()
