defmodule Forest do
  defstruct storage: %{}, rows: 0, columns: 0

  def index_to_row_and_column(index, columns) do
    r = div(index, columns)
    c = rem(index, columns)
    {r, c}
  end

  def new(input) when is_bitstring(input) do
    input
    |> String.split("\n")
    |> Enum.map(&String.to_charlist/1)
    |> new()
  end

  def new(lines) do
    rows = Enum.count(lines)
    columns = Enum.count(List.first(lines))

    storage =
      lines
      |> List.flatten()
      |> Enum.map(&List.to_string([&1]))
      |> Enum.map(&String.to_integer/1)
      |> Enum.with_index(&%{index: &2, value: &1, key: index_to_row_and_column(&2, columns)})
      |> Enum.map(fn v -> {v.key, v.value} end)
      |> Map.new()

    # |> dbg()

    %Forest{rows: rows, columns: columns, storage: storage}
  end

  def get_left(forest, {row, column}) do
    Range.new(0, column - 1)
    |> Enum.map(fn i -> Forest.get(forest, row, i) end)
  end

  def get_right(forest, {row, column}) do
    Range.new(column + 1, forest.columns - 1)
    |> Enum.map(fn i -> Forest.get(forest, row, i) end)
  end

  def get_up(forest, {row, column}) do
    Range.new(0, row - 1)
    |> Enum.map(fn i -> Forest.get(forest, i, column) end)
  end

  def get_down(forest, {row, column}) do
    Range.new(row + 1, forest.rows - 1)
    |> Enum.map(fn i -> Forest.get(forest, i, column) end)
  end

  def get(forest, row, column) do
    Map.get(forest.storage, {row, column})
  end

  def get(forest, {row, column}) do
    get(forest, row, column)
  end

  def put(forest, row, column, value) do
    %Forest{forest | storage: Map.put(forest, {row, column}, value)}
  end

  def visible?(_forest, {0, _column}) do
    true
  end

  def visible?(_forest, {_row, 0}) do
    true
  end

  def visible?(forest, {row, column}) do
    cond do
      forest.rows - 1 == row -> true
      forest.columns - 1 == column -> true
      true -> visible_any?(forest, {row, column})
    end
  end

  def visible_left?(forest, {row, column}) do
    value = Forest.get(forest, {row, column})
    left = Forest.get_left(forest, {row, column})
    Enum.max(left) < value
  end

  def visible_right?(forest, {row, column}) do
    value = Forest.get(forest, {row, column})
    right = Forest.get_right(forest, {row, column})
    Enum.max(right) < value
  end

  def visible_up?(forest, {row, column}) do
    value = Forest.get(forest, {row, column})
    up = Forest.get_up(forest, {row, column})
    Enum.max(up) < value
  end

  def visible_down?(forest, {row, column}) do
    value = Forest.get(forest, {row, column})
    down = Forest.get_down(forest, {row, column})
    Enum.max(down) < value
  end

  def visible_any?(forest, {row, column}) do
    Enum.any?([
      visible_up?(forest, {row, column}),
      visible_down?(forest, {row, column}),
      visible_left?(forest, {row, column}),
      visible_right?(forest, {row, column})
    ])
  end

  def count_visible(forest) do
    for r <- Enum.to_list(Range.new(0, forest.rows - 1)),
        c <- Enum.to_list(Range.new(0, forest.columns - 1)) do
      {r, c}
    end
    |> Enum.map(fn index -> visible?(forest, index) end)
    |> Enum.count(fn v -> v == true end)
  end
end

defimpl String.Chars, for: Forest do
  def to_string(forest) do
    ""
  end
end
