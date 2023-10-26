import math

import numpy as np
from symengine import symbols
from sympy import sympify


def check_interval(func, a, b):
    x = symbols('x')
    f = sympify(func)
    count = 0
    prev = math.copysign(1, float(f.subs(x, a)))
    for i in np.arange(a + (abs(a) + abs(b))/1000, b + (abs(a) + abs(b))/1000, (abs(a) + abs(b))/1000):
        val = math.copysign(1, float(f.subs(x, i)))
        if val != prev:
            count += 1
            prev = val
    return count
