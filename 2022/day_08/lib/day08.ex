defmodule Day08 do
  @moduledoc """
  Documentation for `Day08`.
  """

  def answer01(input) do
    forest = Forest.new(input)
    dbg(forest)
    IO.puts("Rows: #{forest.rows} Columns: #{forest.columns}")
    # IO.puts(forest)
    Forest.count_visible(forest)
  end

  def main(_args \\ []) do
    input = File.read!("./input.txt")
    answer_01 = answer01(input)
    IO.puts("Answer 1 - #{answer_01}")
  end
end
