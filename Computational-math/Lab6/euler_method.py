from math import ceil


def euler(f, x_0, x_n, y_0, h):
    n = ceil((x_n - x_0) / h) + 1
    # if n < 4:
    #     print("Слишком маленький интервал для такого шага! Не хватает данных.")
    #     exit(1)
    x = [x_0 + h * i for i in range(n)]
    y = [y_0]
    cur_x = x_0 + h
    i = 0
    # flag = True
    try:
        while cur_x <= x_n:
            new_y = y[i] + h * f(cur_x, y[i])
            # print(cur_x, new_y)
            y.append(new_y)
            # print(y)
            cur_x += h
            i += 1
    except OverflowError:
        print("Возникло переполнение! Вероятно, шаг слишком велик и метод отрабатывает некорректно.")
        exit(1)

    if len(x) < len(y):
        y.pop(-1)
    return {"x": x, "y": y}


def runge_check_for_euler(f, x_0, x_n, y_0, h, eps, exact):
    res_1 = euler(f, x_0, x_n, y_0, h)
    print("Полученные значения при h =", h)
    print(res_1["y"])
    res_2 = euler(f, x_0, x_n, y_0, h / 2)
    exact_y = []
    for x in res_1["x"]:
        exact_y.append(exact(x, x_0, y_0))

    y_1 = res_1["y"][-1]
    y_2 = exact_y[-1]
    while abs(y_1 - y_2) > eps:
        h /= 2
        res_1 = euler(f, x_0, x_n, y_0, h)
        print("Полученные значения при h =", h)
        print(res_1["y"])

        res_2 = euler(f, x_0, x_n, y_0, h / 2)
        y_1 = res_1["y"][-1]
        for x in res_1["x"]:
            exact_y.append(exact(x, x_0, y_0))
        y_2 = exact_y[-1]
    print("Желаемая точность достигнута при h =", h)
    return {"r": abs(y_1 - y_2) / (2 ** 2 - 1),
            "h": h,
            "x": res_1["x"],
            "y": res_1["y"]}
