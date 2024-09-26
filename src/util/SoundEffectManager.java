package util;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SoundEffectManager {
    private Map<String, Clip> soundEffects = new HashMap<>();

    public SoundEffectManager(String[] soundFiles) {
        loadSoundEffects(soundFiles);
    }

    private void loadSoundEffects(String[] soundFiles) {
        for (String filePath : soundFiles) {
            try {
                InputStream audioSrc = getClass().getResourceAsStream(filePath);
                if (audioSrc == null) {
                    System.err.println("Could not find sound file: " + filePath);
                    continue;
                }
                InputStream bufferedIn = new BufferedInputStream(audioSrc);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                soundEffects.put(getFileNameFromPath(filePath), clip);
            } catch (Exception e) {
                System.err.println("Error loading sound file: " + filePath);
                e.printStackTrace();
            }
        }
    }

    private String getFileNameFromPath(String path) {
        int idx = path.lastIndexOf("/");
        String fileName = path.substring(idx + 1);
        int dotIdx = fileName.lastIndexOf(".");
        if (dotIdx > 0) {
            fileName = fileName.substring(0, dotIdx);
        }
        return fileName;
    }

    public void playSound(String soundName) {
        Clip clip = soundEffects.get(soundName);
        if (clip != null) {
            // Play the clip in a new thread to avoid blocking
            new Thread(() -> {
                try {
                    if (clip.isRunning()) {
                        clip.stop();
                    }
                    clip.setFramePosition(0); // Rewind to the beginning
                    clip.start();
                } catch (Exception e) {
                    System.err.println("Error playing sound: " + soundName);
                    e.printStackTrace();
                }
            }).start();
        } else {
            System.err.println("Sound effect not found: " + soundName);
        }
    }

    public void close() {
        for (Clip clip : soundEffects.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.close();
        }
        soundEffects.clear();
    }
}