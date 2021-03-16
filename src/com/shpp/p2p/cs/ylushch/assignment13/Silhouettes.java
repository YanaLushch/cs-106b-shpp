package com.shpp.p2p.cs.ylushch.assignment13;

/*
 * File: Assignment13.java
 * ---------------------
 * This program counts the quantity of the silhouettes on the image with Breadth-First-Search Algorithm!
 */


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class Silhouettes implements Constants {

    /**
     * Main method which runs the program
     *
     * @param args from "Edit configurations"
     */
    public static void main(String[] args) throws Exception {
        BufferedImage image = getImage(args);
        HashMap colorFrequency = colorFrequency(image);
        Color background = getBackground(colorFrequency);
        int[][] pixels = setToBlackAndWhite(image, background);
        System.out.println("There are(is) " + getSilhouettes(pixels) + " silhouettes on the image.");
    }

    /**
     * Method which reads the image. If no image - default image. If something is wrong - print error
     *
     * @return image
     */
    public static BufferedImage getImage(String[] args) {
        try {
            String imageType = args.length > 0 ? args[0] : DEFAULT_PICTURE;
            return ImageIO.read(new File(imageType));
        } catch (IllegalArgumentException | IOException e) {
            System.out.println("Something is wrong with the path. PLease check it and try one more time");
        }
        return null;
    }

    /**
     * This method gets the color frequency of half-frame of the image
     *
     * @return HashMap of each color in halved-frame and its frequency
     */
    public static HashMap colorFrequency(BufferedImage image) {
        HashMap<Color, Integer> colorFrequency = new HashMap<>();
        for (int i = 0; i < image.getWidth(); i++) {
            getOneRowFrequency(image, i, 0, colorFrequency);
        }
        for (int i = 0; i < image.getHeight(); i++) {
            getOneRowFrequency(image, 0, i, colorFrequency);
        }
        return colorFrequency;
    }

    /**
     * This method pushes color frequencies to hashmap of one row(col)
     */
    public static void getOneRowFrequency(BufferedImage image, int x, int y, HashMap<Color, Integer> colorFrequency) {
        Color color = new Color(image.getRGB(x, y), true);
        if (colorFrequency.containsKey(color)) {
            colorFrequency.replace(color, 1 + colorFrequency.get(color));
        } else {
            colorFrequency.put(color, 1);
        }
    }

    /**
     * This method determines background of image by calculating which color was used mostly in the frame of the image
     */
    public static Color getBackground(HashMap<Color, Integer> frequency) {
        int maxValueInMap = (Collections.max(frequency.values()));
        for (Map.Entry<Color, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() == maxValueInMap) return entry.getKey();
        }
        return null;
    }

    /**
     * This method sets each pixel of image to white(bg) or black(sil)
     * white will be background (+- 10%)
     */
    public static int[][] setToBlackAndWhite(BufferedImage image, Color background) {
        int[][] pixels = new int[image.getWidth()][image.getHeight()];
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                boolean inBackgroundRange = Math.abs(background.getRGB() - image.getRGB(i, j)) <= Math.abs(background.getRGB() * 0.1);
                pixels[i][j] = inBackgroundRange ? WHITE : BLACK;
            }
        }
        return pixels;
    }

    /**
     * Method which counts the silhouettes on the picture
     *
     * @param coloredPixels is an array of color of each pixel
     * @return the count of the Silhouettes
     */
    public static int getSilhouettes(int[][] coloredPixels) {
        ArrayList<Integer> silhouettesArray = new ArrayList<>();
        for (int i = 1; i < coloredPixels.length - 1; i++) {
            for (int j = 1; j < coloredPixels[0].length - 1; j++) {
                if (coloredPixels[i][j] == BLACK) {
                    int silhouette = getSilhouette(coloredPixels, i, j);
                    silhouettesArray.add(silhouette);
                }
            }
        }
        double minSilhouette = Collections.max(silhouettesArray) * 0.1;
        int counterSilhouettes = silhouettesArray.size();
        for (int i = 0; i < silhouettesArray.size(); i++) {
            if (silhouettesArray.get(i) < minSilhouette) {
                counterSilhouettes--;
            }
        }
        return counterSilhouettes;
    }

    /**
     * Method which recognizes one silhouette by the Breath-first-search algorithm.
     * It uses loop to push all neighbors which are also same type to the queue
     * When there are no more nieghbors of a silhouette - the method is over and 1 silhouette added to the count
     *
     * @param i, j - indexes
     * @return count of pixels in 1 silhouette
     */
    public static int getSilhouette(int[][] coloredPixels, int i, int j) {
        int counter = 0;
        Queue<Point> pixelsInSilhouette = new LinkedList<>();
        pixelsInSilhouette.add(new Point(i, j));
        coloredPixels[i][j] = -1;
        counter += 1;
        while (!pixelsInSilhouette.isEmpty()) {
            Point currentPixel = pixelsInSilhouette.poll();
            for (Point direction : DIRECTIONS) {
                int row = currentPixel.x + direction.x;
                int col = currentPixel.y + direction.y;
                try {
                    if (coloredPixels[row][col] != 0 && coloredPixels[row][col] != -1) {
                        pixelsInSilhouette.add(new Point(row, col));
                        coloredPixels[row][col] = -1;
                        counter += 1;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
        return counter;
    }
}




