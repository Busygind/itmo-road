import sympy

from equation_system import *
from symengine import *
from sympy import Add, Mul, nsolve, diff, zeros, sympify


def newtons_method(system, a, b, eps):
    MAX_ITER = 1000
    vars = symbols('x y')
    f = sympify([elem for elem in system.funcs])
    J = zeros(len(f), len(vars))
    for i, fi in enumerate(f):
        for j, s in enumerate(vars):
            J[i, j] = diff(fi, s)
    dx, dy = symbols('dx dy')
    J[0] = Mul(J[0], dx)
    J[1] = Mul(J[1], dy)
    J[2] = Mul(J[2], dx)
    J[3] = Mul(J[3], dy)
    first = Add(J[0], J[1])
    second = Add(J[2], J[3])
    x0 = a
    y0 = b
    found = False
    f[0] = Mul(f[0], -1)
    f[1] = Mul(f[1], -1)
    xAns = 0
    yAns = 0
    k = 0
    while not found:
        k += 1
        if k > MAX_ITER:
            return None
        firstTemp = first.subs(vars[0], x0)
        firstTemp = firstTemp.subs(vars[1], y0)
        secondTemp = second.subs(vars[0], x0)
        secondTemp = secondTemp.subs(vars[1], y0)
        f0Temp = f[0].subs(vars[0], x0)
        f0Temp = f0Temp.subs(vars[1], y0)
        f1Temp = f[1].subs(vars[0], x0)
        f1Temp = f1Temp.subs(vars[1], y0)
        firstTemp = Add(firstTemp, -1 * f0Temp)
        secondTemp = Add(secondTemp, -1 * f1Temp)
        ans = nsolve((firstTemp, secondTemp), (dx, dy), (0, 0))
        x1, y1 = x0 + float(ans[0]), y0 + float(ans[1])
        if abs(x1 - x0) <= eps and abs(y1 - y0) <= eps:
            found, xAns, yAns = True, x1, y1
        else:
            x0, y0 = x1, y1

    final_res = [f[0].subs(vars[0], xAns), f[1].subs(vars[0], xAns)]
    final_res = [float(final_res[0].subs(vars[1], yAns)), float(final_res[1].subs(vars[1], yAns))]
    return [xAns, yAns], k, [xAns - x0, yAns - y0], final_res


if __name__ == '__main__':
    system = EquationSystem("sin(x) - y", "cos(x) - y")
    newtons_method(system)
