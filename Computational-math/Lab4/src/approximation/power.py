from math import log, exp

from approximation.line import line_approximation


def power_approximation(n, x, y):

    points = []
    for i in range(len(x)):
        points.append((x[i], y[i]))
    if not all(point[0] > 0 and point[1] > 0 for point in points): return None

    lin_x = [log(x[i]) for i in range(n)]
    lin_y = [log(y[i]) for i in range(n)]
    result_values = line_approximation(n, [lin_x[i] for i in range(n)], [lin_y[i] for i in range(n)])

    a = exp(result_values['b'])
    b = result_values['a']
 
    result = {
        'Аппроксимирующая функция': f"{round(a, 3)}*x^{round(b, 3)}",
        'function': lambda i: a * (i ** b)
    }

    return result



