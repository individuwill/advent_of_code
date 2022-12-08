defmodule Day08Test do
  use ExUnit.Case
  doctest Day08

  def input do
    File.read!("./input.txt")
  end

  def sample do
    File.read!("./sample.txt")
  end

  def sample_forest do
    Forest.new(sample())
  end

  test "answer 1 sample" do
    assert Day08.answer01(sample()) == 21
  end

  test "answer 1 input" do
    assert Day08.answer01(input()) == 1789
  end

  test "check get left on sample" do
    assert Forest.get_left(sample_forest(), {2, 3}) == [6, 5, 3]
  end

  test "check get right on sample" do
    assert Forest.get_right(sample_forest(), {3, 1}) == [5, 4, 9]
  end

  test "check get up on sample" do
    assert Forest.get_up(sample_forest(), {3, 2}) == [3, 5, 3]
  end

  test "check get down on sample" do
    assert Forest.get_down(sample_forest(), {1, 3}) == [3, 4, 9]
  end

  test "check all visibility of sample" do
    expected = [
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      false,
      true,
      true,
      true,
      false,
      true,
      true,
      true,
      false,
      true,
      false,
      true,
      true,
      true,
      true,
      true,
      true
    ]

    forest = sample_forest()

    result =
      for r <- Enum.to_list(Range.new(0, forest.rows - 1)),
          c <- Enum.to_list(Range.new(0, forest.columns - 1)) do
        {r, c}
      end
      |> Enum.map(fn index -> Forest.visible?(forest, index) end)

    assert result == expected
  end
end
