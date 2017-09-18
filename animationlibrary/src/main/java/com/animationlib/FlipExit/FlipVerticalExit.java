package com.animationlib.FlipExit;

import android.view.View;

import com.animationlib.BaseAnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class FlipVerticalExit extends BaseAnimatorSet {
	@Override
	public void setAnimation(View view) {
		animatorSet.playTogether(ObjectAnimator.ofFloat(view, "rotationX", 0, 90),//
				ObjectAnimator.ofFloat(view, "alpha", 1, 0));
	}
}
