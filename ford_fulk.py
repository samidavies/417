# simple implementation of Ford-Fulkerson algorithm


class Edge():
    def __init__(self, start, sink, capacity):
        self.start = start
        self.sink = sink
        self.capacity = capacity


class FlowNetwork():
    def __init__(self):
        self.edges = {}
        self.flow = {}

    def AddVertex(self, u):
        self.edges[u] = []

    def AddEdge(self, start, sink, c):
        forward = Edge(start, sink, c)
        backward = Edge(sink, start, 0)
        forward.backward = backward
        backward.backward = forward
        self.edges[start].append(forward)
        self.edges[sink].append(backward)
        self.flow[forward] = 0
        self.flow[backward] = 0

    def Adjs(self, vertex):
        return(self.edges[vertex])

    def getPath(self, source, sink, partial):
        if source == sink:
            return(partial)
        for edge in self.Adjs(source):
            residual = edge.capacity - self.flow[edge] 
            if residual > 0 and not (edge, residual) in partial:
                newPath = self.getPath(edge.sink, sink, partial + [(edge, residual)])
                if newPath is not None:
                    return(newPath)
    
    def maxFlow(self, source, sink):
        path = self.getPath(source, sink, [])
        while path is not None:
            push = min(residual for (edge, residual) in path)
            for (edge, residual) in path:
                self.flow[edge] += push
                self.flow[edge.backward] -= push
            path = self.getPath(source, sink, [])
        return(sum(self.flow[edge] for edge in self.Adjs(source)))


g = FlowNetwork()
nodes = ['s', 'o', 'p', 'q', 'r', 't']
for node in nodes:
    g.AddVertex(node)
print(g.edges)
g.AddEdge('s', 'o', 5)
g.AddEdge('s', 'p', 3)
g.AddEdge('o', 'p', 2)
g.AddEdge('o', 'q', 3)
g.AddEdge('p', 'r', 4)
g.AddEdge('r', 't', 3)
g.AddEdge('q', 'r', 4)
g.AddEdge('q', 't', 2)
print(g.maxFlow('s', 't'))

