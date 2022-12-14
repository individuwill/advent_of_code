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

  def get(direction, forest, row, column) do
    get(direction, forest, {row, column})
  end

  def get(direction, forest, {row, column}) do
    case direction do
      :up ->
        Range.new(0, row - 1)
        |> Enum.map(fn i -> Forest.get(forest, i, column) end)

      :down ->
        Range.new(row + 1, forest.rows - 1)
        |> Enum.map(fn i -> Forest.get(forest, i, column) end)

      :left ->
        Range.new(0, column - 1)
        |> Enum.map(fn i -> Forest.get(forest, row, i) end)

      :right ->
        Range.new(column + 1, forest.columns - 1)
        |> Enum.map(fn i -> Forest.get(forest, row, i) end)
    end
  end

  def get(forest, row, column) do
    Map.get(forest.storage, {row, column})
  end

  def get(forest, {row, column}) do
    get(forest, row, column)
  end

  def get_row(forest, row) do
    [get(forest, {row, 0}) | get(:right, forest, {row, 0})]
  end

  def get_column(forest, column) do
    [get(forest, {0, column}) | get(:down, forest, {0, column})]
  end

  def put(forest, row, column, value) do
    %Forest{forest | storage: Map.put(forest, {row, column}, value)}
  end

  def visible?(_direction, _forest, {0, _column}) do
    true
  end

  def visible?(_direction, _forest, {_row, 0}) do
    true
  end

  def visible?(direction, forest, {row, column}) do
    value = Forest.get(forest, {row, column})

    trees = Forest.get(direction, forest, {row, column})
    Enum.max(trees) < value
  end

  def visible?(forest, {row, column}) do
    cond do
      forest.rows - 1 == row -> true
      forest.columns - 1 == column -> true
      true -> visible_any?(forest, {row, column})
    end
  end

  def visible_any?(forest, {row, column}) do
    [:up, :down, :left, :right]
    |> Enum.map(fn direction -> visible?(direction, forest, {row, column}) end)
    |> Enum.any?()
  end

  def all_indexes(forest) do
    for r <- Enum.to_list(Range.new(0, forest.rows - 1)),
        c <- Enum.to_list(Range.new(0, forest.columns - 1)) do
      {r, c}
    end
  end

  def count_visible(forest) do
    forest
    |> all_indexes()
    |> Enum.map(fn index -> visible?(forest, index) end)
    |> Enum.count(fn v -> v == true end)
  end

  def viewing_distance(forest, direction, {row, column}) do
    this_tree = Forest.get(forest, {row, column})

    trees =
      cond do
        direction == :up || direction == :left ->
          Forest.get(direction, forest, {row, column}) |> Enum.reverse()

        true ->
          Forest.get(direction, forest, {row, column})
      end

    case trees do
      [] ->
        0

      _ ->
        take_until(trees, this_tree) |> Enum.count()
    end
  end

  def take_until([], _val) do
    []
  end

  def take_until([i | rest], val) do
    case i do
      i when i < val -> [i | take_until(rest, val)]
      _ -> [i]
    end
  end

  def viewing_distances(forest, {row, column}) do
    [:up, :down, :left, :right]
    |> Enum.map(fn direction -> Forest.viewing_distance(forest, direction, {row, column}) end)
  end

  def scenic_score(forest, {row, column}) do
    Enum.product(viewing_distances(forest, {row, column}))
  end

  def max_scenic_score(forest) do
    forest
    |> all_indexes()
    |> Enum.map(fn index -> scenic_score(forest, index) end)
    |> Enum.max()

    # |> dbg()
  end
end

defimpl String.Chars, for: Forest do
  def to_string(forest) do
    0..(forest.rows - 1)
    |> Enum.map(fn r -> Forest.get_row(forest, r) end)
    |> Enum.map(fn row -> Enum.map(row, &Integer.to_string/1) |> Enum.join("") end)
    |> Enum.join("\n")
  end
end
