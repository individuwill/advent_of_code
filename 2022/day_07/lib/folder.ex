defmodule Folder do
  defstruct name: nil, files: [], folders: [], dirs: [], parent: nil, full_path: ""

  def add_folder(parent, child) do
    new_folders = [child | parent.folders]
    %Folder{parent | folders: new_folders}
  end

  def add_dir(folder, dir) do
    new_dirs = [dir | folder.dirs]

    %Folder{folder | dirs: new_dirs}
  end

  def add_file(folder, file) do
    new_files = [file | folder.files]
    %Folder{folder | files: new_files}
  end

  def size(folder) do
    files_total =
      folder.files
      |> Enum.map(fn {:file, _name, size} -> size end)
      |> Enum.sum()

    folders_total =
      folder.folders
      |> Enum.map(&size/1)
      |> Enum.sum()

    files_total + folders_total
  end

  def all_folders(folder) do
    cond do
      folder.folders == [] -> [folder]
      true -> [folder | List.flatten(Enum.map(folder.folders, &all_folders/1))]
    end
  end
end

defimpl String.Chars, for: Folder do
  def files(folder) do
    Enum.map(folder.files, fn {:file, name, size} -> "- #{name} (file, size=#{size})" end)
  end

  def build_prefix(_char, 0) do
    ""
  end

  def build_prefix(char, count) do
    Range.new(1, count) |> Enum.map(fn _ -> char end) |> Enum.join()
  end

  def indent(char, count, lines) do
    prefix = build_prefix(char, count)

    lines
    |> Enum.map(fn l -> prefix <> l end)
  end

  def folder_to_string(folder, indent \\ 0) do
    folder_size = Folder.size(folder)

    build_prefix(" ", indent) <>
      "- #{folder.name} (dir, size=#{folder_size})\n" <>
      Enum.join(indent(" ", indent + 2, files(folder)), "\n") <>
      "\n" <> Enum.join(Enum.map(folder.folders, fn f -> folder_to_string(f, indent + 2) end))
  end

  def to_string(folder) do
    folder_to_string(folder)
  end
end
