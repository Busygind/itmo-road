def first_gauss(x, y, t, index_x, dif_table):
    used_difs = []
    result = dif_table[0][index_x]
    used_difs.append(dif_table[0][index_x])
    cur_t = t
    factorial = 1
    size = len(x)
    result += dif_table[1][index_x] * t
    used_difs.append(dif_table[1][index_x])
    cur_t *= (t - 1)
    index_x -= 1
    for i in range(2, size):
        if i == size - 1 and index_x == 1:
            index_x -= 1
        factorial *= i
        result += dif_table[i][index_x] * cur_t / factorial
        used_difs.append(dif_table[i][index_x])
        if i % 2 == 0:
            cur_t *= (t + i - 1)
        else:
            cur_t *= (t - i + 1)
        if i % 2 == 1:
            index_x -= 1
    # print("Использованные разности: ", used_difs)
    return result


def second_gauss(x, y, t, index_x, dif_table):
    used_difs = []
    result = dif_table[0][index_x]
    used_difs.append(dif_table[0][index_x])
    cur_t = t
    factorial = 1
    size = len(x)
    index_x -= 1
    result += dif_table[1][index_x] * t
    used_difs.append(dif_table[1][index_x])
    cur_t *= (t + 1)
    for i in range(2, size):
        factorial *= i
        result += dif_table[i][index_x] * cur_t / factorial
        used_difs.append(dif_table[i][index_x])
        if i % 2 == 0:
            cur_t *= (t - i + 1)
        else:
            cur_t *= (t + i - 1)
        # print("числитель  = ", cur_t)
        if i % 2 == 0:
            index_x -= 1
    # print("Использованные разности: ", used_difs)
    return result


def gauss_interpolation(x, y, point):
    dif_table = count_dif_table(y)
    triangle = [[0 for x in range(len(dif_table))] for y in range(len(dif_table))]
    for i in range(len(dif_table)):
        for j in range(len(dif_table[i])):
            triangle[i][j] = dif_table[i][j]
    imageSwap(triangle, len(triangle))
    # print()
    # print("Таблица конечных разностей: ")
    # for i in range(len(triangle)):
    #     for j in range(len(triangle)):
    #         print(round(triangle[i][j], 2), end=", ")
    #     print()
    index_x = round(len(y) / 2)
    if index_x + 1 == len(y):
        index_x -= 1
    h = x[index_x + 1] - x[index_x]
    t = (point - x[index_x]) / h
    print("t", t)
    if point > x[index_x]:
        return first_gauss(x, y, t, index_x, dif_table)
    else:
        return second_gauss(x, y, t, index_x, dif_table)


def count_dif_table(y):
    dif_table = [y]
    for i in range(len(y) - 1):
        new_arr = []
        for j in range(1, len(dif_table[i])):
            new_arr.append(dif_table[i][j] - dif_table[i][j - 1])
        dif_table.append(new_arr)
    return dif_table


def imageSwap(mat, n):
    # for diagonal which start from at
    # first row of matrix
    row = 0

    # traverse all top right diagonal
    for j in range(n):

        # here we use stack for reversing
        # the element of diagonal
        s = []
        i = row
        k = j
        while (i < n and k >= 0):
            s.append(mat[i][k])
            i += 1
            k -= 1

        # push all element back to matrix
        # in reverse order
        i = row
        k = j
        while (i < n and k >= 0):
            mat[i][k] = s[-1]
            k -= 1
            i += 1
            s.pop()

    # do the same process for all the
    # diagonal which start from last
    # column
    column = n - 1
    for j in range(1, n):

        s = []
        i = j
        k = column
        while (i < n and k >= 0):
            s.append(mat[i][k])
            i += 1
            k -= 1

        i = j
        k = column
        while (i < n and k >= 0):
            mat[i][k] = s[-1]
            i += 1
            k -= 1
            s.pop()