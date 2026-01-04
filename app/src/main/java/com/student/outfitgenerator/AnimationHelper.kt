package com.student.outfitgenerator

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator

/**
 * Simple animation helper class
 * Makes it easy to add animations to views
 */
object AnimationHelper {
    
    // Animation durations (in milliseconds)
    private const val SHORT_DURATION = 200L
    private const val MEDIUM_DURATION = 400L
    private const val LONG_DURATION = 600L
    
    /**
     * Fade in a view (make it appear smoothly)
     */
    fun fadeIn(view: View, duration: Long = MEDIUM_DURATION) {
        view.alpha = 0f
        view.visibility = View.VISIBLE
        view.animate()
            .alpha(1f)
            .setDuration(duration)
            .setInterpolator(DecelerateInterpolator())
            .start()
    }
    
    /**
     * Slide in from bottom (for cards and buttons)
     */
    fun slideInFromBottom(view: View, delay: Long = 0, duration: Long = MEDIUM_DURATION) {
        view.translationY = 100f
        view.alpha = 0f
        view.visibility = View.VISIBLE
        view.animate()
            .translationY(0f)
            .alpha(1f)
            .setStartDelay(delay)
            .setDuration(duration)
            .setInterpolator(DecelerateInterpolator())
            .start()
    }
    
    /**
     * Slide in from left (for side elements)
     */
    fun slideInFromLeft(view: View, delay: Long = 0, duration: Long = MEDIUM_DURATION) {
        view.translationX = -100f
        view.alpha = 0f
        view.visibility = View.VISIBLE
        view.animate()
            .translationX(0f)
            .alpha(1f)
            .setStartDelay(delay)
            .setDuration(duration)
            .setInterpolator(DecelerateInterpolator())
            .start()
    }
    
    /**
     * Scale in animation (for buttons and icons)
     */
    fun scaleIn(view: View, delay: Long = 0, duration: Long = MEDIUM_DURATION) {
        view.scaleX = 0f
        view.scaleY = 0f
        view.alpha = 0f
        view.visibility = View.VISIBLE
        view.animate()
            .scaleX(1f)
            .scaleY(1f)
            .alpha(1f)
            .setStartDelay(delay)
            .setDuration(duration)
            .setInterpolator(DecelerateInterpolator())
            .start()
    }
    
    /**
     * Button press animation (makes button feel responsive)
     */
    fun pressAnimation(view: View) {
        view.animate()
            .scaleX(0.95f)
            .scaleY(0.95f)
            .setDuration(SHORT_DURATION)
            .withEndAction {
                view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(SHORT_DURATION)
                    .start()
            }
            .start()
    }
    
    /**
     * Card elevation animation (makes card appear to lift when clicked)
     */
    fun liftCard(view: View) {
        view.animate()
            .scaleX(0.98f)
            .scaleY(0.98f)
            .setDuration(SHORT_DURATION)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }
    
    /**
     * Reset card elevation
     */
    fun resetCard(view: View) {
        view.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(SHORT_DURATION)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .start()
    }
    
    /**
     * Animate multiple views with staggered delays (for lists)
     */
    fun animateViewsStaggered(views: List<View>, delayBetween: Long = 100) {
        views.forEachIndexed { index, view ->
            slideInFromBottom(view, delay = index * delayBetween)
        }
    }
}

