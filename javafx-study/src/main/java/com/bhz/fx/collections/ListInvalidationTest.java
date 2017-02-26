package com.bhz.fx.collections;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ListInvalidationTest {
	public static void main(String[] args) {
		ObservableList<String> list = FXCollections.<String>observableArrayList("one","two");
		list.addListener((Observable x)->{System.out.println("List is invalid");});
		
		System.out.println("Before adding three.");
		list.add("three");
		System.out.println("After adding three");
		
		System.out.println("Before adding four and five");
		list.addAll("four","five");
		System.out.println("After adding four and five");
		
	}
}
