
class Analizador():
    
   
    def __init__(self,alfabeto):
        """Constructor de un Analizador de expresiones(pequeño y no tan poderoso)
            para analizar una expresion solo se requiere de un alfabeto
        """
        self.__constantes=['(',')','|','+','*','?','@','.']
        self.__operadores=['|','+','*','?','.']
        self.__precedencia={'+':3,'?':3,'*':3,'.':2,'|':1}
        self._expresion=None
        self._estados=[]
        self._transiciones=[]
        self._alfabeto=alfabeto
        self._alfabeto.extend(self.__constantes)
    
    

    def setExpresion(self,expresion):
        """setter de expresion 

        Args:
            expresion ([string]): [expresion que tiene el analizador ]
        """
        self._expresion=expresion


    def getExpresion(self):
        """[getter de la expresion para saber cual es]

        Returns:
            [string]: [la expresion del analizador]
        """
        return self._expresion

    def verificar(self,expresion):
        """[verifica si la expresion es valida en el alfabeto definido]

        Args:
            expresion ([string]): [la expresion que se desea evaluar ]

        Returns:
            [boolean]: [True si es válida False si no lo es]
        """
        
        for x in self._estados:
            if x not in self._alfabeto:
                print(x ,"No esta")
                return False
        return True

    def analizar(self,expresion):
        if self.verificar(expresion):
           postfijo=self.obtenerPosfijo(expresion)



    def obtenerPosfijo(self,expresion):
        """[metodo que convierte la expresion en una lista de postfijo es util para convetir un ER a AFND]

        Args:
            expresion ([type]): [la expresion que se quiere trasnformar a PostFijo]

        Returns:
            [type]: [una lista con los valores del PostFijo]
        """
        salida=[] #esta será una lista que tendra el recorrido en postfijo
        temporal=[] #esta lista se comportara como pila para el funcionamiento
        self._estados=self.eliminaespacios(expresion)
        for x in self._estados:
            if x not in self.__constantes:
                salida.append(x)
            if x in self.__operadores:
                if  temporal:
                    if self.__Evaluaprecedencia(x)>=self.__Evaluaprecedencia(temporal[len(temporal)-1]):
                        temporal.append(x)
                    else:   
                        salida.append(temporal.pop(len(temporal)-1))
                        temporal.append(x)
                else:
                    temporal.append(x)    
            if x==')':
                if temporal:
                    
                    temporal.reverse()
                    
                    salida.extend(temporal)
                    temporal=[]         
        if temporal:
            salida.extend(temporal)
        return salida


    def PostFijoToAFND(self,lista):
        """[permite pasar una lista de expresiones a un AFND]

        Args:
            lista ([list]): [la lista con la expresion en PostFijo]
        """
        pass


    def __Evaluaprecedencia(self,x):
        """[evlua un caracter para saber el valor de su precedencia (esta es predefinida)]

        Args:
            x ([caracter(char)]): [el caracter que se desea evaluar]

        Returns:
            [int]: [el valor predefinido de su precedencia]
        """
        return self.__precedencia[x]


    def eliminaespacios(self,expresion):
        """[permite eliminar espacio de una expresion]

        Args:
            expresion ([string]): [una expresion regular]

        Returns:
            [string]: [una expresion regular sin espacios]
        """
        salida=''
        for x in expresion:
            if x != ' ':
                salida+=x
        return salida