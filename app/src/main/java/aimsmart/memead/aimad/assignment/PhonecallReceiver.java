package aimsmart.memead.aimad.assignment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

public abstract class PhonecallReceiver extends BroadcastReceiver {

    private static int lastState = 0;
    private static Date callStartTime;
    private static boolean isIncoming;
    private static String savedNumber;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
                savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
        }
        else{
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            if (!intent.getExtras().containsKey(TelephonyManager.EXTRA_INCOMING_NUMBER)) {
                Log.i("Call receiver", "skipping intent=" + intent + ", extras=" + intent.getExtras() + " - no number was supplied");
                return;
            }
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            int state = 0;
            if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                state = TelephonyManager.CALL_STATE_IDLE;
            }
            else if(stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            }
            else if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                state = TelephonyManager.CALL_STATE_RINGING;
            }


            onCallStateChanged(context, state, number);
        }
    }

    protected abstract void onIncomingCallReceived(Context ctx, String number, Date start);
    //    protected abstract void onIncomingCallAnswered(Context ctx, String number, Date start);
//    protected abstract void onIncomingCallEnded(Context ctx, String number, Date start, Date end);
//
//    protected abstract void onOutgoingCallStarted(Context ctx, String number, Date start);
//    protected abstract void onOutgoingCallEnded(Context ctx, String number, Date start, Date end);
//
//    protected abstract void onMissedCall(Context ctx, String number, Date start);
    public void onCallStateChanged(Context context, int state, String number) {
        if(lastState == state){
            Log.i("TAG", "err");
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();
                savedNumber = number;
                onIncomingCallReceived(context, number, callStartTime);
                break;
        }
        lastState = state;
    }
}
