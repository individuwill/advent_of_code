#!/usr/bin/env python
from typing import List


def sanitize_data(data: str) -> List[int]:
    lines = [l.strip() for l in data.splitlines()]
    return [0 if l == "" else int(l) for l in lines]


def group_totals(lines: List[int]) -> List[int]:
    total_calories = 0
    all_totals = []
    for line in lines:
        if line == 0:
            all_totals.append(total_calories)
            total_calories = 0
        else:
            total_calories += int(line)
    all_totals.append(total_calories)
    return all_totals


def calculate_max(data: str) -> int:
    lines = sanitize_data(data)
    all_totals = group_totals(lines)
    return max(all_totals)


def calculate_top_3(data: str) -> int:
    lines = sanitize_data(data)
    all_totals = group_totals(lines)
    all_totals.sort(reverse=True)
    return sum(all_totals[0:3])


def main():
    with open("./input.txt") as in_file:
        data = in_file.read()
        most_calories = calculate_max(data)
        top_3 = calculate_top_3(data)
        print(f"The elf carrying the most calories totals {most_calories}")
        print(f"The top 3 elves are carrying: {top_3} calories")


if __name__ == "__main__":
    main()
