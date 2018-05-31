package com.example.lenovo.myapplication;



import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity {
    private follow_fragment f_a;
    private discovery_fragment f_b;
    private nearby_fragment f_c;
    private mine_fragment f_d;
    private Fragment[] mFragments;
    private int mIndex;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();//方法一，默认第一fragment
        //changeFragment(new FragmentA().getFragmentA());//方法二，默认第一fragment
    }

    private void initView() {
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int arg1) {
                //遍历RadioGroup 里面所有的子控件。
                for (int index = 0; index < group.getChildCount(); index++) {
                    //获取到指定位置的RadioButton
                    RadioButton rb = (RadioButton)group.getChildAt(index);
                    //如果被选中
                    if (rb.isChecked()) {
                        setIndexSelected(index);
                        //setIndexSelectedTwo(index);  //方法二
                        break;
                    }
                }

            }
        });

    }
    //方法一，默认第一fragment
    private void initFragment() {
        f_a =new follow_fragment();
        f_b =new discovery_fragment();
        f_c =new nearby_fragment();
        f_d =new mine_fragment();
        //添加到数组
        mFragments = new Fragment[]{f_a,f_b,f_c,f_d};
        //开启事务
        FragmentManager    fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft= fragmentManager.beginTransaction();
        //添加首页
        ft.add(R.id.content,f_a).commit();
        //默认设置为第0个
        setIndexSelected(0);
    }
    //方法一，选中显示与隐藏
    private void setIndexSelected(int index) {

        if(mIndex==index){
            return;
        }
        FragmentManager    fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft= fragmentManager.beginTransaction();

        //隐藏
        ft.hide(mFragments[mIndex]);
        //判断是否添加
        if(!mFragments[index].isAdded()){
            ft.add(R.id.content,mFragments[index]).show(mFragments[index]);
        }else {
            ft.show(mFragments[index]);
        }

        ft.commit();
        //再次赋值
        mIndex=index;

    }
    //方法二，选中替换
    private void setIndexSelectedTwo(int index) {
        switch (index) {
            case 0:
                changeFragment(new follow_fragment().getFragmentA());
                break;
            case 1:
                changeFragment(new discovery_fragment().getFragmentB());
                break;
            case 2:
                changeFragment(new nearby_fragment().getFragmentC());
                break;
            case 3:
                changeFragment(new mine_fragment().getFragmentD());
                break;


            default:
                break;
        }
    }

    ////方法二，默认第一fragment
    private void changeFragment(Fragment fm) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(R.id.content, fm);
        transaction.commit();
    }

}

//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTabHost;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TabHost;
//
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class MainActivity extends FragmentActivity {
//
//    private FragmentTabHost myTabHost;
//    private Map<String,View> ViewMap;
//    private Map<String,Images> ImagesMap;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        /**
//         * 初始布局框架，设立点击函数
//         */
//        ViewMap=new HashMap<>();
//        ImagesMap=new HashMap<>();
//        initTabHost();
//        myTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String tabId) {
//                for (String mapid:ViewMap.keySet()){
//                    if (mapid.equals(tabId)){
//                        ImageView imageView=ViewMap.get(mapid).findViewById(R.id.tab_imgaes);
//                        imageView.setImageResource(ImagesMap.get(mapid).getSelected_imges());
//                    }else {
//                        ImageView imageView=ViewMap.get(mapid).findViewById(R.id.tab_imgaes);
//                        imageView.setImageResource(ImagesMap.get(mapid).getNormal_images());
//                    }
//                }
//            }
//        });
//
//
//
//    }
//
//    /**
//     * 用于做启动文件
//     */
//    private void initTabHost(){
//        myTabHost=findViewById(android.R.id.tabhost);
//        myTabHost.setup(this,getSupportFragmentManager(), android.R.id.tabhost);
//        addTabHost("tab1",R.drawable.icon_tab_main_select,
//                R.drawable.icon_tab_main_selected,follow_fragment.class);
//        addTabHost("tab2",R.drawable.icon_tab_discovery_select,
//                R.drawable.icon_tab_discovery_selected,discovery_fragment.class);
//        addTabHost("tab3",R.drawable.icon_menu_address,
//                R.drawable.icon_menu_address,nearby_fragment.class);
//        addTabHost("tab4",R.drawable.icon_tab_user_center_select,
//                R.drawable.icon_tab_user_center_selected,mine_fragment.class);
//    }
//
//    private void addTabHost(String id, int normalDrawable,int selectedDrawable,Class<?> fragment){
//        View view=setTablayout(normalDrawable);
//        TabHost.TabSpec tabSpec=myTabHost.newTabSpec(id).setIndicator(view);
//        myTabHost.addTab(tabSpec,fragment,null);
//        Images images=new Images();
//        images.setNormal_images(normalDrawable);
//        images.setSelected_imges(selectedDrawable);
//        ViewMap.put(id,view);
//        ImagesMap.put(id,images);
//
//
//    }
//
//    /**
//     * 将图片绘制到tab布局文件中
//     * @param drawable 图片
//     * @return 绘制好的视图
//     */
//    private View setTablayout(int drawable) {
//        View view=getLayoutInflater().inflate(R.layout.fragmeng_tab,null);
//        ImageView imageView=view.findViewById(R.id.tab_imgaes);
//        imageView.setImageResource(drawable);
//        return view;
//    }
//
//}
//


//import android.app.Activity;
//import android.support.v4.app.FragmentTabHost;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class MainActivity extends Activity implements ViewPager.OnPageChangeListener {
//
//
////轮播图
//    private ViewPager viewPager;
//    private int[] imageResIds;
//    private ArrayList<ImageView> imageViewList;
//    private LinearLayout ll_point_container;
//    private String[] contentDescs;
//    private TextView tv_desc;
//    private int previousSelectedPosition = 0;
//    boolean isRunning = false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_discovery);
//
//        // 初始化布局 View视图
//        initViews();
//
//        // Model数据
//        initData();
//
//        // Controller 控制器
//        initAdapter();
//
//        // 开启轮询
//        new Thread() {
//            public void run() {
//                isRunning = true;
//                while (isRunning) {
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    // 往下跳一位
//                    runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            System.out.println("设置当前位置: " + viewPager.getCurrentItem());
//                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
//                        }
//                    });
//                }
//            }
//
//            ;
//        }.start();
//
//        // 准备数据源
//        List<Map<String, Object>> dataSource = new ArrayList<>();
//        Map<String, Object> itemData1 = new HashMap<>();
//        // 定义第1个数据项
//        itemData1.put("header", R.drawable.wanda);
//        itemData1.put("name", "万达");
//        dataSource.add(itemData1);
//
//        itemData1.put("header", R.drawable.wanda);
//        itemData1.put("name", "万达");
//        dataSource.add(itemData1);
//
//        itemData1.put("header", R.drawable.wanda);
//        itemData1.put("name", "万达");
//        dataSource.add(itemData1);
//
//        ListView lv_discovery_list = findViewById(R.id.lv_discovery_list);     // 获取ListView控件
//        DiscoveryAdapter customAdapter = new DiscoveryAdapter(// 创建Adapter对象
//                this,       // 上下文环境
//                dataSource, // 数据源
//                R.layout.discovery_list // 列表项布局文件
//        );
//        lv_discovery_list.setAdapter(customAdapter); // 给ListView控件设置适配器
//        // 给ListView的列表项注册点击事件监听器
//        lv_discovery_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                Toast.makeText(getApplicationContext(),
//                        parent.getAdapter().getItem(position).toString(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        isRunning = false;
//    }
//
//    private void initViews() {
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
//        viewPager.setOnPageChangeListener(this);// 设置页面更新监听
////      viewPager.setOffscreenPageLimit(1);// 左右各保留几个对象
//        ll_point_container = (LinearLayout) findViewById(R.id.ll_point_container);
//
//        tv_desc = (TextView) findViewById(R.id.tv_desc);
//
//    }
//
//    /**
//     * 初始化要显示的数据
//     */
//    private void initData() {
//        // 图片资源id数组
//        imageResIds = new int[]{R.drawable.wanda, R.drawable.wanda ,
//                R.drawable.wanda , R.drawable.wanda , R.drawable.wanda};
//
//        // 文本描述
//        contentDescs = new String[]{
//                "完达欢迎您！",
//                "完达欢迎您！",
//                "完达欢迎您！",
//                "完达欢迎您！",
//                "完达欢迎您！"
//        };
//
//        // 初始化要展示的5个ImageView
//        imageViewList = new ArrayList<ImageView>();
//
//        ImageView imageView;
//        View pointView;
//        LinearLayout.LayoutParams layoutParams;
//        for (int i = 0; i < imageResIds.length; i++) {
//            // 初始化要显示的图片对象
//            imageView = new ImageView(this);
//            imageView.setBackgroundResource(imageResIds[i]);
//            imageViewList.add(imageView);
//
//            // 加小白点, 指示器
//            pointView = new View(this);
//            pointView.setBackgroundResource(R.drawable.selector_bg_point);
//            layoutParams = new LinearLayout.LayoutParams(5, 5);
//            if (i != 0)
//                layoutParams.leftMargin = 10;
//            // 设置默认所有都不可用
//            pointView.setEnabled(false);
//            ll_point_container.addView(pointView, layoutParams);
//        }
//    }
//
//    private void initAdapter() {
//        ll_point_container.getChildAt(0).setEnabled(true);
//        tv_desc.setText(contentDescs[0]);
//        previousSelectedPosition = 0;
//
//        // 设置适配器
//        viewPager.setAdapter(new MyAdapter());
//
//        // 默认设置到中间的某个位置
//        int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
//        // 2147483647 / 2 = 1073741823 - (1073741823 % 5)
//        viewPager.setCurrentItem(5000000); // 设置到某个位置
//    }
//
//    class MyAdapter extends PagerAdapter {
//
//        @Override
//        public int getCount() {
//            return Integer.MAX_VALUE;
//        }
//
//        // 3. 指定复用的判断逻辑, 固定写法
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
////          System.out.println("isViewFromObject: "+(view == object));
//            // 当划到新的条目, 又返回来, view是否可以被复用.
//            // 返回判断规则
//            return view == object;
//        }
//
//        // 1. 返回要显示的条目内容, 创建条目
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            System.out.println("instantiateItem初始化: " + position);
//            // container: 容器: ViewPager
//            // position: 当前要显示条目的位置 0 -> 4
//
////          newPosition = position % 5
//            int newPosition = position % imageViewList.size();
//
//            ImageView imageView = imageViewList.get(newPosition);
//            // a. 把View对象添加到container中
//            container.addView(imageView);
//            // b. 把View对象返回给框架, 适配器
//            return imageView; // 必须重写, 否则报异常
//        }
//
//        // 2. 销毁条目
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            // object 要销毁的对象
//            System.out.println("destroyItem销毁: " + position);
//            container.removeView((View) object);
//        }
//    }
//
//    @Override
//    public void onPageScrolled(int position, float positionOffset,
//                               int positionOffsetPixels) {
//        // 滚动时调用
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//        // 新的条目被选中时调用
//        System.out.println("onPageSelected: " + position);
//        int newPosition = position % imageViewList.size();
//
//        //设置文本
//        tv_desc.setText(contentDescs[newPosition]);
//
////      for (int i = 0; i < ll_point_container.getChildCount(); i++) {
////          View childAt = ll_point_container.getChildAt(position);
////          childAt.setEnabled(position == i);
////      }
//        // 把之前的禁用, 把最新的启用, 更新指示器
//        ll_point_container.getChildAt(previousSelectedPosition).setEnabled(false);
//        ll_point_container.getChildAt(newPosition).setEnabled(true);
//
//        // 记录之前的位置
//        previousSelectedPosition = newPosition;
//
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }
//}