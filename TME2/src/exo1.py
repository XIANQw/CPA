import networkx as nx  
import matplotlib.pyplot as plt 
import random

G = nx.Graph()
with open("./cleaned/graph.txt") as data:
	for line in data:
		u, v = line.strip().split(" ")
		G.add_edge(int(u), int(v))

nx.draw(G)
plt.savefig("exo1.png")
plt.show()
