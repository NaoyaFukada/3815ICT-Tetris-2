package util;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class MP3Player implements Runnable {
    private final String resourcePath;
    private Player player;
    private boolean isPaused;

    public MP3Player(String resourcePath) {
        this.resourcePath = resourcePath;
        this.isPaused = true;
    }

    private synchronized void initPlayer() {
        try {
            InputStream audioSrc = getClass().getResourceAsStream(resourcePath);
            if (audioSrc == null) {
                System.err.println("Could not find MP3 file: " + resourcePath);
                return;
            }
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            this.player = new Player(bufferedIn);
        } catch (JavaLayerException e) {
            System.out.println("Error: Failed to load MP3 file." + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void run() {
        while (true) {
            if (!this.isPaused) {
                try {
                    this.initPlayer();
                    if (this.player != null) {
                        this.player.play();
                    }
                } catch (JavaLayerException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                System.out.println("Error during repeat: " + e.getMessage());
            }
        }
    }

    public synchronized void stop() {
        this.isPaused = true;

        try {
            if (this.player != null) {
                this.player.close();
            }
        } catch (Exception e) {
            System.out.println("Error stopping the MP3 file: " + e.getMessage());
        }
    }

    public synchronized void play() {
        this.isPaused = false;
    }
}