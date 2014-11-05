package com.gandalf1209.yge2.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class Sound {

	private static String def = "";

	private Clip clip;
	private AudioInputStream ain;
	private boolean loop;

	public Sound(String url) {
		try {
			InputStream in = getClass().getResourceAsStream(def + url);
			InputStream buf = new BufferedInputStream(in);
			ain = AudioSystem.getAudioInputStream(buf);
			AudioFormat format = ain.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void play(final Sound s) {
		try {
			final Clip c = s.clip;
			c.open(s.ain);
			c.start();
			if (s.loop) {
				Thread t = new Thread("YGE-Music") {
					public void run() {
						while (true) {
							if (c.getFramePosition() == c.getFrameLength() - 1) {
								play(s);
							}
						}
					}
				};
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setLoop(boolean b) {
		loop = b;
	}

	public Clip getClip() {
		return clip;
	}

}
