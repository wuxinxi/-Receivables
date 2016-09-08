package com.wizarpos.drivertest.jniinterface;

public class PrinterInterface 
{
	/**
	 * probe the baudrate of current printer
	 * return value : 0 : low baudrate 9600
	 *                1 : high baudrate 115200
	 */
	public native static int PrinterProbe();
	
	/**
	 * query the status of printer
	 * return value : 
	 *                = 0 : no paper
	 *                != 0 : has paper
	 *                other value : RFU
	 */
	public native static int PrinterStatus();

	/**
	 * open the device
	 * @return value  >= 0, success in starting the process; value < 0, error code
	 * */
	public native static int PrinterOpen();
	/**
	 * close the device
	 * @return value  >= 0, success in starting the process; value < 0, error code
	 * */
	
	public native static int PrinterClose();
	/**
	 * prepare to print
	 * @return value  >= 0, success in starting the process; value < 0, error code
	 * */
	
	public native static int PrinterBegin();
	/** end to print
	 *  @return value  >= 0, success in starting the process; value < 0, error code
	 * */
	
	public native static int PrinterEnd();
	/**
	 * write the data to the device
	 * @param arryData : data or control command
	 * @param nDataLength : length of data or control command
	 * @return value  >= 0, success in starting the process; value < 0, error code
	 * */
	
	public native static int PrinterWrite(byte arryData[], int nDataLength);
}
