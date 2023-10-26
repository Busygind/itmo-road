from math import factorial


def left_newton(x, y, point):
    t = (point - x[0]) / (x[1] - x[0])
    diff = calculate_finite_diffs(y)
    result = 0
    for i in range(len(y)):
        tmp = 1
        for j in range(0, i):
            # print("j: ", j)
            tmp *= (t - j)

        result += diff[0][i] * tmp / factorial(i)
        print(diff[0][i])
    print(result)
    return result


def right_newton(x, y, point):
    t = (point - x[-1]) / (x[1] - x[0])
    diff = calculate_finite_diffs(y)
    result = 0
    for i in range(len(y)):
        tmp = 1
        for j in range(0, i):
            # print("j: ", j)
            tmp *= (t + j)

        result += diff[len(y) - 1 - i][i] * tmp / factorial(i)
        print(diff[len(y) - 1 - i][i])
    # print(result)
    return result


def newton_interpolation(x, y, point):
    if not intervals_are_the_same(x):
        print("Для метода Ньютона узлы должны быть равноотстоящими!")
    else:
        if abs(point - x[0]) <= abs(point - x[-1]):
            return left_newton(x, y, point)
        else:
            return right_newton(x, y, point)


def intervals_are_the_same(x):
    for i in range(2, len(x)):
        if round(x[i] - x[i - 1], 5) != round(x[i - 1] - x[i - 2], 5):
            return False
    return True


def calculate_finite_diffs(y):
    n = len(y)
    diff = [[0] * n for _ in range(n)]
    print()
    for i in range(n):
        diff[i][0] = y[i]
    k = 1
    while k <= n:
        for i in range(n - k):
            diff[i][k] = (diff[i + 1][k - 1] - diff[i][k - 1])
        k += 1
    print("ТАБЛИЦА КОНЕЧНЫХ РАЗНОСТЕЙ:")
    for i in range(len(diff)):
        print(diff[i])
    return diff

