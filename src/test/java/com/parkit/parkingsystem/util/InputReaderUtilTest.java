package com.parkit.parkingsystem.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InputReaderUtilTest {
	private static final Logger logger = LogManager.getLogger("ParkingServiceTest");

	private static final InputStream DEFAULT_STDIN = System.in;
	
	@BeforeAll
	private static void beforeAll() {
		System.setIn(new ByteArrayInputStream("aaa\n1\n \nABC".getBytes()));
	}
	
	@BeforeEach
    private void setUpPerTest() {
	}
	
	@AfterEach
	private void reset() {
		System.setIn(DEFAULT_STDIN);
	}
	
	@Test
	public void readVehicleRegistrationNumberTest() throws Exception {
		System.setIn(new ByteArrayInputStream("ABC\n \n1\naaa".getBytes()));
		InputReaderUtil input = new InputReaderUtil();
		String result = input.readVehicleRegistrationNumber();
		assertEquals("ABC",result);
		
		
//		System.setIn(new ByteArrayInputStream(" ".getBytes()));
//		InputReaderUtil input = new InputReaderUtil();
		assertThrows(IllegalArgumentException.class, () -> input.readVehicleRegistrationNumber());
		
		int temp = input.readSelection();
		assertEquals(1,temp);
		
		
		temp = input.readSelection();
		assertEquals(-1,temp);
	}
	
//	@Test
	public void readVehicleRegistrationNumberExceptionTest() {
//		System.setIn(new ByteArrayInputStream(" ".getBytes()));
//		InputReaderUtil input = new InputReaderUtil();
//		assertThrows(IllegalArgumentException.class, () -> input.readVehicleRegistrationNumber());
	}
	
//	@Test
	public void readSelectionTest() {
//		System.setIn(new ByteArrayInputStream("1".getBytes()));
		InputReaderUtil input = new InputReaderUtil();
		int result = input.readSelection();
		assertEquals(1,result);
	}
	
//	@Test
	public void readSelectionExceptionTest() {
//		System.setIn(new ByteArrayInputStream("aaa".getBytes()));
		InputReaderUtil input = new InputReaderUtil();
		int result = input.readSelection();
		assertEquals(-1,result);
	}
	
	
}
