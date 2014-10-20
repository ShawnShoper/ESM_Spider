package org.shoper.spider.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;

import org.apache.log4j.Logger;

public class MainStage extends Application {
	private static Logger logger = Logger.getLogger(MainStage.class);

	@Override
	public void start(Stage stage) throws Exception {
		stage.show();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
	}
}
