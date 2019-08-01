package com.cic.pas.procotol.s7;

import com.cic.pas.common.util.DataType;
import com.cic.pas.common.util.Util;

public class S7Response {
    /**
     * byte 0
     * 版本号
     */
    private byte version = 3;
    /**
     * byte 1
     * 保留 写0x00
     */
    private byte resrved = 0;
    /**
     * byte 2 byte3
     * 包总长度
     */
    private byte[] totalLength = {0, 31};
    /**
     * byte 4
     * cotp包长度  一般为2
     */
    private byte cotplength = 2;
    /**
     * byte 5
     * PDU TYPE
     * 0x01: ED Expedited Data，加急数据
     * 0x02: EA Expedited Data Acknowledgement，加急数据确认
     * 0x04: UD，用户数据
     * 0x05: RJ Reject，拒绝
     * 0x06: AK Data Acknowledgement，数据确认
     * 0x07: ER TPDU Error，TPDU错误
     * 0x08: DR Disconnect Request，断开请求
     * 0x0C: DC Disconnect Confirm，断开确认
     * 0x0D: CC Connect Confirm，连接确认
     * 0x0E: CR Connect Request，连接请求
     * 0x0F: DT Data，数据传输
     */
    private byte cotpfunction = (byte) 0xF0;
    /**
     * byte 6
     * 暂时固定为 0x80
     */
    private byte opt = (byte) 0x80;
    /**
     * byte 7
     * 协议ID 一般为 0x32
     */
    private byte procotolId = (byte) 0x32;
    /**
     * byte 8
     * ROSCTR，PDU type，PDU的类型
     * 0x01 – JOB(Request： job with acknowledgement)：作业请求。由主设备发送的请求（例如，读/写存储器，读/写块，启动/停止设备，设置通信）；
     * 0x02 – ACK(acknowledgement without additional field)：确认响应，没有数据的简单确认（未遇到过由S7 300/400设备发送得）；
     * 0x03 – ACK_DATA(Response： acknowledgement with additional field)：确认数据响应，这个一般都是响应JOB的请求；
     * 0x07 – USERDATA：原始协议的扩展，参数字段包含请求/响应ID（用于编程/调试，读取SZL，安全功能，时间设置，循环读取…）
     */
    private byte s7commPduType = 1;
    /**
     * byte 9 byte 10
     * 两个字节
     * Redundancy Identification (Reserved)，冗余数据，通常为0x0000
     */
    private byte[] redundancyIdentification = new byte[]{0, 0};
    /**
     * byte 11 byte 12
     * 两个字节
     * Protocol Data Unit Reference，it’s increased by request event。协议数据单元参考，通过请求事件增加
     */
    private byte[] protocolDataUnitReference = {0, 1};
    /**
     * byte 13 byte 14
     * 两个字节
     * Parameter length，the total length (bytes) of parameter part。参数的总长度；
     */
    private byte[] paramsLength = {0, 14};
    /**
     * byte 15 byte 16
     * 两个字节
     * Data length，数据长度。如果读取PLC内部数据，此处为0x0000；对于其他功能，则为Data部分的数据长度；
     */
    private byte[] dataLength = {0, 0};
    /**
     * byte 17
     * 0x00 - No error
     * 0x81 - Application relationship error
     * 0x82 - Object definition error
     * 0x83 - No ressources available error
     * 0x84 - Error on service processing
     * 0x85 - Error on supplies
     * 0x87 - Access error
     */
    private byte errorClass;
    /**
     * byte 17
     * 0x00 - No error
     * 0x81 - Application relationship error
     * 0x82 - Object definition error
     * 0x83 - No ressources available error
     * 0x84 - Error on service processing
     * 0x85 - Error on supplies
     * 0x87 - Access error
     */
    private String errorClassStr;
    /**
     * byte 18
     */
    private byte errorCode;

    /**
     * byte 17
     * 0x00 - No error
     * 0x81 - Application relationship error
     * 0x82 - Object definition error
     * 0x83 - No ressources available error
     * 0x84 - Error on service processing
     * 0x85 - Error on supplies
     * 0x87 - Access error
     */
    public String getErrorClassStr() {
        return errorClassStr;
    }

    /**
     * byte 17
     * 0x00 - No error
     * 0x81 - Application relationship error
     * 0x82 - Object definition error
     * 0x83 - No ressources available error
     * 0x84 - Error on service processing
     * 0x85 - Error on supplies
     * 0x87 - Access error
     */
    public void setErrorClassStr(String errorClassStr) {
        this.errorClassStr = errorClassStr;
    }

    /**
     * byte 18
     */
    public String getErrorCodeStr() {
        return errorCodeStr;
    }

    /**
     * byte 18
     */
    public void setErrorCodeStr(String errorCodeStr) {
        this.errorCodeStr = errorCodeStr;
    }

    /**
     * byte 21
     * Return code，返回代码
     * 0x00 - Reserved
     * 0x01 - Hardware fault
     * 0x03 - Accessing the object not allowed
     * 0x05 - Address out of range
     * 0x06 - Data type not supported
     * 0x07 - Data type inconsistent
     * 0x0a - Object does not exist
     * 0xff - Success
     */
    public String getReturnCodeStr() {
        return returnCodeStr;
    }

    /**
     * byte 21
     * Return code，返回代码
     * 0x00 - Reserved
     * 0x01 - Hardware fault
     * 0x03 - Accessing the object not allowed
     * 0x05 - Address out of range
     * 0x06 - Data type not supported
     * 0x07 - Data type inconsistent
     * 0x0a - Object does not exist
     * 0xff - Success
     */
    public void setReturnCodeStr(String returnCodeStr) {
        this.returnCodeStr = returnCodeStr;
    }

    /**
     * byte 18
     */
    private String errorCodeStr;
    /**
     * byte 19
     * 命令 0x04 读值 0x05 写入  0xf0 建立通信的作业请求
     * 0x00 - CPU services
     * 0xF0 - Setup communication
     * 0x04 - Read Variable
     * 0x05 - Write Variable
     * 0x1A - Request download
     * 0x1B - Download block
     * 0x1C - Download ended
     * 0x1D - Start upload
     * 0x1E - Upload
     * 0x1F - End upload
     * 0x28 - PLC Control
     * 0x29 - PLC Stop
     */
    private byte function = 4;
    /**
     * byte 20
     * item count（项目个数），其类型为Unsigned integer，大小为1 byte
     */
    private byte itemCount = 1;
    /**
     * byte 21
     * Return code，返回代码
     * 0x00 - Reserved
     * 0x01 - Hardware fault
     * 0x03 - Accessing the object not allowed
     * 0x05 - Address out of range
     * 0x06 - Data type not supported
     * 0x07 - Data type inconsistent
     * 0x0a - Object does not exist
     * 0xff - Success
     */
    private byte returnCode = 16;
    /**
     * byte 21
     * Return code，返回代码
     * 0x00 - Reserved
     * 0x01 - Hardware fault
     * 0x03 - Accessing the object not allowed
     * 0x05 - Address out of range
     * 0x06 - Data type not supported
     * 0x07 - Data type inconsistent
     * 0x0a - Object does not exist
     * 0xff - Success
     */
    private String returnCodeStr;

    /**
     * byte 0
     * 版本号
     */
    public byte getVersion() {
        return version;
    }

    /**
     * byte 0
     * 版本号
     */
    public void setVersion(byte version) {
        this.version = version;
    }

    /**
     * byte 1
     * 保留 写0x00
     */
    public byte getResrved() {
        return resrved;
    }

    /**
     * byte 1
     * 保留 写0x00
     */
    public void setResrved(byte resrved) {
        this.resrved = resrved;
    }

    /**
     * byte 2 byte3
     * 包总长度
     */
    public byte[] getTotalLength() {
        return totalLength;
    }

    /**
     * byte 2 byte3
     * 包总长度
     */
    public void setTotalLength(byte[] totalLength) {
        this.totalLength = totalLength;
    }

    /**
     * byte 4
     * cotp包长度  一般为2
     */
    public byte getCotplength() {
        return cotplength;
    }

    /**
     * byte 4
     * cotp包长度  一般为2
     */
    public void setCotplength(byte cotplength) {
        this.cotplength = cotplength;
    }

    /**
     * byte 5
     * PDU TYPE
     * 0x01: ED Expedited Data，加急数据
     * 0x02: EA Expedited Data Acknowledgement，加急数据确认
     * 0x04: UD，用户数据
     * 0x05: RJ Reject，拒绝
     * 0x06: AK Data Acknowledgement，数据确认
     * 0x07: ER TPDU Error，TPDU错误
     * 0x08: DR Disconnect Request，断开请求
     * 0x0C: DC Disconnect Confirm，断开确认
     * 0x0D: CC Connect Confirm，连接确认
     * 0x0E: CR Connect Request，连接请求
     * 0x0F: DT Data，数据传输
     */
    public byte getCotpfunction() {
        return cotpfunction;
    }

    /**
     * byte 5
     * PDU TYPE
     * 0x01: ED Expedited Data，加急数据
     * 0x02: EA Expedited Data Acknowledgement，加急数据确认
     * 0x04: UD，用户数据
     * 0x05: RJ Reject，拒绝
     * 0x06: AK Data Acknowledgement，数据确认
     * 0x07: ER TPDU Error，TPDU错误
     * 0x08: DR Disconnect Request，断开请求
     * 0x0C: DC Disconnect Confirm，断开确认
     * 0x0D: CC Connect Confirm，连接确认
     * 0x0E: CR Connect Request，连接请求
     * 0x0F: DT Data，数据传输
     */
    public void setCotpfunction(byte cotpfunction) {
        this.cotpfunction = cotpfunction;
    }

    /**
     * byte 6
     * 暂时固定为 0x80
     */
    public byte getOpt() {
        return opt;
    }

    /**
     * byte 6
     * 暂时固定为 0x80
     */
    public void setOpt(byte opt) {
        this.opt = opt;
    }

    /**
     * byte 7
     * 协议ID 一般为 0x32
     */
    public byte getProcotolId() {
        return procotolId;
    }

    /**
     * byte 7
     * 协议ID 一般为 0x32
     */
    public void setProcotolId(byte procotolId) {
        this.procotolId = procotolId;
    }

    /**
     * byte 8
     * ROSCTR，PDU type，PDU的类型
     * 0x01 – JOB(Request： job with acknowledgement)：作业请求。由主设备发送的请求（例如，读/写存储器，读/写块，启动/停止设备，设置通信）；
     * 0x02 – ACK(acknowledgement without additional field)：确认响应，没有数据的简单确认（未遇到过由S7 300/400设备发送得）；
     * 0x03 – ACK_DATA(Response： acknowledgement with additional field)：确认数据响应，这个一般都是响应JOB的请求；
     * 0x07 – USERDATA：原始协议的扩展，参数字段包含请求/响应ID（用于编程/调试，读取SZL，安全功能，时间设置，循环读取…）
     */
    public byte getS7commPduType() {
        return s7commPduType;
    }

    /**
     * byte 8
     * ROSCTR，PDU type，PDU的类型
     * 0x01 – JOB(Request： job with acknowledgement)：作业请求。由主设备发送的请求（例如，读/写存储器，读/写块，启动/停止设备，设置通信）；
     * 0x02 – ACK(acknowledgement without additional field)：确认响应，没有数据的简单确认（未遇到过由S7 300/400设备发送得）；
     * 0x03 – ACK_DATA(Response： acknowledgement with additional field)：确认数据响应，这个一般都是响应JOB的请求；
     * 0x07 – USERDATA：原始协议的扩展，参数字段包含请求/响应ID（用于编程/调试，读取SZL，安全功能，时间设置，循环读取…）
     */
    public void setS7commPduType(byte s7commPduType) {
        this.s7commPduType = s7commPduType;
    }

    /**
     * byte 9 byte 10
     * 两个字节
     * Redundancy Identification (Reserved)，冗余数据，通常为0x0000
     */
    public byte[] getRedundancyIdentification() {
        return redundancyIdentification;
    }

    /**
     * byte 9 byte 10
     * 两个字节
     * Redundancy Identification (Reserved)，冗余数据，通常为0x0000
     */
    public void setRedundancyIdentification(byte[] redundancyIdentification) {
        this.redundancyIdentification = redundancyIdentification;
    }

    /**
     * byte 11 byte 12
     * 两个字节
     * Protocol Data Unit Reference，it’s increased by request event。协议数据单元参考，通过请求事件增加
     */
    public byte[] getProtocolDataUnitReference() {
        return protocolDataUnitReference;
    }

    /**
     * byte 11 byte 12
     * 两个字节
     * Protocol Data Unit Reference，it’s increased by request event。协议数据单元参考，通过请求事件增加
     */
    public void setProtocolDataUnitReference(byte[] protocolDataUnitReference) {
        this.protocolDataUnitReference = protocolDataUnitReference;
    }

    /**
     * byte 13 byte 14
     * 两个字节
     * Parameter length，the total length (bytes) of parameter part。参数的总长度；
     */
    public byte[] getParamsLength() {
        return paramsLength;
    }

    /**
     * byte 13 byte 14
     * 两个字节
     * Parameter length，the total length (bytes) of parameter part。参数的总长度；
     */
    public void setParamsLength(byte[] paramsLength) {
        this.paramsLength = paramsLength;
    }

    /**
     * byte 15 byte 16
     * 两个字节
     * Data length，数据长度。如果读取PLC内部数据，此处为0x0000；对于其他功能，则为Data部分的数据长度；
     */
    public byte[] getDataLength() {
        return dataLength;
    }

    /**
     * byte 15 byte 16
     * 两个字节
     * Data length，数据长度。如果读取PLC内部数据，此处为0x0000；对于其他功能，则为Data部分的数据长度；
     */
    public void setDataLength(byte[] dataLength) {
        this.dataLength = dataLength;
    }

    /**
     * byte 17
     * 命令 0x04 读值 0x05 写入  0xf0 建立通信的作业请求
     * 0x00 - CPU services
     * 0xF0 - Setup communication
     * 0x04 - Read Variable
     * 0x05 - Write Variable
     * 0x1A - Request download
     * 0x1B - Download block
     * 0x1C - Download ended
     * 0x1D - Start upload
     * 0x1E - Upload
     * 0x1F - End upload
     * 0x28 - PLC Control
     * 0x29 - PLC Stop
     */
    public byte getFunction() {
        return function;
    }

    /**
     * byte 17
     * 命令 0x04 读值 0x05 写入  0xf0 建立通信的作业请求
     * 0x00 - CPU services
     * 0xF0 - Setup communication
     * 0x04 - Read Variable
     * 0x05 - Write Variable
     * 0x1A - Request download
     * 0x1B - Download block
     * 0x1C - Download ended
     * 0x1D - Start upload
     * 0x1E - Upload
     * 0x1F - End upload
     * 0x28 - PLC Control
     * 0x29 - PLC Stop
     */
    public void setFunction(byte function) {
        this.function = function;
    }

    /**
     * byte 18
     * item count（项目个数），其类型为Unsigned integer，大小为1 byte
     */
    public byte getItemCount() {
        return itemCount;
    }

    /**
     * byte 18
     * item count（项目个数），其类型为Unsigned integer，大小为1 byte
     */
    public void setItemCount(byte itemCount) {
        this.itemCount = itemCount;
    }

    /**
     * byte 17
     * 0x00 - No error
     * 0x81 - Application relationship error
     * 0x82 - Object definition error
     * 0x83 - No ressources available error
     * 0x84 - Error on service processing
     * 0x85 - Error on supplies
     * 0x87 - Access error
     */
    public byte getErrorClass() {
        return errorClass;
    }

    /**
     * byte 17
     * 0x00 - No error
     * 0x81 - Application relationship error
     * 0x82 - Object definition error
     * 0x83 - No ressources available error
     * 0x84 - Error on service processing
     * 0x85 - Error on supplies
     * 0x87 - Access error
     */
    public void setErrorClass(byte errorClass) {
        this.errorClass = errorClass;
        switch (errorClass) {
            case (byte) 0x00:
                setErrorClassStr("无错误");
                break;
            case (byte) 0x81:
                setErrorClassStr("Application relationship error");
                break;
            case (byte) 0x82:
                setErrorClassStr("Object definition error");
                break;
            case (byte) 0x83:
                setErrorClassStr(" No ressources available error");
                break;
            case (byte) 0x84:
                setErrorClassStr("Error on service processing");
                break;
            case (byte) 0x85:
                setErrorClassStr("Error on supplies");
                break;
            case (byte) 0x87:
                setErrorClassStr("Access error");
                break;
        }
    }

    /**
     * byte 18
     */
    public byte getErrorCode() {
        return errorCode;
    }

    /**
     * byte 18
     */
    public void setErrorCode(byte errorCode) {
        this.errorCode = errorCode;
        switch (errorCode) {
            case (byte) 0x00:
                errorCodeStr = "无错误";
                break;
        }
    }

    /**
     * byte 21
     * Return code，返回代码
     * 0x00 - Reserved
     * 0x01 - Hardware fault
     * 0x03 - Accessing the object not allowed
     * 0x05 - Address out of range
     * 0x06 - Data type not supported
     * 0x07 - Data type inconsistent
     * 0x0a - Object does not exist
     * 0xff - Success
     */
    public byte getReturnCode() {
        return returnCode;
    }

    /**
     * byte 21
     * Return code，返回代码
     * 0x00 - Reserved
     * 0x01 - Hardware fault
     * 0x03 - Accessing the object not allowed
     * 0x05 - Address out of range
     * 0x06 - Data type not supported
     * 0x07 - Data type inconsistent
     * 0x0a - Object does not exist
     * 0xff - Success
     */
    public void setReturnCode(byte returnCode) {
        this.returnCode = returnCode;
        switch (returnCode) {
            case (byte) 0x01:
                this.returnCodeStr = "Hardware fault";
                break;
            case (byte) 0x03:
                this.returnCodeStr = "Accessing the object not allowed";
                break;
            case (byte) 0x05:
                this.returnCodeStr = " Address out of range";
                break;
            case (byte) 0x07:
                this.returnCodeStr = "Data type not supported";
                break;
            case (byte) 0x0a:
                this.returnCodeStr = "Object does not exist";
                break;
            case (byte) 0xff:
                this.returnCodeStr = "Success";
                break;
        }
    }

    /**
     * byte 22
     * Transport sizes in item data，Transport size，数据的传输尺寸：
     * 0x03 - BIT
     * 0x04 - BYTE\WORD\DWORD
     */
    private byte transportSize;
    /**
     * byte 23 byte 24
     * 两个字节
     * Request data length，接收的数据长度；
     */
    private byte[] responseDataLength = new byte[2];
    /**
     * byte25-byte25+responseDataLength-1
     * Data，数据；
     */
    private byte[] responseData;


    /**
     * byte 22
     * Transport sizes in item data，Transport size，数据的传输尺寸：
     * 0x03 - BIT
     * 0x04 - BYTE\WORD\DWORD
     */
    public byte getTransportSize() {
        return transportSize;
    }

    /**
     * byte 22
     * Transport sizes in item data，Transport size，数据的传输尺寸：
     * 0x03 - BIT
     * 0x04 - BYTE\WORD\DWORD
     */
    public void setTransportSize(byte transportSize) {
        this.transportSize = transportSize;
    }

    /**
     * byte 23 byte 24
     * 两个字节
     * Request data length，接收的数据长度；
     */
    public byte[] getResponseDataLength() {
        return responseDataLength;
    }

    /**
     * byte 23 byte 24
     * 两个字节
     * Request data length，接收的数据长度；
     */
    public void setResponseDataLength(byte[] responseDataLength) {
        this.responseDataLength = responseDataLength;
    }

    /**
     * byte25-byte25+responseDataLength-1
     * Data，数据；
     */
    public byte[] getResponseData() {
        return responseData;
    }

    /**
     * byte25-byte25+responseDataLength-1
     * Data，数据；
     */
    public void setResponseData(byte[] responseData) {
        this.responseData = responseData;
    }

    public void setResponseBytes(byte[] bytes) {
        version = bytes[0];
        resrved = bytes[1];
        totalLength[0] = bytes[2];
        totalLength[1] = bytes[3];
        cotplength = bytes[4];
        cotpfunction = bytes[5];
        opt = bytes[6];
        procotolId = bytes[7];
        s7commPduType = bytes[8];
        redundancyIdentification[0] = bytes[9];
        redundancyIdentification[1] = bytes[10];
        protocolDataUnitReference[0] = bytes[11];
        protocolDataUnitReference[1] = bytes[12];
        paramsLength[0] = bytes[13];
        paramsLength[1] = bytes[14];
        dataLength[0] = bytes[15];
        dataLength[1] = bytes[16];
        setErrorClass(bytes[17]);
        setErrorCode(bytes[18]);
        function = bytes[19];
        itemCount = bytes[20];
        setReturnCode(bytes[21]);
        if (function == 0x04) {
            transportSize = bytes[22];
            responseDataLength[0] = bytes[23];
            responseDataLength[1] = bytes[24];
            int length = Integer.parseInt(Util.bytesToValueRealOffset(bytes, 23, DataType.TWO_BYTE_INT_UNSIGNED).toString());
//            if ((int) transportSize == 3) {
            responseData = new byte[length];
//            } else {
//                responseData = new byte[length / 8];
//            }
            for (int i = 25; i < bytes.length; i++) {
                responseData[i - 25] = bytes[i];
            }
        }
    }
}
