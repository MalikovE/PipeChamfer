package ru.ultrakraft.parsing;

import ru.ultrakraft.chamfer.Scan;
import ru.ultrakraft.math.Point;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class implements parsing of txt files
 * to get scanner data
 *
 * @author Malikov E.
 * @since 1.0
 */

public class TxtParser implements IParser {
    private List<Scan> result;

    @Override
    public List<Scan> getResult() {
        return result;
    }

    @Override
    public void parse(String file) {
        try (FileInputStream in = new FileInputStream(file)) {
            Scanner scanner = new Scanner(in);
            result = new ArrayList<>();
            List<Point> points = new ArrayList<>();
            while (scanner.hasNext()) {
                double x = Double.parseDouble(scanner.next());
                double y = Double.parseDouble(scanner.next());
                points.add(new Point(x, y));
            }
            result.add(new Scan(points));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
