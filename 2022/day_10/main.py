#!/usr/bin/env python

x = 1
cycles = [1]


def noop():
    global cycles
    print(f"noop")
    cycles.append(x)


def addx(val):
    global cycles
    global x
    print(f"add {val}")
    cycles.append(x)
    x += val
    cycles.append(x)


def process(line: str):
    if line == "noop":
        noop()
    else:
        addx(int(line.split(" ")[-1]))


def answer01(input: str):
    for line in input.split("\n"):
        process(line)
    print(x)
    print(cycles)
    important = []
    answer = 0
    for i in range(20, len(cycles), 40):
        val = cycles[i - 1]
        answer += val * i
        important.append(cycles[i - 1])
    print(important)
    return answer


def main():
    with open("./input.txt") as f:
        answer_01 = answer01(f.read())
        print(f"answer 1: {answer_01}")


if __name__ == "__main__":
    main()
