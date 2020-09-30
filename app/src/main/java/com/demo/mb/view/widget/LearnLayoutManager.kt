package com.demo.mb.view.widget

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * 自定义layoutManager学习
 * learn by https://blog.csdn.net/u011387817/article/details/81875021
 */
abstract class LearnLayoutManager : RecyclerView.LayoutManager() {

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
    }

    /**
     * 这两个方法都可以测量子View，不同的是第二个方法会把Item设置的Margin也考虑进去，所以如果我们的LayoutManager需要支持Margin属性的话，就用第二个了。
     */
    override fun measureChild(child: View, widthUsed: Int, heightUsed: Int) {
        super.measureChild(child, widthUsed, heightUsed)
    }

    override fun measureChildWithMargins(child: View, widthUsed: Int, heightUsed: Int) {
        super.measureChildWithMargins(child, widthUsed, heightUsed)
    }

    /**
     * 这两个方法是LayoutManager提供的，其实它们内部也是会调用child的getMeasuredWidth或getMeasuredHeight的，
     * 只是在返回的时候，会考虑到Decorations的大小，并根据Decorations的尺寸对应的放大一点，所以如果我们有设置ItemDecorations的话，
     * 用这两个方法得到的尺寸往往会比直接调用getMeasuredWidth或getMeasuredHeight方法大就是这个原因了,
     * 它们在返回的时候，还加上了Decoration对应方向的值。
     */
    override fun getDecoratedMeasuredWidth(child: View): Int {
        return super.getDecoratedMeasuredWidth(child)
    }

    override fun getDecoratedMeasuredHeight(child: View): Int {
        return super.getDecoratedMeasuredHeight(child)
    }

    /**
     * 这两个方法也是LayoutManager提供的，我们使用layoutDecorated方法的话，它会给ItemDecorations腾出位置
     */
    override fun layoutDecorated(child: View, left: Int, top: Int, right: Int, bottom: Int) {
        super.layoutDecorated(child, left, top, right, bottom)
    }

    /**
     * 而下面layoutDecoratedWithMargins方法，相信同学们看方法名就已经知道了，没错，这个方法就是在layoutDecorated的基础上，把Item设置的Margin也应用进去
     */
    override fun layoutDecoratedWithMargins(
        child: View,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.layoutDecoratedWithMargins(child, left, top, right, bottom)
    }

    /**
     * 回收
     * 说到RecyclerView的回收机制，相信也有不少同学了解过了，RecyclerView的回收任务是交给一个内部类: Recycler 来负责的，
     * 一般情况下(忽略ViewCacheExtension，因为这个需要自己实现)，它有4个存放回收Holder的集合，分别是：
     *
     * 可直接重用的临时缓存：mAttachedScrap，mChangedScrap；
     * 可直接重用的缓存：mCachedViews；
     * 需重新绑定数据的缓存：mRecyclerPool.mScrap；
     *
     * 为什么说前面两个是临时缓存呢？
     * 因为每当RecyclerView的dispatchLayout方法结束之前（当调用RecyclerView的requestLayout方法或者调用Adapter的
     * 一系列notify方法会回调这个dispatchLayout），它们里面的Holder都会移动到mCachedViews或mRecyclerPool.mScrap中。
     *
     * 那为什么有两个呢？它们之间有什么区别吗？
     * 它们之间的区别就是：mChangedScrap只能在预布局状态下重用，因为它里面装的都是即将要放到mRecyclerPool中的Holder，
     * 而mAttachedScrap则可以在非预布局状态下重用。
     *
     * 什么是预布局(PreLayout)？
     * 顾名思义，就是在真正布局之前，事先布局一次。但在预布局状态下，应该把已经remove掉的Item也layout出来，
     * 我们可以通过ViewHolder的LayoutParams.isViewRemoved()方法来判断这个ViewHolder是否已经被remove掉。
     * 只有在Adapter的数据集更新时，并且调用的是除notifyDataSetChanged以外的一系列notify方法，预布局才会生效。
     * 这也是为什么调用notifyDataSetChanged方法不会播放Item动画的原因了。
     * 这个其实有点像我们加载Bitmap的操作：先设置只读边，等获取到图片尺寸后设置好缩放比例再真正把图片加载进来。
     * 要开启预布局的话，需要重写LayoutManager中的supportsPredictiveItemAnimations方法并return true;
     * 这样就能生效了(当然，自带的那三个LayoutManager已经是开启了这个效果的)，当Adapter的数据集更新时，
     * onLayoutChildren方法就会回调两次，第一次是预布局，第二次是真实的布局，我们也可以通过state.isPreLayout() 来判断当前是否为预布局状态，
     * 并根据这个状态来决定要layout的Item。
     */

    override fun detachAndScrapView(child: View, recycler: RecyclerView.Recycler) {
        super.detachAndScrapView(child, recycler)
    }

    override fun detachAndScrapViewAt(index: Int, recycler: RecyclerView.Recycler) {
        super.detachAndScrapViewAt(index, recycler)
    }

    /**
     * 前面两个方法都是回收指定的View，而第三个方法会把RecyclerView中全部未分离的子View都回收，
     * 我们看源码可以发现，这三个方法最终调用scrapOrRecycleView方法
     *
     * 它会根据viewHolder的状态来决定放哪里，如果这个viewHolder已经被标记无效，并且还没有移除，又没有设置StableId的话，
     * 就会把它从RecyclerView中移除并尝试放到mRecyclerPool.mScrap中，如果没有满足以上条件的话，就会先把它分离，
     * 然后放进临时缓存(mAttachedScrap或mChangedScrap)，以便稍后直接重用。
     *
     * 刚刚说到了StableId，什么是StableId？
     * 其实就是这个Item的唯一标识。
     * 这个是需要我们自己调用Adapter的setHasStableIds(true) 来开启，还需要在Adapter中重写getItemId(int position) 方法，
     * 根据position返回一个对应的唯一id
     * 这样一来，当LayoutManager调用上面三个回收方法时，那些Holder就永远不会被放到mRecyclerPool.mScrap中，
     * 等到LayoutManager调用getViewForPosition方法时，如果没能根据position在mAttachedScrap和mCachedViews中找到合适的Holder的话，
     * 就会根据Adapter的getItemId方法返回的id来再次从上面两个集合中找(匹配id)，如果能匹配到的话，就表示能直接重用了，
     * 所以，如果我们做了这个StableId的话，理论上是会提高滑动的流畅度的。
     */
    override fun detachAndScrapAttachedViews(recycler: RecyclerView.Recycler) {
        super.detachAndScrapAttachedViews(recycler)
    }

    /**
     * 通过看名字可以大概知道，这几个方法会把holder放进mRecyclerPool.mScrap中，但不一定每次都直接放进去的，
     * 如果这个holder未被标记为无效的话，会经过我们上面说的mCachedViews缓冲一下(它默认能装2个，当然我们也可以根据需求来设置合适的大小)，
     * 这个mCachedViews就好像一个队列，当有新的holder要被添加进来，而这个时候它又装满了的话，就会把最先存进去的holder拿出来，
     * 扔进mRecyclerPool.mScrap里面，这样新的holder就有空间放进来了。
     * 所以，在mCachedViews中取出来的holder，也是能直接重用而不需重新绑定数据的。
     */
    override fun removeView(child: View?) {
        super.removeView(child)
    }

    override fun removeViewAt(index: Int) {
        super.removeViewAt(index)
    }

    override fun removeAndRecycleAllViews(recycler: RecyclerView.Recycler) {
        super.removeAndRecycleAllViews(recycler)
    }

    /**
     * 处理滚动动作
     * 好，现在到了基本流程中最后一步了，我们来看看如何使LayoutManager的Item能够跟随手指滚动。
     * 当RecyclerView接收到触摸事件时，会根据： canScrollHorizontally，canScrollVertically这两个方法的返回值来判断是否可以接受水平或垂直触摸事件，
     * 如果返回的是true的话，就会回调：scrollHorizontallyBy，scrollVerticallyBy方法
     */
    override fun canScrollHorizontally(): Boolean {
        return super.canScrollHorizontally()
    }

    override fun canScrollVertically(): Boolean {
        return super.canScrollVertically()
    }

    /**
     * 1.dx(dy) 表示本次较于上一次的偏移量，<0为 向右(下) 滚动，>0为向左(上) 滚动；
     * 2.recycler 就是我们刚刚说到的，处理回收和获取Items的对象；
     * 3.state 看名字就能大概知道，我们可以借助它来获取到一些很有用的信息，比如说isPreLayout，itemCount之类的；
     *
     * 可以看到这两个方法还需要返回一个int，就是要告诉RecyclerView，本次我们实际消费(偏移)的距离，比如说当滚动到最底部时，不能继续往下滚动，这时候就应该返回0了。
     * 我们在重写这两个方法时，就要根据当前偏移量来对Items做出相应的偏移，这样列表就能随手指滚动起来了，当然了，别忘了回收这一重要环节。
     */
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        return super.scrollHorizontallyBy(dx, recycler, state)
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        return super.scrollVerticallyBy(dy, recycler, state)
    }

    /**
     * 让Items显示出来
     * 我们在自定义ViewGroup中，想要显示子View，无非就三件事：
     *
     * 1.添加 通过addView方法把子View添加进ViewGroup或直接在xml中直接添加；
     *
     * 2.测量 重写onMeasure方法并在这里决定自身尺寸以及每一个子View大小；
     *
     * 3.布局 重写onLayout方法，在里面调用子View的layout方法来确定它的位置和尺寸；
     *
     * 其实在自定义LayoutManager中，在流程上也是差不多的，我们需要重写onLayoutChildren方法，
     * 这个方法会在初始化或者Adapter数据集更新时回调，在这方法里面，需要做以下事情：
     *
     * 1.进行布局之前，我们需要调用detachAndScrapAttachedViews方法把屏幕中的Items都分离出来，内部调整好位置和数据后，再把它添加回去(如果需要的话)；
     *
     * 2.分离了之后，我们就要想办法把它们再添加回去了，所以需要通过addView方法来添加，那这些View在哪里得到呢？
     * 我们需要调用 Recycler的getViewForPosition(int position) 方法来获取；
     *
     * 3.获取到Item并重新添加了之后，我们还需要对它进行测量，这时候可以调用measureChild或measureChildWithMargins方法，
     * 两者的区别我们已经了解过了，相信同学们都能根据需求选择更合适的方法；
     *
     * 4.在测量完还需要做什么呢？ 没错，就是布局了，我们也是根据需求来决定使用layoutDecorated还是layoutDecoratedWithMargins方法；
     *
     * 5.在自定义ViewGroup中，layout完就可以运行看效果了，但在LayoutManager还有一件非常重要的事情，就是回收了，
     * 我们在layout之后，还要把一些不再需要的Items回收，以保证滑动的流畅度；
     */
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
    }

}