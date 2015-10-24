package rail;

/**
 * Created by zhengsuren on 15/10/23.
 */

import java.util.Vector;

//以无向图G为入口，得出任意两点之间的路径长度length[i][j]，路径Vrailway，
//途中无连接得点距离用0表示，点自身也用0表示
public class FLOYD {
    int[][] length = null;// 任意两点之间路径长度
    int passby2 = 0;    //记录经过的车站数
    int[][] numRoute = null;               //最佳路径记录顺序
    Vector VRailway = null;

    public FLOYD(int[][] G, int depart, int arrive) {
        int MAX = 10000;                //设置一个不可能到达的最大权值
        int row = G.length;             // 图G的行数
        int[][][] spot = new int[row][row][10];// 定义任意两点之间经过的点

        length = new int[row][row];
        numRoute = new int[row][row];           //记录路线数的数组
        VRailway = new Vector(10);

        for (int i = 0; i < row; i++)// 处理图两点之间的路径
            for (int j = 0; j < row; j++) {
                if (G[i][j] == 0)G[i][j] = MAX;// 没有路径的两个点之间的路径为默认最大
                if (i == j)G[i][j] = 0;// 本身的路径长度为0
            }
        for (int i = 0; i < row; i++)              // 初始化为任意两点之间没有路径
            for (int j = 0; j < row; j++)
                for (int k = 0;k < 10; k++)
                spot[i][j][k] = -1;

        for (int v = 0; v < row; ++v)           //初始化权值矩阵，路线数
            for (int w = 0; w < row; ++w) {
                numRoute[v][w] = 0;
                length[v][w] = G[v][w];
            }
        for (int u = 0; u < row; ++u) {
            for (int v = 0; v < row; ++v){
                for (int w = 0; w < row; ++w)
                    {
                        if (length[v][w] > length[v][u] + length[u][w]) {
                            numRoute[v][w]++;
                            length[v][w] = length[v][u] + length[u][w];         // 如果存在更短路径则取更短路径
                            spot[v][w][numRoute[v][w]] = u;                          // 把经过的点加入
                        }
                    }
                }
        }

        VRailway.add(depart);                                            //将路线中的点加入

        for (int x = 1; x <= numRoute[depart][arrive]; x++)
        {
             VRailway.add(spot[depart][arrive][x]);
        }

        VRailway.add(arrive);

        passby2 = VRailway.size();


        }

    public static void main(String[] args) {
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
              */
                {0,2,8,12,3,0},
                {2,0,18,5,7,20},
                {8,18,0,0,30,0},
                {12,5,0,0,0,40},
                {3,7,30,0,0,1},
                {0,20,0,40,1,0}
        };

        int depart = 2, arrive = 0;

        for (int i = 0; i < data.length; i++)
            for (int j = i; j < data.length; j++)
            {
                if (data[i][j] != data[j][i])return;                  //权值矩阵规范性检查
            }

        FLOYD test=new FLOYD(data,depart,arrive);

        System.out.print("From " + depart + " to " + arrive + " path is: ");

        for (int k = 0; k < test.VRailway.size(); k++)
            System.out.print(test.VRailway.get(k) + " ");
        System.out.println();
        System.out.println("From " + depart + " to " + arrive + " length :"+ test.length[depart][arrive]);
        System.out.println("There passby "+ test.passby2 + " stations");
        test.VRailway.removeAllElements();
    }
}