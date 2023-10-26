import matplotlib.pyplot as plt


def plot_results(result, exact, y_0):
    # print("X:", result["x"])
    # print("Полученные значения Y:", result["y"])


    exact_y = []
    x_0 = result["x"][0]
    for x in result["x"]:
        exact_y.append(exact(x, x_0, y_0))

    if len(result["x"]) < len(exact_y):
        exact_y.pop(-1)

    if len(result["x"]) < len(result["y"]):
        exact_y.pop(-1)
    # print("Точные значения Y:", exact_y)
    for i in range(0, len(result["x"])):
        print("X:", round(result["x"][i], 3), "Y:", round(result["y"][i], 3), "Точный Y:", round(exact_y[i], 3))

    fig = plt.figure()
    fig.suptitle('Result', fontsize=20)
    plt.xlabel('x', fontsize=16)
    plt.ylabel('y', fontsize=16)
    # plt.scatter([point], [lagrange], marker="o",
    #             label=f"lagrange interpolation result ({round(lagrange, 5)})")
    # plt.scatter([point], [gauss], marker="o",
    #             label=f"gauss interpolation result ({round(gauss, 5)})")
    plt.plot(result["x"], result["y"], label="calculated data", linewidth=3)
    plt.plot(result["x"], exact_y, label="exact data")
    plt.legend()
    plt.show()
