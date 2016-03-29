package com.itme.ActivityClass;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.imte.utils.ClsUtils;

public class MainActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */
	private static BluetoothDevice remoteDevice=null;
	private Button btn_autopair=null;
	private Button btn=null;
	private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        btn_autopair=(Button)findViewById(R.id.btn_autopair);
        btn_autopair.setOnClickListener(this);
        btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(this);
    }
    
    public boolean pair(String strAddr, String strPsw)
	{
		boolean result = false;
		//取消发现当前设备的过程
		bluetoothAdapter.cancelDiscovery();
		if (!BluetoothAdapter.checkBluetoothAddress(strAddr))
		{ // 检查蓝牙地址是否有效
			Log.e("mylog", "devAdd un effient!");
		}
		if (!bluetoothAdapter.isEnabled())
		{
			bluetoothAdapter.enable();
			return false;
		}
		
		//由蓝牙设备地址获得另一蓝牙设备对象
		BluetoothDevice device = bluetoothAdapter.getRemoteDevice(strAddr.toUpperCase());
		if (device.getBondState() != BluetoothDevice.BOND_BONDED)
		{
			try
			{
				Log.e("mylog", "NOT BOND_BONDED");
				ClsUtils.createBond(device.getClass(), device);//发出了配对广播
				result = true;
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				Log.e("mylog", "setPiN failed!");
				e.printStackTrace();
			} //

		}
		else
		{
			Log.e("mylog", "HAS BOND_BONDED");
			try
			{
				remoteDevice = device; // 如果绑定成功，就直接把这个设备对象传给全局的remoteDevice
				result = true;
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				Log.e("mylog", "setPiN failed!");
				e.printStackTrace();
			}
		}
		return result;
	}
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_autopair:

			if (!bluetoothAdapter.isEnabled())
			{
				bluetoothAdapter.enable();//异步的，不会等待结果，直接返回。
			}else{
				bluetoothAdapter.startDiscovery();
			}
			break;

		case R.id.button1:
			
		ClsUtils.printAllInform(remoteDevice.getClass());
			
		default:
			break;
		}
	}
}