from math import ceil

from runge_kutta_method import runge_kutta


def milne(f, x_0, x_n, y_0, h, eps):
    n = ceil((x_n - x_0) / h) + 1
    if n < 4:
        print("Слишком маленький интервал для такого шага! Не хватает данных.")
        exit(1)
    x = [x_0 + h * i for i in range(n)]

    y = runge_kutta(f, x_0, x_0 + 3 * h - 0.0001, y_0, h)["y"]
    print("Первые значения y, найденные методом Рунге-Кутты:", y)

    for i in range(4, len(x)):
        y_prediction = prediction(h, f, x, y, i)
        y_correction = correction(h, f, x, y, i, y_prediction)

        while abs(y_correction - y_prediction) > eps:
            y_prediction = y_correction
            y_correction = correction(h, f, x, y, i, y_prediction)
        y.append(y_correction)

    return {"x": x, "y": y}


def prediction(h, f, x, y, i):
    tmp = 2 * f(x[i - 3], y[i - 3]) - f(x[i - 2], y[i - 2]) + 2 * f(x[i - 1], y[i - 1])
    return y[i - 4] + 4 * h * tmp / 3


def correction(h, f, x, y, i, y_pred):
    tmp = f(x[i - 2], y[i - 2]) + 4 * f(x[i - 1], y[i - 1]) + f(x[i], y_pred)
    return y[i - 2] + h * tmp / 3


def check_accuracy(result, exact, y_0, eps):
    exact_y = []
    x_0 = result["x"][0]
    for x in result["x"]:
        exact_y.append(exact(x, x_0, y_0))

    y_diffs = []
    for i in range(0, len(exact_y)):
        y_diffs.append(abs(exact_y[i] - result["y"][i]))
    eps_calc = max(y_diffs)
    return [eps_calc <= eps, eps_calc, eps]
