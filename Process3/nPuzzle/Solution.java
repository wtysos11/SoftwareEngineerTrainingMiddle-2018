package solution;

import jigsaw.Jigsaw;
import jigsaw.JigsawNode;

import java.util.Map;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;

/**
 * 在此类中填充算法，完成重拼图游戏（N-数码问题）
 */
public class Solution extends Jigsaw {

	/**
	 * 拼图构造函数
	 */
	public Solution() {
	}

	/**
	 * 拼图构造函数
	 * @param bNode - 初始状态节点
	 * @param eNode - 目标状态节点
	 */
	public Solution(JigsawNode bNode, JigsawNode eNode) {
		super(bNode, eNode);
	}

	/*
	 * test_function, output all the element in the node_list
	 * */
	private void printNode(int[] node_list)
	{
		System.out.print("{");
		for(int i=0;i<node_list.length;i++)
		{
			System.out.print(node_list[i]+" ");
		}
		System.out.print("}\n");
	}
	
	/*
	 * 根据给定的int数组，返回一个int型。
	 * 利用进制计算哈希，保证不重复。即origin.length进制，对于每一个数，乘上origin.length后进行相加
	 * */
	public int calculateHashCode(int[] origin)
	{
		int base = 1;
		int sum = 0;
		for(int i=0;i<origin.length;i++)
		{
			sum+=origin[i]*base;
			base*=origin.length;
		}
		return sum;
		
	}
	
	/**
	 *（实验一）广度优先搜索算法，求指定5*5拼图（24-数码问题）的最优解
     * 填充此函数，可在Solution类中添加其他函数，属性
	 * @param bNode - 初始状态节点
     * @param eNode - 目标状态节点
	 * @return 搜索成功时为true,失败为false
	 */
	public boolean BFSearch(JigsawNode bNode, JigsawNode eNode) {
		HashSet<Integer> visited = new HashSet<Integer>();
		Queue<JigsawNode> queue = new LinkedList<JigsawNode>();
		queue.offer(bNode);
		while(!queue.isEmpty())
		{
			JigsawNode head = queue.poll();
			int[] head_node = head.getNodesState();
			int hashsetCode = calculateHashCode(head_node);
			//printNode(head_node);
			if(!visited.add(hashsetCode))//检查，如果已经访问过这个节点，则进入下一个节点。不然，就顺便压入
			{
				continue;
			}
			//判断是否完成
			if(head.equals(eNode))
			{
				//System.out.println("found it!");
				return true;
			}
			
			//广度优先搜索，扩展
			int[] moveable = head.canMove();
			for(int i=0;i<4;i++)
			{
				if(moveable[i]==1)
				{
					JigsawNode movingNode = new JigsawNode(head);
					movingNode.move(i);
					queue.offer(movingNode);
				}
			}
		}
		
		return false;
	}


	/**
	 *（Demo+实验二）计算并修改状态节点jNode的代价估计值:f(n)
	 * 如 f(n) = s(n). s(n)代表后续节点不正确的数码个数
     * 此函数会改变该节点的estimatedValue属性值
     * 修改此函数，可在Solution类中添加其他函数，属性
     * 
     * 代价为全体节点远离应有位置的曼哈顿距离（为方便计算）
	 * @param jNode - 要计算代价估计值的节点
	 */
	public void estimateValue(JigsawNode jNode) {
		int sum=0;
		/*
		 * 代价计算：全体节点远离应有位置的总距离
		 * 
		 * */
		int[] node_list = jNode.getNodesState();
		int n=jNode.getDimension();
		for(int i=1;i<node_list.length;i++)
		{
			int now_col = (i-1)%n;
			int now_row = (i-1)/n;
			int element = node_list[i];
			int aim_col = (element-1)%n;
			int aim_row = (element-1)/n;
			sum += Math.abs(now_col-aim_col)+Math.abs(now_row-aim_row);
		}
		jNode.setEstimatedValue(sum);
	}
}
