package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import ex1.src.weighted_graph_algorithms;

class WGraph_Algo_Test {



	@Test
	void isConnected() {
		weighted_graph g = new WGraph_DS();
		int size=10000;
		g.addNode(0);
		for (int i = 1; i < size; i++) {
			g.addNode(i);
			g.connect(i, i-1, i);
		}
		weighted_graph_algorithms algo = new WGraph_Algo();
		algo.init(g);
		assertTrue(algo.isConnected());
		g.removeNode(size/2);
		assertFalse(algo.isConnected());


	}

	@Test
	void shortestPathDist() {
		weighted_graph g = new WGraph_DS();
		weighted_graph_algorithms algo = new WGraph_Algo();
		algo.init(g);
		int size=1000;
		g.addNode(0);
		for (int i = 1; i < size; i++) g.addNode(i);

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(i!=j) {
					g.connect(i, j, 1);
				}
			}
		}
		double path = algo.shortestPathDist(0, size-1);
		assertEquals(path ,1 );


	}


	@Test
	void shortestPath() {

		weighted_graph g = new WGraph_DS();
		weighted_graph_algorithms algo = new WGraph_Algo();
		algo.init(g);
		int size=1000;
		int keys[]= {0 , size-1};
		g.addNode(0);
		for (int i = 1; i < size; i++) {g.addNode(i) ;}// keys[i]=i;}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(i!=j) {

					g.connect(i, j, 1);
				}
			}
		}

		List<node_info> collec = algo.shortestPath(0,size-1);
		int i = 0;
		for(node_info n: collec) {
			//assertEquals(n.getTag(), checkTag[i]);
			assertEquals(n.getKey(), keys[i]);
			i++;
		}
	}

	@Test
	void save_load() {
		weighted_graph g = new WGraph_DS();
		weighted_graph_algorithms algo = new WGraph_Algo();
		algo.init(g);
		int size=10000;
		g.addNode(0);
		for (int i = 1; i < size; i++) {g.addNode(i) ;}

		weighted_graph g1=g;
		String str = "yuvaltry.txt";
		algo.save(str);
		algo.load(str);
		assertEquals(g,g1);
	}

}
