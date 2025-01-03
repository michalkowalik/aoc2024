#!/usr/bin/env python


def build_disk_map(data: str):
    index = 0
    file_index = 0
    disk_map = []

    for char in data:
        if index % 2 == 0:
            disk_map.extend([file_index] * int(char))
            file_index += 1
        else:
            disk_map.extend([None] * int(char))
        index += 1
    return disk_map


def calculate_checksum(disk_map):
    checksum = 0
    for i in range(0, len(disk_map)):
        if disk_map[i] is None:
            continue
        checksum += disk_map[i] * i
    return checksum


def solve_part1(disk_map):
    file_blocks = len([x for x in disk_map if x is not None])
    empty_blocks = len([x for x in disk_map if x is None])
    print(f"got {empty_blocks} empty blocks")

    b_cursor = len(disk_map) - 1
    f_cursor = 0
    while empty_blocks > 0 and f_cursor < b_cursor:
        if disk_map[f_cursor] is None:
            while disk_map[b_cursor] is None:
                b_cursor -= 1
            disk_map[f_cursor] = disk_map[b_cursor]
            empty_blocks -= 1
            b_cursor -= 1
        f_cursor += 1
    print(f'checksum: {calculate_checksum(disk_map[:file_blocks])}')


def solve_part2(disk_map):
    print(f'checksum: {calculate_checksum(disk_map)}')


def main():
    with open('inputs/day9.txt') as f:
        data = f.readline().strip()
    disk_map = build_disk_map(data)

    solve_part1(disk_map)
    solve_part2(disk_map)


if __name__ == '__main__':
    main()
