package com.animationlib.FadeEnter;

import android.view.View;

import com.animationlib.BaseAnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import static android.R.attr.duration;

public class FadeEnter extends BaseAnimatorSet {
	@Override
	public void setAnimation(View view) {
		animatorSet.playTogether(//
				ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(duration));
	}
}
