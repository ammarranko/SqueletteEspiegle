package ca.qc.bdeb.inf203.SqueletteEspiegle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;



public class Particule extends GameObject {
    private int rayon =7;

    public int getRayon() {
        return rayon;
    }
    private Magie parent;

    public Particule(double x, double y, Magie magie,Color color) {

        this.parent = magie;
        this.x = x;
        this.y = y;
        this.ay = 0;
        this.color = color;
    }


    @Override
    public void draw(GraphicsContext context) {
        context.setFill(color);

        context.fillOval(parent.x + x, y + parent.y, rayon, rayon);
    }





}
