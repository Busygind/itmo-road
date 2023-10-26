from math import exp, sqrt

from sympy import ln


def input_data():
    print("Выберите функцию: ")
    print("1: y' = x*y^2")
    print("2: y' = x^2 - y + 3")
    print("3: y' = 1 / sqrt(x^2 - 1)")
    while True:
        choice = input()
        if choice == "1":
            f = lambda x, y: x * (y ** 2)
            exact_sol = lambda x, x_0, y_0: -2 / (x ** 2 - (2 / y_0 - x_0 ** 2))
            break
        if choice == "2":
            f = lambda x, y: x ** 2 - y + 3
            exact_sol = lambda x, x_0, y_0: (y_0 - x_0 ** 2 + 2 * x_0 - 5) * exp(x_0) / exp(x) + x ** 2 - 2 * x + 5
            break
        if choice == "3":
            f = lambda x, y: 1 / (sqrt(x ** 2 - 1))
            exact_sol = lambda x, x_0, y_0: ln(x + sqrt(x ** 2 - 1)) + y_0 - ln(x_0 + sqrt(x_0 ** 2 - 1))
            break
        else:
            print("Повторите попытку")

    print("Введите через пробел границы интервала дифференцирования [x_0, x_n]: ")
    while True:
        try:
            values = input().strip().split()
            values = [float(i) for i in values]
            if len(values) == 2:
                bounds = (min(values[0], values[1]), max(values[0], values[1]))
                break
            else:
                print("Неверный формат! Повторите ввод!")
        except ValueError:
            print("Оба значения должны быть числами, повторите ввод")

    print("Введите начальное условие y(x_0): ")
    while True:
        try:
            initial_conditions = float(input())
            break
        except ValueError:
            print("Значение должно быть числом, повторите ввод")

    print("Введите точность вычисления: ")
    while True:
        try:
            eps = float(input())
            break
        except ValueError:
            print("Значение должно быть числом, повторите ввод")

    print("Введите шаг: ")
    while True:
        try:
            h = float(input())
            break
        except ValueError:
            print("Шаг должен быть числом, повторите ввод")

    print("Выберите метод: ")
    print("1 - Метод Эйлера")
    print("2 - Метод Рунге-Кутты 4-ого порядка")
    print("3 - Метод Милна")
    while True:
        try:
            choose_data_method = int(input())
            if choose_data_method == 1:
                method = "euler"
                break
            if choose_data_method == 2:
                method = "runge-kutta"
                break
            if choose_data_method == 3:
                method = "milne"
                break
            else:
                print("Неверный ввод, повторите попытку")
        except ValueError:
            print("Неверный ввод, повторите попытку")

    return {"f": f, "bounds": bounds, "init": initial_conditions, "h": h, "method": method, "eps": eps, "exact": exact_sol}
