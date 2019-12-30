package doggyzhang.com.detectemulator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetectActivity extends AppCompatActivity
{

    private TextView textTitle;
    private TextView textView;
    private Button   btnDetect2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);

        textTitle = (TextView) findViewById(R.id.textTitle1);
        textView = (TextView) findViewById(R.id.textView1);
        btnDetect2 = (Button) findViewById(R.id.button2);

        btnDetect2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                detectEmulatorSimple();
            }
        });
        // 为确保准确性 detect() 是通过多维度的检测方法进行检测的
        // 考虑性能可以使用 detectSimple() 方法 经测试效果一致
        detectEmulatorSimple();
    }

    // 全面检测
    private void detectEmulator()
    {
        EmulatorDetector.with(this)
                .setDebug(false)
                .setCheckQumeProps(false)  // 该属性控制的 getProps() 用到了反射 相对耗时 默认关闭
                .detect(new EmulatorDetector.OnEmulatorDetectorListener()
                {
                    @Override
                    public void onResult(final boolean isEmulator)
                    {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                if (isEmulator)
                                {
                                    textTitle.setText("This device is emulator");
                                    textView.setText(EmulatorDetector.getDeviceInfo());
                                } else
                                {
                                    textTitle.setText("This device is not emulator");
                                    textView.setText(EmulatorDetector.getDeviceInfo());
                                }
                            }
                        });
                    }
                });
    }
    // 简单检测
    private void detectEmulatorSimple()
    {
        EmulatorDetector.with(this)
                .detectSimple(new EmulatorDetector.OnEmulatorDetectorListener()
                {
                    @Override
                    public void onResult(final boolean isEmulator)
                    {
                        runOnUiThread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                if (isEmulator)
                                {
                                    textTitle.setText("This device is emulator");
                                    textView.setText(EmulatorDetector.getDeviceInfo());
                                } else
                                {
                                    textTitle.setText("This device is not emulator");
                                    textView.setText(EmulatorDetector.getDeviceInfo());
                                }
                            }
                        });
                    }
                });
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        textTitle = null;
        textView = null;
        btnDetect2 = null;
    }
}
