import random
import networkx as nx
import matplotlib.pyplot as plt 


def creat_graph(filename):
	edges = []
	with open(filename, "r") as file:
		for line in file: 
			edge = line.split()
			edges.append((int(edge[0]),int(edge[1])))
	graph = nx.from_edgelist(edges)
	return graph

# set its label to a label occurring with the 
# highest frequency among its neighbours
def set_label(node, voisins, labels):
	# Computing neighbor's label frequency
	freq_labels = {}
	for v in voisins:
		freq_labels[labels[v]] = freq_labels.get(labels[v], 0) + 1
	# Find the highest frequency label
	max_label, max_freq = list(freq_labels.items())[0]
	for label in freq_labels:
		if freq_labels[label] > max_freq:
			max_freq = freq_labels[label]
			max_label = label
	labels[node] = max_label

def draw_graph(graph, labels=None):
	dict_community = {}
	for label in labels:
		dict_community[labels[label]] = dict_community.get(labels[label], []) + [label]
	# positions for all nodes
	pos = nx.spring_layout(graph)
	color = [ 'blue', 'red', 'yellow', 'orange', 'magenta',
				'cyan', 'chocolate', 'pink','green' ]
	i = 0
	for k, v in dict_community.items():
		while i >= len(color):
			i -= 1
		nx.draw_networkx_nodes(graph,
								pos,
								nodelist=v,
								node_color=color[i],
								node_size=50,
								alpha=0.8)
		i += 1
	del dict_community
	nx.draw_networkx_edges(graph, pos, width=1.0, alpha=0.5)
	plt.axis('off')
	# sauvegarder
	plt.savefig("comunity_youtube.png")  
	# nx.draw(graph)
	plt.show()

def verifier_fin(graph,nodes,labels):
	fin = True
	for u in nodes:
		neighbors = nx.neighbors(graph, u)
		# Computing neighbor's label frequency
		freq_labels = {}
		for v in neighbors:
			freq_labels[labels[v]] = freq_labels.get(labels[v], 0) + 1
		# Find the highest frequency label
		max_label ,max_freq = list(freq_labels.items())[0]
		for label in freq_labels:
			if freq_labels[label] > max_freq:
				max_freq = freq_labels[label]
				max_label = label
		
		if not labels[u] == max_label :
			fin = False
	return fin

def LabelPropagation(graph, nodes, labels):
	fin = False
	while not fin :
		# print("labels : ",labels)
		# Step 2: Arrange the nodes in the network in a random order
		random.shuffle(nodes)
		# Step 3:for each node, set its label to a label occurring with the 
		# highest frequency among its neighbours
		for node in nodes:
			voisins = nx.neighbors(graph, node)
			set_label(node, voisins ,labels)
		# Step 4:go to 2 as long as there exists a node with a labelthat 
		# does not have the highest frequency among itsneighbours.
		if verifier_fin(graph, nodes, labels):
			fin = True
	return labels

if __name__ == "__main__":
	filename = "./cleaned/graph.txt"
	graph = creat_graph(filename)
	nodes = list(graph.nodes())
	edges = list(nx.edges(graph))
	# Step 1:give a unique label to each node in the network
	labels = {node: node for node in nodes}
	for i in range(0,3):
		labels = LabelPropagation(graph, nodes,labels)		
	#the numbers of communities obtained
	set_labels = set(labels.values())
	l_labels = list(labels.values())
	nb_communities = len(set_labels)
	print("the numbers of communities: ", nb_communities)
	size_community = {x:l_labels.count(x) for x in l_labels}
	print(size_community)
	draw_graph(graph, labels)

