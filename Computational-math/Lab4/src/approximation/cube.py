import numpy as np

from approximation.tools.matrix import solve_matrix


def cube_approximation(n, x, y):
    sum_x = sum(x)
    sum_x2 = sum([xi ** 2 for xi in x])
    sum_x3 = sum([xi ** 3 for xi in x])
    sum_x4 = sum([xi ** 4 for xi in x])
    sum_x5 = sum([xi ** 5 for xi in x])
    sum_x6 = sum([xi ** 6 for xi in x])
    sum_y = sum(y)
    sum_xy = sum([x[i] * y[i] for i in range(n)])
    sum_x2y = sum([(x[i] ** 2) * y[i] for i in range(n)])
    sum_x3y = sum([(x[i] ** 3) * y[i] for i in range(n)])

    matrix = [[n, sum_x, sum_x2, sum_x3, sum_y],
              [sum_x, sum_x2, sum_x3, sum_x4, sum_xy],
              [sum_x2, sum_x3, sum_x4, sum_x5, sum_x2y],
              [sum_x3, sum_x4, sum_x5, sum_x6, sum_x3y]]
    r = solve_matrix(matrix)
    # matrix =[[n, sum_x, sum_x2, sum_x3],
    #           [sum_x, sum_x2, sum_x3, sum_x4],
    #           [sum_x2, sum_x3, sum_x4, sum_x5],
    #           [sum_x3, sum_x4, sum_x5, sum_x6]]
    #
    # res = [sum_y, sum_xy, sum_x2y, sum_x3y]
    # r = np.linalg.solve(matrix, res)

    result = {
        'Аппроксимирующая функция': f"{round(r[3], 3)}x^3 + {round(r[2], 3)}x^2 + {round(r[1], 3)}*x + {round(r[0], 3)}",
        'function': lambda i: r[3] * (i ** 3) + r[2] * (i ** 2) + r[1] * i + r[0]
    }
    return result
