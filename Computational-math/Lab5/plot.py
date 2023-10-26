import matplotlib.pyplot as plt

from lagrange import lagrange_interpolation


def show_input_data(x, y):
    fig = plt.figure()
    fig.suptitle('input data', fontsize=20)
    plt.xlabel('x', fontsize=16)
    plt.ylabel('y', fontsize=16)
    fig.savefig('interpolation_input_data.png')
    plt.plot(x, y, label="input data")
    plt.legend()
    plt.show()


def plot_result(x, y, point, result):
    x_min = min(x)
    x_max = max(x)
    h = (x_max - x_min) / 100
    y_new = []
    x_new = []
    i = x_min
    while i < x_max:
        x_new.append(i)
        y_new.append(lagrange_interpolation(x, y, i))
        i += h
    fig = plt.figure()
    fig.suptitle('Interpolation result', fontsize=20)
    plt.xlabel('x', fontsize=16)
    plt.ylabel('y', fontsize=16)
    fig.savefig('interpolation_result.png')
    plt.plot([point], [result], marker="o", label=f"interpolation result ({round(result, 5)})")  # marker circle
    plt.plot(x, y, marker=".", label="input data")  # marker point
    plt.plot(x_new, y_new, label="interpolated input data")
    plt.legend()
    plt.show()


def plot_all_results(x, y, point, lagrange, gauss, newton):
    x_min = min(x)
    x_max = max(x)
    h = (x_max - x_min) / 100
    y_new = []
    x_new = []
    i = x_min
    while i < x_max:
        x_new.append(i)
        y_new.append(lagrange_interpolation(x, y, i))
        i += h

    fig = plt.figure()
    fig.suptitle('Interpolation result', fontsize=20)
    plt.xlabel('x', fontsize=16)
    plt.ylabel('y', fontsize=16)
    plt.scatter([point], [lagrange], marker="o", linewidths=6,
                label=f"lagrange interpolation result ({round(lagrange, 5)})")
    plt.scatter([point], [gauss], marker="o", linewidths=4,
                label=f"gauss interpolation result ({round(gauss, 5)})")
    if newton is not None:
        plt.scatter([point], [newton], marker="o", linewidths=2,
                    label=f"newton interpolation result ({round(newton, 5)})")
    # plt.plot(x_new, y_new, marker=".", label="input data")  # marker point
    plt.plot(x_new, y_new, label="input data")
    plt.legend()
    plt.show()
