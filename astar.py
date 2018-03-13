
class Node:

    def __init__(self, name, neighbors, start, end):
        self.name = name
        self.neighbors = neighbors
        self.start = start
        self.end = end

    cost_from_start = 1000
    
    came_from = None


#Neighbors = {'start': ['v1', 'v4'], 'v1': ['start', 'v2'], 'v2' : ['v1', 'end', 'v3'], 'v3' : ['v2'], 'v4' : ['start', 'v5'], 'v5' : ['v4'], 'end' : ['v2']}
Neighbors = {'start': ['v1', 'v4'], 'v1' : ['start', 'v2', 'v3'], 'v2' : ['v1', 'v3', 'end'], 'v3' : ['v2', 'v1', 'v4'], 'v4' : ['start', 'v3', 'end'], 'end' : ['v4', 'v2']}

start = Node('start', Neighbors['start'], True, False)
v1 = Node('v1', Neighbors['v1'], False, False)
v2 = Node('v2', Neighbors['v2'], False, False)
v3 = Node('v3', Neighbors['v3'],False, False)
v4 = Node('v4', Neighbors['v4'], False, False)
end = Node('end', Neighbors['end'], False, True)

StoVar = {'start' : start, 'v1' : v1, 'v2': v2, 'v3': v3, 'v4' : v4, 'end': end}

start.cost_from_start = 0

discovered = [start]
evaluated = []
graph = [start, v1, v2, v3, v4, end]
def get_path():
    while discovered:
        current = min(discovered, key = lambda x: x.cost_from_start)
        if current.end:
            return(make_path(current))
        else:
            discovered.remove(current)
            evaluated.append(current)
            for nbr in current.neighbors:
                nbr_var = StoVar[nbr]
                if nbr_var in evaluated:
                    continue
                elif nbr_var not in discovered:
                    discovered.append(nbr_var)
                    pathcost = current.cost_from_start + 1
                    if pathcost < nbr_var.cost_from_start:
                        nbr_var.cost_from_start = pathcost
                        nbr_var.came_from = current





path = [end.name]
def make_path(n):
    current = n
    while not current.start:
        path.append(current.came_from.name)
        current = current.came_from




print(get_path(), path)

