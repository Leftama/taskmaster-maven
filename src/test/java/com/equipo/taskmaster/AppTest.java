package com.equipo.taskmaster;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AppTest {
	 @Test
	 public void testAddTask() {
 		App.tasks.clear();
 		App.addTask("Terminar ejercicio Maven");
 		assertEquals(1, App.tasks.size());
 	}
}