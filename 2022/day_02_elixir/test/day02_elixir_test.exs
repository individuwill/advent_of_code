defmodule Day02ElixirTest do
  use ExUnit.Case
  doctest Day02Elixir

  def get_sample do
    File.read!("./sample.txt")
  end

  def get_input do
    File.read!("./input.txt")
  end

  test "part 1 total score from sample" do
    assert Day02Elixir.calculate_part_1_total_score(get_sample()) == 15
  end

  test "part 2 total score from sample" do
    assert Day02Elixir.calculate_part_2_total_score(get_sample()) == 12
  end

  test "part 1 total score from input" do
    assert Day02Elixir.calculate_part_1_total_score(get_input()) == 11475
  end

  test "part 2 total score from input" do
    assert Day02Elixir.calculate_part_2_total_score(get_input()) == 16862
  end
end
