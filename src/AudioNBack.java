
import java.io.File;
import javax.sound.sampled.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author samhincks
 */
public class AudioNBack {
    private static String directory = "audio/"; 

    private static String [] files =  {directory+"rbnbacka.wav", directory+"rbnbackb.wav", directory+"rbnbackc.wav",directory+"rbnbackd.wav"};
    public AudioNBack() {
    }
    public void playNext(boolean zeroBack) {
        if (zeroBack)
            play(directory+"0back.wav");
        else
            play(directory+"1back.wav");
        int randFile = (int) (Math.random() * files.length);
        System.out.println(randFile);
        play(files[randFile]);
        
    }
    
    public  void play(String filename) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filename)));
            clip.start();
            clip.start();
            while (!clip.isRunning())
                Thread.sleep(10);
            while (clip.isRunning())
                Thread.sleep(10);
            clip.close();
        }
        catch (Exception exc)
        {
            exc.printStackTrace(System.out);
            System.err.println("err");
        }
        
    }
    
    /** args[0] = 0 or 1, for 0 versus 1 nback.
     *  args[1] = #played 
     **/
    public static void main(String[] args) {
        boolean zeroBack = true;
        if (args.length == 1) {
            int nback = Integer.parseInt(args[0]);
            if (nback == 0) zeroBack =true;
        }
        
        AudioNBack nBack = new AudioNBack();
        nBack.playNext(zeroBack);
    }
    
}
