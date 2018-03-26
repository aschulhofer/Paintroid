package org.catrobat.catroid.paintroid.ui;

/**
 *
 */

public class DrawingSurfacePerspectiveEventHandler implements PerspectiveEventHandler {

    private DrawingSurface drawingSurface;
    private Perspective perspective;

    public DrawingSurfacePerspectiveEventHandler(DrawingSurface drawingSurface, Perspective perspective) {
        this.drawingSurface = drawingSurface;
        this.perspective = perspective;
    }

    @Override
    public void resetScaleAndTranslation() {
        resetScaleAndTranslation(drawingSurface.getBitmapWidth(), drawingSurface.getBitmapHeight());
    }

    @Override
    public void resetScaleAndTranslation(float width, float height) {
        perspective.resetScaleAndTranslation(width, height);
    }
}
