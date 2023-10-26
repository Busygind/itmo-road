from symengine import *
from sympy import plot_implicit, plot, sympify


def plot_system(system, xl, xr, yd, yt):
    x, y = symbols('x y')
    f = sympify(system.funcs[0])
    g = sympify(system.funcs[1])
    if xl < 0 and xr < 0:
        xr = -1 * xl
    elif xl > 0 and xr > 0:
        xl = -1 * xr
    if yd < 0 and yt < 0:
        yt = -1 * yd
    elif yd > 0 and yt > 0:
        yd = -1 * yt
    if xl == 0:
        xl = -1 * xr
    if xr == 0:
        xr = -1 * xl
    if yd == 0:
        yd = -1 * yt
    if yt == 0:
        yt = -1 * yd
    p = plot_implicit(f, (x, xl, xr), (y, yd, yt), show=False, line_color='r')
    p2 = plot_implicit(g, (x, xl, xr), (y, yd, yt), show=False)
    p.extend(p2)
    p.show()


def plot_equation(eq, xl, xr):
    x = symbols('x')
    f = sympify(eq)
    if xl < 0 and xr < 0:
        xr = -1 * xl
    elif xl > 0 and xr > 0:
        xl = -1 * xr
    if xl == 0:
        xl = -1 * xr
    if xr == 0:
        xr = -1 * xl
    p = plot(f, (x, xl, xr), line_color='r')