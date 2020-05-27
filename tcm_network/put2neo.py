from py2neo import Graph, Node, NodeMatcher, RelationshipMatcher

with open("qa_new_wait_to_neo.txt",'r',encoding='utf-8') as f:
    all = f.readlines()

with open("plant.txt",'r',encoding='utf-8') as f:
    plant_total = f.readlines()
    plant_total = [i[:-1] for i in plant_total]

class BuildGraph:
    def __init__(self):
        self.g = Graph(
                host="10.15.82.71",
                port="57687",
                user="neo4j",
                password="123")
        self.matcher = NodeMatcher(self.g)
        self.re_matcher = RelationshipMatcher(self.g)
        
        
    def create_node(self, label, nodes):
        for node_name in nodes:
            node = Node(label, name=node_name)
            self.g.create(node)
        return
    
    def store_node(self):
        drug=set()
        plant=set()
        disease=set()
        for line in all:
            id,entity1,_,entity2 = line[:-1].split("\t")
            if _ == "伴随":
                plant.add(entity1)
                plant.add(entity2)
            elif _ == "治疗":
                if entity1 in plant_total:
                    plant.add(entity1)
                else:
                    drug.add(entity1)
                disease.add(entity2)
            else:
                plant.add(entity1)
                drug.add(entity2)
        self.create_node("Plant",plant)
        self.create_node("Drug",drug)
        self.create_node("Disease",disease)
        
    def store_rela(self):
        for line in all:
            id,entity1,_,entity2 = line[:-1].split("\t")
            cql = 'Match (a{name:"%s"})-[r]->(b{name:"%s"}) return r.id' % (entity1,entity2)
            try:
                new_id = list(self.g.run(cql).next())[0]
                new_id.append(int(id))
                new_cql = 'Match (a{name:"%s"}), (b{name:"%s"}) Merge (a)-[r:%s]->(b) Set r.id=%s return r'% (entity1,entity2,_,str(new_id))
                self.g.run(new_cql)
            except:
                new_cql = 'Match (a{name:"%s"}), (b{name:"%s"}) Merge (a)-[r:%s]->(b) Set r.id=[%s] return r'% (entity1,entity2,_,str(id))
                self.g.run(new_cql)
                
handler = BuildGraph()
handler.store_node()
handler.store_rela()
