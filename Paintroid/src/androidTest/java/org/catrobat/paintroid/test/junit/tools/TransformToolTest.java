/*
 * Paintroid: An image manipulation application for Android.
 * Copyright (C) 2010-2015 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catrobat.paintroid.test.junit.tools;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import org.catrobat.paintroid.tools.implementation.TransformTool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TransformToolTest {

	private Bitmap bitmap;

	@Before
	public void setUp() {
		bitmap = Bitmap.createBitmap(3, 3, Bitmap.Config.ARGB_8888);
	}

	@After
	public void tearDown() {
		bitmap.recycle();
		bitmap = null;
	}

	@Test
	public void testCropAlgorithmSnail() {
		bitmap.setPixel(1, 1, Color.BLACK);
		Rect expectedBounds = new Rect(1, 1, 1, 1);
		Rect bounds = TransformTool.cropAlgorithmSnail(bitmap);
		assertEquals(expectedBounds, bounds);

		bitmap.eraseColor(Color.TRANSPARENT);
		bitmap.setPixel(2, 2, Color.BLACK);
		expectedBounds = new Rect(2, 2, 2, 2);
		bounds = TransformTool.cropAlgorithmSnail(bitmap);
		assertEquals(expectedBounds, bounds);
	}

	@Test
	public void testCropAlgorithmSnailWithEmptyBitmap() {
		Rect bounds = TransformTool.cropAlgorithmSnail(bitmap);
		assertNull(bounds);
	}

	@Test
	public void testCropAlgorithmSnailWithFilledBitmap() {
		bitmap.eraseColor(Color.BLACK);
		Rect expectedBounds = new Rect(0, 0, 2, 2);
		Rect bounds = TransformTool.cropAlgorithmSnail(bitmap);
		assertEquals(expectedBounds, bounds);
	}
}
