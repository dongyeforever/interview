package vip.lovek.interview.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * author： yuzhirui@douban.com
 * date: 2022-03-17 23:07
 */
public class NetworkUtil {

   public static boolean isConnected(Context context) {
      ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo ni = cm.getActiveNetworkInfo();
      return ni != null && ni.isConnectedOrConnecting();
   }
}
