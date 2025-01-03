#!/usr/bin/env python

def concatenate_ints(a: int, b: int) -> int:
    return int(str(a) + str(b))


def is_solvable(result: int, operands: [int]) -> bool:
    def backtrack(partial_sum: int, op: [int]) -> bool:
        if partial_sum > result:
            return False
        if len(op) == 0:
            return partial_sum == result
        else:
            return (backtrack(partial_sum + op[0], op[1:]) or
                    backtrack(concatenate_ints(partial_sum, op[0]), op[1:]) or  # Required for part 2 only
                    backtrack(partial_sum * op[0], op[1:]))
    return backtrack(operands[0], operands[1:])


def main():
    total_calibrations = 0
    with open('inputs/day7-input.txt') as f:
        for line in f:
            result, operands = line.strip().split(":")
            operands = [int(x) for x in operands.strip().split(" ")]
            if is_solvable(int(result), operands):
                total_calibrations += int(result)
    print(total_calibrations)


if __name__ == '__main__':
    main()
