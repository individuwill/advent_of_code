defmodule Day08 do
  @moduledoc """
  Documentation for `Day08`.
  """

  def answer01(input) do
    forest = Forest.new(input)
    Forest.count_visible(forest)
  end

  def answer02(input) do
    forest = Forest.new(input)
    Forest.max_scenic_score(forest)
  end

  def main(_args \\ []) do
    input = File.read!("./input.txt")
    answer_01 = answer01(input)
    IO.puts("Answer 1 - #{answer_01}")
    answer_02 = answer02(input)
    IO.puts("Answer 2 - #{answer_02}")
  end
end
