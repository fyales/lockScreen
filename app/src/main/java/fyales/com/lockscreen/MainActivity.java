package fyales.com.lockscreen;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.*;
import android.os.Process;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
