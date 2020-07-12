

import graphviz as grp

'''clase temporal  se debe modificar para que sea compatible con las operaciones de automatas
'''

class Graphic:

    def __init__(self,automata):
        self.graph=automata #esto será un diccionario

                
    def graph_all(self, q0, f):
        """[metodo que genera una imagen con libreria graphviz ]
            los datos que se le manda al gr.edge()debe ser string o int
        Args:
            q0 ([Estado]): [estado inicial]
            f ([Estado]): [estado final]
        """
        gr = grp.Digraph(format = 'png', directory = 'D:\Programming\Python\Automata1', strict = True)
        gr.node('ini', shape = 'point')
        gr.graph_attr['rankdir'] = 'LR'
        for node in self.graph:
            if node == f:
                gr.node(node, shape = 'doublecircle', color = 'green', size = '1,1')
            if node == q0:
                gr.node(node, color = 'blue')
                gr.edge('ini', node)
            gr.node(node, label = node)
        
        for node in self.graph:
            for x in self.graph[node]:       
                gr.edge(tail_name=str(x[0]),head_name=str(x[1]),label=str(x[2]))

        gr.render(view = False, directory = 'D:\Programming\Python\Automata1', cleanup = True, filename = 'Full automata')
        
  

    def stepbstep(self, traversal, q0, f):
        #print(traversal)
#       #self.convert_graph()
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
        