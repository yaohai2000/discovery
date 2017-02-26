package com.bhz.fx.collections;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class SimpleListChangeTest {
	public static void main(String[] args) {
		ObservableList<String> list = FXCollections.<String>observableArrayList();
		list.addListener((ListChangeListener.Change<? extends String> change) ->{
			System.out.println("List has changed");
		});
		
		list.add("one");
		list.add("two");
		FXCollections.sort(list);
		list.clear();
	}
}
