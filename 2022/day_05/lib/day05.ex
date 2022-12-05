defmodule Day05 do
  @moduledoc """
  Documentation for `Day05`.
  """

  def main(_args \\ []) do
    input = File.read!("./input.txt")
    answer_01 = answer01(input)
    IO.puts("Answer 01 - #{answer_01}")
    answer_02 = answer02(input)
    IO.puts("Answer 02 - #{answer_02}")
  end

  def parse_instructions(input) do
    Regex.scan(~r/^move (\d+) from (\d+) to (\d+)$/m, input, capture: :all_but_first)
    |> Enum.map(&List.to_tuple/1)
    |> Enum.map(fn {count, from, to} -> {String.to_integer(count), from, to} end)
  end

  def parse_input(input) do
    [stack_text, instruction_text] = String.split(input, "\n\n")
    ship = Ship.new_from_input(stack_text)
    dbg(ship)

    instructions = parse_instructions(instruction_text)
    dbg(instructions)
    {ship, instructions}
  end

  def answer01(input) do
    {ship, instructions} = parse_input(input)

    result = Ship.arrange(ship, instructions)

    Ship.get_tops(result)
  end

  def answer02(input) do
    {ship, instructions} = parse_input(input)

    result = Ship.arrange(ship, instructions, true)

    Ship.get_tops(result)
  end
end
