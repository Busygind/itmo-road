from math import sqrt

from approximation.tools.matrix import solve_matrix


def line_approximation(n, x, y):
    sum_x = sum(x)
    sum_x2 = sum([xi ** 2 for xi in x])
    sum_y = sum(y)
    sum_xy = sum([x[i] * y[i] for i in range(n)])

    matrix = [
        [sum_x2, sum_x, sum_xy],
        [sum_x, n, sum_y]
    ]
    r = solve_matrix(matrix)
    a = r[0]
    b = r[1]

    result = {
        'a': r[0],
        'b': r[1],
        'Аппроксимирующая функция': f"{round(a, 3)}x + {round(b, 3)}",
        'function': lambda x: a * x + b
    }

    points = []
    for i in range(len(x)):
        points.append((x[i], y[i]))
    mean_x = sum_x / n
    mean_y = sum_y / n
    sum_xy = sum([(point[0] - mean_x) * (point[1] - mean_y) for point in points])
    sum_x = sum([(xi - mean_y) ** 2 for xi in x])
    sum_y = sum([(yi - mean_y) ** 2 for yi in y])
    result['pirson'] = sum_xy / sqrt(sum_x * sum_y)

    return result
