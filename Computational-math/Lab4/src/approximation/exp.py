from math import log, exp

from approximation.line import line_approximation


def exp_approximation(n, x, y):
    points = []
    for i in range(len(x)):
        points.append((x[i], y[i]))
    if not all(point[1] > 0 for point in points):
        return None

    lin_y = [log(y[i]) for i in range(n)]
    r = line_approximation(n, [x[i] for i in range(n)], [lin_y[i] for i in range(n)])

    a = exp(r['b'])
    b = r['a']

    result = {
        'Аппроксимирующая функция': f"{round(a, 3)}*e^({round(b, 3)}*x)",
        'function': lambda i: a * exp(b * i)
    }

    return result
