defmodule Day07Test do
  use ExUnit.Case
  doctest Day07

  def sample do
    File.read!("./sample.txt")
  end

  def input do
    File.read!("./input.txt")
  end

  test "answer 1 sample" do
    assert Day07.answer01(sample()) == 95437
  end

  test "answer 1 input" do
    assert Day07.answer01(input()) == 1_543_140
  end

  test "answer 2 sample" do
    assert Day07.answer02(sample()) == 24_933_642
  end

  test "answer 2 input" do
    assert Day07.answer02(input()) == 1_117_448
  end

  test "total folder size" do
    assert Folder.size(Day07.parse(sample())) == 48_381_165
  end

  test "adding a file to a folder" do
    folder = %Folder{name: "test"}
    assert Enum.count(folder.files) == 0
    new_folder = Folder.add_file(folder, {:file, "test1", 3})
    assert new_folder.name == "test"
    assert Enum.count(new_folder.files) == 1
  end

  test "adding a dir to a folder" do
    folder = %Folder{name: "test"}
    assert Enum.count(folder.dirs) == 0
    new_folder = Folder.add_dir(folder, {:dir, "test1"})
    assert new_folder.name == "test"
    assert Enum.count(new_folder.dirs) == 1
  end
end
