package tensor.sbis.hwocalls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.telephony.TelephonyManager;

import android.view.ViewGroup;
import android.view.WindowManager;


public class CallReceiver extends BroadcastReceiver {
    private static boolean incomingCall = false;
    private static WindowManager windowManager;
    private static ViewGroup windowLayout;

    //Основной обработчик входящих intent
    @Override
    public void onReceive(Context context, Intent intent) {
        //Если сигнал поступил именно об изменении состояния вызова
        if (intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            //Тогда получаем тип состояния
            String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            //Входящий звонок
            if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                //Получаем номер звонящего
                String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                incomingCall = true;
            }
            //Телефон находится в режиме звонка (набор номера / разговор) - закрываем окно, что бы не мешать
            else if (phoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                if (incomingCall) {
                    incomingCall = false;
                }
            }
            //Телефон находится в ждущем режиме - это событие наступает по окончанию разговора
            //или в ситуации "отказался поднимать трубку и сбросил звонок"
            else if (phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                if (incomingCall) {
                    incomingCall = false;
                }
            }
        }
    }
}
