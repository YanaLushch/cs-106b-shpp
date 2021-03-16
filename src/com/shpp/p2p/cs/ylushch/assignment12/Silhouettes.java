package com.shpp.p2p.cs.ylushch.assignment12;

/*
 * File: Assignment12.java
 * ---------------------
 * This program counts the quantity of the silhouettes on the image!
 */


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class Silhouettes implements Constants {
    static int recursiveCounter;

    /**
     * Main method which runs the program
     * @param args from "Edit configurations"
     */
    public static void main(String[] args) throws Exception {
        BufferedImage image = getImage(args);
        if (hasAlphaChannel(image)){
            BufferedImage preparedImage = resizeAndBrighten(image, BufferedImage.TYPE_INT_ARGB);
            int[][] alphaPixels = getAlphaPixels(preparedImage);
            HashMap<Integer, Integer> alphaFrequency = countFrequency(alphaPixels);
            Integer maxAlphaFrequency = getMostFrequentColor(alphaFrequency);
            System.out.println("The quantity of silhouettes on the picture is " + findSilhouettes(alphaPixels, maxAlphaFrequency));
        } else {
            BufferedImage preparedImage = resizeAndBrighten(image, BufferedImage.OPAQUE);
            int[][] rgb = getRGBPixel(preparedImage);
            HashMap<Integer, Integer> rgbFrequency = countFrequency(rgb);
            Integer maxRGBFrequency = getMostFrequentColor(rgbFrequency);
            System.out.println("The quantity of silhouettes on the picture is " + findSilhouettes(rgb, maxRGBFrequency));
        }
    }

    /**
     * checks if alphaChannel exists in the image
     * @return true - if alpha channel pixels has alpha  or false if not
     */
    public static Boolean hasAlphaChannel(BufferedImage image){
        return image.getColorModel().hasAlpha();
    }

    /**
     * Method which reads the image. If no image - default image. If something is wrong - print error
     * @return image if exists.
     */
    public static BufferedImage getImage(String[] args) throws Exception {
        try {
            return (args.length > 0) ? ImageIO.read(new File(args[0])) : ImageIO.read(new File(DEFAULT_PICTURE));
        } catch (IllegalArgumentException | IOException e) {
            System.out.println("Something is wrong with the path. PLease check it and try one more time");
            throw new  Exception("Wrong path!");
        }
    }

    /**
     * Method which resizes the image and brightens it to get more clear black and white pixels;
     * @param type if we read alpha channel - TYPE_INT_ARGB, if RGB - OPAQUE - which converts image to black and white
     * @return already resized and brightened image
     */
    public static BufferedImage resizeAndBrighten(BufferedImage image, int type) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        RescaleOp brightness = new RescaleOp(BRIGHTNESS_COEF, 0, null);
        resizedImage = brightness.filter(resizedImage, resizedImage);
        return resizedImage;
    }


//    public static boolean isAlphaChannel(HashMap<Integer, Integer> alphaFrequency) {
//        return alphaFrequency.size() != 1;
//    }

    /**
     * Method which gets Alpha Channel pixels and set them into an array
     * @return array of pixels of alpha
     */
    public static int[][] getAlphaPixels(BufferedImage image) {
        int[][] alphaPixels = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color currentPixel = new Color(image.getRGB(j, i), true);
                alphaPixels[i][j] = currentPixel.getAlpha();
            }
        }
        return alphaPixels;
    }

    /**
     * Method which gets RGB pixels
     * @return array of pixels of RGB
     */
    public static int[][] getRGBPixel(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] pixelsRGB = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color currentPixel = new Color(image.getRGB(j, i), true);
                pixelsRGB[i][j] = currentPixel.getRGB();
                pixelsRGB[i][j] = (pixelsRGB[i][j] == -1) ? WHITE : BLACK;
            }
        }
        return pixelsRGB;
    }

    /**
     * Method counts the frequency of all the different colors
     * @return hash map of color and it's frequency
     */
    public static HashMap<Integer, Integer> countFrequency(int[][] coloredPixels) {
        HashMap<Integer, Integer> colorFrequency = new HashMap<>();
        for (int i = 0; i < coloredPixels.length; i++) {
            for (int j = 0; j < coloredPixels[i].length; j++) {
                int transparency = coloredPixels[i][j];
                if (colorFrequency.containsKey(transparency)) {
                    //key is color and value is its frequency
                    colorFrequency.replace(transparency, 1 + colorFrequency.get(transparency));
                } else {
                    colorFrequency.put(transparency, 1);
                }
            }
        }
        return colorFrequency;
    }

    /**
     * Method counts maxFrequency. The maxFrequency will be our background.
     * @return integer of a color which is most frequent
     */
    public static Integer getMostFrequentColor(HashMap<Integer, Integer> frequency) {
        int maxValueInMap = (Collections.max(frequency.values()));
        for (Map.Entry<Integer, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() == maxValueInMap) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Method which counts the silhouettes on the picture
     * @param coloredPixels is an array of color of each pixel
     * @param bgFrequency background frequency
     * @return the count of the Silhouettes
     */
    public static Integer findSilhouettes(int[][] coloredPixels, Integer bgFrequency) {
        int counter = 0;
        for (int i = 0; i < coloredPixels.length; i++) {
            for (int j = 0; j < coloredPixels[i].length; j++) {
                int currentPixel = coloredPixels[i][j];
                if (currentPixel == -1) continue;               //skip if it is marked already
                // if the pixel is not the background
                if (currentPixel != bgFrequency && findSilhouette(i, j, coloredPixels, bgFrequency) > MIN_OBJECT) {
                    counter += 1;
                    recursiveCounter = 0;
                }
            }
        }
        return counter;
    }

    /**
     * recursive method which visits neighbor if it is not visited and goes down to the end until recursion is over
     * @param i, j - indexes
     * @return number of how many times the recursion was running itself.
     */
    public static int findSilhouette(int i, int j, int[][] coloredPixels, Integer bgFrequency) {
        coloredPixels[i][j] = -1;                   //mark the pixel not to visit it again
        ArrayList<Point> neighborsOfCurrPixel = new ArrayList<>();
        try {                                          //check neighbors
            if (coloredPixels[i + 1][j] > bgFrequency && coloredPixels[i + 1][j] != -1) {
                neighborsOfCurrPixel.add(new Point(i + 1, j));
            }
            if (coloredPixels[i][j + 1] > bgFrequency && coloredPixels[i][j + 1] != -1) {
                neighborsOfCurrPixel.add(new Point(i, j + 1));
            }
            if (coloredPixels[i - 1][j] > bgFrequency && coloredPixels[i - 1][j] != -1) {
                neighborsOfCurrPixel.add(new Point(i - 1, j));
            }
            if (coloredPixels[i][j - 1] > bgFrequency && coloredPixels[i][j - 1] != -1) {
                neighborsOfCurrPixel.add(new Point(i, j - 1));
            }
            if (coloredPixels[i + 1][j - 1] > bgFrequency && coloredPixels[i + 1][j - 1] != -1) {
                neighborsOfCurrPixel.add(new Point(i + 1, j - 1));
            }
            if (coloredPixels[i + 1][j + 1] > bgFrequency && coloredPixels[i + 1][j + 1] != -1) {
                neighborsOfCurrPixel.add(new Point(i + 1, j + 1));
            }
            if (coloredPixels[i - 1][j - 1] > bgFrequency && coloredPixels[i - 1][j - 1] != -1) {
                neighborsOfCurrPixel.add(new Point(i - 1, j - 1));
            }
            if (coloredPixels[i - 1][j + 1] > bgFrequency && coloredPixels[i - 1][j + 1] != -1) {
                neighborsOfCurrPixel.add(new Point(i - 1, j + 1));
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        neighborsOfCurrPixel.forEach(neighbor -> {
            findSilhouette(neighbor.x, neighbor.y, coloredPixels, bgFrequency);
            recursiveCounter++;
        });
        return recursiveCounter;
    }
}
