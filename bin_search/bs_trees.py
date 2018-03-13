# Tree object, just specify root and leaves


class Tree(object):
    
    def __init__(self, root, left=None, right=None):
        assert root and type(root) == Node
        self.left = left
        self.root = root
        self.right = right
        
    def is_leaf(self):
        return not(self.left or self.right)
          
    def __str__(self):
        base_str = self.root.__str__() 
        left_str = '_'
        right_str = '_'
        if self.is_leaf():
            return('[' + base_str + ']')
        if self.left:
            left_str = self.left.__str__()
        if self.right:
            right_str = self.right.__str__()
        return('[' + left_str + ' ' + base_str + ' ' + right_str + ']')      
    
    def __eq__(self, other):
        left_bool = False
        right_bool = False
        if self.root.__eq__(other.root):
            if not (self.left or other.left):
                left_bool = True
            if not (self.right or other.right):
                right_bool = True
            if self.right and other.right:
                right_bool = self.right.__eq__(other.right)
            if self.left and other.left:
                left_bool = self.left.__eq__(other.left)
            return(left_bool and right_bool)
        else:
            return(False)
    
    def __ne__(self, other):
        if self.__eq__(other):
            return(False)
        else:
            return(True)
            

class Node(object):
    
    def __init__(self, value, weight=1):
        self.value = value
        self.weight = weight
    
    def __str__(self):
        return str(self.value)   
    
    def __lt__(self, other):
        return self.value < other.value
    
    def __gt__(self, other):
        return self.value > other.value
    
    def __eq__(self, other):
        return self.value == other.value 

    def __ne__(self, other):
        return self.value != other.value 


# trees to test
tree1 = Tree(Node('B'), Tree(Node('A')), None)
tree2 = Tree(Node('B'))
tree3 = Tree(Node('B'), Tree(Node('A')), Tree(Node('C')))
tree4 = Tree(Node('B'), Tree(Node('A')), Tree(Node('C')))
tree5 = Tree(Node('D'), tree1, tree4)


print(tree1.__str__())
print(tree2.__str__())
print(tree3.__str__())
print(tree4.__str__())
print(tree5.__str__())

print(tree1.__eq__(tree2))
print(tree3.__eq__(tree3))
print(tree4.__ne__(tree4))
print(tree4.__ne__(tree5))

