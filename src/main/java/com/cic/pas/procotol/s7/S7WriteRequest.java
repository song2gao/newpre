package com.cic.pas.procotol.s7;

public class S7WriteRequest {
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
     * byte 18
     * item count（项目个数），其类型为Unsigned integer，大小为1 byte
     */
    private byte itemCount = 1;
    /**
     * byte 19
     * Variable specification，确定项目结构的主要类型，通常为0x12，代表变量规范；
     */
    private byte variableSpecification = 18;
    /**
     * byte 20
     * Length of following address specification，本Item其余部分的长度
     */
    private byte lengthOfFollowing = 10;
    /**
     * byte 21
     * Syntax Ids of variable specification，确定寻址模式和其余项目结构的格式；
     */
    private byte syntaxId = 16;
    /**
     * byte 22
     * Transport sizes in item data，确定变量的类型和长度：
     * 0x01 - BIT
     * 0x02 - BYTE
     * 0x03 - CHAR
     * 0x04 - WORD
     * 0x05 - INT
     * 0x06 - DWORD
     * 0x07 - DINT
     * 0x08 - REAL
     * 0x09 - DATE
     * 0x0A - TOD
     * 0x0B - TIME
     * 0x0C - S5TIME
     * 0x0F - DATE AND TIME
     * 0x1C - COUNTER
     * 0x1D - TIMER
     * 0x1E - IEC TIMER
     * 0x1F - IEC COUNTER
     * 0x20 - HS COUNTER
     */
    private byte transportSize;
    /**
     * byte 23 byte 24
     * 两个字节
     * Request data length，请求的数据长度；
     */
    private byte[] requestDataLength=new byte[2];
    /**
     * byte25 byte26
     * DB number，DB模块的编号，如果访问的不是DB区域，此处为0x0000；
     */
    private byte[] dbNumber = {0, 0};

    /**
     * byte 27
     * 区域类型：
     * 0x1C - S7 counters (C)
     * 0x1D - S7 timers (T)
     * 0x81(HEX) 129(DEC)- Inputs (I)
     * 0x82(HEX) 130(DEC)- Outputs (Q)
     * 0x83(HEX) 131(DEC) Flags (M) (Merker)
     * 0x84(HEX) 132(DEC) Data blocks (DB)
     * 0x85 - Instance data blocks (DI)
     * 0x86 - Local data (L)
     * <p>
     * 0x87 - Unknown yet (V)
     */
    private byte area = (byte) 0x83;
    /**
     * byte28 byte29 byte 30
     * Address，地址。
     */
    private byte[] address = {0, 0, 0};
    /**
     * byte 31
     * 返回代码，这里是未定义，所以为Reserved（0x00）；
     */
    private byte returnCode=0x00;
    /**
     * byte 32
     * Transport size，确定变量的类型和长度：
     */
    private byte dataTransportSize;
    /**
     * byte 33 byte 34
     * 写入值长度
     */
    private byte[]  dataWriteLength;
    /**
     * byte 35 ......byte dataWriteLength
     */
    private byte[] writeData;

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
     * byte 19
     * Variable specification，确定项目结构的主要类型，通常为0x12，代表变量规范；
     */
    public byte getVariableSpecification() {
        return variableSpecification;
    }

    /**
     * byte 19
     * Variable specification，确定项目结构的主要类型，通常为0x12，代表变量规范；
     */
    public void setVariableSpecification(byte variableSpecification) {
        this.variableSpecification = variableSpecification;
    }

    /**
     * byte 20
     * Length of following address specification，本Item其余部分的长度
     */
    public byte getLengthOfFollowing() {
        return lengthOfFollowing;
    }

    /**
     * byte 20
     * Length of following address specification，本Item其余部分的长度
     */
    public void setLengthOfFollowing(byte lengthOfFollowing) {
        this.lengthOfFollowing = lengthOfFollowing;
    }

    /**
     * byte 21
     * Syntax Ids of variable specification，确定寻址模式和其余项目结构的格式；
     */
    public byte getSyntaxId() {
        return syntaxId;
    }

    /**
     * byte 21
     * Syntax Ids of variable specification，确定寻址模式和其余项目结构的格式；
     */
    public void setSyntaxId(byte syntaxId) {
        this.syntaxId = syntaxId;
    }

    /**
     * byte 22
     * Transport sizes in item data，确定变量的类型和长度：
     * 0x01 - BIT
     * 0x02 - BYTE
     * 0x03 - CHAR
     * 0x04 - WORD
     * 0x05 - INT
     * 0x06 - DWORD
     * 0x07 - DINT
     * 0x08 - REAL
     * 0x09 - DATE
     * 0x0A - TOD
     * 0x0B - TIME
     * 0x0C - S5TIME
     * 0x0F - DATE AND TIME
     * 0x1C - COUNTER
     * 0x1D - TIMER
     * 0x1E - IEC TIMER
     * 0x1F - IEC COUNTER
     * 0x20 - HS COUNTER
     */
    public byte getTransportSize() {
        return transportSize;
    }

    /**
     * byte 22
     * Transport sizes in item data，确定变量的类型和长度：
     * 0x01 - BIT
     * 0x02 - BYTE
     * 0x03 - CHAR
     * 0x04 - WORD
     * 0x05 - INT
     * 0x06 - DWORD
     * 0x07 - DINT
     * 0x08 - REAL
     * 0x09 - DATE
     * 0x0A - TOD
     * 0x0B - TIME
     * 0x0C - S5TIME
     * 0x0F - DATE AND TIME
     * 0x1C - COUNTER
     * 0x1D - TIMER
     * 0x1E - IEC TIMER
     * 0x1F - IEC COUNTER
     * 0x20 - HS COUNTER
     */
    public void setTransportSize(byte transportSize) {
        this.transportSize = transportSize;
    }

    /**
     * byte 23 byte 24
     * 两个字节
     * Request data length，请求的数据长度；
     */
    public byte[] getRequestDataLength() {
        return requestDataLength;
    }

    /**
     * byte 23 byte 24
     * 两个字节
     * Request data length，请求的数据长度；
     */
    public void setRequestDataLength(byte[] requestDataLength) {
        this.requestDataLength = requestDataLength;
    }

    /**
     * byte25 byte26
     * DB number，DB模块的编号，如果访问的不是DB区域，此处为0x0000；
     */
    public byte[] getDbNumber() {
        return dbNumber;
    }

    /**
     * byte25 byte26
     * DB number，DB模块的编号，如果访问的不是DB区域，此处为0x0000；
     */
    public void setDbNumber(byte[] dbNumber) {
        this.dbNumber = dbNumber;
    }

    /**
     * byte 27
     * 区域类型：
     * 0x1C - S7 counters (C)
     * 0x1D - S7 timers (T)
     * 0x81 - Inputs (I)
     * 0x82 - Outputs (Q)
     * 0x83 - Flags (M) (Merker)
     * 0x84 - Data blocks (DB)
     * 0x85 - Instance data blocks (DI)
     * 0x86 - Local data (L)
     * <p>
     * 0x87 - Unknown yet (V)
     */
    public byte getArea() {
        return area;
    }

    /**
     * byte 27
     * 区域类型：
     * 0x1C - S7 counters (C)
     * 0x1D - S7 timers (T)
     * 0x81 - Inputs (I)
     * 0x82 - Outputs (Q)
     * 0x83 - Flags (M) (Merker)
     * 0x84 - Data blocks (DB)
     * 0x85 - Instance data blocks (DI)
     * 0x86 - Local data (L)
     * <p>
     * 0x87 - Unknown yet (V)
     */
    public void setArea(byte area) {
        this.area = area;
    }

    public byte[] getAddress() {
        return address;
    }

    /**
     * byte28 byte29 byte 30
     * address，地址。
     *
     * @param address
     */
    public void setAddress(byte[] address) {
        this.address = address;
    }
    /**
     * byte 31
     * 返回代码，这里是未定义，所以为Reserved（0x00）；
     */
    public byte getReturnCode() {
        return returnCode;
    }
    /**
     * byte 31
     * 返回代码，这里是未定义，所以为Reserved（0x00）；
     */
    public void setReturnCode(byte returnCode) {
        this.returnCode = returnCode;
    }
    /**
     * byte 32
     * Transport size，确定变量的类型和长度：
     */
    public byte getDataTransportSize() {
        return dataTransportSize;
    }
    /**
     * byte 32
     * Transport size，确定变量的类型和长度：
     */
    public void setDataTransportSize(byte dataTransportSize) {
        this.dataTransportSize = dataTransportSize;
    }
    /**
     * byte 33 byte 34
     * 写入值长度
     */
    public byte[] getDataWriteLength() {
        return dataWriteLength;
    }
    /**
     * byte 33 byte 34
     * 写入值长度
     */
    public void setDataWriteLength(byte[] dataWriteLength) {
        this.dataWriteLength = dataWriteLength;
    }
    /**
     * byte 35 ......byte dataWriteLength
     */
    public byte[] getWriteData() {
        return writeData;
    }
    /**
     * byte 35 ......byte dataWriteLength
     */
    public void setWriteData(byte[] writeData) {
        this.writeData = writeData;
    }

    public void setSendBytes(byte[] bytes) {
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
        function = bytes[17];
        itemCount = bytes[18];
        variableSpecification = bytes[19];
        lengthOfFollowing = bytes[20];
        syntaxId = bytes[21];
        transportSize = bytes[22];
        requestDataLength[0] = bytes[23];
        requestDataLength[1] = bytes[24];
        dbNumber[0] = bytes[25];
        dbNumber[1] = bytes[26];
        area = bytes[27];
        address[0] = bytes[28];
        address[1] = bytes[29];
        address[2] = bytes[30];
    }

    public byte[] getSendBytes() {
        byte[] bytes = new byte[35+writeData.length];
        bytes[0] = version;
        bytes[1] = resrved;
        bytes[2] = totalLength[0];
        bytes[3] = totalLength[1];
        bytes[4] = cotplength;
        bytes[5] = cotpfunction;
        bytes[6] = opt;
        bytes[7] = procotolId;
        bytes[8] = s7commPduType;
        bytes[9] = redundancyIdentification[0];
        bytes[10] = redundancyIdentification[1];
        bytes[11] = protocolDataUnitReference[0];
        bytes[12] = protocolDataUnitReference[1];
        bytes[13] = paramsLength[0];
        bytes[14] = paramsLength[1];
        bytes[15] = dataLength[0];
        bytes[16] = dataLength[1];
        bytes[17] = function;
        bytes[18] = itemCount;
        bytes[19] = variableSpecification;
        bytes[20] = lengthOfFollowing;
        bytes[21] = syntaxId;
        bytes[22] = transportSize;
        bytes[23] = requestDataLength[0];
        bytes[24] = requestDataLength[1];
        bytes[25] = dbNumber[0];
        bytes[26] = dbNumber[1];
        bytes[27] = area;
        bytes[28] = address[0];
        bytes[29] = address[1];
        bytes[30] = address[2];
        bytes[31]=returnCode;
        bytes[32]=dataTransportSize;
        bytes[33]=dataWriteLength[0];
        bytes[34]=dataWriteLength[1];
        for(int i=0;i<writeData.length;i++){
            bytes[35+i]=writeData[i];
        }
        return bytes;
    }

}
