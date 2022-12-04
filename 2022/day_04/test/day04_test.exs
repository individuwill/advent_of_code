defmodule Day04Test do
  use ExUnit.Case
  doctest Day04

  def sample do
    File.read!("./sample.txt")
  end

  def input do
    File.read!("./input.txt")
  end

  test "answer 01 sample" do
    assert Day04.answer_01(sample()) == 2
  end

  test "answer 02 sample" do
    assert Day04.answer_02(sample()) == -1
  end

  test "answer 01 input" do
    assert Day04.answer_01(input()) == 456
  end

  test "answer 02 input" do
    assert Day04.answer_02(input()) == -1
  end
end
