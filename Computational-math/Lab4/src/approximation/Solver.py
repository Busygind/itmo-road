from approximation.tools.standart_deviation import variance, standard_deviation


class Solver:

    def __init__(self, solver):
        self.solver = solver

    def solve(self, points):
        n = len(points)
        x = [point[0] for point in points]
        y = [point[1] for point in points]
        result = self.solver(n, x, y)
        if result is None:
            return None
        result['Отклонение'] = variance(points, result['function'])
        result['Среднеквадратичное отклонение'] = standard_deviation(points, result['function'])
        result['fi'] = [result['function'](xi) for xi in x]
        result['eps'] = [result['fi'][i] - y[i] for i in range(n)]
        return result
