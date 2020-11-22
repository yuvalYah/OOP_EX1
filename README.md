Exercise 1 OPP
-----------------
in this Exercise i did graph with weight.

i use in ArrayList for save the nodes in the graph.

Node:

the interface of node info contain: get key , get info , get tag , set info , set tag.

in getInfo we save info(=string) with keys of his neighbors .

Node_I implements node info and more 4 methods, contains :
* addNi - to add neighbors to the node
* hasNi - to see if there is neighbor with the given key
* removeNode- remove node from the neighbors list.
* getNi-return the collection of neighbors
* equals - how help us in tests.

also : NodeInfo 

NodeInfo is Similar to Node_I , has the same functions and also contain ArrayList of the weight.

each node we add to the neighbors list in Node_I we add to NodeInfo in his place (for example :we has node1 with key 1, and wants to add neighbor node2 with key 3 , so node2 Will be in place 3 on the neighbors list of node1 (in NodInfo and Node_I ) and be in place 3 on the weight list on NodeInfo).


WGraph_DS:

WGraph_DS implements weighted_graph , contain:
* getNode - return the node with the given key.
* hasEdge - if there an edge between 2 nodes.
* getEdge - return the weight between 2 nodes.
* addNode - add node to the graph.
* connect - connect edge between 2 nodes.
* getV - return the collection of the nodes in the graph
* get v of the neighbors. 
* removeNode.
* removeEdge
* nodeSize, edgeSize , MC
* equals - help us in the tests

WGraph_Algo:

WGraph_Algo implements weighted_graph_algorithem , contain:
* init.
* copy.
* is connecting.
* shortestPath.
* shortestPathDist
* save&load
* equals









 