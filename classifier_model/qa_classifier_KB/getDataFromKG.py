cur = '经'

from py2neo import Graph,Node,NodeMatcher,RelationshipMatcher

graph = Graph(
            host="10.15.82.65",
            port=7687,
            user="neo4j",
            )
node_matcher = NodeMatcher(graph)

nodes = node_matcher.match(cur)

with open(cur+'.txt','a',encoding='utf-8')  as f:
    for node in nodes:
        print(node['name'])
        f.write(node['name'].split('@')[0]+'\n')