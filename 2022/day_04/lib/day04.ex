defmodule Day04 do
  def main do
    input = File.read!("./input.txt")
    answer_01 = answer_01(input)
    IO.puts("Answer 01 - #{answer_01}")
    answer_02 = answer_02(input)
    IO.puts("Answer 02 - #{answer_02}")
  end

  def pairs_to_int_tuples(pairs) do
    pairs
    |> Enum.map(&String.split(&1, "-"))
    |> Enum.map(fn x -> Enum.map(x, &String.to_integer/1) end)
    |> Enum.map(&List.to_tuple/1)
  end

  def tuples_to_ranges(tuples) do
    tuples
    |> Enum.map(&Range.new(elem(&1, 0), elem(&1, 1)))
  end

  def ranges_to_lists(ranges) do
    ranges
    |> Enum.map(&Enum.to_list/1)
  end

  def lists_to_sets(lists) do
    lists
    |> Enum.map(&MapSet.new/1)
    |> List.to_tuple()
  end

  def is_one_a_subset({set1, set2}) do
    MapSet.subset?(set1, set2) || MapSet.subset?(set2, set1)
  end

  def assignments(input) do
    input
    |> String.trim()
    |> String.split("\n", trim: true)
    |> Enum.map(&String.split(&1, ","))
    |> Enum.map(&pairs_to_int_tuples/1)
    |> Enum.map(&tuples_to_ranges/1)
    |> Enum.map(&ranges_to_lists/1)
    |> Enum.map(&lists_to_sets/1)
    |> dbg()
  end

  def answer_01(input) do
    input
    |> assignments
    |> Enum.map(&is_one_a_subset/1)
    |> Enum.count(&(&1 == true))
    |> dbg()
  end

  def answer_02(input) do
  end
end
