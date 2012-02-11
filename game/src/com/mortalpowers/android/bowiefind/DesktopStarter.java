package com.mortalpowers.android.bowiefind;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class DesktopStarter {
public static void main(String[] args) {
	new JoglApplication(new Example(), "Desktop Application",800,480,false);
}
}
