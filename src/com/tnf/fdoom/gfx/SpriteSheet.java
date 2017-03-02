package com.tnf.fdoom.gfx;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class SpriteSheet implements Serializable {
	public static int width;
	public int height;
	public static int spriteSize;
	public int[] pixels;

	public SpriteSheet(BufferedImage image) {
		width = image.getWidth();
		height = image.getHeight();
		spriteSize = 8;
		pixels = image.getRGB(0, 0, width, height, null, 0, width);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = (pixels[i] & 0xff) / 64;
		}
	}
}
