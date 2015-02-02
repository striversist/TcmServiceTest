/*
* aidl file : frameworks/base/core/java/android/os/ITestService.aidl
* This file contains definitions of functions which are exposed by service
*/
package android.os;
interface ITestService {
/**
* {@hide}
*/
	void setValue(int val);
	void setMsg(String msg);
	String getMsg(String msg);
	void writeDb(int value);
	int getDb();
}