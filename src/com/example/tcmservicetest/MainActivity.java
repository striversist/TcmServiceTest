package com.example.tcmservicetest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import qrom.component.statistic.basic.aidl.IQRomStatSysService;
import qrom.component.wup.aidl.IQRomWupService;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.ITestService;
import android.qrom.tcm.IQRomTCMService;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

    public static final String TAG = "tcmstub";
    private Handler mUiHandler = new Handler();
    private TextView mLoggerFlowTextView;
    private ScrollView mLoggerScrollView; 
    
    private int mFlowIndex = 0;
    private SpannableStringBuilder mFlowStringBuilder = new SpannableStringBuilder();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLoggerFlowTextView = (TextView) findViewById(R.id.logger_flow_tv);
        mLoggerScrollView = (ScrollView) findViewById(R.id.logger_sv);
    }
    
    public void testGetContextService(View view) {
        addFlow("testGetContextService Start");
        IBinder binder = null;
        try {
            // 获取qrom系统的wup service
            binder = (IBinder) getSystemService("qrom_framework_wup");
            if (binder == null) {
                addFlow("qrom_framework_wup binder is null", Color.RED);
                return;
            } else {
                addFlow("qrom_framework_wup binder OK", Color.GREEN);
            }
            
            IQRomWupService qRomWupService = IQRomWupService.Stub.asInterface(binder);
            if (qRomWupService == null) {
                addFlow("get IQRomWupService is null", Color.RED);
            } else {
                addFlow("get IQRomWupService OK", Color.GREEN);
            }
            
            // 获取qrom系统的tcm service
            IQRomTCMService qRomTcmService = (IQRomTCMService) getSystemService("qrom_framework_tcm");
            if (qRomTcmService == null) {
                addFlow("get IQRomTCMService is null", Color.RED);
                return;
            } else {
                addFlow("get IQRomTCMService OK", Color.GREEN);
            }
            
            // 获取qrom系统的stat service
            binder = (IBinder) getSystemService("qrom_framework_stat");
            if (binder == null) {
                addFlow("qrom_framework_stat binder is null", Color.RED);
                return;
            } else {
                addFlow("qrom_framework_stat binder OK", Color.GREEN);
            }
            
            IQRomStatSysService qRomStatSysService = IQRomStatSysService.Stub.asInterface(binder);
            if (qRomStatSysService == null) {
                addFlow("get IQRomStatSysService is null", Color.RED);
            } else {
                addFlow("get IQRomStatSysService OK", Color.GREEN);
            }
        } catch (Exception e) {
            Log.e(TAG, "getQRomBinderServiceForSdk -> err msg = " + e.getMessage());
            addFlow("get Exception: " + e.getMessage(), Color.RED);
        } finally {
            addFlow("testGetContextService End");
        }
    }
    
    public void testGetTestService(View view) {
        addFlow("testGetTestService Start");
        try {
            Class<?> managerClass = Class.forName("android.os.ServiceManager");
            Method getServiceMethod = managerClass.getMethod("getService", new Class[] {String.class});
            final IBinder binder = (IBinder) getServiceMethod.invoke(null, "Test");
            if (binder == null) {
                addFlow("getService(Test) fail", Color.RED);
                return;
            } else {
                addFlow("getService(Test) OK", Color.GREEN);
            }
            
            binder.linkToDeath(new DeathRecipient() {
                @Override
                public void binderDied() {
                    Log.d(TAG, "death: Test died");
                    mUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            addFlow("death: TestService died", Color.YELLOW);
                        }
                    });
                }
            }, 0);
            
            ITestService om = ITestService.Stub.asInterface(binder);
            if (om == null) {
                addFlow("get ITestService fail", Color.RED);
                return;
            }
            
            addFlow("Going to call service");
            om.setValue(20);
            addFlow("Service called succesfully", Color.GREEN);
            
            addFlow("Prepare crash begin");
            om.writeDb(20);
            addFlow("Prepare crash end");
        } catch (IllegalAccessException e) {
            addFlow("Exception: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            addFlow("Exception: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            addFlow("Exception: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            addFlow("Exception: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            addFlow("Exception: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        } catch (Exception e) {
            addFlow("Exception: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        } finally {
            addFlow("testGetTestService End");
        }
    }
    
    public void testTcmDaemonService(View view) {
        addFlow("testTcmDaemonService Start");
        try {
            Class<?> managerClass = Class.forName("android.os.ServiceManager");
            Method getServiceMethod = managerClass.getMethod("getService", new Class[] {String.class});
            
            IBinder tcmBinder = (IBinder) getServiceMethod.invoke(null, "TcmDaemon");
            if (tcmBinder == null) {
                addFlow("getService(TcmDaemon) fail", Color.RED);
                return;
            } else {
                addFlow("getService(TcmDaemon) OK", Color.GREEN);
            }
            
            tcmBinder.linkToDeath(new DeathRecipient() {
                @Override
                public void binderDied() {
                    addFlow("death: TcmDaemon Service died", Color.YELLOW);
//                    android.os.Process.killProcess(Process.myPid());
                }
            }, 0);
        } catch (IllegalAccessException e) {
            addFlow("Exception: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            addFlow("Exception: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            addFlow("Exception: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            addFlow("Exception: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            addFlow("Exception: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        } catch (Exception e) {
            addFlow("Exception: " + e.getMessage(), Color.RED);
            e.printStackTrace();
        } finally {
            addFlow("testTcmDaemonService End");
        }
    }

    private void addFlow(String flow) {
        addFlow(flow, Color.WHITE);
    }
    
    private void addFlow(String flow, int color) {
        String rowText = String.valueOf(++mFlowIndex) + ". " + flow;
        SpannableString ss = new SpannableString(rowText);
        ss.setSpan(new ForegroundColorSpan(color), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        mFlowStringBuilder.append(ss);
        mFlowStringBuilder.append("\n");
        mUiHandler.post(new Runnable() {  
            @Override
            public void run() {
                mLoggerFlowTextView.setText(mFlowStringBuilder);
            }
        });
        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoggerScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 100);    // 待ScrollView中的内容全部显示出来，才会有效（否则无效果）。故延时一会儿
    }
}
