package com.snail.mbcomponent.custom

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.View

/**
 * 海报控件练习
 */
class PosterView(context: Context) : View(context) {
    private val TAG = "PosterView"
    private val mPaint: Paint = Paint()//画笔
    private val mPathList: MutableList<PathItem> = mutableListOf()//路径集合
    private var mLastX: Float = 0f//最后触摸点的x坐标
    private var mLastY: Float = 0f//最后触摸点的y坐标
    private var mCurrentPath: PathItem? = null//当前路径
    private var mSelectedPath: PathItem? = null//选中路径

    init {
        // 设置画笔
        mPaint.color = Color.RED
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 20f
        mPaint.isAntiAlias = true
        mPaint.strokeCap = Paint.Cap.ROUND
    }

    private val mTouchGestureDetector: TouchGestureDetector =
        TouchGestureDetector(context, object : TouchGestureDetector.OnTouchGestureListener() {
            val mRectF = RectF()

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                var found = false
                //查找路径
                for (path in mPathList) {
                    path.select = false
                }
                for (path in mPathList) {
                    //计算路径范围
                    path.path.computeBounds(mRectF, true)
                    //偏移量
                    mRectF.offset(path.mX, path.mY)
                    //检查是否在路径范围内
                    if (mRectF.contains(e.x, e.y)) {
                        found = true
                        path.select = true
                        mSelectedPath = path
                        break
                    }
                }
                if (!found) {
                    mSelectedPath = null
                }
                //重绘
                invalidate()
                return true
            }

            //滑动开始
            override fun onScrollBegin(e: MotionEvent?) {
                Log.d(TAG, "onScrollBegin：x-${e?.x} y-${e?.y}")
                if (mSelectedPath == null) {
                    //新的路径
                    mCurrentPath = PathItem().also {
                        //添加进集合
                        mPathList.add(it)
                        e?.run {
                            it.path.moveTo(x, y)
                            mLastX = x
                            mLastY = y
                        }
                    }
                }
                //重绘
                invalidate()
            }

            /**
             * 滑动中
             */
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                Log.d(TAG, "onScroll：x-${e2.x} y-${e2.y}")
                if (mSelectedPath == null) {
                    mCurrentPath?.run {
                        //使用贝塞尔曲线 让涂鸦轨迹更圆滑
                        path.quadTo(mLastX, mLastY, (e2.x + mLastX) / 2, (e2.y + mLastY) / 2)
                    }
                    mLastX = e2.x
                    mLastY = e2.y
                } else {
                    mSelectedPath?.run {
                        mX -= distanceX
                        mY -= distanceY
                    }
                }
                //重绘
                invalidate()
                return true
            }

            /**
             * 滑动结束
             */
            override fun onScrollEnd(e: MotionEvent?) {
                Log.d(TAG, "onScrollEnd：x-${e?.x} y-${e?.y}")
                mCurrentPath?.run {
                    //使用贝塞尔曲线 让涂鸦轨迹更圆滑
                    e?.let {
                        path.quadTo(mLastX, mLastY, (it.x + mLastX) / 2, (it.y + mLastY) / 2)
                    }
                }
                mCurrentPath = null
                //重绘
                invalidate()
            }
        })

    /**
     * 分发触摸事件
     */
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        val consumed = mTouchGestureDetector.onTouchEvent(event!!)
        return if (consumed) consumed else super.dispatchTouchEvent(event)
    }

    /**
     * 绘制
     */
    override fun onDraw(canvas: Canvas?) {
        // 绘制涂鸦轨迹
        for (path in mPathList) {
            canvas?.run {
                // 1.保存画布状态，下面要变换画布
                save()
                //根据涂鸦轨迹偏移值，偏移画布使其画在对应位置上
                translate(path.mX, path.mY)
                //选中为黄色，未选中红色
                if (path.select) {
                    mPaint.color = Color.YELLOW
                } else {
                    mPaint.color = Color.RED
                }
                drawPath(path.path, mPaint)
                //2.恢复画布状态，绘制完一个涂鸦轨迹后取消上面的画布变换，不影响下一个
                restore()
            }
        }
    }
}