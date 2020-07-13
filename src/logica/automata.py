
import itertools
from logica.estado import Estado
from logica.estadoI import EstadoI
from logica.estadoF import EstadoF
class Automata(object):

    _estados=[]
    _transiciones=[]
    isanAFND=None
    isanAFD=None

    def __init__(self,estados,transiciones):
        """[constructor de la clase automata]

        Args:
            estados ([list<Estado>]): [una lista con los estados del automata]
            transiciones ([list<Transicion>]): [una lista con las trasiciones del automata]
        """
        self._estados=estados
        self._transiciones=transiciones
        self.automata=self.genereAutomata(self._estados,self._transiciones)
        self.isanAFD=self.isAFD()
        self.isanAFND=self.isAFND()
        self.__constantes=['(',')','|','+','*','?','@','.']
        #print(self.automata)


    def getISAFND(self):
        """[getter para saber si es AFND]

        Returns:
            [boolean]: [True si es AFND]
        """
        return self.isanAFND

    def getISAFD(self):
        """[getter para saber si es AFD]

        Returns:
            [boolean]: [True si es AFD]
        """
        return self.isanAFD

    
    def genereAutomata(self,estados,transiciones):
        """[metodo que genera el autmata a partir de un diccionario]

        Args:
            estados ([list<Estados>]): [una lista con los estados del automata]
            transiciones ([list<Transicion>]): [una lista con las transiciones del automata]

        Returns:
            [dict]: [el automata como un diccionario]
        """
        automata={}
        for e in estados:
            automata[str(e.getNombre())]=[]
        #print(automata)

        for t in transiciones:
            automata[str(t.getEstadoI())].append((t.getEstadoI(),t.getEstadoF(),t.getValor() ))

        return automata

    def getEstados(self):
        """[metodo que permite obtener la lista con los estados del automata]

        Returns:
            [list]: [una lista con todos los estados del automata]
        """
        return self._estados

    def getTransiciones(self):
        """[metodo que permite obtener todas las transiciones del automata]

        Returns:
            [list]: [una lista con las transiciones del automata]
        """
        return self._transiciones

    def getAutomata(self):
        """[metodo que devuelve los valores del automata(una lista de transiciones , una lista de estados)]

        Returns:
            [lists]: [una lista de estados, y una lista de transiciones]
        """
        return self._estados,self._transiciones


    def isAFND(self):
        """[metodo que calcula si es un AFND]

        Returns:
            [boolean]: [True si es AFND , False si no lo es]
        """
        for e in self.automata.keys():
            if self.validaTransiciones(self.automata[e]):
                return True
       

        return False
        

    def validaTransiciones(self,lista):
        """[metodo que que recorre una lista de transiciones para saber si se repiten o son lambda]

        Args:
            lista ([list]): [una lista de transiciones de un estado]

        Returns:
            [boolean]: [True si la transicion es repetida o lambda, False si ninguna trasicion es ]
        """
        listado=sorted(itertools.combinations(lista,2))
        l=[]
        for x in listado:
            for y in x:
                if y[2]=='@':  #@ representa una transicion lambda
                    return True
                l.append(y)
            if self.compararTuplas(l[0],l[1]):
                return True
            l=[]
        return False


    def compararTuplas(self,tupla1,tupla2): 
        """[metodo que compara si los valores de dos tuplas son iguales]

        Args:
            tupla1 ([tuple]): [una tupla que representa una transicion]
            tupla2 ([tuple]): [una tupla que representa una transicion]

        Returns:
            [boolean]: [True si son iguales]
        """

        return tupla1[:2]==tupla2[:2]


    def isAFD(self):
        """[metodo que permite saber si el automata es AFD]

        Returns:
            [boolean]: [True si es AFD, False si no lo es]
        """        
        for e in self.automata.keys():
            if not (self.validaTransiciones(self.automata[e])):
                return True
        return False


    def minimizar(self):
        if self.isAFD():
            finales=self.obtenerFinales()
            estados=self.__restar__(self.__getEstados__(),finales)
            distinguibles=[]
            for x in self._transiciones:
                y=x.getTransicion()
                if (y[0] in estados and y[1] in finales) or (y[0] in finales and y[1] in estados):
                    distinguibles.append([y[0],y[1]])
                print(y)
            print(distinguibles)
        

                


    def conjuntos(self):
        finales=self.obtenerFinales()
        estados=self.__restar__(self.__getEstados__(),finales)
        lista=[estados,finales]
        t=self.obtenerConjuntos(estados,finales)
        t2=[]
        while t!=t2:
            t2=self.obtenerConjuntos(t,finales)


    def obtenerConjuntos(self,e,f):
        pass
    
    def marcar(self,t,l):
        lis=[]
        for x in t:
            #print(x.getTransicion())
            if x in l :
                lis.append(x)
        #print("saliendo",l)
        return l

    def funcionTransicion(self,x,v):
        l=self.getTransicionesDe(self.getEstadoX(x))
        for y in l:
            if y.getTransicion()[2]==v:
                return y.getTransicion()[1]
        
    
    def obtenerValores(self):
        valores=[]
        for t in self._transiciones:
            if t.getTransicion()[2] not in valores:
                valores.append(t.getTransicion()[2])
        return valores
            

    def obtenerFinales(self):
        finales=[]
        for e in self._estados:
            if e.isFinal():
                finales.append(e.getNombre())
        return finales

    def recorrer(self):
        pass


    def transformar(self,d):
        k= self.__restar__(self.obtenerValores(),self.__constantes)
        estados=[]
        transiciones=[]
        f=True
        f2=True
        for e in d.keys():
            if d[e]!=[]:
                for x in e:
                    if self.getEstadoX(x).isInicial():
                        if f:
                            estados.append(EstadoI(str(d[e])))
                            f=False
                    elif self.getEstadoX(x).isInicial():
                        if f2:
                            estados.append(EstadoF(str(d[e])))
                            f=False
                estados.append(Estado(str(d[e])))

        for l in d.keys():
            if d[l]!=[]:
                for w in d[l]:
                    pass


                








    def afndToAfd(self):
        if self.isAFND():
            char =1
            dic={}
            ce=self.clausura_epsilon(self.getInicial(),'@')
            dic[char]=ce
            k= self.__restar__(self.obtenerValores(),self.__constantes)
            for x in ce:
                for y in k:
                    m=self.moverA(x,y)
                    if m is not None:
                        cl=self.clausura_epsilon(m,'@',[m])
                        dt=self.validaDic(dic,cl)
                        if dt!=dic:
                            dic=dt
                        else:
                            print(dic)
                        cl.clear()
            return dic

    def validaDic(self,dic,l):
        for x in dic.keys():
            y=dic[x]
            if self.comparaListas(y,l):
                continue
            else:
                lis=dic.keys()
                n=len(lis)
                dic[n+1]=l
                return dic

        return dic




    def moverA(self,estado,transicion):
        """[metodo de operacion MoverA]

        Args:
            estado ([type]): [description]
            transicion ([type]): [description]

        Returns:
            [type]: [description]
        """
        lista=self.transicionx(self.getEstadoX(estado))
        for x in lista:
            y=x.getTransicion()
            if transicion == y[2]:
                return y[1]
        
        return None

    def clausura_epsilon(self,estado,valor='@',listaTransiciones=[]):
        """[metod de la operacion de clausura de epsilon]

        Args:
            estado ([type]): [description]
            listaTransiciones (list, optional): [description]. Defaults to [].

        Returns:
            [type]: [description]
        """
        if estado not in listaTransiciones:
            listaTransiciones.append(estado)
            
        for transicion in self.automata[estado]:
            if transicion[2]==valor:
                self.clausura_epsilon(transicion[1],listaTransiciones)
                
        return listaTransiciones



    def transicionx(self,estado):
        lista=[]
        for x in self._transiciones:
            if x.getTransicion()[0]==estado.getNombre():
                lista.append(x)
        return lista



    def getInicial(self):
        """[metodo que permite saber cual es el nodo inicial]

        Returns:
            [Estado]: [retorna el nombre del  estado inicial]
        """
        for x in self._estados :
            if x.isInicial():
                return x.getNombre()

    def getFinal(self):
        """[metodo que permite saber el nodo final]

        Returns:
            [Estado]: [retorna el estado final]
        """
        for y in self._estados:
            if y.isFinal():
                return y.getNombre()



    def getEstadoInicial(self):
        """[metodo que permite saber cual es el nodo inicial]

        Returns:
            [Estado]: [retorna el estado inicial]
        """
        for x in self._estados :
            if x.isInicial():
                return x
        print("no se encuentra estado inincial")

    def getEstadoFinal(self):
        """[metodo que permite saber cual es el estado final]

        Returns:
            [Estado]: [retorna el estado final]
        """
        for x in self._estados :
            if x.isFinal():
                return x

    def setEstadoI(self,estado):
        """[permite cambiar el asiganr un nuevo estado Inicial]

        Args:
            estado ([Estado]): [el estado que sera un nuevo estado Inicial]
        """
        self.getEstadoInicial().setIsInicial(False)
        estado.setIsInicial(True)
        self.automata=self.genereAutomata(self._estados,self._transiciones)

    def setEstadoF(self,estado):
        """[permite cambiar o asiganr un nuevo estado Final]

        Args:
            estado ([Estado]): [el estado que sera un nuevo estado Final]
        """
        self.getEstadoFinal().setIsFinal(False)
        estado.setIsInicial(True)
        self.automata=self.genereAutomata(self._estados,self._transiciones)



    def agregarEstado(self,estado):
        """[permite agregar un nuevo estado a la lista de estados]

        Args:
            estado ([Estado]): [estado que se agregara]
        """
        self._estados.append(estado)
        self.automata=self.genereAutomata(self._estados,self._transiciones)

    def agregarTransicion(self,transicion):
        """[permite agregar una transicion]

        Args:
            transicion ([Transicion]): [la transicion que se agregara]
        """
        self._transiciones.append(transicion)
        self.automata=self.genereAutomata(self._estados,self._transiciones)
    
    def getTransicionesDe(self,estado):
        """[permite tener las transiciones de un estado]

        Args:
            estado ([Estado]): [el estado que se queire tener la transiciones]
        """
        lista=[]
        for x in self._transiciones:
            if x.getTransicion()[0]==estado.getNombre() or estado.getNombre()==x.getTransicion()[1]:
                lista.append(x)
        return lista

    def eliminarEstado(self,estado):
        """[permite eliminar un estado de un automata]

        Args:
            estado ([Estado]): [el estado que se quiere eliminar]
        """
        self._estados.remove(estado)
        self.automata=self.genereAutomata(self._estados,self._transiciones)

    def getEstadoX(self,x):
        """[permite obtener un estado X(cualquiera que este en el automata)]

        Args:
            x (string): [el nombre del estado buscado]

        Returns:
            [type]: [description]
        """
        for e in self._estados:
            if e.getNombre()==x:
                return e

    def __restar__(self,listaUno,listaDos):
        """[permite restar los valores de dos listas]

        Args:
            listaUno ([list]): [la lista principal de la que se va a restar]
            listaDos ([list]): [la  lista secundaria que contiene los valores a restar]

        Returns:
            [list]: [la lista sin los valores restados]
        """
        for x in listaDos:
            if x in listaUno:
                listaUno.remove(x)
        return listaUno

    def __getEstados__(self):
        """[permite obtener todos los nombres de los estados]

        Returns:
            [list]: [la lista con los nombres de todos los estados]
        """
        lista=[]
        for x in self.getEstados():
            lista.append(x.getNombre())
        return lista

    def comparaListas(self,l1,l2):
        if len(l1)>len(l2) or len(l2)<len(l1):
            return False
        else:
          return l1==l2
          