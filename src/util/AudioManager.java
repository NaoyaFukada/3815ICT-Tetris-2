package util;

public class AudioManager {
    private static MP3Player backgroundMusicPlayer;
    public static SoundEffectManager soundEffectManager;

    static {
        // Initialize backgroundMusicPlayer
        backgroundMusicPlayer = new MP3Player("/resources/audios/background.mp3");
        Thread musicThread = new Thread(backgroundMusicPlayer);
        musicThread.start();

        // Initialize soundEffectManager with the sound files
        String[] soundFiles = new String[] {
                "/resources/audios/erase-line.wav",
                "/resources/audios/game-finish.wav",
                "/resources/audios/level-up.wav",
                "/resources/audios/move-turn.wav"
        };
        soundEffectManager = new SoundEffectManager(soundFiles);
    }

    public static void playBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.play();
        }
    }

    public static void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
    }

    public static void playSoundEffect(String soundName) {
        if (soundEffectManager != null) {
            soundEffectManager.playSound(soundName);
        } else {
            System.err.println("SoundEffectManager is not initialized.");
        }
    }

    public static void close() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
        }
        if (soundEffectManager != null) {
            soundEffectManager.close();
        }
    }
}