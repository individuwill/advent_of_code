defmodule Day03 do
  @moduledoc """
  Documentation for `Day03`.
  """

  @doc """
  Hello world.

  ## Examples

      iex> Day03.hello()
      :world

  """
  def hello do
    :world
  end

  def unique_item(line) do
    line
    |> String.split_at(div(String.length(line), 2))
    |> Tuple.to_list()
    |> Enum.map(&String.to_charlist/1)
    |> Enum.map(&MapSet.new/1)
    |> (fn [a, b] -> MapSet.intersection(a, b) end).()
    |> MapSet.to_list()
    |> List.to_string()
  end

  def shift(i) when i < 97 do
    i - 38
  end

  def shift(i) do
    i - 96
  end

  def score_item(c) do
    c
    |> String.to_charlist()
    |> List.first()
    |> shift()

    # if less than 97 (A), subtract 38
    # else subtract 96
  end

  def answer_01_sum_priorities(input) do
    answer =
      input
      |> String.trim()
      |> String.split("\n")
      |> Enum.map(&unique_item/1)
      |> Enum.map(&score_item/1)
      |> Enum.sum()

    IO.inspect(answer)
    answer
  end
end
