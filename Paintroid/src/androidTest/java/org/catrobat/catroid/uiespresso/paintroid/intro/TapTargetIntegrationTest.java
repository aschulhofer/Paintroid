/**
 * Paintroid: An image manipulation application for Android.
 * Copyright (C) 2010-2015 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.catroid.uiespresso.paintroid.intro;

import android.support.test.runner.AndroidJUnit4;
import android.widget.LinearLayout;

import com.getkeepsafe.taptargetview.TapTarget;

import org.catrobat.catroid.paintroid.R;
import org.catrobat.catroid.paintroid.intro.TapTargetBottomBar;
import org.catrobat.catroid.paintroid.intro.TapTargetTopBar;
import org.catrobat.catroid.uiespresso.paintroid.intro.util.WelcomeActivityIntentsTestRule;
import org.catrobat.catroid.uiespresso.paintroid.util.EspressoUtils;
import org.catrobat.catroid.uiespresso.paintroid.util.IntroUtils;
import org.catrobat.catroid.common.paintroid.SystemAnimationsRule;
import org.catrobat.catroid.paintroid.tools.ToolType;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.catrobat.catroid.uiespresso.paintroid.util.IntroUtils.getPageIndexFromLayout;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TapTargetIntegrationTest {

	@Rule
	public WelcomeActivityIntentsTestRule activityRule = new WelcomeActivityIntentsTestRule(false);

	@Rule
	public SystemAnimationsRule systemAnimationsRule = new SystemAnimationsRule();

	@Test
	public void numberTapTargetsBottomBar() {
		EspressoUtils.changeIntroPage(getPageIndexFromLayout(activityRule.getLayouts(), R.layout.islide_tools));
		LinearLayout targetItemView = IntroUtils.getBottomBarFromToolSlide(activityRule.getActivity());
		TapTargetBottomBar tapTargetBottomBar = IntroUtils.getTapTargetBottomBar(activityRule.getActivity());
		tapTargetBottomBar.initTargetView();
		HashMap<ToolType, TapTarget> tapTargetMap = IntroUtils.getMapFromTapTarget(tapTargetBottomBar);
		Assert.assertEquals("TapTarget doesn't have same size. Tool is missing",
				IntroUtils.numberOfVisibleChildren(targetItemView), tapTargetMap.size());
	}

	@Test
	public void numberTapTargetsTopBar() {
		EspressoUtils.changeIntroPage(getPageIndexFromLayout(activityRule.getLayouts(), R.layout.islide_possibilities));
		LinearLayout targetItemView = IntroUtils.getTopBarFromPossibilitiesSlide(activityRule.getActivity());
		TapTargetTopBar tapTargetTopBar = IntroUtils.getTapTargetTopBar(activityRule.getActivity());
		tapTargetTopBar.initTargetView();

		HashMap<ToolType, TapTarget> tapTargetMap = IntroUtils.getMapFromTapTarget(tapTargetTopBar);
		Assert.assertEquals("TapTarget doesn't have same size. Tool is missing",
				IntroUtils.numberOfVisibleChildren(targetItemView), tapTargetMap.size());
	}

	@Test
	public void testRadiusTopBar() {
		EspressoUtils.changeIntroPage(getPageIndexFromLayout(activityRule.getLayouts(), R.layout.islide_possibilities));
		EspressoUtils.waitMillis(200);
		TapTargetTopBar tapTargetTopBar = IntroUtils.getTapTargetTopBar(activityRule.getActivity());
		int expectedRadius = IntroUtils.getExpectedRadiusForTapTarget();
		int actualRadius = tapTargetTopBar.radius;

		assertEquals("Radius calculated Wrong", expectedRadius, actualRadius);
	}

	@Test
	public void testRadiusBottomBar() {
		EspressoUtils.changeIntroPage(getPageIndexFromLayout(activityRule.getLayouts(), R.layout.islide_tools));
		TapTargetBottomBar tapTargetBottomBar = IntroUtils.getTapTargetBottomBar(activityRule.getActivity());
		int expectedRadius = IntroUtils.getExpectedRadiusForTapTarget();
		int actualRadius = tapTargetBottomBar.radius;
		assertEquals("Radius calculated Wrong", expectedRadius, actualRadius);
	}
}