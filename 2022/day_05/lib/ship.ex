defmodule Ship do
  defstruct stacks: %{}

  def parse_stack_row(row) do
    row
    |> Enum.chunk_every(4)
    |> Enum.map(&List.to_string/1)
    |> Enum.map(&String.trim/1)
    |> Enum.map(&String.replace(&1, "[", ""))
    |> Enum.map(&String.replace(&1, "]", ""))
  end

  def new do
    %Ship{}
  end

  def new_from_input(input) do
    stacks =
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

    %Ship{stacks: stacks}
  end

  def move(ship, {count, from, to}, keep_order \\ false) do
    IO.puts("moving #{count} blocks from stack #{from} to stack #{to}")
    IO.puts(ship)
    {new_from, to_move} = Enum.split(ship.stacks[from], length(ship.stacks[from]) - count)
    IO.puts("moving #{to_move}")

    new_to =
      Enum.concat(ship.stacks[to], if(keep_order, do: to_move, else: Enum.reverse(to_move)))

    new_stack = %{ship.stacks | from => new_from, to => new_to}
    new_ship = %Ship{stacks: new_stack}
    IO.puts(new_ship)
    dbg(new_stack)
    new_ship
  end

  def arrange(ship, instructions, keep_order \\ false)

  def arrange(ship, [next_instruction | remaining_instructions], keep_order) do
    arrange(move(ship, next_instruction, keep_order), remaining_instructions, keep_order)
  end

  def arrange(ship, [], _keep_order) do
    ship
  end

  def get_tops(ship) do
    ship.stacks
    |> Map.values()
    |> Enum.map(&List.last/1)
    |> List.to_string()
  end
end

defimpl String.Chars, for: Ship do
  def blockify(32) do
    "   "
  end

  def blockify(b) do
    "[#{List.to_string([b])}]"
  end

  def to_string(ship) do
    stack_height =
      ship.stacks
      |> Map.values()
      |> Enum.map(&Enum.count/1)
      |> Enum.max()

    normalized_stacks =
      ship.stacks
      |> Map.values()
      |> Enum.map(&List.to_string/1)
      |> Enum.map(&String.pad_trailing(&1, stack_height, " "))
      |> Enum.map(&String.to_charlist/1)

    stacks =
      for col <- Range.new(stack_height - 1, 0) do
        for row <- normalized_stacks do
          Enum.at(row, col)
        end
      end
      |> Enum.map(
        &for x <- &1 do
          blockify(x)
        end
      )
      |> Enum.map(&Enum.join(&1, " "))

    labels =
      for x <- Range.new(1, length(Map.keys(ship.stacks))) do
        " #{x} "
      end
      |> Enum.join(" ")

    Enum.join(stacks, "\n") <> "\n" <> labels
  end
end
