#!/usr/bin/env python

def solve_part_1(frequency_map: [list[str]]) -> int:
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
