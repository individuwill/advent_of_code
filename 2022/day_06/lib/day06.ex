defmodule Day06 do
  @moduledoc """
  Documentation for `Day06`.
  """

  def num_of_slides([head | tail], chunks) do
    unique_count =
      [head | tail]
      |> Enum.take(chunks)
      |> Enum.uniq()
      |> Enum.count()

    # |> dbg()

    case unique_count do
      ^chunks -> 0
      _ -> 1 + num_of_slides(tail, chunks)
    end
  end

  def calculate(input, chunks) do
    slides =
      input
      |> String.to_charlist()
      |> num_of_slides(chunks)

    slides + chunks
  end

  def answer01(input) do
    calculate(input, 4)
  end

  def answer02(input) do
    calculate(input, 14)
  end

  def main(_args \\ []) do
    input = File.read!("./input.txt")
    answer_01 = answer01(input)
    IO.puts("Answer 01 - #{answer_01}")
    answer_02 = answer02(input)
    IO.puts("Answer 02 - #{answer_02}")
  end
end
