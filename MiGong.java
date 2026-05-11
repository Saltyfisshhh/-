
public class MiGong {

    //编写一个main方法
    public static void main(String[] args) {

        //老鼠出迷宫
        //思路：
        //1. 先创建迷宫，用二维数组表示 int[][] ...
        //2. 规定 map 数组的元素值： 0 表示可以走 1 表示障碍物

        //扩展思考：如何求出最短路径
        //（1）穷举 （2）图

        int[][] map = new int[8][7];
        
        //将地图最外围赋值1
        for(int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }

        for(int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }

        //设置障碍物
        map[3][1] = 1;
        map[3][2] = 1;
        map[2][2] = 1;

        //输出地图全貌
        System.out.println("————————查看当前地图状况————————");
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        //走迷宫(使用方法寻找)
        T t1 = new T();
        t1.findWay(map, 1, 1);

        System.out.println("=====找路情况如下=====");
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        
        //穷举法求出最短路径
        //调用四次方法，四种方法为不同策略
        //用count计数，计算共走了多少步，方法返回count值
        
    }
}

class T {

    //小鼠走(使用递归回溯思想)
    //1. findWay方法，专门找出迷宫的路径
    //2. 如果找到，就返回ture
    //3. i,j 就是老鼠位置，初始化位置（1，1）.出迷宫位置（6，5） 
    //4. 因为是递归找路，所以先规定 map数组各个值的含义
    //   0 表示可以走 1 表示障碍物 2 表示可以走 3 走过但不通
    //5. 当map[6][5] = 2 时，出迷宫
    //6. 确定老鼠找路的策略： 下->右->上->左
    public boolean findWay(int[][] map , int i , int j) {

        if(map[6][5] == 2) { //说明已经找到
            return true;
        } else {
            if(map[i][j] == 0) { //当前位置为0，表示可走
                //假设可以走通
                map[i][j] = 2;
                //使用找路策略，确定该位置是否真的可以走
                if(findWay(map, i + 1, j)) { //先走下
                    return true;
                } else if(findWay(map, i, j + 1)) { //走右边
                    return true;
                } else if(findWay(map, i - 1, j)) { //走上面
                    return true;
                } else if(findWay(map, i, j - 1)) { //走左边
                    return true;
                } else {
                    map[i][j] = 3;
                    return false;
                }


            } else { //如果不为0，map[i][j] = 1 / 2 / 3;
                return false; //说明已经测试过，就不再走
            }
        }

    }
}


