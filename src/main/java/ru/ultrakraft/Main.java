package ru.ultrakraft;

import ru.ultrakraft.chamfer.Chamfer;
import ru.ultrakraft.chamfer.ChamferCalculator;
import ru.ultrakraft.chamfer.Scan;
import ru.ultrakraft.math.Point;
import ru.ultrakraft.parsing.DrecParser;
import ru.ultrakraft.parsing.IParser;
import ru.ultrakraft.visual.Animation;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private final static double ANGLE_ALLOWABLE_ERROR = 0.5;
    private final static double BLUNTING_ALLOWABLE_ERROR = 0.15;

    private final static String SAMPLE = "20191210-153635.drec";
    private final static double SAMPLE_ANGLE = 30.0;
    private final static double SAMPLE_THICKNESS = 13.0;
    private final static double SAMPLE_BLUNTING = 2.5;

    public static void main(String[] args) {
        IParser parser = new DrecParser();
        parser.parse(SAMPLE);
        List<Scan> scans = parser.getResult();

        ChamferCalculator chamferCalculator = new ChamferCalculator();
        Map<Scan, Chamfer> chamfers = new HashMap<>();
        for (Scan scan : scans) {
            chamferCalculator.calc(scan, SAMPLE_THICKNESS);
            Chamfer chamfer = chamferCalculator.getResult();
            chamfers.put(scan, chamfer);
        }

        double angleError = 0;
        double bluntingError = 0;
        double maxAngleError = 0;
        double maxBluntingError = 0;
        List<Scan> passedScans = new ArrayList<>();
        List<Scan> nonPassedScans = new ArrayList<>();
        int maxBluntingErrorIndex = 0;
        double averageAngle = 0;
        double averageBlunting = 0;
        for (Scan scan : chamfers.keySet()) {
            Chamfer chamfer = chamfers.get(scan);
            averageAngle += chamfer.getAngle();
            averageBlunting += chamfer.getBlunting();
            System.out.println(chamfer);
            angleError = Math.abs(SAMPLE_ANGLE - chamfer.getAngle());
            bluntingError = Math.abs(SAMPLE_BLUNTING - chamfer.getBlunting());
            if (angleError <= ANGLE_ALLOWABLE_ERROR && bluntingError <= BLUNTING_ALLOWABLE_ERROR) {
                System.out.println("OK!");
                passedScans.add(scan);
            } else {
                System.out.println("No allowable");
                nonPassedScans.add(scan);
            }
            if (angleError > maxAngleError) {
                maxAngleError = angleError;
            }
            if (bluntingError > maxBluntingError) {
                maxBluntingError = bluntingError;
                maxBluntingErrorIndex = nonPassedScans.indexOf(scan);
            }
        }
        int nonPassedScansNumber = nonPassedScans.size();
        int passedScansNumber = passedScans.size();
        averageAngle /=  chamfers.size();
        averageBlunting /= chamfers.size();
        System.out.println("Number of chamfers: " + chamfers.size());
        System.out.println("Good chamfers: " + passedScansNumber);
        System.out.println("Bad chamfers: " + nonPassedScansNumber);
        System.out.println("Max angle error: " + maxAngleError);
        System.out.println("Max blunting error: " + maxBluntingError);
        System.out.println("Average angle: " + averageAngle);
        System.out.println("Average blunting: " + averageBlunting);

        double averageAngleForPassed = 0;
        double averageBluntingForPassed = 0;
        for (Scan scan : passedScans) {
            chamferCalculator.calc(scan, SAMPLE_THICKNESS);
            averageAngleForPassed += chamferCalculator.getResult().getAngle();
            averageBluntingForPassed += chamferCalculator.getResult().getBlunting();
        }
        averageAngleForPassed /= passedScans.size();
        averageBluntingForPassed /= passedScans.size();
        System.out.println("Average angle for passed: " + averageAngleForPassed);
        System.out.println("Average blunting for passed: " + averageBluntingForPassed);

        double averageAngleForNonPassed = 0;
        double averageBluntingForNonPassed = 0;
        for (Scan scan : nonPassedScans) {
            chamferCalculator.calc(scan, SAMPLE_THICKNESS);
            averageAngleForNonPassed += chamferCalculator.getResult().getAngle();
            averageBluntingForNonPassed += chamferCalculator.getResult().getBlunting();
        }
        averageAngleForNonPassed /= nonPassedScans.size();
        averageBluntingForNonPassed /= nonPassedScans.size();
        System.out.println("Average angle for non passed: " + averageAngleForNonPassed);
        System.out.println("Average blunting for non passed: " + averageBluntingForNonPassed);

        Scan result = nonPassedScans.get(7);
        chamferCalculator.calc(result, SAMPLE_THICKNESS);
        System.out.println(chamferCalculator.getResult());

        try (FileWriter writer = new FileWriter("outputNonPass.txt", false)) {
            StringBuilder text = new StringBuilder();
            List<Point> points = result.getData();
            for (Point point : points)
                text.append(point.x()).append(" ").append(point.y()).append("\r\n");
            writer.write(text.toString());
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        result = passedScans.get(7);
        chamferCalculator.calc(result, SAMPLE_THICKNESS);
        System.out.println(chamferCalculator.getResult());

        try (FileWriter writer = new FileWriter("outputPass.txt", false)) {
            StringBuilder text = new StringBuilder();
            List<Point> points = result.getData();
            for (Point point : points)
                text.append(point.x()).append(" ").append(point.y()).append("\r\n");
            writer.write(text.toString());
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JFrame f = new JFrame("Chamfer");
        Animation p = new Animation(new ArrayList<>(chamfers.keySet()));
        f.add(p, BorderLayout.CENTER);
        f.setSize(600, 600);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        new Thread(p).start();
    }
}
