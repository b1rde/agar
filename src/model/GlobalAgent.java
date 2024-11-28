package model;

import java.awt.*;

public abstract class GlobalAgent {
    // __CAMILLE__
    protected double posX;
    protected double posY;
    protected double size;
    protected double targetX;
    protected double targetY;
    protected Color color;

    public GlobalAgent(double posX, double posY, double size) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
    }

    public void setPosition(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getSize() {
        return size;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public double distanceWith(GlobalAgent otherAgent) {
        if (otherAgent == null) {
            throw new IllegalArgumentException("distanceWith() : otherAgent est null");
        }
        double deltaX = this.getPosX() - otherAgent.getPosX();
        double deltaY = this.getPosY() - otherAgent.getPosY();

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public boolean collidesWith(GlobalAgent otherAgent) {
        if (size > otherAgent.getSize()) {
            double distance = distanceWith(otherAgent);
            double radiusDifference = size - otherAgent.getSize();

            return distance * 2 <= radiusDifference;
        }
        else return false;
    }

    public abstract void move();
}
