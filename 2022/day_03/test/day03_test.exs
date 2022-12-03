defmodule Day03Test do
  use ExUnit.Case
  doctest Day03

  test "greets the world" do
    assert Day03.hello() == :world
  end

  test "sample input sum of priorities" do
    sample_input = "vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw"
    assert Day03.answer_01_sum_priorities(sample_input) == 157
  end

  test "input sum of priorities correct answer" do
    result =
      File.read!("./input.txt")
      |> Day03.answer_01_sum_priorities()

    assert result == 7821
  end
end
