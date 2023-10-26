from typing import Callable
from data.functions import functions, functions_views


class InputData:
    integral: Callable[[float], float]
    method: int
    interval: tuple[float, float]
    accuracy: float


def input_choice(choices: set, msg: str) -> int:
    choice: int
    while True:
        try:
            choice = int(input(msg))
            if choice not in choices:
                raise ValueError
            return choice
        except ValueError:
            print("Некорректный ввод, попробуйте снова...")


def choose_integral() -> Callable[[float], float]:
    for i in range(len(functions_views)):
        print(f'{i + 1}. {functions_views[i]}')
    eq_choice: int = input_choice(set(range(1, len(functions_views) + 1)), "Введите номер выбранной функции: ")
    return functions[eq_choice - 1]


def input_data() -> InputData:
    data: InputData = InputData()
    data.integral = choose_integral()
    data.method = input_choice({1, 2, 3, 4, 5},
                               "1. Метод Симпсона\n2. Метод трапеций\n3. Метод левых прямоугольников\n4. Метод правых прямоугольников\n5. Метод средних прямоугольников\nВыберите метод: ")

    data.interval = input_interval()
    data.accuracy = input_accuracy()

    return data


def input_interval() -> tuple[float, float]:
    while True:
        try:
            start: float = float(input("Введите левую границу интервала: ").replace(",", "."))
            break
        except ValueError:
            print("Некорректный ввод, попробуйте снова...")

    while True:
        try:
            end = float(input("Введите правую границу интервала: ").replace(",", "."))
            if end <= start:
                print("Правая граница должна быть больше левой, попробуйте еще раз...")
                continue
            break
        except ValueError:
            print("Некорректный ввод, попробуйте снова... ")

    return start, end


def input_accuracy() -> float:
    while True:
        try:
            accuracy: float = float(input("Введите точность вычислений: ").replace(",", "."))
            if accuracy <= 0:
                raise ValueError
            break
        except ValueError:
            print("Некорректная точность, попробуйте снова...")

    return accuracy
