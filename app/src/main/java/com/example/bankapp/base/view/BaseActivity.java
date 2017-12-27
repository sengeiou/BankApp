package com.example.bankapp.base.view;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.example.bankapp.util.PromptManager;

import butterknife.ButterKnife;

/**
 * Activity的基类
 *
 * @author Guanluocang
 *         created at 2017/12/6 15:17
 */
public abstract class BaseActivity extends FragmentActivity {
    String TAG = this.getClass().getSimpleName();

    protected Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onViewCreateBefore();
        onSetContentView();
        ButterKnife.bind(this);
        onViewCreated();
        setOnListener();
    }

    /**
     * called by {@link # onCreate}
     * 在 setContentView 方法前调用
     */
    protected void onViewCreateBefore() {
    }

    /**
     * 初始化ContentView之后调用，可以进行findViewById等操作onViewInit
     */
    protected void onViewInit() {
    }

    /**
     * called by {@link # onCreate}
     * 在 setContentView 方法后调用
     */
    protected void onViewCreated() {
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * setContentView
     */
    protected void onSetContentView() {
        setContentView(getContentViewResource());
        onViewInit();
    }

    /**
     * called by {@link # onCreate}
     * 进行设置监听
     */
    protected void setOnListener() {
    }

    /**
     * 得到ContentView 的 Resource
     *
     * @return eg R.layout.main_activity
     */
    protected abstract int getContentViewResource();

    /**
     * 开启一个服务
     *
     * @param mService
     */
    public void startService(Service mService) {
        startService(mService.getClass());
    }

    /**
     * 开启一个服务
     *
     * @param clazz
     */
    public void startService(Class<? extends Service> clazz) {
        startService(new Intent(this, clazz));
    }

    /**
     * 启动一个Activity
     *
     * @param mActivity
     */
    public void startActivity(Activity mActivity) {
        startActivity(mActivity.getClass());
    }

    /**
     * 启动一个Activity
     *
     * @param clazz
     */
    public void startActivity(Class<? extends Activity> clazz) {
        startActivity(new Intent(this, clazz));
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 显示Toast
     *
     * @param toastId
     */
    private void showToast(int toastId) {
        PromptManager.showToast(getApplicationContext(), toastId);
    }

    /**
     * 显示Toast
     *
     * @param text
     */
    public void showToast(final String text) {
        PromptManager.showToast(getApplicationContext(), text);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(BaseActivity.this, text, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    /**
     * 切换Fragment
     *
     * @param layoutId 填充的布局
     * @param fragment
     */
    public void replaceFragment(int layoutId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(layoutId, fragment)
                .commitAllowingStateLoss();
    }

    /**
     * 返回键时间，Fragment中不会调用此方法
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}