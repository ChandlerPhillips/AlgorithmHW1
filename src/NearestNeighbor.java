//Chandler Phillips
//Algorithms Project 3
//2-14-18

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class NearestNeighbor extends JPanel {

    public static String filename1 = "/Users/johnchandlerphillips/Desktop/input.txt"; //inputFile
    public static String filename2 = "/Users/johnchandlerphillips/Desktop/output.txt"; //outputFile
    public static List<Point> pointList = new ArrayList<Point>();
    public static List<Point> newPointList = new ArrayList<Point>();
    public static List<String> stringList = new ArrayList<>();
    public static List<String> sortedList = new ArrayList<>();
    public static String ln;
    public static int count = 0;

    public static void readFileContents() throws IOException //reads contents from file.
    {
        BufferedReader br = new BufferedReader(new FileReader(filename1));
        while ((ln = br.readLine()) != null) {
            count++;     //ignores first line in file (first line is typically how many points there are).
            if (count == 1) //my code knows how many points there are so the first line is
            {            //pointless and can cause errors.
                continue;
            }
            String[] value = ln.split(" ");
            int xConvert = Integer.parseInt(value[0]);
            int yConvert = Integer.parseInt(value[1]);
            pointList.add(new Point(xConvert, yConvert));
            stringList.add(new String(value[0] + "," + value[1]));
        }
    }

    public static void main(String[] args) throws IOException //main method
    {
        NearestNeighbor points = new NearestNeighbor();
        BufferedReader br = new BufferedReader(new FileReader(filename2));
        JFrame frame = new JFrame("Nearest Neighbor Points");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(points);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        try //reads input file
        {
            readFileContents();
        } catch (IOException e) {
            System.out.println("File not found.\n");
        }

        for (String s : kNearestNeighbors(stringList, stringList.get(0), pointList.size() - 1)) //nearest neighbor method
        {
            sortedList.add(s);
        }

        while ((ln = br.readLine()) != null) {
            String[] value = ln.split(",");
            int xConvert = Integer.parseInt(value[0]);
            int yConvert = Integer.parseInt(value[1]);
            newPointList.add(new Point(xConvert, yConvert));
        }

        try //writes sorted list to output file
        {
            writeFileContents();
        } catch (IOException e) {
            System.out.println("Cannot write.\n");
        }
    }

    public static void writeFileContents() throws IOException //writes new sorted points to output file
    {
        try {
            FileWriter fileWriter = new FileWriter(filename2);
            PrintWriter bufferedWriter = new PrintWriter(fileWriter);

            for (int i = 0; i < sortedList.size() - 1; i++) {
                bufferedWriter.println(sortedList.get(i));
            }
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("Error writing to file: '" + filename2 + "'");
        }
    }

    static List<String> kNearestNeighbors(List<String> point, String origin, int K) {
        if (point == null || point.size() == 0) {
            return null;
        }
        List<String> result = new ArrayList<>();
        point.add(origin);
        Collections.sort(point);
        for (String s : point) {
            if (s.equals(origin)) {
                int orgIndex = point.indexOf(origin);
                int left = orgIndex - 1;
                int right = orgIndex + 1;
                while (K > 0) {
                    if (left >= 0) {
                        result.add(point.get(left));
                        --left;
                    } else if (right < point.size()) {
                        result.add(point.get(right));
                        ++right;
                    }
                    --K;
                }
            }
        }
        return result;
    }

    public void paintComponent(Graphics g) {
        for (int i = 0; i < newPointList.size() - 1; i++) {
            g.setColor(Color.BLUE);
            g.fillOval((int) newPointList.get(i).getX(), (int) newPointList.get(i).getY(), 9, 9);
            g.fillOval((int) newPointList.get(i + 1).getX(), (int) newPointList.get(i + 1).getY(), 9, 9);
            g.setColor(Color.RED);
            g.drawLine((int) newPointList.get(i).getX(), (int) newPointList.get(i).getY(), (int) newPointList.get(i + 1).getX(), (int) pointList.get(i + 1).getY());
        }
    }
}

class Point {

    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
