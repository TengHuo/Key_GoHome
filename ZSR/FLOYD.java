package FLOYD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

//以无向图G为入口，得出任意两点之间的路径长度length[i][j]，路径path[i][j][k]，
//途中无连接得点距离用0表示，点自身也用0表示
public class FLOYD {
    int[][] length = null;// 任意两点之间路径长度
    int[][][] path = null;// 任意两点之间的路径
    int[][] passby2 = null;    //记录经过的车站数
    
    int depart = -1, arrive = -1;
    
    boolean isNormal = true; //用于判断运算过程是否有异常
    
    String[] cn_name = {"哈尔滨","沈阳","北京","济南","上海","广州","太原","郑州","武汉","南昌",
    		"西安","兰州","乌鲁木齐","成都","南宁"};

    public FLOYD(int[][] G, String cn_depart, String cn_arrive) {
        int MAX = 999999;                //设置一个不可能到达的最大权值
        int row = G.length;             // 图G的行数
        int[][] spot = new int[row][row];// 定义任意两点之间经过的点
        int[] onePath = new int[row];// 记录一条路径
        length = new int[row][row];
        path = new int[row][row][];
        passby2 = new int[row][row];            //记录经过车站数的数组
        
        for (int cal = 0; cal < cn_name.length; cal++) { //中文，数字转化
			if (cn_name[cal].equals(cn_depart)) {
				depart = cal;
			}
			if (cn_name[cal].equals(cn_arrive)) {
				arrive = cal;
			}
		}
        
        if (depart == -1 || arrive == -1) {
        	isNormal = false;
        	System.out.println("请输入正确的出发地或目的地！");
			return;
		}

        for (int i = 0; i < row; i++)// 处理图两点之间的路径
            for (int j = 0; j < row; j++) {
                if (G[i][j] == 0)G[i][j] = MAX;// 没有路径的两个点之间的路径为默认最大
                if (i == j)G[i][j] = 0;// 本身的路径长度为0
            }
        for (int i = 0; i < row; i++){              // 初始化为任意两点之间没有路径
            for (int j = 0; j < row; j++){
                spot[i][j] = -1;
            }
        }
        for (int i = 0; i < row; i++) {          // 假设任意两点之间的没有路径
            onePath[i] = -1;
        }
        for (int v = 0; v < row; ++v) {          //初始化权值矩阵，途经站，路线数
            for (int w = 0; w < row; ++w) {
                passby2[v][w] = 2;
                length[v][w] = G[v][w];
            }
        }
        for (int u = 0; u < row; ++u) {
            for (int v = 0; v < row; ++v) {
               for (int w = 0; w < row; ++w) {
                    if (length[v][w] > length[v][u] + length[u][w])
                    {
                        length[v][w] = length[v][u] + length[u][w];    // 如果存在更短路径则取更短路径
                        spot[v][w] = u;                          // 把经过的点加入	
                    }
               }
            }
        }
      
        int[] point = new int[1];
        point[0] = 0;
        onePath[point[0]++] = depart;                                       //始发站
        outputPath(spot, depart, arrive, onePath, point);                     //路线输出
        path[depart][arrive] = new int[point[0]];
        for (int s = 0; s < point[0]; s++)
        {
             path[depart][arrive][s] = onePath[s];
        }
    }

    void outputPath(int[][] spot, int i, int j, int[] onePath, int[] point)
    {                                           // 输出i// 到j// 的路径的实际代码，point[]记录一条路径的长度
        if (i == j)return;
        if (spot[i][j] == -1)
            onePath[point[0]++] = j;
        else {
            outputPath(spot, i, spot[i][j], onePath, point);
            outputPath(spot, spot[i][j], j, onePath, point);
        }
    }
    
    public boolean getStatus() {
		return isNormal;
	}
    
    private void print_output(FLOYD data) {
    	System.out.print("从 " + cn_name[depart] + " 到 " + cn_name[arrive] + " 的换乘方案是: ");
        for (int k = 0; k < data.path[depart][arrive].length; k++)
        {
            System.out.print(cn_name[data.path[depart][arrive][k]]);
            if (k+1 < data.path[depart][arrive].length) {
            	System.out.print("->");
			}
        }
        System.out.println();
        System.out.println("从 " + cn_name[depart] + " 到 " + cn_name[arrive] + " 最少需要 "+ data.length[depart][arrive] + " 分钟");
        System.out.println("途径 "+ data.path[depart][arrive].length + " 个车站");
	}

    /*public static void main(String[] args) {
        int data[][] = {
              /*  { 0, 27, 44, 17, 11, 27, 42, 0, 0, 0, 20, 25, 21, 21, 18, 27, 0 },// x1
                { 27, 0, 31, 27, 49, 0, 0, 0, 0, 0, 0, 0, 52, 21, 41, 0, 0 },// 1
                { 44, 31, 0, 19, 0, 27, 32, 0, 0, 0, 47, 0, 0, 0, 32, 0, 0 },// 2
                { 17, 27, 19, 0, 14, 0, 0, 0, 0, 0, 30, 0, 0, 0, 31, 0, 0 },// 3
                { 11, 49, 0, 14, 0, 13, 20, 0, 0, 28, 15, 0, 0, 0, 15, 25, 30 },// 4
                { 27, 0, 27, 0, 13, 0, 9, 21, 0, 26, 26, 0, 0, 0, 28, 29, 0 },// 5
                { 42, 0, 32, 0, 20, 9, 0, 13, 0, 32, 0, 0, 0, 0, 0, 33, 0 },// 6
                { 0, 0, 0, 0, 0, 21, 13, 0, 19, 0, 0, 0, 0, 0, 0, 0, 0 },// 7
                { 0, 0, 0, 0, 0, 0, 0, 19, 0, 11, 20, 0, 0, 0, 0, 33, 21 },// 8
                { 0, 0, 0, 0, 28, 26, 32, 0, 11, 0, 10, 20, 0, 0, 29, 14, 13 },// 9
                { 20, 0, 47, 30, 15, 26, 0, 0, 20, 10, 0, 18, 0, 0, 14, 9, 20 },// 10
                { 25, 0, 0, 0, 0, 0, 0, 0, 0, 20, 18, 0, 23, 0, 0, 14, 0 },// 11
                { 21, 52, 0, 0, 0, 0, 0, 0, 0, 0, 0, 23, 0, 27, 22, 0, 0 },// 12
                { 21, 21, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 27, 0, 0, 0, 0 },// 13
                { 18, 41, 32, 31, 15, 28, 0, 0, 0, 29, 14, 0, 22, 0, 0, 11, 0 },// 14
                { 27, 0, 0, 0, 25, 29, 33, 0, 33, 14, 9, 14, 0, 0, 11, 0, 9 },// 15
                { 0, 0, 0, 0, 30, 0, 0, 0, 21, 13, 20, 0, 0, 0, 0, 9, 0 } // 16
        };

        int depart = 3, arrive = 2;

        for (int i = 0; i < data.length; i++)
            for (int j = i; j < data.length; j++)
            {
                if (data[i][j] != data[j][i])return;                  //权值矩阵规范性检查
            }

        FLOYD test=new FLOYD(data,depart,arrive);

        test.print_output(test, depart, arrive);
        
    }*/
    
    public static void main(String[] args){
    	
   
		Station mStation = new Station();
		int[][] mdata = mStation.getData();
		/*for (int i = 0; i < 15; i++) {  // 测试输出的矩阵是否正确
			for (int j = 0; j < 15; j++) {
				System.out.print( mdata[i][j] + " ");
			}
			System.out.println();
		}*/
		
		try 
		{  
            BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));  
            
            System.out.print("请输入出发地：");
            String str = strin.readLine();  
              
           // System.out.println("出发地："+str);  
              
            System.out.print("请输入目的地：");
            
            String str2 = strin.readLine();  
         //   System.out.println("目的地："+str2); 
            
            if (str != null && str2 != null) {
            	FLOYD test=new FLOYD(mdata,str.toString(),str2.toString());

                if (test.isNormal) {
                	test.print_output(test);
        		}
			}
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
}