package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import org.junit.jupiter.api.Test;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;

class WGraph_DS_Test {



	@Test
	void nodeSize() {
		weighted_graph g = new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);

		for (int i = 4; i < 5000000; i++) {
			g.addNode(i);


		}
		int nodesize = g.nodeSize();
		int count=0;
		for (int i = 0; i < 5000000; i+=20) {
			g.removeNode(i);
			count++;
		}
		nodesize= nodesize-count;
		assertEquals(nodesize,g.nodeSize());

	}

	@Test
	void edgeSize() {
		weighted_graph g = new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);

		for (int i = 4; i < 10000; i++) {
			g.addNode(i);
			g.connect(i, i-1, (i+i+1)/(i+20));
			g.connect(i, i-2, (2*i)/(20));
			g.connect(i, i-3, (1000)/(20*i));
			g.connect(i, i-4, i);


		}
		int EdgeSize = g.edgeSize();
		assertEquals(g.edgeSize(), 39984);
		g.removeNode(0);
		EdgeSize--;
		for (int i = 20; i < 10000; i+=20) {
			g.removeNode(i);
			EdgeSize=EdgeSize-8;
		}

		assertEquals(EdgeSize,g.edgeSize());

	}

	@Test
	void getV() {
		weighted_graph g = new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		for (int i = 4; i < 10000; i++) {
			g.addNode(i);
			g.connect(i, i-1, (i+i+1)/(i+20));
			g.connect(i, i-2, (2*i)/(20));
			g.connect(i, i-3, (1000)/(20*i));
			g.connect(i, i-4, i);


		}
		Collection<node_info> v = g.getV();
		assertNotNull(v);

		java.util.Iterator<node_info> iter=  g.getV().iterator();//// max to do get v of nodeinfo
		while (iter.hasNext()) {
			node_info n = iter.next();
			assertNotNull(n);

		}
	}

	@Test
	void hasEdge() {
		int size= 10000;
		weighted_graph g = new WGraph_DS();
		g.addNode(0);
		g.addNode(1);
		g.addNode(2);
		g.addNode(3);
		for (int i = 4; i < size; i++) {
			g.addNode(i);
			g.connect(i, i-1, (i+i+1)/(i+20));
			g.connect(i, i-2, (2*i)/(20));
			g.connect(i, i-3, (size)/(20*i));
			g.connect(i, i-4, i);
		}
		for (int i = 4; i < size; i+=4) {
			assertTrue(g.hasEdge(i-4,i));	
		}
	}

	@Test
	void connect() {
		int size=10000;
		weighted_graph g = new WGraph_DS();

		for (int i = 0; i < size; i++) {
			g.addNode(i);
		}
		for (int i = 1; i < size; i++) {
			assertFalse(g.hasEdge(i, i -1));
			g.connect(i, i-1, i);
			assertTrue(g.hasEdge(i, i-1));
			double w = g.getEdge(i,i-1);
			assertEquals(w,i);
		}
	}


	@Test
	void removeNode() {
		weighted_graph g = new WGraph_DS();
		int size=10000;
		g.addNode(0);
		for (int i = 1; i < size; i++) {
			g.addNode(i);
			g.connect(i, i-1, i);
		}
		g.connect(size-1, 0, size-1);
		for (int i = 0; i < size; i+=20) {
			node_info k= g.removeNode(i);
			g.connect(i, i-1, i);
			assertNotNull(k);
			assertNull(g.getNode(i));
		}
		for (int i = 0; i < size; i+=20) {
			assertFalse(g.hasEdge(i, i+1));
		}
		assertEquals(g.nodeSize(), size - size/20);
		assertEquals(g.edgeSize(), size-size/10);

	}

	@Test
	void removeEdge() {
		weighted_graph g = new WGraph_DS();
		int size=10000;
		g.addNode(0);
		for (int i = 1; i < size; i++) {
			g.addNode(i);
			g.connect(i, i-1, i);
			assertEquals(g.getEdge(i,i-1),i);
			g.removeEdge(i, i-1);
			assertEquals(g.getEdge(i,i-1),-1);

		}

	}
	@Test
	void getEdge() {
		weighted_graph g = new WGraph_DS();
		int size=10000;
		g.addNode(0);
		for (int i = 1; i < size; i++) {
			g.addNode(i);
			g.connect(i, i-1, i);
			assertEquals(g.getEdge(i,i-1),i);


		}

	}
	@Test
	void getNode() {
		weighted_graph g = new WGraph_DS();
		int size=10000;
		g.addNode(0);
		for (int i = 1; i < size; i++) {
			g.addNode(i);
			node_info n= g.getNode(i);
			assertEquals(n.getKey(),i);
		}		
	}
}
