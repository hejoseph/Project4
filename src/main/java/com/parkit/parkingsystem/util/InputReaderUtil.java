package com.parkit.parkingsystem.util;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InputReaderUtil {

    private static Scanner scan = new Scanner(System.in,"UTF-8");
    private static final Logger logger = LogManager.getLogger("InputReaderUtil");

    public int readSelection() {
        try {
            int input = Integer.parseInt(scan.nextLine());
            return input;
        }catch(Exception e){
            logger.error("Error while reading user input from Shell", e);
            System.out.println("Error reading input. Please enter valid number for proceeding further");
            return -1;
        }
    }
    
    public String readVehicleRegistrationNumber() throws Exception {
        try {
        	logger.debug("reading");
            String vehicleRegNumber= scan.nextLine();
            logger.debug("reading="+vehicleRegNumber);
            if(vehicleRegNumber == null || vehicleRegNumber.trim().length()==0) {
                throw new IllegalArgumentException("Invalid input provided");
            }
            logger.debug("no pb");
            return vehicleRegNumber;
        }catch(Exception e){
            logger.error("Error while reading user input from Shell", e);
            System.out.println("Error reading input. Please enter a valid string for vehicle registration number");
            throw e;
        }
    }


}
