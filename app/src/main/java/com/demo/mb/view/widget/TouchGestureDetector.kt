package com.demo.mb.view.widget

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.OnDoubleTapListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector

/**
 * 手势辅助类
 */
class TouchGestureDetector(context: Context?, listener: IOnTouchGestureListener) {
    private val mGestureDetector: GestureDetector
    private val mScaleGestureDetector: ScaleGestureDetector
    private val mOnTouchGestureListener: IOnTouchGestureListener
    private var mIsScrollAfterScaled = true // 在一串事件序列中，缩放onScale后继续识别onScroll手势

    init {
        mOnTouchGestureListener = OnTouchGestureListenerProxy(listener)
        mGestureDetector = GestureDetector(context, mOnTouchGestureListener)
        mScaleGestureDetector = ScaleGestureDetector(context, mOnTouchGestureListener)
        mScaleGestureDetector.isQuickScaleEnabled = false
    }

    /**
     * 是否开启长按识别。如果取消长按识别，则长按后移动手指会触发onScroll事件
     *
     * @param isLongPressEnabled
     */
    fun setIsLongPressEnabled(isLongPressEnabled: Boolean) {
        mGestureDetector.setIsLongpressEnabled(isLongPressEnabled)
    }

    /**
     * 是否开启长按识别
     *
     * @return
     */
    fun isLongPressEnabled(): Boolean {
        return mGestureDetector.isLongpressEnabled
    }

    /**
     * 在一串事件序列中，缩放onScale后是否继续识别onScroll手势
     *
     * @param scrollAfterScaled
     */
    fun setIsScrollAfterScaled(scrollAfterScaled: Boolean) {
        mIsScrollAfterScaled = scrollAfterScaled
    }

    fun isScrollAfterScaled(): Boolean {
        return mIsScrollAfterScaled
    }

    fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP ||
            event.action == MotionEvent.ACTION_CANCEL ||
            event.action == MotionEvent.ACTION_OUTSIDE
        ) {
            mOnTouchGestureListener.onUpOrCancel(event)
        }
        var ret: Boolean = mScaleGestureDetector.onTouchEvent(event)
        if (!mScaleGestureDetector.isInProgress) {
            ret = ret or mGestureDetector.onTouchEvent(event)
        }
        return ret
    }

    /**
     * 代理
     */
    private inner class OnTouchGestureListenerProxy(private val mListener: IOnTouchGestureListener) :
        IOnTouchGestureListener {
        private var mHasScaled = false // 当前触摸序列中是否已经识别了缩放手势，如果是的话，则后续不会触发onScroll
        private var mIsScrolling = false
        private var mLastScrollMotionEvent: MotionEvent? = null // 最后一次滚动的事件

        override fun onDown(e: MotionEvent): Boolean {
            // 触摸序列的开始，初始化
            mHasScaled = false
            mIsScrolling = false
            return mListener.onDown(e)
        }

        override fun onUpOrCancel(e: MotionEvent?) {
            mListener.onUpOrCancel(e)
            if (mIsScrolling) {
                mIsScrolling = false
                mLastScrollMotionEvent = null
                onScrollEnd(e)
            }
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            return mListener.onFling(e1, e2, velocityX, velocityY)
        }

        override fun onLongPress(e: MotionEvent) {
            mListener.onLongPress(e)
        }

        override fun onScrollBegin(e: MotionEvent?) {
            mListener.onScrollBegin(e)
        }

        override fun onScrollEnd(e: MotionEvent?) {
            mListener.onScrollEnd(e)
        }

        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (!mIsScrollAfterScaled && mHasScaled) { // 当前触摸序列中已经识别了缩放手势,后续不会触发onScroll
                mIsScrolling = false
                return false
            }
            if (!mIsScrolling) { // 通知开始滚动
                mIsScrolling = true
                onScrollBegin(e1)
            }
            mLastScrollMotionEvent = MotionEvent.obtain(e2)
            return mListener.onScroll(e1, e2, distanceX, distanceY)
        }

        override fun onShowPress(e: MotionEvent) {
            mListener.onShowPress(e)
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return mListener.onSingleTapUp(e)
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            return mListener.onDoubleTap(e)
        }

        override fun onDoubleTapEvent(e: MotionEvent): Boolean {
            return mListener.onDoubleTapEvent(e)
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return mListener.onSingleTapConfirmed(e)
        }

        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            return mListener.onScale(detector)
        }

        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            mHasScaled = true
            if (mIsScrolling) {
                mIsScrolling = false
                onScrollEnd(mLastScrollMotionEvent)
            }
            return mListener.onScaleBegin(detector)
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {
            mListener.onScaleEnd(detector)
        }

    }

    /**
     * 识别手势的回调接口
     */
    interface IOnTouchGestureListener : GestureDetector.OnGestureListener, OnDoubleTapListener,
        ScaleGestureDetector.OnScaleGestureListener {
        /**
         * 监听ACTION_UP和ACTION_CANCEL、ACTION_OUTISIDE事件
         * 该事件在其他事件之前调用
         *
         * @param e
         */
        fun onUpOrCancel(e: MotionEvent?)

        /**
         * 标志滚动开始
         *
         * @param e
         */
        fun onScrollBegin(e: MotionEvent?)

        /**
         * 标志滚动结束
         *
         * @param e
         */
        fun onScrollEnd(e: MotionEvent?)
    }

    abstract class OnTouchGestureListener :
        IOnTouchGestureListener {
        /**
         * 沿用系统的GestureDetector逻辑，双击时第二次onDown在onDoubleTap之后调用
         *
         * @param e
         * @return
         */
        override fun onDown(e: MotionEvent): Boolean {
            return false
        }

        override fun onUpOrCancel(e: MotionEvent?) {}
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            return false
        }

        override fun onLongPress(e: MotionEvent) {}

        /**
         * 标志滚动开始
         *
         * @param e
         */
        override fun onScrollBegin(e: MotionEvent?) {}

        /**
         * 标志滚动结束
         *
         * @param e
         */
        override fun onScrollEnd(e: MotionEvent?) {}
        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            return false
        }

        override fun onShowPress(e: MotionEvent) {}
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return false
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            return false
        }

        override fun onDoubleTapEvent(e: MotionEvent): Boolean {
            return false
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return false
        }

        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            return false
        }

        override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
            return false
        }

        override fun onScaleEnd(detector: ScaleGestureDetector?) {}
    }
}