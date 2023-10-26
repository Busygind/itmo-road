import sys

from approximation.cube import cube_approximation
from approximation.exp import exp_approximation
from approximation.line import line_approximation
from approximation.log import log_approximation
from approximation.power import power_approximation
from approximation.square import square_approximation
from input_data import read_data
from plot import plot_results
from approximation.Solver import Solver


def print_results(results):
    print()
    print("====== РЕЗУЛЬТАТЫ АППРОКСИМИРУЮЩИХ МЕТОДОВ ======")
    for key in results:
        print("Метод: " + key)
        for inner_key in results[key]:
            if inner_key == 'function':
                continue
            print(inner_key + " = " + str(results[key][inner_key]))


def print_best(results):
    print()
    print("====== НАЙДЕМ ЛУЧШИЙ МЕТОД ======")
    min = float('inf')
    best = None
    for key in results:
        value = results[key]
        if min > value['Среднеквадратичное отклонение']:
            min = value.get('Среднеквадратичное отклонение')
            best = key
        print(f"Среднеквадратическое отклонение({key}) =", (value.get('Среднеквадратичное отклонение')))
    print(f"Лучший метод - {best} со среднеквадратичным отклонением = {results[best].get('Среднеквадратичное отклонение')}")
    print()
    for key in results[best]:
        if key == 'function' or key == 'a' or key == 'b': continue
        print(f"{key} = {results[best].get(key)}")


def sort_data(data):
    data['dots'].sort(key=lambda tup: tup[0])
    return data


def check_results_not_none(results):
    fail_list = []
    for key in results:
        if results[key] is None:
            fail_list.append(key)

    for key in fail_list:
        results.pop(key)


if __name__ == '__main__':
    data = sort_data(read_data())
    if data is None:
        sys.exit(0)

    points = data['dots']
    print(points)

    line_solver = Solver(line_approximation)
    square_solver = Solver(square_approximation)
    cube_solver = Solver(cube_approximation)
    power_solver = Solver(power_approximation)
    log_solver = Solver(log_approximation)
    exp_solver = Solver(exp_approximation)

    results = {
        'line': line_solver.solve(points),
        'square': square_solver.solve(points),
        'cube': cube_solver.solve(points),
        'power': power_solver.solve(points),
        'log': log_solver.solve(points),
        'exp': exp_solver.solve(points)
    }

    check_results_not_none(results)

    print_results(results)

    print_best(results)

    plot_results(data, results)

    sys.exit(0)

