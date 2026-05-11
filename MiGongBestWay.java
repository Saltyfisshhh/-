import java.util.*;

public class MiGongBestWay {
    
    // 定义方向：下、右、上、左
    private static final int[][] DIRECTIONS = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    
    public static void main(String[] args) {
        // 创建迷宫（0可走，1障碍）
        int[][] maze = {
            {1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 0, 1},
            {1, 1, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1}
        };
        
        // 起点和终点
        Point start = new Point(1, 1);
        Point end = new Point(6, 5);
        
        System.out.println("迷宫地图：");
        printMaze(maze);
        
        // 用BFS找最短路径
        List<Point> shortestPath = findShortestPathBFS(maze, start, end);
        
        if (shortestPath != null) {
            System.out.println("\n找到最短路径！长度：" + (shortestPath.size() - 1) + "步");
            System.out.println("路径坐标：");
            for (Point p : shortestPath) {
                System.out.println("  (" + p.x + ", " + p.y + ")");
            }
            
            // 在地图上标记路径
            markPath(maze, shortestPath);
            System.out.println("\n标记路径后的地图（2表示路径）：");
            printMaze(maze);
        } else {
            System.out.println("\n没有找到路径！");
        }
    }
    
    // BFS寻找最短路径
    public static List<Point> findShortestPathBFS(int[][] maze, Point start, Point end) {
        int rows = maze.length;
        int cols = maze[0].length;
        
        // 验证起点终点
        if (maze[start.x][start.y] == 1 || maze[end.x][end.y] == 1) {
            return null;
        }
        
        // 队列用于BFS
        Queue<Point> queue = new LinkedList<>();
        queue.add(start);
        
        // 记录每个点的前驱节点（用于重构路径）
        Point[][] prev = new Point[rows][cols];
        prev[start.x][start.y] = null;  // 起点没有前驱
        
        // 记录是否访问过
        boolean[][] visited = new boolean[rows][cols];
        visited[start.x][start.y] = true;
        
        // BFS主循环
        while (!queue.isEmpty()) {
            Point current = queue.poll();
            
            // 到达终点
            if (current.x == end.x && current.y == end.y) {
                return reconstructPath(prev, end);
            }
            
            // 探索四个方向
            for (int[] dir : DIRECTIONS) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];
                
                // 检查边界和可行性
                if (isValid(maze, newX, newY, rows, cols) && !visited[newX][newY]) {
                    queue.add(new Point(newX, newY));
                    visited[newX][newY] = true;
                    prev[newX][newY] = current;  // 记录前驱
                }
            }
        }
        
        return null;  // 没有找到路径
    }
    
    // 检查位置是否有效
    private static boolean isValid(int[][] maze, int x, int y, int rows, int cols) {
        return x >= 0 && x < rows && y >= 0 && y < cols && maze[x][y] == 0;
    }
    
    // 从终点重构路径
    private static List<Point> reconstructPath(Point[][] prev, Point end) {
        List<Point> path = new ArrayList<>();
        Point current = end;
        
        // 从终点回溯到起点
        while (current != null) {
            path.add(0, current);  // 加到开头，保持顺序
            current = prev[current.x][current.y];
        }
        
        return path;
    }
    
    // 在地图上标记路径
    private static void markPath(int[][] maze, List<Point> path) {
        for (Point p : path) {
            if (p.x != 0 && p.x != maze.length-1 && p.y != 0 && p.y != maze[0].length-1) {
                maze[p.x][p.y] = 2;  // 用2表示路径
            }
        }
    }
    
    // 打印迷宫
    private static void printMaze(int[][] maze) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 0) {
                    System.out.print("  ");  // 可走
                } else if (maze[i][j] == 1) {
                    System.out.print("██");  // 障碍
                } else if (maze[i][j] == 2) {
                    System.out.print("◇ ");  // 路径
                }
            }
            System.out.println();
        }
    }
    
    // 坐标点类
    static class Point {
        int x, y;
        
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}