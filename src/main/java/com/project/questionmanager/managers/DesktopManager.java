package com.project.questionmanager.managers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.project.questionmanager.gui.util.Alerts;

import javafx.scene.control.Alert.AlertType;

public class DesktopManager {
	public static void openInBrowser(String path) {
		Configuration configuration = new Configuration();
		try {
			if(configuration.getSystemEnvironment().equals("linux")) {
				new ProcessBuilder("/bin/sh", "-c", "xdg-open ", "/home/manjaro/Área de trabalho/teste.html").start();
			} else {
			Desktop.getDesktop().browse(new File(path).toURI());
			}
		} catch (IOException e) {
			new Alerts().showAlert(AlertType.ERROR, "Erro", "IOException", e.getMessage(), false);
		}
	}

	public static void openManyInBrowser(File[] listHTML) {
		Configuration configuration = new Configuration();
		for(File x : listHTML) {
			try {
				if(configuration.getSystemEnvironment().equals("linux")) {
					Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", "xdg-open " + x.getAbsolutePath()});
				} else {
					Desktop.getDesktop().browse(x.toURI());
				}
			} catch (IOException e) {
				new Alerts().showAlert(AlertType.ERROR, "Erro", "IOException", e.getMessage(), false);
			}
		}
	}
}
