package com.tnf.fdoom.gfx;

import java.io.Serializable;

public class Screen implements Serializable {
	/*
	 * public static final int MAP_WIDTH = 64; // Must be 2^x public static final int MAP_WIDTH_MASK = MAP_WIDTH - 1;
	 *
	 * public int[] tiles = new int[MAP_WIDTH * MAP_WIDTH]; public int[] colors = new int[MAP_WIDTH * MAP_WIDTH]; public int[] databits = new int[MAP_WIDTH * MAP_WIDTH];
	 */
	public int xOffset;
	public int yOffset;

	public static final int BIT_MIRROR_X = 0x01;
	public static final int BIT_MIRROR_Y = 0x02;

	public final int w, h;
	public int[] pixels;

	private SpriteSheet sheet;

	public Screen(int w, int h, SpriteSheet sheet) {
		this.sheet = sheet;
		this.w = w;
		this.h = h;

		pixels = new int[w * h];
	}

	public void clear(int color) {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = color;
	}

	public void render(int xp, int yp, int tile, int colors, int bits) {
		xp -= xOffset;
		yp -= yOffset;
		boolean mirrorX = (bits & BIT_MIRROR_X) > 0;
		boolean mirrorY = (bits & BIT_MIRROR_Y) > 0;

		int xTile = tile % 32;
		int yTile = tile / 32;
		int toffs = xTile * 8 + yTile * 8 * sheet.width;

		for (int y = 0; y < 8; y++) {
			int ys = y;
			if (mirrorY) ys = 7 - y;
			if (y + yp < 0 || y + yp >= h) continue;
			for (int x = 0; x < 8; x++) {
				if (x + xp < 0 || x + xp >= w) continue;

				int xs = x;
				if (mirrorX) xs = 7 - x;
				int col = (colors >> (sheet.pixels[xs + ys * sheet.width + toffs] * 8)) & 255;
				if (col < 255) pixels[(x + xp) + (y + yp) * w] = col;
			}
		}
	}

	/**
	 * Renders a square "point" that is size * size large.
	 *
	 * @param xp
	 * @param yp
	 * @param size
	 * @param col
	 */
	public void renderPoint(int xp, int yp, int size, int col) {
		xp -= xOffset;
		yp -= yOffset;

		for (int y = 0; y < size; y++) {
			if (y + yp < 0 || y + yp >= h) continue;
			for (int x = 0; x < size; x++) {
				if (x + xp < 0 || x + xp >= w) continue;

				if (col < 255) pixels[(x + xp) + (y + yp) * w] = col;
			}
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	/**
	 * Gets pixel color in the given position.
	 * If the position is not valid, 0 is returned.
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public int getPixel(int x, int y)
	{
		if (x < 0 || y < 0 || x >= w || y >= h) {
			return 0;
		}
		return pixels[x + y * w];
	}

	private int[] dither = new int[] { 0, 8, 2, 10, 12, 4, 14, 6, 3, 11, 1, 9, 15, 7, 13, 5, };

	public void overlay(Screen screen2, int xa, int ya) {
		int[] oPixels = screen2.pixels;
		int i = 0;
		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				if (oPixels[i] / 10 <= dither[((x + xa) & 3) + ((y + ya) & 3) * 4]) pixels[i] = 0;
				i++;
			}

		}
	}
	private void renderSpriteSimple(final int xp, final int yp, final int xSprite, final int ySprite, final int colors, final int spriteWidth, final int spriteHeight) {
			final int toffs = xSprite * SpriteSheet.spriteSize + ySprite * SpriteSheet.spriteSize * SpriteSheet.width;
			for (int y = 0; y < spriteHeight; ++y) {
					if (y + yp >= 0) {
							if (y + yp < this.h) {
									for (int x = 0; x < spriteWidth; ++x) {
											if (x + xp >= 0) {
													if (x + xp < this.w) {
															final int col = colors >> this.sheet.pixels[x + y * SpriteSheet.width + toffs] * 8 & 0xFF;
															if (col < 255) {
																	this.pixels[x + xp + (y + yp) * this.w] = col;
															}
													}
											}
									}
							}
					}
			}
	}
	public void renderSprite(int xp, int yp, final int xSprite, final int ySprite, final int colors, final int bits, final int spriteWidth, final int spriteHeight) {
			xp -= this.xOffset;
			yp -= this.yOffset;
			if (yp + spriteHeight < 0 || yp - spriteHeight >= this.h) {
					return;
			}
			if (xp + spriteWidth < 0 || xp - spriteWidth >= this.w) {
					return;
			}
			if (bits == 0) {
					this.renderSpriteSimple(xp, yp, xSprite, ySprite, colors, spriteWidth, spriteHeight);
					return;
			}
			boolean mirrorX = (bits & 0x1) > 0;
			boolean mirrorY = (bits & 0x2) > 0;
			boolean rotate90 = (bits & 0x10) > 0;
			boolean rotate91 = (bits & 0x4) > 0;
			if (rotate91) {
					rotate91 = false;
					mirrorY = (mirrorX = true);
			}
			boolean rotate92 = (bits & 0x8) > 0;
			if (rotate92) {
					rotate92 = false;
					mirrorX = (rotate90 = (mirrorY = true));
			}
			final int toffs = xSprite * SpriteSheet.spriteSize + ySprite * SpriteSheet.spriteSize * SpriteSheet.width;
			for (int y = 0; y < spriteHeight; ++y) {
					int ys = y;
					if (mirrorY) {
							ys = spriteHeight - 1 - y;
					}
					if (y + yp >= 0) {
							if (y + yp < this.h) {
									for (int x = 0; x < spriteWidth; ++x) {
											if (x + xp >= 0) {
													if (x + xp < this.w) {
															int xs = x;
															if (mirrorX) {
																	xs = spriteWidth - 1 - x;
															}
															int xz = xs;
															int yz = ys;
															if (rotate90) {
																	yz = xs;
																	xz = spriteHeight - 1 - ys;
															}
															final int col = colors >> this.sheet.pixels[xz + yz * SpriteSheet.width + toffs] * 8 & 0xFF;
															if (col < 255) {
																	this.pixels[x + xp + (y + yp) * this.w] = col;
															}
													}
											}
									}
							}
					}
			}
	}
	public void copyRect(Screen screen2, int x2, int y2, int w2, int h2) {
		int[] oPixels = screen2.pixels;
		for (int y = 0; y < h2; y++) {
			for (int x = 0; x < w2; x++) {
				oPixels[(x+x2) + (y+y2)*screen2.w] = pixels[x + y*this.w];
			}
		}
	}

	public void renderLight(int x, int y, int r) {
		x -= xOffset;
		y -= yOffset;
		int x0 = x - r;
		int x1 = x + r;
		int y0 = y - r;
		int y1 = y + r;

		if (x0 < 0) x0 = 0;
		if (y0 < 0) y0 = 0;
		if (x1 > w) x1 = w;
		if (y1 > h) y1 = h;
		// System.out.println(x0 + ", " + x1 + " -> " + y0 + ", " + y1);
		for (int yy = y0; yy < y1; yy++) {
			int yd = yy - y;
			yd = yd * yd;
			for (int xx = x0; xx < x1; xx++) {
				int xd = xx - x;
				int dist = xd * xd + yd;
				// System.out.println(dist);
				if (dist <= r * r) {
					int br = 255 - dist * 255 / (r * r);
					if (pixels[xx + yy * w] < br) pixels[xx + yy * w] = br;
				}
			}
		}
	}
}
