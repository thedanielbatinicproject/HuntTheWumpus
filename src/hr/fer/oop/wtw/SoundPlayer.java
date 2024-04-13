package hr.fer.oop.wtw;

import java.io.File;
import java.net.URL;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.ArrayList;

public class SoundPlayer {
    private ArrayList<Clip> playingClips = new ArrayList<>();
    private String srcFolderPath;

    public SoundPlayer() {
        // Initialize src folder path
        srcFolderPath = getSrcFolderPath();
    }

    public void playSoundInBackground(String fileName) {
        new Thread(() -> {
            try {
                // Construct the file path
                String filePath = srcFolderPath + File.separator + fileName;
                File soundFile = new File(filePath);

                // Check if the file exists
                if (!soundFile.exists()) {
                    System.err.println("Sound file not found: " + filePath);
                    return;
                }

                // Open the sound file
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(soundFile));

                // Stop the last clip if it's playing
                stopLastClip();

                // Add the current clip to the list of playing clips
                playingClips.add(clip);

                // Play the sound
                clip.start();

                // Sleep while the sound plays
                Thread.sleep(clip.getMicrosecondLength() / 1000);

                // Close the clip
                clip.close();

                // Remove the clip from the list of playing clips
                playingClips.remove(clip);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void stopLastClip() {
        if (!playingClips.isEmpty()) {
            Clip lastClip = playingClips.get(playingClips.size() - 1);
            if (lastClip != null && lastClip.isRunning()) {
                lastClip.stop();
                lastClip.close();
            }
        }
    }

    private String getSrcFolderPath() {
        try {
            // Get the current working directory
            String userDir = System.getProperty("user.dir");

            // Construct the src folder path
            String srcFolderPath = userDir + "\\audio";

            // Decode the path (in case of special characters)
            return new File(srcFolderPath).getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
