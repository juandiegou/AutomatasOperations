

import graphviz as grp

'''clase temporal  se debe modificar para que sea compatible con las operaciones de automatas
'''

class Graphic:

    def __init__(self,automata):
        self.graph=automata

    def convert_graph(self):
        new_graph = {}
        for node in self.graph:
            new_node = ''.join(str(node))
            new_graph[new_node] = {}
            for adj in self.graph[node]:    
                new_adj = ''.join(str(adj))
                value = self.graph[node][adj]
                new_graph[new_node].update({new_adj: value})
        self.graph = new_graph
        
    def graph_all(self, q0, f):
        self.convert_graph()
        gr = grp.Digraph(format = 'png', directory = 'D:\Programming\Python\Automata1', strict = True)
        gr.node('ini', shape = 'point')
        
        for node in self.graph:
            if node == f:
                gr.node(node, shape = 'doublecircle', color = 'green', size = '1,1')
            if node == q0:
                gr.node(node, color = 'blue')
                gr.edge('ini', node)
            gr.node(node, label = node)
        
        for node in self.graph:
            for adj in self.graph[node]:
                label_name = self.graph[node][adj]
                gr.edge(node, adj, color = 'red', label = label_name)
#                name = 'aista' + node + adj
#                gr.render(view = False, directory = 'D:\Programming\Python\Automata1', cleanup = True, filename = name)
        gr.render(view = True, directory = 'D:\Programming\Python\Automata1', cleanup = True, filename = 'Full automata')
        
    def graph_shortest(self, traversal, q0, f):
        if traversal is not None:
            print(traversal)
    #        self.convert_graph()
            new_graph = {}
            last_node = None
            for step in traversal:
                for node in self.graph:
                    if str(step) == node:
                        new_graph[node] = {}
                        if last_node is not None:
                            new_graph[last_node] = {node: self.graph[last_node][node]}
                        last_node = node
                        break
                  
            self.graph = new_graph
            self.convert_graph()
            gr = grp.Digraph(format = 'png', directory = 'D:\Programming\Python\Automata1', strict = True)
            gr.node('ini', shape = 'point')
            
            for node in new_graph:
                if node == f:
                    gr.node(node, shape = 'doublecircle', color = 'green', size = '1,1')
                if node == q0:
                    gr.node(node, color = 'blue')
                    gr.edge('ini', node)
                gr.node(node, label = node)
            
            for node in new_graph:
                for adj in new_graph[node]:
                    label_name = new_graph[node][adj]
                    gr.edge(node, adj, color = 'red', label = label_name)
    #                name = 'aista' + node + adj
    #                gr.render(view = False, directory = 'D:\Programming\Python\Automata1', cleanup = True, filename = name)
            gr.render(view = True, directory = 'D:\Programming\Python\Automata1', cleanup = True, filename = 'Shortest traversal')


    def stepbstep(self, traversal, q0, f):
        #print(traversal)
#       self.convert_graph()
        new_graph = {}
        last_node = None
        for step in traversal:
            for node in self.graph:
                if str(step) == node:
                    new_graph[node] = {}
                    if last_node is not None:
                        new_graph[last_node] = {node: self.graph[last_node][node]}
                    last_node = node
                    break
              
        self.graph = new_graph
        self.convert_graph()
        gr = grp.Digraph(format = 'png', directory = 'D:\Programming\Python\Automata1/sbs', strict = True)
        gr.graph_attr['rankdir'] = 'LR'
        gr.node('ini', shape = 'point')
        
        for node in new_graph:
            if node == f:
                gr.node(node, shape = 'doublecircle', color = 'GREEN', size = '1,1')
            if node == q0:
                gr.node(node, color = 'BLUE')
                gr.edge('ini', node)
            gr.node(node, label = node)
            
        i=0
        for node in new_graph:
            for adj in new_graph[node]:
                label_name = new_graph[node][adj]
                gr.edge(node, adj, color = 'YELLOW', label = label_name)
#               name = 'aista' + node + adj
#                gr.render(view = False, directory = 'D:\Programming\Python\Automata1', cleanup = True, filename = name)
                gr.render(view = False, directory = 'D:\Programming\Python\Automata1/sbs', cleanup = True, filename = 'img'+str(i))
                i+=1
        