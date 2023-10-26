from data.functions import functions_primordial

eps = 1e-10


def is_death_dot(f, a):
    try:
        result = f(a)
        if abs(result) > 1e10:
            print(a)
            return True
        return False
    except Exception:
        print(a)
        return True


def check_convergence(f, a):
    try:
        result = functions_primordial[f](a)
        if abs(result) > 1e10:
            raise ValueError("Интеграл не сходится")
    except Exception:
        raise ValueError("Интеграл не сходится")


def find_death_dot_intervals(f, a, b) -> list[tuple[float, float]]:
    intervals: list[tuple[float, float]] = []
    step = (b - a) / 1000
    left = a
    if is_death_dot(f, a):
        check_convergence(f, a)
        left = a + eps
    while a < b:
        a += step
        if is_death_dot(f, a):
            check_convergence(f, a)
            intervals.append((left, a - eps))
            left = a + eps
    if is_death_dot(f, b):
        check_convergence(f, b)
        intervals.append((left, b - eps))
    else:
        intervals.append((left, b))
    return intervals
