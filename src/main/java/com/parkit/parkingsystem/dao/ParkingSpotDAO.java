package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ParkingSpotDAO {
    private static final Logger logger = LogManager.getLogger("ParkingSpotDAO");

    public DataBaseConfig dataBaseConfig;

    public ParkingSpotDAO() {
    	dataBaseConfig = new DataBaseConfig();
	}

    public void setDataBaseConfig(DataBaseConfig config) {
    	this.dataBaseConfig = config;
    }
    
    public ParkingSpot getParkingSpot(int parkingNumber){
    	Connection con = null;
    	ParkingSpot parkingSpot = null;
    	PreparedStatement ps = null;
    	ResultSet rs = null;
    	try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.GET_PARKING_SPOT);
            ps.setInt(1,parkingNumber);
            rs = ps.executeQuery();
            if(rs.next()){
            	boolean isAvailable = rs.getBoolean(1);
            	logger.debug(isAvailable);
            	ParkingType type = ParkingType.valueOf(rs.getString(2));
            	logger.debug(type);
            	parkingSpot = new ParkingSpot(parkingNumber, type, isAvailable);
            }
    	}catch (Exception ex){
            logger.error("Error getting parkingspot",ex);
        }finally {
        	dataBaseConfig.closeResultSet(rs);
        	dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
        }
    	return parkingSpot;
    }
    
    public int getNextAvailableSlot(ParkingType parkingType){
        Connection con = null;
        int result=-1;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT);
            ps.setString(1, parkingType.toString());
            rs = ps.executeQuery();
            if(rs.next()){
                result = rs.getInt(1);
            }
        }catch (Exception ex){
            logger.error("Error fetching next available slot",ex);
        }finally {
        	dataBaseConfig.closeResultSet(rs);
        	dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
        }
        return result;
    }

    public boolean updateParking(ParkingSpot parkingSpot){
        //update the availability fo that parking slot
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = dataBaseConfig.getConnection();
            ps = con.prepareStatement(DBConstants.UPDATE_PARKING_SPOT);
            ps.setBoolean(1, parkingSpot.isAvailable());
            ps.setInt(2, parkingSpot.getId());
            int updateRowCount = ps.executeUpdate();
            return (updateRowCount == 1);
        }catch (Exception ex){
            logger.error("Error updating parking info",ex);
            return false;
        }finally {
        	dataBaseConfig.closePreparedStatement(ps);
            dataBaseConfig.closeConnection(con);
        }
    }

}
