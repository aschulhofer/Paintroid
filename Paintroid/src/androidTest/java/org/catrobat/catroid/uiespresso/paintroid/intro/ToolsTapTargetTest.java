/**
 * Paintroid: An image manipulation application for Android.
 * Copyright (C) 2010-2015 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.uiespresso.paintroid.intro;

import org.catrobat.catroid.paintroid.R;
import org.catrobat.catroid.uiespresso.paintroid.intro.util.WelcomeActivityIntentsTestRule;
import org.catrobat.catroid.uiespresso.paintroid.util.IntroUtils;
import org.catrobat.catroid.common.paintroid.SystemAnimationsRule;
import org.catrobat.catroid.paintroid.tools.ToolType;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static org.catrobat.catroid.uiespresso.paintroid.util.EspressoUtils.changeIntroPage;
import static org.catrobat.catroid.uiespresso.paintroid.util.IntroUtils.getPageIndexFromLayout;
import static org.catrobat.catroid.uiespresso.paintroid.util.IntroUtils.introClickToolAndCheckView;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ToolsTapTargetTest {

	@Rule
	public WelcomeActivityIntentsTestRule activityRule = new WelcomeActivityIntentsTestRule();

	@Rule
	public SystemAnimationsRule systemAnimationsRule = new SystemAnimationsRule();
	@Parameter
	public ToolType toolType;

	@Parameters(name = "{0}")
	public static Iterable<ToolType> data() {
		return Arrays.asList(
				ToolType.BRUSH,
				ToolType.SHAPE,
				ToolType.TRANSFORM,
				ToolType.LINE,
				ToolType.CURSOR,
				ToolType.FILL,
				ToolType.PIPETTE,
				ToolType.STAMP,
				ToolType.IMPORTPNG,
				ToolType.ERASER,
				ToolType.TEXT);
	}

	@Before
	public void setUp() {
		changeIntroPage(getPageIndexFromLayout(activityRule.getLayouts(), R.layout.islide_tools));
	}

	@Test
	public void testTool() {
		introClickToolAndCheckView(toolType, IntroUtils.IntroSlide.Tools);
	}
}