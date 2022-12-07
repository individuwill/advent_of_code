defmodule Day07 do
  @moduledoc """
  Documentation for `Day07`.
  """

  def instruct("$ cd " <> dir) do
    {:cd, dir}
  end

  def instruct("$ ls") do
    {:ls}
  end

  def instruct("dir " <> dir) do
    {:dir, dir}
  end

  def instruct(file_line) do
    [size, name] = String.split(file_line, " ")
    {:file, name, String.to_integer(size)}
  end

  def interpret([], folder) when folder.parent == nil do
    IO.puts("End of instructions and at root")
    folder
  end

  def interpret([], folder) do
    IO.puts("End of instructions while on #{folder.name}")
    new_folder = Folder.add_folder(folder.parent, %Folder{folder | parent: nil})
    interpret([], new_folder)
  end

  def interpret([{:dir, name} | instructions], folder) do
    IO.puts("Found directory #{name} in #{folder.name}")
    interpret(instructions, Folder.add_dir(folder, {:dir, name}))
  end

  def interpret([{:file, name, size} | instructions], folder) do
    IO.puts("Found file #{name} in #{folder.name}")
    interpret(instructions, Folder.add_file(folder, {:file, name, size}))
  end

  def interpret([{:ls} | instructions], folder) do
    IO.puts("listing dir #{folder.name}")
    interpret(instructions, folder)
  end

  def interpret([{:cd, ".."} | instructions], folder) do
    IO.puts("Moving up a directory from #{folder.name}")
    # dbg(folder)
    new_folder = Folder.add_folder(folder.parent, %Folder{folder | parent: nil})
    interpret(instructions, new_folder)
  end

  def interpret([{:cd, dir} | instructions], folder) do
    IO.puts("Moving to #{dir}")
    # dbg(folder)
    new_folder = %Folder{name: dir, parent: folder, full_path: folder.full_path <> "/" <> dir}
    interpret(instructions, new_folder)
  end

  def interpret([{:cd, "/"} | instructions]) do
    IO.puts("Starting at the root")
    folder = %Folder{name: "/"}
    interpret(instructions, folder)
  end

  def parse(input) do
    input
    |> String.split("\n", trim: true)
    |> Enum.map(&instruct/1)
    |> interpret()
    |> dbg()
  end

  def answer01(input) do
    folder =
      input
      |> parse()

    IO.puts(folder)

    folder
    |> Folder.all_folders()
    |> Enum.filter(fn f -> Folder.size(f) <= 100_000 end)
    |> Enum.map(&Folder.size/1)
    |> Enum.sum()
    |> dbg()
  end

  def answer02(input) do
  end

  def main(_args \\ []) do
    input = File.read!("./input.txt")
    answer_01 = answer01(input)
    IO.puts("Answer 1 - #{answer_01}")
    # answer_02 = answer02(input)
    # IO.puts("Answer 2 - #{answer_02}")
  end
end
