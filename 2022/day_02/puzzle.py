#!/usr/bin/env python
from typing import List, Tuple
import functools


def get_input() -> str:
    with open("./input.txt") as in_file:
        return in_file.read()


## Scoring matrix
# 1 point for Rock
# 2 points for Paper
# 3 points for Scissors

# 0 if it's a loss
# 3 if it's a draw
# 6 if it's a win

## column 1, the elf
# A is Rock is 1
# B is Paper is 2
# C is Scissors is 3

## column 2, me
# X is Rock is 1
# Y is Paper is 2
# Z is Scissors is 3

ROCK = "Rock"
PAPER = "Paper"
SCISSORS = "Scissors"

ELF_MAP = {"A": ROCK, "B": PAPER, "C": SCISSORS}
ME_MAP = {"X": ROCK, "Y": PAPER, "Z": SCISSORS}


def unencrypt_elf(elf_hand):
    return ELF_MAP[elf_hand]


def unencrypt_me(me_hand):
    return ME_MAP[me_hand]


def convert_to_unencrypted_tuple(round: Tuple[str]) -> Tuple[str]:
    elf, me = round
    return (unencrypt_elf(elf), unencrypt_me(me))


def convert_to_unencrypted_tuples(data: str) -> List[Tuple[int]]:
    the_tuples = [tuple(l.strip().split()) for l in data.strip().splitlines()]
    print(the_tuples)
    unencrypted_tuples = [convert_to_unencrypted_tuple(t) for t in the_tuples]
    print(unencrypted_tuples)
    return unencrypted_tuples


WIN_SCORE_LOOKUP = {
    (ROCK, PAPER): (0, 6),
    (ROCK, SCISSORS): (6, 0),
    (PAPER, ROCK): (6, 0),
    (PAPER, SCISSORS): (0, 6),
    (SCISSORS, ROCK): (0, 6),
    (SCISSORS, PAPER): (6, 0),
}

DRAW_SCORE = 3

THROW_SCORE_LOOKUP = {ROCK: 1, PAPER: 2, SCISSORS: 3}


def convert_to_win_score(round: Tuple[str]) -> Tuple[int]:
    elf_hand, me_hand = round
    elf_hand_score = THROW_SCORE_LOOKUP[elf_hand]
    me_hand_score = THROW_SCORE_LOOKUP[me_hand]
    if round[0] == round[1]:
        return (elf_hand_score + DRAW_SCORE, me_hand_score + DRAW_SCORE)
    return functools.reduce(
        add_rounds, [(elf_hand_score, me_hand_score), WIN_SCORE_LOOKUP[round]]
    )


def convert_to_win_scores(rounds: List[Tuple[str]]) -> Tuple[int]:
    scores = [convert_to_win_score(round) for round in rounds]
    print(f"win scores: {scores}")
    return scores


def add_rounds(round_1: Tuple[int], round_2: Tuple[int]) -> Tuple[int]:
    print(f"add: {round_1} + {round_2}")
    return (round_1[0] + round_2[0], round_1[1] + round_2[1])


def convert_to_total_score(round_scores: List[Tuple[int]]) -> Tuple[int]:
    total_scores = functools.reduce(add_rounds, round_scores)
    print(f"total scores: {total_scores}")
    return total_scores


def total_scores(data: str) -> Tuple[int]:
    unencrypted_tuples = convert_to_unencrypted_tuples(data)
    win_scores = convert_to_win_scores(unencrypted_tuples)
    elf, me = convert_to_total_score(win_scores)
    return (elf, me)


def my_total_score(data: str) -> int:
    return total_scores(data)[1]


def main():
    my_score = my_total_score(get_input())
    print(f"Your total score is {my_score}")


if __name__ == "__main__":
    main()
