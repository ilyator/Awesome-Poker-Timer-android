package com.ily.pakertymer.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.SweepGradient
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import com.ily.pakertymer.R
import com.ily.pakertymer.util.MeasureUtil

/**
 * Created by ily on 22.08.2016.
 */
class CircleTimerView : AppCompatImageView {

    private var levelFullTime: Long = 0
    private var levelCurrentTime: Long = 0

    private var mProgressBarEnabled: Boolean = false
    private val mProgressWidth = MeasureUtil.dpToPx(context, 6f)
    private var mProgressCircleBounds = RectF()
    private val mProgressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mLastTimeAnimated: Long = 0
    private val mSpinSpeed: Float = 0.toFloat()
    private var mCurrentProgress: Float = 0.toFloat()
    private val mTargetProgress: Float = 0.toFloat()
    @get:Synchronized
    private var progress: Int = 0
    private val mProgressMax = 100

    private val circleSize: Int
        get() = resources.getDimensionPixelSize(R.dimen.fab_size_normal)


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        progress = 0
        isClickable = true
    }

    fun setLevelFullTime(levelFullTime: Long) {
        this.levelFullTime = levelFullTime
    }

    fun setLevelCurrentTime(levelCurrentTime: Long) {
        this.levelCurrentTime = levelCurrentTime
        updateTimerView()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        setupProgressBounds()
        setupProgressBarPaints()
    }

    @Synchronized
    private fun updateTimerView() {
        val progress = (levelCurrentTime * 100).toFloat() / levelFullTime
        this.progress = progress.toInt()
        mCurrentProgress = progress / mProgressMax.toFloat() * 360
        mProgressBarEnabled = true
        invalidate()
    }

    @Synchronized
    fun hideProgress() {
        mProgressBarEnabled = false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mProgressBarEnabled) {
            canvas.drawArc(mProgressCircleBounds, 0f, mCurrentProgress, false, mProgressPaint)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(calculateMeasuredWidth(), calculateMeasuredHeight())
    }

    private fun calculateMeasuredWidth(): Int {
        var width = circleSize
        if (mProgressBarEnabled) {
            width += mProgressWidth * 2
        }
        return width
    }

    private fun calculateMeasuredHeight(): Int {
        var height = circleSize
        if (mProgressBarEnabled) {
            height += mProgressWidth * 2
        }
        return height
    }

    private fun setupProgressBarPaints() {
        mProgressPaint.style = Paint.Style.STROKE
        mProgressPaint.strokeWidth = mProgressWidth.toFloat()
        val resIds = intArrayOf(ResourcesCompat.getColor(resources, R.color.timer_red, null), ResourcesCompat.getColor(resources, R.color.timer_yellow, null), ResourcesCompat.getColor(resources, R.color.timer_green_last, null))

        val shader = SweepGradient((width / 2).toFloat(), (height / 2).toFloat(), resIds, null)

        mProgressPaint.shader = shader
    }

    private fun setupProgressBounds() {
        val circleInsetHorizontal = 0
        val circleInsetVertical = 0
        mProgressCircleBounds = RectF(
                (circleInsetHorizontal + mProgressWidth / 2).toFloat(),
                (circleInsetVertical + mProgressWidth / 2).toFloat(),
                (calculateMeasuredWidth() - circleInsetHorizontal - mProgressWidth / 2).toFloat(),
                (calculateMeasuredHeight() - circleInsetVertical - mProgressWidth / 2).toFloat()
        )
    }

}
