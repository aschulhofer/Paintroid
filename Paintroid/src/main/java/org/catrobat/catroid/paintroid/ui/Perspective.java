/**
 *  Paintroid: An image manipulation application for Android.
 *  Copyright (C) 2010-2015 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.paintroid.ui;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.VisibleForTesting;
import android.view.SurfaceHolder;

import java.io.Serializable;

public class Perspective implements Serializable {
	public static final float MIN_SCALE = 0.1f;
	public static final float MAX_SCALE = 100f;
	public static final float SCROLL_BORDER = 50f;

	private static final long serialVersionUID = 7742690846128292452L;
	private static final float BORDER_ZOOM_FACTOR = 0.95f;

	private final float actionbarHeight;

	private float surfaceWidth;
	private float surfaceHeight;
	private float surfaceCenterX;
	private float surfaceCenterY;

	@VisibleForTesting
	public float surfaceScale;

	private float surfaceTranslationX;
	private float surfaceTranslationY;
	private float bitmapWidth;
	private float bitmapHeight;
	private boolean isFullscreen;

	private float initialTranslationX;
	@VisibleForTesting
	public float initialTranslationY;

	public Perspective(SurfaceHolder holder, float actionbarHeight) {
		setSurfaceHolder(holder);
		this.actionbarHeight = actionbarHeight;
		surfaceScale = 1f;
		isFullscreen = false;
	}

	public synchronized void setSurfaceHolder(SurfaceHolder holder) {
		Rect surfaceFrame = holder.getSurfaceFrame();
		surfaceWidth = surfaceFrame.right;
		surfaceCenterX = surfaceFrame.exactCenterX();
		surfaceHeight = surfaceFrame.bottom;
		surfaceCenterY = surfaceFrame.exactCenterY();
	}

	public synchronized void resetScaleAndTranslation(float bitmapWidth, float bitmapHeight) {

		this.bitmapWidth = bitmapWidth;
		this.bitmapHeight = bitmapHeight;

		surfaceScale = 1f;

		if (surfaceWidth == 0 || surfaceHeight == 0) {
			surfaceTranslationX = 0f;
			surfaceTranslationY = -actionbarHeight;
		} else {
			surfaceTranslationX = surfaceWidth / 2 - bitmapWidth / 2;
			initialTranslationX = surfaceTranslationX;

			surfaceTranslationY = (surfaceHeight / 2 - bitmapHeight / 2);
			initialTranslationY = surfaceTranslationY;
		}

		float zoomFactor = (isFullscreen) ? 1.0f : BORDER_ZOOM_FACTOR;
		surfaceScale = getScaleForCenterBitmap() * zoomFactor;
	}

	public synchronized void multiplyScale(float factor) {
		setScale(surfaceScale * factor);
	}

	public synchronized void translate(float dx, float dy) {
		surfaceTranslationX += dx / surfaceScale;
		surfaceTranslationY += dy / surfaceScale;

		float xmax = (bitmapWidth / 2)
				+ (((surfaceWidth / 2) - SCROLL_BORDER) / surfaceScale);
		if (surfaceTranslationX > (xmax + initialTranslationX)) {
			surfaceTranslationX = xmax + initialTranslationX;
		} else if (surfaceTranslationX < (-xmax + initialTranslationX)) {
			surfaceTranslationX = -xmax + initialTranslationX;
		}

		float ymax = (bitmapHeight / 2)
				+ (((surfaceHeight / 2) - SCROLL_BORDER) / surfaceScale);
		if (surfaceTranslationY > (ymax + initialTranslationY)) {
			surfaceTranslationY = (ymax + initialTranslationY);
		} else if (surfaceTranslationY < (-ymax + initialTranslationY)) {
			surfaceTranslationY = -ymax + initialTranslationY;
		}
	}

	public synchronized PointF getCanvasPointFromSurfacePoint(PointF surfacePoint) {
		float canvasX = (surfacePoint.x - surfaceCenterX) / surfaceScale
				+ surfaceCenterX - surfaceTranslationX;
		float canvasY = (surfacePoint.y - surfaceCenterY) / surfaceScale
				+ surfaceCenterY - surfaceTranslationY;

		return new PointF(canvasX, canvasY);
	}

	public synchronized void convertToCanvasFromSurface(PointF surfacePoint) {
		float canvasX = (surfacePoint.x - surfaceCenterX) / surfaceScale
				+ surfaceCenterX - surfaceTranslationX;
		float canvasY = (surfacePoint.y - surfaceCenterY) / surfaceScale
				+ surfaceCenterY - surfaceTranslationY;
		surfacePoint.set(canvasX, canvasY);
	}

	public synchronized PointF getSurfacePointFromCanvasPoint(PointF canvasPoint) {

		float surfaceX = (canvasPoint.x + surfaceTranslationX - surfaceCenterX)
				* surfaceScale + surfaceCenterX;
		float surfaceY = (canvasPoint.y + surfaceTranslationY - surfaceCenterY)
				* surfaceScale + surfaceCenterY;

		return new PointF(surfaceX, surfaceY);
	}

	public synchronized void applyToCanvas(Canvas canvas) {
		canvas.scale(surfaceScale, surfaceScale, surfaceCenterX, surfaceCenterY);
		canvas.translate(surfaceTranslationX, surfaceTranslationY);
	}

	public float getScale() {
		return surfaceScale;
	}

	public synchronized void setScale(float scale) {
		surfaceScale = Math.max(MIN_SCALE, Math.min(MAX_SCALE, scale));
	}

	public float getScaleForCenterBitmap() {

		float ratioDependentScale;
		float screenSizeRatio = surfaceWidth / surfaceHeight;
		float bitmapSizeRatio = bitmapWidth / bitmapHeight;

		if (screenSizeRatio > bitmapSizeRatio) {
			ratioDependentScale = surfaceHeight / bitmapHeight;
		} else {
			ratioDependentScale = surfaceWidth / bitmapWidth;
		}

		if (ratioDependentScale > 1f) {
			ratioDependentScale = 1f;
		}
		if (ratioDependentScale < MIN_SCALE) {
			ratioDependentScale = MIN_SCALE;
		}

		return ratioDependentScale;
	}

	public boolean getFullscreen() {
		return isFullscreen;
	}

	public void setFullscreen(boolean isFullscreen) {
		this.isFullscreen = isFullscreen;
	}

	public float getSurfaceTranslationX() {
		return surfaceTranslationX;
	}

	public void setSurfaceTranslationX(float translationX) {
		surfaceTranslationX = translationX;
	}

	public float getSurfaceTranslationY() {
		return surfaceTranslationY;
	}

	public void setSurfaceTranslationY(float translationY) {
		surfaceTranslationY = translationY;
	}

	public float getSurfaceWidth() {
		return surfaceWidth;
	}

	public float getSurfaceHeight() {
		return surfaceHeight;
	}

	public float getSurfaceCenterX() {
		return surfaceCenterX;
	}

	public float getSurfaceCenterY() {
		return surfaceCenterY;
	}
}