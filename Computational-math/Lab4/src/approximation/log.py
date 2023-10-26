from math import log

from approximation.line import line_approximation


def log_approximation(n, x, y):
    points = []
    for i in range(len(x)):
        points.append((x[i], y[i]))
    if not all(point[0] > 0 for point in points):
        return None

    lin_x = [log(xi) for xi in x]
    result_values = line_approximation(n, [lin_x[i] for i in range(n)], [y[i] for i in range(n)])

    a = result_values['a']
    b = result_values['b']

    result = {
        'Аппроксимирующая функция': f"{round(a, 3)}*ln(x) + {round(b, 3)}",
        'function': lambda i: a * log(i) + b
    }

    return result
