package ru.ultrakraft.parsing;

import ru.ultrakraft.chamfer.Scan;
import ru.ultrakraft.math.Point;

import java.util.List;
import java.util.ArrayList;
import java.io.RandomAccessFile;
import java.io.FileWriter;
import java.io.EOFException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


/**
 * This class implements parsing of drec files
 * to get scanner data
 *
 * @author Malikov E.
 * @since 1.0
 */

public class DrecParser implements IParser {
    private final int POINTS_THRESHOLD = 400;

    private List<Scan> result;

    private short reverseBytesOrderShort(short number) {
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putShort(number);
        bb.order(ByteOrder.BIG_ENDIAN);
        return bb.getShort(0);
    }

    private int reverseBytesOrderInt(int number) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putInt(number);
        bb.order(ByteOrder.BIG_ENDIAN);
        return bb.getInt(0);
    }

    private double reverseBytesOrderDouble(double number) {
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putDouble(number);
        bb.order(ByteOrder.BIG_ENDIAN);
        return bb.getDouble(0);
    }

    @Override
    public List<Scan> getResult() {
        return result;
    }

    @Override
    public void parse(final String file) {
        System.out.println("Parsing of " + file + " is started...");

        result = new ArrayList<>();

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {

            for(int i = 0; i < 34; i++)
                System.out.print((char)raf.read());

            System.out.println("\nVersion: " + reverseBytesOrderShort(raf.readShort()));
            System.out.println("DataTime: " + raf.readLong());
            System.out.println("SCCount: " + reverseBytesOrderInt(raf.readInt()));
            System.out.println("Objects: " + raf.read());

            System.out.print("PLCDataSize: ");
            int plcDataSize = reverseBytesOrderInt(raf.readInt());
            System.out.println(plcDataSize);

            System.out.print("ScannerDataSize: ");
            int scannerDataSize = reverseBytesOrderInt(raf.readInt());
            System.out.println(scannerDataSize);

            System.out.print("CommentDataSize: ");
            int commentDataSize = reverseBytesOrderInt(raf.readInt());
            System.out.println(commentDataSize);
            raf.seek(raf.getFilePointer() + commentDataSize);

            System.out.print("SettingsDataSize: ");
            int settingsDataSize = reverseBytesOrderInt(raf.readInt());
            System.out.println(settingsDataSize);
            raf.seek(raf.getFilePointer() + settingsDataSize);

            int countBreak = 0;
            while (true) {
                try {
                    for(int i = 0; i < 16; i++) {
                        int count = raf.read();

                        for (int j = 0; j < count; j++) {
                            short index = reverseBytesOrderShort(raf.readShort());
                            short realLen = reverseBytesOrderShort(raf.readShort());

                            List<Double> xCoords = new ArrayList<>();
                            List<Double> yCoords = new ArrayList<>();
                            List<Point> scannerData = new ArrayList<>();

                            for (int k = 0; k < realLen; k++) {
                                double coordX = reverseBytesOrderDouble(raf.readDouble());
                                if (i == 0 && realLen > POINTS_THRESHOLD) {
                                    xCoords.add(coordX);
                                    countBreak++;
                                }
                            }
                            for (int k = 0; k < realLen; k++) {
                                double coordY = reverseBytesOrderDouble(raf.readDouble());
                                if (i == 0 && realLen > POINTS_THRESHOLD) yCoords.add(coordY);
                            }

                            if (realLen > POINTS_THRESHOLD) {
                                for (int ind = 0; ind < xCoords.size(); ind++) {
                                    scannerData.add(new Point(xCoords.get(ind), yCoords.get(ind)));
                                }
                            }

                            if (scannerData.size() != 0) {
                                result.add(new Scan(scannerData));
                            }
                        }
                    }
                    if (countBreak > 80000) break;
                    int plcDataCount = raf.read();
                    for (int j = 0; j < plcDataCount; j++)
                        raf.seek(raf.getFilePointer() + plcDataSize);

                } catch (EOFException e) {
                    System.out.println("End of file!");
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveToTxt(final String file) {
        try (FileWriter writer = new FileWriter(file, false)) {
            String text = "";
            for (int i = 0; i < result.size(); i++) {
                List<Point> points = result.get(i).getData();
                for (int j = 0; j < points.size(); j++)
                    text += points.get(i).x() + " "
                            + points.get(i).y() + "\r\n";
            }
            writer.write(text);
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
