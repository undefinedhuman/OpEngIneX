package de.undefinedhuman.editor;

import de.undefinedhuman.core.utils.Maths;
import de.undefinedhuman.core.world.generation.noise.Noise;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class NoiseGUI extends JFrame {

    int oct = 3;
    float ampl = 1f, rough = 0.1f, thresh = 0.1f;
    private JLabel drawingLabel;

    private BufferedImage img;

    public NoiseGUI() {

        setTitle("Noise GUI");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        drawingLabel = new JLabel();
        drawingLabel.setSize(800, 800);
        add(drawingLabel);
        setResizable(false);

        JLabel label2 = new JLabel();
        label2.setBounds(800, 0, 200, 800);

        JLabel thresholdLabel = new JLabel("Threshold");
        thresholdLabel.setBounds(920, 25, 100, 25);
        JTextField threshold = new JTextField(String.valueOf(thresh));
        threshold.setBounds(810, 25, 100, 25);
        label2.add(threshold);
        label2.add(thresholdLabel);

        JLabel octavesLabel = new JLabel("Octaves");
        octavesLabel.setBounds(920, 60, 100, 25);
        label2.add(octavesLabel);
        JTextField octaves = new JTextField(String.valueOf(oct));
        octaves.setBounds(810, 60, 100, 25);
        label2.add(octaves);

        JLabel amplitudeLabel = new JLabel("Amplitude");
        amplitudeLabel.setBounds(920, 95, 100, 25);
        label2.add(amplitudeLabel);
        JTextField amplitude = new JTextField(String.valueOf(ampl));
        amplitude.setBounds(810, 95, 100, 25);
        label2.add(amplitude);

        JLabel roughnessLabel = new JLabel("Roughness");
        roughnessLabel.setBounds(920, 130, 100, 25);
        label2.add(roughnessLabel);
        JTextField roughness = new JTextField(String.valueOf(rough));
        roughness.setBounds(810, 130, 100, 25);
        label2.add(roughness);

        JLabel seedLabel = new JLabel("Seed");
        seedLabel.setBounds(920, 165, 100, 25);
        label2.add(seedLabel);
        JTextField seed = new JTextField("3455467");
        seed.setBounds(810, 165, 100, 25);
        label2.add(seed);

        JButton generate = new JButton("Generate");
        generate.setBounds(810, 235, 100, 25);
        generate.addActionListener(e -> setNoise(
                oct = Integer.parseInt(octaves.getText()),
                ampl = Float.parseFloat(amplitude.getText()),
                rough = Float.parseFloat(roughness.getText()),
                thresh = Float.parseFloat(threshold.getText()),
                Integer.parseInt(seed.getText())));
        label2.add(generate);

        JButton random = new JButton("Random");
        random.setBounds(810, 305, 100, 25);
        random.addActionListener(e -> setNoise(
                Maths.clamp(new Random().nextInt(12), 5, 10),
                Maths.clamp(new Random().nextFloat() * 2f, 0.1f, 1.8f),
                Maths.clamp(new Random().nextFloat() * 1.5f, 0.2f, 1.5f),
                Maths.clamp(new Random().nextFloat(), 0.25f, 0.75f),
                new Random().nextInt(1000000000)));
        label2.add(random);

        JButton saveFile = new JButton("Save");
        saveFile.setBounds(810, 270, 100, 25);
        saveFile.addActionListener(e -> saveBackground());
        label2.add(saveFile);

        add(label2);

        setNoise(oct, ampl, rough, thresh, new Random().nextInt(1000000));
        setVisible(true);

    }

    private void setNoise(int octaves, float amplitude, float roughness, float threshold, int seed) {
        int width = 640, height = 640;
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Noise noise = new Noise(octaves, amplitude, roughness);
        noise.setSeed(seed);

        for (int i = 0; i < height/2; i++) {

            for (int j = 0; j < width/2; j++) {

                int y = i*2, x = j*2;
                float k = 0;
                if(threshold < 0) k = Maths.clamp(noise.calculateFractalNoise(x, y), 0, 1);
                else k = noise.select(threshold, (1f - noise.calculateFractalNoise(x, y))) ? 1 : 0;
                int a = 255, r = (int) (k * 255f), g = (int) (k * 255f), b = (int) (k * 255f);
                int p = (a << 24) | (r << 16) | (g << 8) | b;
                img.setRGB(x, y, p);
                img.setRGB(x, y+1, p);
                img.setRGB(x+1, y, p);
                img.setRGB(x+1, y+1, p);
            }

        }
        drawingLabel.setIcon(new ImageIcon(img));

    }

    public static void main(String[] args) {
        new NoiseGUI();
    }

    private void saveBackground() {
        try {
            File file = new File("image (Octaves: " + oct + ", Amplitude: " + ampl + ", Roughness: " + rough + ", Threshold: " + thresh + ").png");
            ImageIO.write(img, "png", file);
        } catch(IOException e) {
            System.out.println("Error: " + e);
        }
    }

}
