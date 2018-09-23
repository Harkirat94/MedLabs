package com.iiitd.medlabs;

public interface MyEventListener {
	public void onEventCompleted();
	public void onEvent2Completed();
	public void onEvent3Completed(int myVersion, int dbVersion);
    public void onEventFailed();
}

