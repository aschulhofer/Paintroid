/*
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

package org.catrobat.catroid.uiespresso.paintroid.tools;

import android.graphics.Color;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.catrobat.catroid.paintroid.MainActivity;
import org.catrobat.catroid.uiespresso.paintroid.util.BitmapLocationProvider;
import org.catrobat.catroid.uiespresso.paintroid.util.DrawingSurfaceLocationProvider;
import org.catrobat.catroid.common.paintroid.SystemAnimationsRule;
import org.catrobat.catroid.paintroid.tools.ToolType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.catrobat.catroid.uiespresso.paintroid.util.EspressoUtils.TRANSPARENT_COLOR_PICKER_BUTTON_POSITION;
import static org.catrobat.catroid.uiespresso.paintroid.util.EspressoUtils.selectColorPickerPresetSelectorColor;
import static org.catrobat.catroid.uiespresso.paintroid.util.UiInteractions.touchAt;
import static org.catrobat.catroid.uiespresso.paintroid.util.wrappers.DrawingSurfaceInteraction.onDrawingSurfaceView;
import static org.catrobat.catroid.uiespresso.paintroid.util.wrappers.LayerMenuViewInteraction.onLayerMenuView;
import static org.catrobat.catroid.uiespresso.paintroid.util.wrappers.ToolBarViewInteraction.onToolBarView;
import static org.catrobat.catroid.uiespresso.paintroid.util.wrappers.ToolPropertiesInteraction.onToolProperties;
import static org.catrobat.catroid.uiespresso.paintroid.util.wrappers.TopBarViewInteraction.onTopBarView;

@RunWith(AndroidJUnit4.class)
public class PipetteToolIntegrationTest {

	@Rule
	public ActivityTestRule<MainActivity> launchActivityRule = new ActivityTestRule<>(MainActivity.class);

	@Rule
	public SystemAnimationsRule systemAnimationsRule = new SystemAnimationsRule();

	@Before
	public void setUp() {
		onToolBarView()
				.performSelectTool(ToolType.BRUSH);
	}

	@Test
	public void testEmpty() {
		onToolProperties()
				.checkColor(Color.BLACK);

		onDrawingSurfaceView()
				.checkPixelColor(Color.TRANSPARENT, BitmapLocationProvider.MIDDLE);

		onToolBarView()
				.performSelectTool(ToolType.PIPETTE);

		onDrawingSurfaceView()
				.perform(touchAt(DrawingSurfaceLocationProvider.MIDDLE));

		onToolProperties()
				.checkColor(Color.TRANSPARENT);
	}

	@Test
	public void testPipetteAfterBrushOnSingleLayer() {
		onDrawingSurfaceView()
				.perform(touchAt(DrawingSurfaceLocationProvider.MIDDLE));

		onDrawingSurfaceView()
				.checkPixelColor(Color.BLACK, BitmapLocationProvider.MIDDLE);

		selectColorPickerPresetSelectorColor(TRANSPARENT_COLOR_PICKER_BUTTON_POSITION);
		onToolProperties()
				.checkColor(Color.TRANSPARENT);
		onToolBarView()
				.performSelectTool(ToolType.PIPETTE);

		onDrawingSurfaceView()
				.perform(touchAt(DrawingSurfaceLocationProvider.MIDDLE));

		onToolProperties()
				.checkColor(Color.BLACK);
	}

	@Test
	public void testPipetteAfterBrushOnMultiLayer() {
		onDrawingSurfaceView()
				.perform(touchAt(DrawingSurfaceLocationProvider.MIDDLE));

		onDrawingSurfaceView()
				.checkPixelColor(Color.BLACK, BitmapLocationProvider.MIDDLE);

		onLayerMenuView()
				.performOpen()
				.performAddLayer()
				.performClose();

		selectColorPickerPresetSelectorColor(TRANSPARENT_COLOR_PICKER_BUTTON_POSITION);
		onToolBarView()
				.performSelectTool(ToolType.PIPETTE);

		onToolProperties()
				.checkColor(Color.TRANSPARENT);

		onDrawingSurfaceView()
				.checkPixelColor(Color.TRANSPARENT, BitmapLocationProvider.MIDDLE)
				.perform(touchAt(DrawingSurfaceLocationProvider.MIDDLE));

		onToolProperties()
				.checkColor(Color.BLACK);
	}

	@Test
	public void testPipetteAfterUndo() {
		onDrawingSurfaceView()
				.perform(touchAt(DrawingSurfaceLocationProvider.MIDDLE));

		onToolBarView()
				.performSelectTool(ToolType.PIPETTE);

		onDrawingSurfaceView()
				.perform(touchAt(DrawingSurfaceLocationProvider.MIDDLE));
		onToolProperties()
				.checkColor(Color.BLACK);

		onTopBarView()
				.performUndo();
		onDrawingSurfaceView()
				.perform(touchAt(DrawingSurfaceLocationProvider.MIDDLE));
		onToolProperties()
				.checkColor(Color.TRANSPARENT);
	}

	@Test
	public void testPipetteAfterRedo() {
		onDrawingSurfaceView()
				.perform(touchAt(DrawingSurfaceLocationProvider.MIDDLE));

		onToolBarView()
				.performSelectTool(ToolType.PIPETTE);

		onDrawingSurfaceView()
				.perform(touchAt(DrawingSurfaceLocationProvider.MIDDLE));
		onToolProperties()
				.checkColor(Color.BLACK);

		onTopBarView()
				.performUndo()
				.performRedo();

		onDrawingSurfaceView()
				.perform(touchAt(DrawingSurfaceLocationProvider.MIDDLE));
		onToolProperties()
				.checkColor(Color.BLACK);
	}
}