defmodule Day06Test do
  use ExUnit.Case
  doctest Day06

  def input do
    File.read!("./input.txt")
  end

  def sample do
    File.read!("./sample.txt")
  end

  test "answer 01 sample 1" do
    assert Day06.answer01("mjqjpqmgbljsphdztnvjfqwrcgsmlb") == 7
  end

  test "answer 01 sample 2" do
    assert Day06.answer01("bvwbjplbgvbhsrlpgdmjqwftvncz") == 5
  end

  test "answer 01 sample 3" do
    assert Day06.answer01("nppdvjthqldpwncqszvftbrmjlhg") == 6
  end

  test "answer 01 sample 4" do
    assert Day06.answer01("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 10
  end

  test "answer 01 sample 5" do
    assert Day06.answer01("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 11
  end

  # test "answer 02 sample" do
  #   assert Day06.answer02(sample()) == -1
  # end

  test "answer 01 input" do
    assert Day06.answer01(input()) == 1356
  end

  # test "answer 02 input" do
  #   assert Day06.answer02(input()) == -1
  # end
end
