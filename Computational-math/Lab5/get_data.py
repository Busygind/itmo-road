from plot import show_input_data
from math import sin


def get_data():
    print("Как вы хотите ввести функцию?")
    print("1  -  таблицей")
    print("2  -  функцией")
    print("3  -  таблицей из файла")
    while True:
        try:
            choose_data_method = int(input())
            if choose_data_method == 1:
                data = read_table()
                break
            elif choose_data_method == 2:
                data = choose_function()
                break
            elif choose_data_method == 3:
                data = read_table_from_file()
                break
            else:
                print("Ошибка! Повторите ввод")
        except ValueError:
            print("Введите число! Повторите попытку")

    data.sort()
    print(f"Введенные данные:", data)
    x = [i[0] for i in data]
    y = [i[1] for i in data]
    data = {"x": x, "y": y}
    show_input_data(x, y)

    print("Выберите метод интерполяции: ")
    print("1  -  Лагранж")
    print("2  -  Гаусс")
    print("3  -  Ньютон")
    print("4  -  Все")
    while True:
        try:
            choose_data_method = int(input())
            if choose_data_method == 1:
                method = "lagrange"
                break
            elif choose_data_method == 2:
                method = "gauss"
                break
            elif choose_data_method == 3:
                method = "newton"
                break
            elif choose_data_method == 4:
                method = "all"
                break
            else:
                print("Ошибка! Повторите ввод")
        except ValueError:
            print("Введите число! Повторите попытку")

    print("Введите точку для интерполяции")
    while True:
        try:
            point = float(input())
            if point < x[0] or point > x[len(x) - 1]:
                print("Точка должна быть > x_min и < x_max. Повторите ввод")
            else:
                break
        except ValueError:
            print("Введите число! Повторите попытку")

    return data, method, point


def read_table():
    print("Введите точки через пробел")
    print("Введите 'q' чтобы закончить")
    data = []
    line = input()
    while line != "q":
        try:
            values = line.strip().split()
            if len(values) == 2:
                data.append((float(values[0]), float(values[1])))
            else:
                print("Неверный формат! Повторите ввод")
            line = input()
        except ValueError:
            print("Оба значения должны быть числами! Повторите попытку")
            line = input()      
    return data


def read_table_from_file():
    print("Введите имя файла")
    data = []
    while True:
        line = input()
        try:
            f = open(line, "r")
            for line in f:
                try:
                    values = line.strip().split()
                    if len(values) == 2:
                        data.append((float(values[0]), float(values[1])))
                    else:
                        print("Неверный формат! Повторите ввод")
                        continue
                except ValueError:
                    print("Оба значения должны быть числами! Повторите попытку")
                    continue
        except Exception:
            print("Файл с таким названием не найден")
            continue
        return data


def choose_function():
    print("Выберите функцию:")
    print("1  -  x^2")
    print("2  -  sin(x)")
    while True:
        try:
            choose_data_method = int(input())
            if choose_data_method == 1:
                f = lambda x : x**2
                break
            elif choose_data_method == 2:
                f = lambda x : sin(x)
                break
            else:
                print("Ошибка ввода! Повторите попытку!")
        except ValueError:
            print("Значение должно быть числом! Повторите попытку")
       
    print("Введите границы промежутка через пробел")
    while True:
        try:
            values = input().strip().split()
            if len(values) == 2:
                bounds = (float(values[0]), float(values[1]))
                break
            else:
                print("Неверный формат! Повторите ввод")
        except ValueError:
            print("Оба значения должны быть числами! Повторите ввод")
        
    print("Введите количество узлов интерполяции:")
    while True:
        try:
            n = int(input())
            if n < 2:
                print("Количество узлов интерполяции должно быть больше 1! Повторите ввод")
            else:
                break
        except ValueError:
            print("Значение должно быть числом! Повторите попытку")
       
    h = abs(bounds[1] - bounds[0])/n
    data = []
    [data.append((bounds[0] + h*i, f(bounds[0] + h*i))) for i in range(n)]
    return data
