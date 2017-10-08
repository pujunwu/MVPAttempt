
        <div data-note-content="" class="show-content">
          <p>纯粹是个人学习总结，如有不对的地方请吐槽。<br>
目录结构</p>
<h4>app模块下的目录结构</h4>
<div class="image-package">
<img src="//upload-images.jianshu.io/upload_images/4986308-06c3a6a5e20ab500.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" data-original-src="http://upload-images.jianshu.io/upload_images/4986308-06c3a6a5e20ab500.png?imageMogr2/auto-orient/strip" alt="image.png" style="cursor: zoom-in;"><br><div class="image-caption">image.png</div>
</div><br>
<p>app目录下放全局配置文件，包括Application<br>
base目录一眼就看清是什么<br>
di目录存放dagger有关的文件<br>
ui这个目录也很清楚</p>

<h4>mvplibrary模块下的目录</h4>
<div class="image-package">
<img src="//upload-images.jianshu.io/upload_images/4986308-9d609f4d300f1a4f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" data-original-src="http://upload-images.jianshu.io/upload_images/4986308-9d609f4d300f1a4f.png?imageMogr2/auto-orient/strip" alt="image.png" style="cursor: zoom-in;"><br><div class="image-caption">image.png</div>
</div><br>
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
<div class="image-package">
<img src="//upload-images.jianshu.io/upload_images/4986308-ed3fcf161780a02b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" data-original-src="http://upload-images.jianshu.io/upload_images/4986308-ed3fcf161780a02b.png?imageMogr2/auto-orient/strip" alt="image.png" style="cursor: zoom-in;"><br><div class="image-caption">image.png</div>
</div><br>
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
<p>上面的没有看懂也没关系，只需要看懂下面的如何调用就可以了<br>
总结一下如何调用：</p>
<pre class="hljs undefined"><code>public class App extends Application {
        private AppDelegate mAppDelegate;
        //当前app实例对象
        public static App sApp;
        //全局上下文对象
        public static Context sContext;
    
        @Override
        public void onCreate() {
            super.onCreate();
            sApp = this;
            sContext = this;
            mAppDelegate = new AppDelegate(sApp);
            //这个方法执行之后会监听每个activity和fragment的生命周期
            //建议在Application的onCreate方法里面调用
            mAppDelegate.onCreate();
            initInject();//初始化全局注入
        }
    
        /**
         * 初始化全局注入
         */
        public void initInject() {
            /**初始化注入,这个方法调用了之后就会调用{@link com.junwu.mvplibrary.config.IConfigModule#applyOptions(ConfigModule.Builder)}方法配置参数，
             * 接着就会调用{@link com.junwu.mvplibrary.config.IRegisterApiModule#registerComponents(IRepositoryManager)}方法，设置api接口
             * 可以在测试接口确定了之后在调用该方法，比如：测试阶段需要选择测试服务器地址，选择之后再调用这个方法
             **/
            mAppDelegate.injectRegApiService(new AppConfigModule(), new RegisterApiModule());
        }
 }
关键代码就三句话：
mAppDelegate = new AppDelegate(sApp);
mAppDelegate.onCreate();
mAppDelegate.injectRegApiService(new AppConfigModule(), new RegisterApiModule());
前面两句很好理解，最后一句就是设置OkHttp、Retrofit、RxCache的配置类和关于Retrofit、RxCache的api接口service类的配置
</code></pre>
<p>到这里，Activity和Fragment的生命周期监听部分和控件的初始化和事件注册，已经介绍完了，下面继续看关于MVP部分。</p>
<p>我们在写Activity和Fragment的时候一般都有base类，这里有两种选择，一就是自己写base类但是必须要实现IActivity和IFragment接口就行，也可以直接继承<a href="https://github.com/pujunwu/MVPAttempt/blob/master/mvplibrary/src/main/java/com/junwu/mvplibrary/ui/activity/LibBaseActivity.java" target="_blank">LibBaseActivity</a>和<a href="https://github.com/pujunwu/MVPAttempt/blob/master/mvplibrary/src/main/java/com/junwu/mvplibrary/ui/fragment/LibBaseFragment.java" target="_blank">LibBaseFragment</a>，这里面实现也比较简单，就是实现了<a href="https://github.com/pujunwu/MVPAttempt/blob/master/mvplibrary/src/main/java/com/junwu/mvplibrary/delegate/IActivity.java" target="_blank">IActivity</a>和<a href="https://github.com/pujunwu/MVPAttempt/blob/master/mvplibrary/src/main/java/com/junwu/mvplibrary/delegate/IFragment.java" target="_blank">IFragment</a>两个接口。</p>
<p>再来看看dagger的配置，如果对dagger不熟悉的就自行查阅相关文档<br>
这篇关于MVP模式对dagger的要求不高，只需要知道基本概念即可，如果实在不想看dagger的直接照搬也是可以的。<br>
dagger相关的文件Component、Module、Scope以及注解Inject<br>
<a href="https://github.com/pujunwu/MVPAttempt/blob/master/mvplibrary/src/main/java/com/junwu/mvplibrary/di/component/AppComponent.java" target="_blank">AppComponent</a>这个就是让Inject和Module产生关系的类<br>
Module类有：<a href="https://github.com/pujunwu/MVPAttempt/blob/master/mvplibrary/src/main/java/com/junwu/mvplibrary/di/module/AppModule.java" target="_blank">AppModule</a>配置Model的，关于更多<a href="https://github.com/pujunwu/MVPAttempt/tree/master/app/src/main/java/com/junwu/mvpattempt/di/module" target="_blank">Module</a></p>
<p>举个例子来看看，可能会更清楚</p>
<pre class="hljs undefined"><code>@ViewScope
@Component(modules = {ViewModule.class, ModelModule.class, UtilsModule.class}, dependencies = AppComponent.class)
public interface IViewComponent {
    /*****************************activity注入***************************/
//    void inject(StartActivity activity);
    /*****************************Fragment注入***************************/
    void inject(StartFragment fragment);
    void inject(HomeFragment fragment);
    /*****************************其他注入***************************/
}
</code></pre>
<p>从这里不难看出这个Component依赖AppComponent、ViewModule、ModelModule、UtilsModule</p>
<p>AppComponent在mvpLibary模块下：</p>
<pre class="hljs undefined"><code>@Singleton
@Component(modules = {AppModule.class, ClientHttpModule.class, ConfigModule.class})
public interface AppComponent {

    /**
     * 注入
     *
     * @param delegate AppDelegate
     */
    void inject(AppDelegate delegate);

    /**
     * 获取当前application对象
     *
     * @return Application
     */
    Application getApplication();

    /**
     * 获取OkHttpClient
     */
    OkHttpClient getOkHttp();

    /**
     * 获取Retrofit
     */
    Retrofit getRetrofit();

    /**
     * 获取RxCache
     */
    RxCache getRxCache();

    /**
     * 获取RxCacheBuild对应的实体
     */
    RxCacheBuilderEntity getRxCacheBuilderEntity();

    /**
     * 获取IRepositoryManager
     */
    IRepositoryManager getIRepositoryManager();
}
</code></pre>
<p>这个就像是基础模块的获取类</p>
<p>ViewModule，这个必须同你写的代码是一个Module下</p>
<pre class="hljs undefined"><code>@Module
public class ViewModule {

    private IView mIView;
    private Activity mActivity;

    public ViewModule(IView view) {
        this(null, view);
    }

    public ViewModule(Activity activity, IView view) {
        mActivity = activity;
        this.mIView = view;
    }

    @ViewScope
    @Provides
    public Activity provideActivity() {
        return mActivity;
    }

    @ViewScope
    @Provides
    public IView provideIView() {
        return mIView;
    }
}
</code></pre>
<p>这里的IView和Presenter里面的IView以及Activity实现的IView有密切关系</p>
<p>ModelModule类的代码</p>
<pre class="hljs undefined"><code>@Module
public class ModelModule {

    @ViewScope
    @Provides
    StartContract.Model provideStartContractModel(StartModel startModel) {
        return startModel;
    }
}
</code></pre>
<p>UtilsModule就不介绍了，它是用于配置一些工具类的注入，为了思路清晰就单独分出来了</p>
<p>然后在看看LibBasePresenter</p>
<pre class="hljs undefined"><code>public class LibBasePresenter&lt;M extends IModel, V extends IView&gt; implements IPresenter {
    protected M mModel;
    protected V mView;
    //是否注册了eventBus事件
    private boolean isEventBus = false;

    /**
     * 处理IView的所有业务逻辑
     *
     * @param m model 数据来源，网络、文件、数据库等数据
     */
    public LibBasePresenter(IModel m) {
        this.mModel = (M) m;
        onStart();
    }

    /**
     * 处理IView的所有业务逻辑
     *
     * @param v IView的子类接口实现类
     */
    public LibBasePresenter(IView v) {
        this(null, v);
    }

    /**
     * 处理IView的所有业务逻辑
     *
     * @param m model 提供网络、文件、数据库等数据来源
     * @param v IView的子类接口实现类
     */
    public LibBasePresenter(IModel m, IView v) {
        if (m != null)
            this.mModel = (M) m;
        if (v != null)
            this.mView = (V) v;
        onStart();
    }

    @Override
    public void onStart() {
        if (useEventBus()) {
            registerEventBus();
        }
    }

    @Override
    public void onDestroy() {
        //解除订阅
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
        if (isEventBus) {
            EventBus.getDefault().unregister(this);
            isEventBus = false;
        }
        if (mModel != null) {
            mModel.onDestroy();
        }
        mModel = null;
        mView = null;
    }

    /**
     * 注册EventBus事件
     */
    protected void registerEventBus() {
        EventBus.getDefault().register(this);
        isEventBus = true;
    }
    /**
     * 是否注册事件
     *
     * @return
     */
    protected boolean useEventBus() {
        return false;
    }
}
</code></pre>
<p>请看构造函数Model和View都是可以为空的，这就可以根据业务来判断虚部需要Model模块，有的MVP是吧Model模块和Presenter是融合在一起的，对于一些业务不是很复杂的可以这么做，如果业务比较多还是建议将Model和Presenter区分开。</p>
<p>看在这里基本上应该已经看懂了，下面就来总结一下：<br>
1：IViewComponent将所有需要注入的类都用它来注入<br>
2：ModelModule所有的Model都有它来提供<br>
3：ViewModule里面将所有的Activity和Fragment都视为IView，在LibBasePresenter里面会自动转换为对应的子类，减少了IView的注册<br>
4：还是要将代码自己梳理一遍，具体可以参照<a href="https://github.com/pujunwu/MVPAttempt" target="_blank">MVPAttempt</a><br>
这里打个小广告：关于MVPAttempt框架的应用这里有个<a href="http://p.gdown.baidu.com/09e616d9f9814eab5605a4cc3369709b8c5dce6a8c90f702b42fd2a49da6c0380310a1816d2e3dfc3d574bc9e7c6b90fd15623d31c5c9910244df9d1c97246a48ed1997cbd513dc25abdef5e538017ff6bc9ab2f364b761f12bb3b528bebef9d5da03c99896c8e7617c75b872dd21d987582ae7017e1e02f96928c39958ea7e70226be49638f1e33d55a3f7bab4512cb358721ca9ddde6a38c3d2839ae85d979b7b2d231f9bdc14fa83fa01a6651dc7d40cd75b122c619109a681c5d7d78a7073d1f6c731502b5067fac969dbaec514be1d051db1632f8664181f54dded32eb7949c771c11c70083daebacfb2c2e1f3af02852b6ab79e0d4713775e476464fcb1538926fee90cd00e8559705cd387ebf53238af34d3cd59a6003b474633f6506263b7ee2981aabb5f03a6bd6a187b16f9d735d7ea490a296088bd7a7210a70f3cfcb136ac47e342379e3d9c9764e9febb8420f96f1a4d67c" target="_blank">app</a>已经上线，有兴趣的可以下载下来看看<br>
所有的注入都由IViewComponent来完成，可能会太死板，如果有特殊注入就可以按照dagger完整的方式写一套注入来完成，这样也是可以的。</p>

        </div>
