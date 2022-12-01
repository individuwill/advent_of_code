import pytest
import puzzle


@pytest.fixture
def sample_data():
    return """
1000
2000
3000

4000

5000
6000

7000
8000
9000

10000
"""


@pytest.fixture
def input():
    with open("./input.txt") as in_file:
        return in_file.read()


def test_sample_data_max(sample_data):
    assert puzzle.calculate_max(sample_data) == 24000


def test_sample_data_top_3(sample_data):
    assert puzzle.calculate_top_3(sample_data) == 45000


def test_input_data_max(input):
    assert puzzle.calculate_max(input) == 71023


def test_input_data_top_3(input):
    assert puzzle.calculate_top_3(input) == 206289
