package com.kin.processing.util;

import com.kin.processing.build.PositionSizeAbs;

public class VerificationAndResetG2d {

	public static <T extends PositionSizeAbs> T verification(T build) {

		if (build.getScaleX() == 0.0) {
			build.setScaleX(1);
		}

		if (build.getScaleY() == 0.0) {
			build.setScaleY(1);
		}

		return build;
	}

	public static <T extends PositionSizeAbs> T reset(T build) {
		build.setX(build.getX() * -1);
		build.setY(build.getY() * -1);
		build.setTranslateX(build.getTranslateX() * -1);
		build.setTranslateY(build.getTranslateY() * -1);
		build.setRotate(build.getRotate() * -1);

		build.setScaleX(1 / build.getScaleX());
		build.setScaleY(1 / build.getScaleY());
		return build;
	}

}
