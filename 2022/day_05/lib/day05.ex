defmodule Day05 do
  @moduledoc """
  Documentation for `Day05`.
  """

  def main(_args \\ []) do
    input = File.read!("./input.txt")
    answer_01 = answer01(input)
    IO.puts("Answer 01 - #{answer_01}")
    # answer_02 = answer02(input)
    # IO.puts("Answer 02 - #{answer_02}")
  end

  def parse_stack_row(row) do
    row
    |> Enum.chunk_every(4)
    |> Enum.map(&List.to_string/1)
    |> Enum.map(&String.trim/1)
    |> Enum.map(&String.replace(&1, "[", ""))
    |> Enum.map(&String.replace(&1, "]", ""))
  end

  def build_stacks(input) do
    input
    |> String.split("\n")
    |> Enum.map(&String.to_charlist/1)
    |> Enum.map(&parse_stack_row/1)
    |> List.zip()
    |> Enum.map(&Tuple.to_list/1)
    |> Enum.map(&Enum.reverse/1)
    |> Enum.map(fn list -> Enum.filter(list, fn l -> l != "" end) end)
    |> Enum.map(fn [name | items] -> {name, items} end)
    |> Enum.into(%{})
  end

  def parse_instructions(input) do
    Regex.scan(~r/^move (\d+) from (\d+) to (\d+)$/m, input, capture: :all_but_first)
    |> Enum.map(&List.to_tuple/1)
    |> Enum.map(fn {count, from, to} -> {String.to_integer(count), from, to} end)
  end

  def move(stacks, {count, from, to}) do
    IO.puts("moving #{count} blocks from stack #{from} to stack #{to}")
    IO.inspect(stacks)
    {new_from, to_move} = Enum.split(stacks[from], length(stacks[from]) - count)
    IO.puts("moving #{to_move}")
    new_to = Enum.concat(stacks[to], Enum.reverse(to_move))
    new_stack = %{stacks | from => new_from, to => new_to}
    IO.inspect(new_stack)
    new_stack
  end

  def arrange(stacks, []) do
    stacks
  end

  def arrange(stacks, [next_instruction | remaining_instructions]) do
    arrange(move(stacks, next_instruction), remaining_instructions)
  end

  def answer01(input) do
    [stack_text, instruction_text] = String.split(input, "\n\n")

    stacks = build_stacks(stack_text)
    dbg(stacks)

    instructions = parse_instructions(instruction_text)
    dbg(instructions)

    result = arrange(stacks, instructions)

    result
    |> Map.values()
    |> Enum.map(&List.last/1)
    |> List.to_string()
  end

  def answer02(input) do
  end
end
