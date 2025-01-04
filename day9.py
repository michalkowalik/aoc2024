#!/usr/bin/env python


def build_disk_map(data: str):
    file_index = 0
    disk_map = []

    for i, char in enumerate(data):
        if i % 2 == 0:
            disk_map.extend([file_index] * int(char))
            file_index += 1
        else:
            disk_map.extend([None] * int(char))
    return disk_map

def build_maps(disk_map):
    file_map = []
    empty_map = []

    start_cursor = 0
    end_cursor = 0

    for i, char in enumerate(disk_map):
        if char == disk_map[start_cursor]:
            end_cursor = i
        else:
            if disk_map[start_cursor] is None:
                empty_map.append((start_cursor, end_cursor))
            else:
                file_map.append({'id':disk_map[start_cursor], 'start': start_cursor, 'len': (end_cursor-start_cursor)+1})
            start_cursor = end_cursor = i

    # the last block
    if disk_map[start_cursor] is None:
        empty_map.append((start_cursor, i))
    else:
        file_map.append({'id': disk_map[start_cursor], 'start': start_cursor, 'len': (i-start_cursor)+1})
    return file_map, empty_map

def calculate_checksum(disk_map):
    return sum([i*c for i, c in enumerate(disk_map) if c is not None])

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

    file_map, empty_map = build_maps(disk_map)
    current_file = 0
    moved_files = []

    while True:
        # Nothing left to move
        if len(file_map) < 2:
            break

        file = file_map[-1]
        matching_empty_block = [x for x in empty_map if (x[1] - x[0]) >= file['len']]
        if matching_empty_block:
            print(f'we can move file {file['id']} to the empty block {matching_empty_block[0]}')
            if file['id'] in moved_files:
                continue
            # move file
            for i in range(0, file['len']):
                disk_map[matching_empty_block[0][0]+i] = file['id']
                disk_map[file['start']+i] = None
            # re-create maps
            moved_files.append(file['id'])
            file_map, empty_map = build_maps(disk_map)
            # continue



    # while 0 < file_end_cursor: # not sure if that is the correct condition!
    #     empty_start_cursor = 0
    #
    #     file_end_cursor = file_start_cursor
    #
    #     while disk_map[file_end_cursor] is None:
    #         file_end_cursor -= 1
    #
    #     # we've found a file. now let's check how big it is:
    #     file_start_cursor = file_end_cursor
    #     while disk_map[file_start_cursor] is not None and disk_map[file_end_cursor] == disk_map[file_start_cursor]:
    #         file_start_cursor -= 1
    #
    #     # we've found the end of the file, let's report the lenght:
    #     # print(f'file id {disk_map[file_end_cursor]}, length: {file_end_cursor - file_start_cursor}')
    #     if disk_map[file_end_cursor] in moved_files:
    #         file_end_cursor = file_start_cursor
    #         continue
    #
    #     file_len = file_end_cursor - file_start_cursor
    #
    #     # now let's start looking for a space big enough to fit the file:
    #     while empty_start_cursor < file_start_cursor:
    #         while disk_map[empty_start_cursor] is not None:
    #             empty_start_cursor += 1
    #
    #         # now we're on an empty space:
    #         empty_end_cursor = empty_start_cursor
    #         while disk_map[empty_end_cursor] is None:
    #             empty_end_cursor += 1
    #
    #         if file_len <= empty_end_cursor - empty_start_cursor: # file fits:
    #             for i in range(0, file_len):
    #                 disk_map[empty_start_cursor + i] = disk_map[file_start_cursor + i + 1]
    #                 disk_map[file_start_cursor + i + 1] = None
    #             moved_files.append(disk_map[empty_start_cursor])
    #             break # we've found and moved our file.
    #
    #         # the empty block is not big enough, we continue to search
    #         empty_start_cursor = empty_end_cursor + 1

    print(f'checksum: {calculate_checksum(disk_map)}')


def main():
    with open('inputs/day9-test.txt') as f:
        data = f.readline().strip()
    disk_map = build_disk_map(data)

    #solve_part1(disk_map)
    solve_part2(disk_map)


if __name__ == '__main__':
    main()
