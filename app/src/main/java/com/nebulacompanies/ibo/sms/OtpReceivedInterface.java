package com.nebulacompanies.ibo.sms;

/**
 * Created on : May 21, 2019
 * Author     : AndroidWave
 */
public interface OtpReceivedInterface {
  void onOtpReceived(String otp);
  void onOtpTimeout();
}
