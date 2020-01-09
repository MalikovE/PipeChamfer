package ru.ultrakraft.visual;

import ru.ultrakraft.chamfer.Scan;
import ru.ultrakraft.math.Point;

import java.awt.*;
import javax.swing.*;

import java.util.List;

public class Animation extends JPanel implements Runnable {

    private int current;
    private List<Scan> scans;

    public Animation(List<Scan> scans) {
        this.scans = scans;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        List<Point> points = scans.get(current).getData();
        int[] x = new int[points.size()];
        int[] y = new int[points.size()];
        double scaling = 3;
        for (int i = 0; i < points.size(); i++) {
            x[i] = (int)points.get(i).x() + 100;
            x[i] *= scaling;
            y[i] = (int)points.get(i).y() - 100;
            y[i] *= scaling;
        }
        g.drawPolyline(x, y, x.length);
    }

    @Override
    public void run() {
        while (true) {
            current = (current + 1) % scans.size();
            repaint();
            try { Thread.sleep(15); } catch (InterruptedException ex) {}
        }
    }

}