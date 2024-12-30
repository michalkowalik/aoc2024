#!/usr/bin/env python


def find_antinode(frequency_map: [list[str]], x: int, y: int) -> tuple[int, int] | None:
    node = frequency_map[y][x]
    # vertical
    for ay in range(len(frequency_map)):
        if frequency_map[ay][x] == node and ay != y:
            # calculate distance
            distance = abs(ay - y)
            antinode_y = ay - (distance * 2)
            if ay > y:
                antinode_y = ay + (distance * 2)
            return antinode_y, x
    # horizontal

    # diagonal

    return None


def solve_part_1(frequency_map: [list[str]]) -> int:
    antinodes = []

    for line in frequency_map:
        antinodes.append(list(len(frequency_map[0])*'.'))

    # populate antinodes
    for x in range(len(antinodes)):
        for y in range(len(antinodes)):
            if frequency_map[x][y] not in ['.']:
                antinode = find_antinode(frequency_map, x, y)
                if antinode is not None and antinode[0] >= 0 and antinode[1] >= 0:
                    antinodes[antinode[0]][antinode[1]] = '#'
                    print(f'Antinode on {antinode[0]}:{antinode[1]}')

    # return count of antinodes
    antinode_sum = 0
    for line in antinodes:
        antinode_sum += len([x for x in line if x == '#'])
    return antinode_sum


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
