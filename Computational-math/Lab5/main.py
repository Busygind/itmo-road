from gauss import gauss_interpolation
from get_data import get_data
from lagrange import lagrange_interpolation
from newton import newton_interpolation
from plot import plot_result
from plot import plot_all_results


def main():
    data, method, point = get_data()
    x = data["x"]
    y = data["y"]

    if method == "lagrange":
        result = lagrange_interpolation(x, y, point)
        print("Результат: ", result)
        plot_result(x, y, point, result)
    elif method == "gauss":
        result = gauss_interpolation(x, y, point)
        print("Результат: ", result)
        plot_result(x, y, point, result)
    elif method == "newton":
        result = newton_interpolation(x, y, point)
        print("Результат: ", result)
        plot_result(x, y, point, result)
    elif method == "all":
        lagrange = lagrange_interpolation(x, y, point)
        gauss = gauss_interpolation(x, y, point)
        newton = newton_interpolation(x, y, point)
        print("Результат Лагранж: ", lagrange)
        print("Результат Гаусс: ", gauss)
        print("Результат Ньютон: ", newton)
        plot_all_results(x, y, point, lagrange, gauss, newton)
    else:
        print("Возникла ошибка ввода!")


main()
