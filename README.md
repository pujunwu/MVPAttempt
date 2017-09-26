<div class="span6 preview" style="max-height: 1280px; min-height: 1280px;"><h1 class="title mousetrap">MVP模式做的尝试</h1><div class="content mousetrap"><p>纯粹是个人学习总结，如有不对的地方请吐槽。<br>
目录结构</p>
<h4>app模块下的目录结构</h4>
<div class="image-package"><img src="http://upload-images.jianshu.io/upload_images/4986308-06c3a6a5e20ab500.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240"><br><div class="image-caption">image.png</div></div><br>
<p>app目录下放全局配置文件，包括Application<br>
base目录一眼就看清是什么<br>
di目录存放dagger有关的文件<br>
ui这个目录也很清楚</p>

<h4>mvplibrary模块下的目录</h4>
<div class="image-package"><img src="//upload-images.jianshu.io/upload_images/4986308-9d609f4d300f1a4f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" data-original-src="http://upload-images.jianshu.io/upload_images/4986308-9d609f4d300f1a4f.png?imageMogr2/auto-orient/strip" alt="image.png"><br><div class="image-caption">image.png</div></div><br>
<p>config是配置有关的文件<br>
delegate是监听Activity和Fragment生命周期的文件，这里就是不需要继承的关键代码<br>
di同样是dagger相关的文件，我这用的是dagger2<br>
mvp就是我们的mvp各个模块的基类以及接口<br>
screen是存放dp和sp设配文件的工具类，后面会将怎么使用<br>
sharedpre不是很形象，仔细看还是能看出是SharedPreferences相关类<br>
后面的目录就不介绍了</p>

<h4>下面就从如何使用说起</h4>
<p>先来看看library下的Activity基类</p>
<pre class="hljs undefined"><code>public abstract class LibBaseActivity extends AppCompatActivity implements IActivity, IView {

@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initInject();//执行注入
    initData();//初始化
}

/************************{@link IActivity 接口实现}************************/

@Override
public View getLayoutView() {
    return null;
}

@Override
public boolean eventBus() {
    return false;
}

@Override
public boolean fragment() {
    return false;
}

/************************{@link IView 接口实现}************************/

@Override
public void showLoading() {

}

@Override
public void hideLoading() {

}

@Override
public void showMessage(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
}

/************************其他方法************************/
/**
 * 获取AppComponent
 *
 * @return AppComponent
 */
protected AppComponent getAppComponent() {
    return AppDelegate.sAppDelegate.getAppComponent();
  }
}
</code></pre>
<p>关于IActivity接口我们先来看看delegate目录下的文件</p>
<br>
<div class="image-package"><img src="//upload-images.jianshu.io/upload_images/4986308-ed3fcf161780a02b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" data-original-src="http://upload-images.jianshu.io/upload_images/4986308-ed3fcf161780a02b.png?imageMogr2/auto-orient/strip" alt="image.png"><br><div class="image-caption">image.png</div></div><br>
<p>再来看看IActivity里面的方法</p>

<pre class="hljs undefined"><code>public interface IActivity {

/**
 * 获取当前布局Id
 *
 * @return
 */
int getLayoutId();

/**
 * 执行注入方法
 */
void initInject();

/**
 * 获取当前显示的布局
 *
 * @return
 */
View getLayoutView();

/**
 * 子类做初始化操作
 */
void initData();

/**
 * 是否有事件绑定
 *
 * @return
 */
boolean eventBus();

/**
 * 是否使用fragment
 *
 * @return
 */
boolean fragment();
}
</code></pre>
<p>eventBus这个方法用于判断是否需要事件监听，返回true就完成了事件注册和事件注销<br>
fragment这个方法用于判断是否需要监听fragment生命周期。</p>
<p>是不是觉得，看到这里还是不清楚到底是怎么回事，不用担心，这很正常，下面就来介绍如何不需要继承也能监听activity和Fragment的生命周期。</p>
<p>神奇之处就在于Application.ActivityLifecycleCallbacks和FragmentManager.FragmentLifecycleCallbacks对就是它们让我们脱离继承也能监听activity和Fragment的生命的周期，也许有很多小伙伴都不知道这个（我以前我也是其中之一），只是写着写着，就发现继承也存在不便（当你你已经继承了一个类，如果使用到其他第三方库，它也需要继承他的类，这时就JJ了）。</p>
<p>下面来看看监听的具体实现AppDelegate类，监听的关键代码</p>
<pre class="hljs undefined"><code>/**
 * 在application的onCreate方法中调用
 */
public void onCreate() {
    mActivityLifecycle = new ActivityLifecycle();
    mApplication.registerActivityLifecycleCallbacks(mActivityLifecycle);
    sAppDelegate = this;
}
</code></pre>
<p>第一行创建activity监听器，第二行设置监听器，fragment监听器后面介绍</p>
<p>然后在Application中创建并调用onCreate方法这样就完成了监听</p>
<pre class="hljs undefined"><code> mAppDelegate = new AppDelegate(sApp);
    //这个方法执行之后会监听每个activity和fragment的生命周期
    //建议在Application的onCreate方法里面调用
    mAppDelegate.onCreate();
</code></pre>
<p>ActivityLifecycleCallbacks具体有那些回调方法可以自行百度，这里就不介绍了。</p>
<p>到这里，关键部分已经介绍完了，其他的后面继续更新。</p></div></div>


https://jitpack.io/#pujunwu/MVPAttempt/v1.0.0

<h4>关于MVP部分</h4>
未完待续...