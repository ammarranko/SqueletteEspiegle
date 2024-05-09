package ca.qc.bdeb.inf203.SqueletteEspiegle;

import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;


public class Magie extends GameObject {

    public double getRayon() {
        return rayon;
    }

    private final double rayon = 35;

    private double radiant;

    private double forceEnX;
    private double forceEnY;


    ArrayList<Particule> particules = new ArrayList<>();
    ArrayList<Particule> particulesInterieur = new ArrayList<>();


    @Override
    public void draw(GraphicsContext context) {


        for (Particule particule : particulesInterieur) {
            particule.draw(context);
        }

    }


    public Magie(Squelette squelette) {
        color = Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        initializerMagie(squelette);
        initializerParticules();

        ay = 0;
    }

    public void initializerMagie(Squelette squelette) {
        x = squelette.x;
        y = squelette.y - 40;
        vy = -400;
    }

    private void initializerParticules() {

        for (int i = 0; i < 100; i++) {
            radiant = radiant - Math.PI / 50;
            particules.add(new Particule(
                    calculePosParticuleX(radiant),
                    calculePosParticuleY(radiant),
                    this, Color.BLACK
            ));
        }
        for (int i = 0; i < 15; i++) {
            particulesInterieur.add(new Particule(
                    calculePosParticuleX(randomDouble(0, Math.PI)),
                    calculePosParticuleY(randomDouble((-Math.PI / 2), (-4 * Math.PI / 3))),
                    this, color
            ));
        }
    }

    @Override
    public void update(double deltaTemps) {


        for (var particule : particulesInterieur) {
            forceEnY = 0;
            forceEnX = 0;
            calculerForceParticules(particules, particulesInterieur);
            calculerForceParticuleInterieur(particulesInterieur);

            if (Math.pow(particule.x, 2) + Math.pow(particule.y, 2) > Math.pow(rayon, 2)) {
                particule.x = 0;
                particule.y = 0;
            }

            particule.ax = forceEnX;
            particule.ay = forceEnY;


            particule.vx += particule.ax * deltaTemps;
            particule.vy += particule.ay * deltaTemps;


            if (particule.vx > 50) {
                particule.vx = 50;
            } else if (particule.vx < -50) {
                particule.vx = -50;
            }
            if (particule.vy > 50) {
                particule.vy = 50;
            } else if (particule.vy < -50) {
                particule.vy = -50;
            }

            particule.y += particule.vy * deltaTemps;
            particule.x += particule.vx * deltaTemps;

        }
        updatePhysique(deltaTemps);

    }


    public double calculePosParticuleX(double radiant) {
        return rayon * Math.cos(radiant);
    }

    public double calculePosParticuleY(double radiant) {
        return rayon * Math.sin(radiant);
    }

    public static double randomDouble(double min, double max) {
        return (new Random().nextDouble() * (max - min)) + min;
    }

    /**
     * Cette methode
     *
     * @param particules ertyuy
     */
    private void calculerForceParticuleInterieur(ArrayList<Particule> particules) {
        for (int i = 0; i < particules.size(); i++) {
            for (int j = i + 1; j < particules.size(); j++) {

                calculerForce(particules, i, j);
            }
        }
    }

    /**
     * Cette méthode prend en compte le...
     *
     * @param particules           dfgff
     * @param particulesInterieurs fgfhjd
     */
    private void calculerForceParticules(ArrayList<Particule> particules, ArrayList<Particule> particulesInterieurs) {
        for (int i = 0; i < particulesInterieurs.size(); i++) {
            for (int j = 0; j < particules.size(); j++) {

                calculerForce(particules, i, j);

            }
        }
    }

    private void calculerForce(ArrayList<Particule> particules, int i, int j) {
        double distance = Math.sqrt(
                Math.pow((particules.get(i).x - particules.get(j).x), 2)
                        + Math.pow((particules.get(i).y - particules.get(j).y), 2));
        if (distance < 0.01)
            distance = 0.01;

        double deltaX = particules.get(i).x - particules.get(j).x;
        double deltaY = particules.get(i).y - particules.get(j).y;

        double proportionX = deltaX / distance;
        double proportionY = deltaY / distance;

        int q = 10;
        int k = 30;
        double forceElectrique = (k * Math.pow(q, 2)) / Math.pow(distance, 2);
        forceEnX += forceElectrique * proportionX;
        forceEnY += forceElectrique * proportionY;
    }

    public boolean validerSortiBouleMagique() {
        return y + h < -40;
    }

}
