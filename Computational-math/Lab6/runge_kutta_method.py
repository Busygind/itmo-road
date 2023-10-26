from math import ceil


def runge_kutta(f, x_0, x_n, y_0, h):
    n = ceil((x_n - x_0) / h) + 1
    if n < 2:
        print("Слишком маленький интервал для такого шага! Не хватает данных.")
        exit(1)
    x = [x_0 + h * i for i in range(n)]
    y = [y_0]
    cur_x = x_0
    i = 0
    while cur_x <= x_n:
        try:
            k1 = h * f(cur_x, y[i])
            k2 = h * f(cur_x + h / 2, y[i] + k1 / 2)
            k3 = h * f(cur_x + h / 2, y[i] + k2 / 2)
            k4 = h * f(cur_x + h, y[i] + k3)
            new_y = y[i] + 1 / 6 * (k1 + 2 * k2 + 2 * k3 + k4)
            y.append(new_y)
            i += 1
            cur_x += h
        except ValueError:
            print("Функция имеет разрыв на заданном промежутке!")

    if len(x) < len(y):
        y.pop(-1)

    return {"x": x, "y": y}


def runge_check_for_rk(f, x_0, x_n, y_0, h, eps):
    res_1 = runge_kutta(f, x_0, x_n, y_0, h)
    print("Полученные значения при h =", h)
    print(res_1["y"])
    res_2 = runge_kutta(f, x_0, x_n, y_0, h / 2)
    y_1 = res_1["y"][-1]
    y_2 = res_2["y"][-1]
    while abs(y_1 - y_2) / (2 ** 4 - 1) > eps:
        h /= 2
        res_1 = runge_kutta(f, x_0, x_n, y_0, h)
        print("Полученные значения при h =", h)
        print(res_1["y"])
        res_2 = runge_kutta(f, x_0, x_n, y_0, h / 2)
        y_1 = res_1["y"][-1]
        y_2 = res_2["y"][-1]
    print("Желаемая точность достигнута при h = ", h)
    return {"r": abs(y_1 - y_2) / (2 ** 4 - 1),
            "h": h,
            "x": res_1["x"],
            "y": res_1["y"]}
