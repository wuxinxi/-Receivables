package com.wizarpos.drivertest.jniinterface;

public class PinpadInterface 
{
	public final static int KEY_TYPE_DUKPT = 0;
	public final static int KEY_TYPE_TDUKPT = 1;
	public final static int KEY_TYPE_MASTER = 2;
	public final static int KEY_TYPE_PUBLIC = 3;
	public final static int KEY_TYPE_FIX = 5;
	
	public final static int MAC_METHOD_X99 = 0;
	public final static int MAC_METHOD_ECB = 1;
	

	/**open the device
	 * @return value  >= 0, success in starting the process; value < 0, error code
	 * */
	public native static int PinpadOpen();
	/** close the device
	 * @return value  >= 0, success in starting the process; value < 0, error code
	 * */
	public native static int PinpadClose();
	/**
	 * @param	nLineIndex	line index, from top to down
	 * @param	arryText	encoded string
	 * @param	nTextLength	length of string
	 * @param	nFlagSound	0 : no voice prompt, 1 : voice prompt
	 * @return	value  >= 0, success in starting the process; value < 0, error code
	 * @see maybe, your display string is too long!when your happen error.
	 * */
	public native static int PinpadShowText(int nLineIndex, byte arryText[], int nTextLength, int nFlagSound);
	//TODO what's master key and user key
		/**@param	nKeyType		:select master key and user key
		 * @param	nMasterKeyID 	:master key ID , [0x00, ..., 0x09], make sense only when nKeyType is master-session pair,
		 * @param	nUserKeyID	 :user key ID, [0x00, 0x01], make sense only when nKeyType is master-session pair,
		 * @param	nAlgorith	1 : 3DES,
		 * 						0 : DES
		 * @return	value  >= 0, success in starting the process; value < 0, error code
		 * */
	public native static int PinpadSelectKey(int nKeyType, int nMasterKeyID, int nUserKeyID, int nAlgorith);
	//TODO what is "calculate pin block"
		/**
		 * encrypt string using user key
		 * @param	arryPlainText :arryPlainText plain text
		 * @param 	nTextLength   :length of plain text
		 * @param	arryCipherTextBuffer	:buffer for saving cipher text
		 * @return  value  >= 0, success in starting the process; value < 0, error code
		 * */
	public native static int PinpadEncryptString(byte arryPlainText[], int nTextLength, byte arryCipherTextBuffer[]);
	/**calculate pin block
	 * @param	arryASCIICardNumber	:card number in ASCII format
	 * @param	nCardNumberLength	:length of card number
	 * @param	arryPinBlockBuffer	:buffer for saving pin block
	 * @param	nTimeout_MS	timeout :waiting for user input in milliseconds, if it is less than zero, then wait forever
	 * @param	nFlagSound	0 : no voice prompt, 1 : voice prompt
	 * @return  value  >= 0, success in starting the process; value < 0, error code
	 * */
	public native static int PinpadCalculatePinBlock(byte arryASCIICardNumber[], int nCardNumberLength, byte arryPinBlockBuffer[], int nTimeout_MS, int nFlagSound);
	//TODO WHAT'S calculate the MAC 
		/**calculate the MAC using current user key
		 * @param	arryData :data
		 * @param	nDataLength	:data length
		 * @param	nMACFlag	:	0: X99, 
		 * 							1 : ECB
		 * @param	arryMACOutBuffer	:MAC data buffer
		 * @return  value  >= 0, success in starting the process; value < 0, error code
		 * */
	public native static int PinpadCalculateMac(byte arryData[], int nDataLength, int nMACFlag, byte arryMACOutBuffer[]);
	//TODO what's update the user key?
	/**update the user key
	 * @param	nMasterKeyID	: master key id
	 * @param	nUserKeyID		: user key id
	 * @param	arryCipherNewUserKey	: new user key in cipher text
	 * @param	nCipherNewUserKeyLength	: length of new user key in cipher text
	 * @return  value  >= 0, success in starting the process; value < 0, error code
	 * */
	public native static int PinpadUpdateUserKey(int nMasterKeyID, int nUserKeyID, byte arryCipherNewUserKey[], int nCipherNewUserKeyLength);
	//TODO  who this method is working for.<?> pinpad's text
	/** set the max length of pin
	 * @param	nLength	: length >= 0 && length <= 0x0D
	 * @param	nFlag	: 1, max length,
	 * 						  0, min length.
	 * @return  value  >= 0, success in starting the process; value < 0, error code
	 * */
	public native static int PinpadSetPinLength(int nLength, int nFlag);
	
	public native static int PinpadTest();
}
