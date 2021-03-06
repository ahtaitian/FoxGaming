/**
 * FileName:     Enemy_Duck.java
 * @Description: TODO
 * All rights Reserved, Designed By Noisyfox
 * Copyright:    Copyright(C) 2012
 * Company       FoxTeam.
 * @author:      Noisyfox
 * @version      V1.0
 * Createdate:   2012-8-12 下午7:20:17
 *
 * Modification  History:
 * Date         Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2012-8-12      Noisyfox        1.0             1.0
 * Why & What is modified:
 */
package org.foxteam.noisyfox.THEngine.Section.Enemys;

import org.foxteam.noisyfox.FoxGaming.Core.*;
import org.foxteam.noisyfox.FoxGaming.G2D.*;
import org.foxteam.noisyfox.THEngine.GlobalResources;
import org.foxteam.noisyfox.THEngine.Section.BasicElements.Bullet;
import org.foxteam.noisyfox.THEngine.Section.BasicElements.Explosion;
import org.foxteam.noisyfox.THEngine.Section.BasicElements.SectionStage;
import org.foxteam.noisyfox.THEngine.Section.Bullets.Bullet_Enemy_1;
import org.foxteam.noisyfox.THEngine.Section.Bullets.Bullet_Player;

import android.graphics.Canvas;

/**
 * @ClassName: Enemy_Duck
 * @Description: 从屏幕的一侧水平移动到另一侧，均匀抛下静止子弹
 * @author: Noisyfox
 * @date: 2012-8-12 下午7:20:17
 * 
 */
public class Enemy_Duck extends EnemyInAir {

	private int inY = 0;
	private boolean frmL = false;

	private float speed = 0;

	FGSpriteConvertor sc = new FGSpriteConvertor();

	@Override
	protected void onCreate() {
		FGSprite duckSprite = new FGSprite();
		duckSprite.bindFrames(GlobalResources.FRAMES_ENEMY_DUCK);
		duckSprite.setOffset(duckSprite.getWidth() / 2,
				duckSprite.getHeight() / 2);
		this.bindSprite(duckSprite);

		this.setPosition(
				frmL ? -(duckSprite.getWidth() - duckSprite.getOffsetX())
						: FGStage.getCurrentStage().getWidth()
								+ duckSprite.getOffsetX(), inY);

		speed = 20f / FGStage.getSpeed();

		this.motion_set(frmL ? 0 : -180, speed);
		this.motion_add(270, SectionStage.getScrollSpeedV());

		this.setAlarm(0, (int) (FGStage.getSpeed() * 3f), true);// 发射子弹
		this.startAlarm(0);

		this.setAlarm(1, (int) (FGStage.getSpeed() * 0.1f), true);// 播放动画
		this.startAlarm(1);

		FGGraphicCollision co = new FGGraphicCollision();
		co.addCircle(-3, -5, 11, true);
		co.addCircle(0, -11, 9, true);
		co.addCircle(-2, 5, 11, true);
		co.addCircle(-15, 1, 8, true);
		co.addCircle(11, 1, 8, true);
		co.addCircle(-20, -7, 4, true);
		co.addCircle(18, -6, 4, true);
		co.addCircle(-5, 16, 5, true);
		this.bindCollisionMask(co);
		if (frmL) {
			sc.setScale(-1, 1);
			FGConvertor GCConvertor = new FGConvertor();
			GCConvertor.setScale(-1, 1);
			co.applyConvertor(GCConvertor);
		}

		this.setHP(10);

		this.requireCollisionDetection(Bullet_Player.class);
	}

	@Override
	protected void onAlarm(int whichAlarm) {
		if (whichAlarm == 0) {// 发射子弹
			Bullet b = new Bullet_Enemy_1((int) this.getX(), (int) this.getY()
					- this.getSprite().getOffsetY()
					+ this.getSprite().getHeight(), 270,
					SectionStage.getScrollSpeedV());
			b.setDepth(this.getDepth() + 1);
		} else if (whichAlarm == 1) {// 播放动画
			this.getSprite().nextFrame();
		}
	}

	@Override
	public boolean isOutOfStage() {
		return super.isOutOfStage()
				&& ((frmL && this.getX() > FGStage.getCurrentStage().getWidth() / 2) || (!frmL && this
						.getX() < FGStage.getCurrentStage().getWidth() / 2));
	}

	@Override
	protected void onOutOfStage() {
		this.dismiss();
		this.bindCollisionMask(null);
	}

	@Override
	protected void onDraw() {
		Canvas c = this.getCanvas();
		if (!frmL) {
			this.getSprite().draw(c, (int) this.getX(), (int) this.getY());
		} else {
			this.getSprite().draw(c, (int) this.getX(), (int) this.getY(), sc);
		}
	}

	@Override
	protected void Explosion(Bullet bullet) {
		new Explosion(GlobalResources.FRAMES_EXPLOSION_NORMAL, 1, 0.5f,
				(int) this.getX(), (int) this.getY(), -1);
		this.dismiss();

		this.bindCollisionMask(null);

		FGGamingThread.score += 14;
	}

	@Override
	public void createEnemy(int x, int y, int... extraConfig) {
		this.perform(FGStage.getCurrentStage().getStageIndex());
		inY = y;
		frmL = extraConfig[0] == 0;
	}

}
