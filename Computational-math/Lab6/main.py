from data_input import input_data
from euler_method import runge_check_for_euler
from runge_kutta_method import runge_check_for_rk
from milne_method import milne, check_accuracy
from plot import plot_results


def main():
    data = input_data()
    result = {}
    if data["method"] == "euler":
        result = runge_check_for_euler(data["f"], data["bounds"][0], data["bounds"][1], data["init"], data["h"], data["eps"], data["exact"])
    if data["method"] == "runge-kutta":
        result = runge_check_for_rk(data["f"], data["bounds"][0], data["bounds"][1], data["init"], data["h"], data["eps"])
    if data["method"] == "milne":
        result = milne(data["f"], data["bounds"][0], data["bounds"][1], data["init"], data["h"], data["eps"])
        is_accurate = check_accuracy(result, data["exact"], data["init"], data["eps"])
        if is_accurate[0]:
            print("Метод отработал с приемлемой точностью")
        else:
            print("Необходимая точность не достигнута")
        print("Полученная точность eps =", is_accurate[1])
    plot_results(result, data["exact"], data["init"])


if __name__ == '__main__':
    main()
