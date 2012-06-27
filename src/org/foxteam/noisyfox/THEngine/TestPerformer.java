/**
 * FileName:     TestPerformer.java
 * @Description: TODO
 * All rights Reserved, Designed By Noisyfox
 * Copyright:    Copyright(C) 2012
 * Company       FoxTeam.
 * @author:      Noisyfox
 * @version      V1.0
 * Createdate:   2012-6-22 下午2:37:23
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2012-6-22      Noisyfox        1.0             1.0
 * Why & What is modified:
 */
package org.foxteam.noisyfox.THEngine;

import org.foxteam.noisyfox.FoxGaming.Core.*;
import org.foxteam.noisyfox.FoxGaming.G2D.*;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * @ClassName: TestPerformer
 * @Description: TODO
 * @author: Noisyfox
 * @date: 2012-6-22 下午2:37:23
 * 
 */
public class TestPerformer extends Performer {

	int x1 = 0;
	int y1 = 0;

	int x2 = 0;
	int y2 = 0;

	int x3 = 0;
	int y3 = 0;

	private EventsListener eventsListener = new EventsListener() {
		@Override
		public void onTouchPress(Performer unit, int whichfinger, int x, int y) {
			if (whichfinger == 0) {
				x1 = x;
				y1 = y;
			} else if (whichfinger == 1) {
				x2 = x;
				y2 = y;
			} else {
				x3 = x;
				y3 = y;
			}
		}

		@Override
		public void onTouch(Performer unit, int whichfinger, int x, int y) {
			if (whichfinger == 0) {
				x1 = x;
				y1 = y;
			} else if (whichfinger == 1) {
				x2 = x;
				y2 = y;
			} else {
				x3 = x;
				y3 = y;
			}
		}
	};

	MySprite s;
	SpriteConvertor sc;

	public TestPerformer() {
		this.setEventsListener(eventsListener);
		s = new MySprite();
		Bitmap b = BitmapFactory.decodeResource(
				GameCore.mContext.getResources(),
				org.foxteam.noiyfox.THEngine.R.drawable.button);
		s.loadFromBitmap(b);
		s.setOffset(s.getWidth() / 2, 0);
		sc = new SpriteConvertor();
		sc.setRotation(60);
		sc.setScale(1, 0.5);
		sc.setAlpha(0.5);
	}

	@Override
	protected void onDraw(Performer unit) {
		Paint p = new Paint();
		p.setColor(Color.BLACK);
		unit.getCanvas().drawText(GamingThread.getSPS() + "", 50, 50, p);
		unit.getCanvas().drawText("1", x1, y1, p);
		unit.getCanvas().drawText("2", x2, y2, p);
		unit.getCanvas().drawText("3", x3, y3, p);
		s.draw(unit.getCanvas(), x1, y1, sc);
		unit.getCanvas().drawPoint(x1, y1, p);
	}

}
