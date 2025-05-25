import networkx as nx
import matplotlib.pyplot as plt
import json

G = nx.dense_gnm_random_graph(59, 599, seed=99)

# Нарисовать
nx.draw(G, with_labels=True)
# plt.show()

# Плотность
print(nx.classes.density(G))

# Кратчайший путь
print(nx.shortest_path(G, source=3, target=58))

# Наибольшая клика
print(max(nx.find_cliques(G), key=len))

data = '{\n\"1\": \"huy\"\n}'

with open('data.json', 'w') as f:
    json.dump(data, f)
