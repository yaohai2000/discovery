package com.bhz.fx.beans;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class BindTest {
	public static void main(String[] args) {
		StringProperty s1 = new SimpleStringProperty();
		StringProperty s2 = new SimpleStringProperty();
		StringProperty s = new SimpleStringProperty();
		s.addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable observable) {
				// TODO Auto-generated method stub
				
			}
		});
		s.addListener(( v, o, n) ->{
			System.out.println(v + " has changed from " + o + " to " + n);
		});
		s.bind(s1.concat(s2));
		s1.set("Hello ");
		s2.set("World!");
		System.out.println(s.get());
		s2.set("JavaFX!");
		
		System.out.println(s.get());
	}
}
