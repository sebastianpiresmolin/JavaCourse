import java.util.ArrayList;
import java.util.List;

public class ObstacleHandler {
    private List<Obstacle> obstacles;

    public ObstacleHandler() {
        this.obstacles = new ArrayList<>();
    }

    public void addObstacles(Obstacle[] obstaclesArray) {
        for (Obstacle obstacle : obstaclesArray) {
            obstacles.add(obstacle);
        }
    }

    public Obstacle getObstacleAt(int x, int y) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getX() == x && obstacle.getY() == y) {
                return obstacle;
            }
        }
        return null;
    }

    public void removeObstacle(Obstacle obstacle) {
        obstacles.remove(obstacle);
    }
    
    public boolean isPassable(int x, int y) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getX() == x && obstacle.getY() == y && !obstacle.isPassable()) {
                return false;
            }
        }
        return true;
    }
}