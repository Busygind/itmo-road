import pathlib


def read_data():
    response = input("Прочитать данные из файла? (y/n)")

    if response == "n":
        return read_stdin()
    elif response == "y":
        filename = input("Введите путь до файла: ")
        try:
            return read_file(filename)
        except:
            print("Файл не найден или не может быть прочтен, повторите ввод!")
            return read_data()
    else:
        print("Ошибка ввода!")
        return read_data()


def read_stdin():
    data = {'dots': []}
    
    print("Введите точки через пробел, каждую точку начинайте с новой строки")
    print("Введите \"q\" для завершения ввода")
    while True:
        try:
            new_line = input().strip()
            if new_line == "q":
                if len(data['dots']) < 3:
                    raise AttributeError
                break
            current_dot = tuple(map(float, new_line.split()))
            if len(current_dot) != 2:
                raise ValueError
            data['dots'].append(current_dot)
        except ValueError:
            print("Неверный ввод, должны быть числа! :)")
        except AttributeError:
            print("Слишком мало точек")
    return data


def read_file(filename):
    if filename[0] != "/":
        filename = str(pathlib.Path().resolve()) + "/" + filename
    f = open(filename, "r")
    data = {'dots': []}
    try:
        for line in f:
            current_dot = tuple(map(float, line.strip().split()))
            if len(current_dot) != 2:
                raise ValueError
            data['dots'].append(current_dot)
        if len(data['dots']) < 2:
            raise AttributeError
    except (ValueError, AttributeError):
        return None
    return data
