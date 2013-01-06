package test;

import javapns.Push;
import javapns.notification.PushedNotifications;

public class IPhonePushNotification {

	private static final String url = "gateway.sandbox.push.apple.com";
	
	public static void main(String[] args) throws Exception {
//		PushedNotifications notification = Push.alert("test test", "/Users/onur/Development/Workspace/PassNRun_v1/doc/CertificatesPrd.p12", "PassAndRun", true, "a0653e616322ef4183259a4917c91609f76766ad0889adbbdb32ddae9ad07c45");
		PushedNotifications notification = Push.alert("oral nerdesin?", "/Users/onur/Development/Workspace/PassNRun_v1/doc/CertificatesPrd.p12", "PassAndRun", true, "eb1c1b8287cceab70687d1af2c16cf4f8569247f6a7ec0103faf6a4efb01865c");
//		PushedNotifications notification = Push.alert("League fixture is drawn, check out you fixture", "/Users/onur/Development/Workspace/PassNRun_v1/doc/CertificatesPrd.p12", "PassAndRun", true, "5960c1467ed2add2288b74ee176c5385cabd195e372e3cf061d88b3bc5c9e1ce");
//		List<Device> inactiveDevices = Push.feedback("/Users/onur/Development/Workspace/PassNRun_v1/doc/CertificatesPrd.p12", "PassAndRun", true);
		System.out.println(notification);
	}
}
