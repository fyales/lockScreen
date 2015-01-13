    package fyales.com.lockscreen;

    import android.app.Activity;
    import android.app.admin.DevicePolicyManager;
    import android.content.ComponentName;
    import android.content.Context;
    import android.content.Intent;
    import android.os.*;
    import android.os.Process;


    public class MainActivity extends Activity {
        private DevicePolicyManager devicePolicyManager;
        private ComponentName componentName;
        private final static int LOCK_SCREEN_CODE = 201;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
            componentName = new ComponentName(this,DeviceFyalesReceiver.class);
            lockScreen();
            android.os.Process.killProcess(Process.myPid());

        }

        /**
         * 实现系统锁屏
         */
        private void lockScreen(){
            if (devicePolicyManager.isAdminActive(componentName)){
                devicePolicyManager.lockNow();
            }else{
                 Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                 intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                 startActivityForResult(intent, LOCK_SCREEN_CODE);
            }

        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case LOCK_SCREEN_CODE:
                        devicePolicyManager.lockNow();
                        break;
                    default:
                        break;
                }

            }
        }
    }
