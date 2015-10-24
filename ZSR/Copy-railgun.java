package rail;

/**
 * Created by zhengsuren on 15/10/23.
 */
//以无向图G为入口，得出任意两点之间的路径长度length[i][j]，路径path[i][j][k]，
//途中无连接得点距离用0表示，点自身也用0表示
public class FLOYD {
    int[][] length = null;// 任意两点之间路径长度
    int[][][] path = null;// 任意两点之间的路径
    int[][] passby2 = null;    //记录经过的车站数
    int[][] numRoute = null;               //最佳路径记录顺序


    public FLOYD(int[][] G, int depart, int arrive) {
        int MAX = 10000;                //设置一个不可能到达的最大权值
        int row = G.length;             // 图G的行数
        int[][][] spot = new int[row][row][10];// 定义任意两点之间经过的点
      //  int[][][] spot = null;
        int[] onePath = new int[row];// 记录一条路径
        length = new int[row][row];
        path = new int[row][row][];
        passby2 = new int[row][row];            //记录经过车站数的数组
        numRoute = new int[row][row];           //记录路线数的数组


        for (int i = 0; i < row; i++)// 处理图两点之间的路径
            for (int j = 0; j < row; j++) {
                if (G[i][j] == 0)G[i][j] = MAX;// 没有路径的两个点之间的路径为默认最大
                if (i == j)G[i][j] = 0;// 本身的路径长度为0
            }
        for (int i = 0; i < row; i++)              // 初始化为任意两点之间没有路径
            for (int j = 0; j < row; j++)
                for (int k = 0;k < 10; k++)
                spot[i][j][k] = -1;
        for (int i = 0; i < row; i++)           // 假设任意两点之间的没有路径
            onePath[i] = -1;
        for (int v = 0; v < row; ++v)           //初始化权值矩阵，途经站，路线数
            for (int w = 0; w < row; ++w) {
                passby2[v][w] = 2;
                numRoute[v][w] = 0;
                length[v][w] = G[v][w];
            }
        for (int u = 0; u < row; ++u) {
            for (int v = 0; v < row; ++v) {
               for (int w = 0; w < row; ++w) {

                    //if (passby2[v][w] > 3)break;                      //如果中转站数大于6则不继续进行该算法
                    if (length[v][w] > length[v][u] + length[u][w])
                    {
                        passby2[v][w]++;                          //经过站数加1
                        numRoute[v][w]++;
                        length[v][w] = length[v][u] + length[u][w];         // 如果存在更短路径则取更短路径
                        spot[v][w][numRoute[v][w]] = u;                          // 把经过的点加入
                    } else if (v == w) {
                        passby2[v][w] = 1;                        //只停留在本站，经过站数记录为1
                    }
               }
            }
        }
      /*  for (int i = 0; i < row; i++) {// 求出所有的路径
            int[] point = new int[1];
            for (int j = 0; j < row; j++) {
                point[0] = 0;
                onePath[point[0]++] = i;
                outputPath(spot, i, j, numRoute, onePath, point);
                path[i][j] = new int[point[0]];
                for (int s = 0; s < point[0]; s++)
                    path[i][j][s] = onePath[s];
            }
        }*/
      //  for (int i = 0; i < row; i++) {
            int[] point = new int[1];
        //    for (int j = 0; j < row; j++) {
                point[0] = 0;
                onePath[point[0]++] = depart;                                       //始发站
                outputPath(spot, depart, arrive, 1, onePath, point);                     //路线输出
                path[depart][arrive] = new int[point[0]];
                for (int s = 0; s < point[0]; s++)
                    path[depart][arrive][s] = onePath[s];
         //   }
        for (;numRoute[depart][arrive]>=0;numRoute[depart][arrive]--)
        {
            System.out.println("spot["+depart+"]["+arrive+"]["+numRoute[depart][arrive]+"]= "
                    +spot[depart][arrive][numRoute[depart][arrive]]);
        }
    }

    void outputPath(int[][][] spot, int i, int j, int k, int[] onePath, int[] point)
    {                                           // 输出i// 到j// 的路径的实际代码，point[]记录一条路径的长度
        if (i == j)return;
        if (spot[i][j][k] == -1)
            onePath[point[0]++] = j;
        else {
            outputPath(spot, i, spot[i][j][k], k, onePath, point);
            outputPath(spot, spot[i][j][k], j, k, onePath, point);
        }
    }

    void outputPath2(int[][][] spot, int i, int j, int k, int[] onePath, int[] point)
    {                                           // 输出i// 到j// 的路径的实际代码，point[]记录一条路径的长度
        if (i == j)return;
        if (spot[i][j][k] == -1)
            onePath[point[0]++] = j;
        else {
            outputPath2(spot, i, spot[i][j][k], k, onePath, point);
            outputPath2(spot, spot[i][j][k], j, k, onePath, point);
        }
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
                {0,2,8,12,3,4},
                {2,0,18,5,7,20},
                {8,18,0,0,30,0},
                {12,5,0,0,0,40},
                {3,7,30,0,0,1},
                {4,20,0,40,1,0}
        };

        int depart = 2, arrive = 3;

        for (int i = 0; i < data.length; i++)
            for (int j = i; j < data.length; j++)
            {
                if (data[i][j] != data[j][i])return;                  //权值矩阵规范性检查
            }

        FLOYD test=new FLOYD(data,depart,arrive);

      /* for (int i = 0; i < data.length; i++)
            for (int j = i; j < data[i].length; j++) {
                System.out.println();
                System.out.print("From " + i + " to " + j + " path is: ");
                for (int k = 0; k < test.path[i][j].length; k++)
                    System.out.print(test.path[i][j][k] + " ");
                System.out.println();
                System.out.println("From " + i + " to " + j + " length :"+ test.length[i][j]);
                System.out.println("There passby "+ test.passby2[i][j] + " stations");
                System.out.println("This is the "+ test.numRoute + " route for choice");
            }*/
        System.out.print("From " + depart + " to " + arrive + " path is: ");
        for (int k = 0; k < test.path[depart][arrive].length; k++)
            System.out.print(test.path[depart][arrive][k] + " ");
        System.out.println();
        test.numRoute[depart][arrive] = test.numRoute[depart][arrive] + 1;
        System.out.println("From " + depart + " to " + arrive + " length :"+ test.length[depart][arrive]);
        System.out.println("There passby "+ test.passby2[depart][arrive] + " stations");
        System.out.println("This is the "+ test.numRoute[depart][arrive] + " route for choice");
    }
}