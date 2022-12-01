#!/usr/bin/env python
import re
from typing import List


def sanitize_data(data: str) -> List[List[str]]:
    splits = [s.strip() for s in re.split(r"^$\n", data.strip(), flags=re.M)]
    groups = [split.split("\n") for split in splits]
    return groups


def get_int_groups(groups: List[List[str]]) -> List[List[int]]:
    int_groups = [[int(g) for g in group] for group in groups]
    return int_groups


def get_sum_groups(int_groups: List[List[int]]) -> List[int]:
    sum_groups = [sum(int_group) for int_group in int_groups]
    return sum_groups


def calculate_max(data: str) -> int:
    groups = sanitize_data(data)
    return max(get_sum_groups(get_int_groups(groups)))


def calculate_top_3(data: str) -> int:
    groups = sanitize_data(data)
    return sum(sorted(get_sum_groups(get_int_groups(groups)), reverse=True)[0:3])


def main():
    with open("./input.txt") as in_file:
        data = in_file.read()
        most_calories = calculate_max(data)
        top_3 = calculate_top_3(data)
        print(f"The elf carrying the most calories totals {most_calories}")
        print(f"The top 3 elves are carrying: {top_3} calories")


if __name__ == "__main__":
    main()
