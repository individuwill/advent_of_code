defmodule Day02Elixir do
  @win 6
  @lose 0
  @draw 3
  @rock 1
  @paper 2
  @scissors 3
  def main do
    input = File.read!("./sample.txt")
    part_1_total_score = calculate_part_1_total_score(input)
    IO.puts("Part 1 - Total score #{part_1_total_score}")
    part_2_total_score = calculate_part_2_total_score(input)
    IO.puts("Part 2 - Total score #{part_2_total_score}")
  end

  def i_lose({elf, me}) do
    {elf + @win, me + @lose}
  end

  def i_win({elf, me}) do
    {elf + @lose, me + @win}
  end

  def we_draw({elf, me}) do
    {elf + @draw, me + @draw}
  end

  def score_round({elf, me}) do
    %{
      {:rock, :paper} => i_win({@rock, @paper}),
      {:rock, :rock} => we_draw({@rock, @rock}),
      {:rock, :scissors} => i_lose({@rock, @scissors}),
      {:paper, :paper} => we_draw({@paper, @paper}),
      {:paper, :rock} => i_lose({@paper, @rock}),
      {:paper, :scissors} => i_win({@paper, @scissors}),
      {:scissors, :paper} => i_lose({@scissors, @paper}),
      {:scissors, :rock} => i_win({@scissors, @rock}),
      {:scissors, :scissors} => we_draw({@scissors, @scissors})
    }[{elf, me}]
  end

  def chose_my_hand({elf_hand, desired}) do
    %{
      {:rock, :win} => {:rock, :paper},
      {:rock, :lose} => {:rock, :scissors},
      {:rock, :draw} => {:rock, :rock},
      {:paper, :win} => {:paper, :scissors},
      {:paper, :lose} => {:paper, :rock},
      {:paper, :draw} => {:paper, :paper},
      {:scissors, :win} => {:scissors, :rock},
      {:scissors, :lose} => {:scissors, :paper},
      {:scissors, :draw} => {:scissors, :scissors}
    }[{elf_hand, desired}]
  end

  def unencrypt_round({elf_encrypt, me_encrypt}) do
    {%{"A" => :rock, "B" => :paper, "C" => :scissors}[elf_encrypt],
     %{"X" => :rock, "Y" => :paper, "Z" => :scissors}[me_encrypt]}
  end

  def unencrypt_desired_round({elf_encrypt, desired_encrypt}) do
    {%{"A" => :rock, "B" => :paper, "C" => :scissors}[elf_encrypt],
     %{"X" => :lose, "Y" => :draw, "Z" => :win}[desired_encrypt]}
  end

  def read_rounds(input) do
    input
    |> String.trim()
    |> String.split("\n")
    |> Enum.map(&String.trim/1)
    |> Enum.map(&List.to_tuple(String.split(&1)))
  end

  def calculate_part_1_total_score(input) do
    input
    |> read_rounds
    |> Enum.map(&unencrypt_round/1)
    |> Enum.map(&score_round/1)
    |> Enum.reduce(fn {e, m}, {elf, me} -> {e + elf, m + me} end)
    |> elem(1)
  end

  def calculate_part_2_total_score(input) do
    input
    |> read_rounds
    |> Enum.map(&unencrypt_desired_round/1)
    |> Enum.map(&chose_my_hand/1)
    |> Enum.map(&score_round/1)
    |> Enum.reduce(fn {e, m}, {elf, me} -> {e + elf, m + me} end)
    |> elem(1)
  end
end
