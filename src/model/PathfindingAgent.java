package model;

import java.awt.*;
import java.util.List;

public class PathfindingAgent extends GlobalAgent {
    private List<GlobalAgent> allAgents;
    private double speed = Math.max(1.0, 10.0 / Math.log(size + 2));

    public PathfindingAgent(double posX, double posY, double size, List<GlobalAgent> allAgents) {
        super(posX, posY, size);
        this.color = Color.MAGENTA;
        this.allAgents = allAgents;
    }

    @Override
    public void move() {
        // algo pathfinding
    }
}