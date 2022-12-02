import pytest
import puzzle


@pytest.fixture
def input():
    return puzzle.get_input()


@pytest.fixture
def sample_data():
    return """
A Y
B X
C Z
"""


def test_total_sample(sample_data):
    assert puzzle.my_total_score(sample_data) == 15


def test_total_input_less_than_13377(input):
    assert puzzle.my_total_score(input) < 13377


def test_total_input_less_than_11858(input):
    assert puzzle.my_total_score(input) < 11858


def test_total_input_wrong_values(input):
    assert puzzle.my_total_score(input) != 13377


@pytest.mark.parametrize(
    "me_encrypted, me_unencrypted", [("X", "Rock"), ("Y", "Paper"), ("Z", "Scissors")]
)
def test_unencrypte_me(me_encrypted, me_unencrypted):
    assert puzzle.unencrypt_me(me_encrypted) == me_unencrypted


@pytest.mark.parametrize(
    "elf_encrypted, elf_unencrypted", [("A", "Rock"), ("B", "Paper"), ("C", "Scissors")]
)
def test_unencrypte_elf(elf_encrypted, elf_unencrypted):
    assert puzzle.unencrypt_elf(elf_encrypted) == elf_unencrypted


def test_unencrypt_sample(sample_data):
    expected = [
        ("Rock", "Paper"),
        ("Paper", "Rock"),
        ("Scissors", "Scissors"),
    ]
    result = puzzle.convert_to_unencrypted_tuples(sample_data)
    print(f"expected: {expected}")
    print(f"result: {result}")
    assert result == expected
