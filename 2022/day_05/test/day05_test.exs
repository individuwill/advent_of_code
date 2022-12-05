defmodule Day05Test do
  use ExUnit.Case
  doctest Day05

  def input do
    File.read!("./input.txt")
  end

  def sample do
    File.read!("./sample.txt")
  end

  test "answer 01 sample" do
    assert Day05.answer01(sample()) == "CMZ"
  end

  test "answer 02 sample" do
    assert Day05.answer02(sample()) == "MCD"
  end

  test "answer 01 input" do
    assert Day05.answer01(input()) == "RFFFWBPNS"
  end

  test "answer 02 input" do
    assert Day05.answer02(input()) == "CQQBBJFCS"
  end
end
