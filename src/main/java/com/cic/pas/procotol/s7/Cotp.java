package com.cic.pas.procotol.s7;

public class Cotp extends Tpkt{
    private byte length;
    private byte pduType;
    private byte[] dstRef;
    private byte[] srcRef;
    private byte opt;
    private byte[] params;

    /**
     * 0
     * Length，COTP后续数据的长度（注意：长度不包含length的长度），一般为17 bytes。
     * @return
     */
    public byte getLength() {
        return length;
    }
    public void setLength(byte length) {
        this.length = length;
    }

    /**
     * 1
     * (Unsigned integer, 1 byte): PDU typ，类型有：
     *
     * 0×1: ED Expedited Data，加急数据
     *
     * 0×2: EA Expedited Data Acknowledgement，加急数据确认
     *
     * 0×4: UD，用户数据
     *
     * 0×5: RJ Reject，拒绝
     *
     * 0×6: AK Data Acknowledgement，数据确认
     *
     * 0×7: ER TPDU Error，TPDU错误
     *
     * 0×8: DR Disconnect Request，断开请求
     *
     * 0xC: DC Disconnect Confirm，断开确认
     *
     * 0xD: CC Connect Confirm，连接确认
     *
     * 0xE: CR Connect Request，连接请求
     *
     * 0xF: DT Data，数据传输
     * @return
     */
    public byte getPduType() {
        return pduType;
    }

    public void setPduType(byte pduType) {
        this.pduType = pduType;
    }

    /**
     * 2~3
     * (Unsigned integer, 2 bytes): Destination reference.
     * @return
     */
    public byte[] getDstRef() {
        return dstRef;
    }

    public void setDstRef(byte[] dstRef) {
        this.dstRef = dstRef;
    }

    /**
     * 4~5 (Unsigned integer, 2 bytes): Source reference.
     * @return
     */
    public byte[] getSrcRef() {
        return srcRef;
    }
    public void setSrcRef(byte[] srcRef) {
        this.srcRef = srcRef;
    }
    /**
     *6 (1 byte):
     * opt，其中包括Extended formats、No explicit flow control，值都是Boolean类型。
     * @return
     */
    public byte getOpt() {
        return opt;
    }

    public void setOpt(byte opt) {
        this.opt = opt;
    }

    public byte[] getParams() {
        return params;
    }

    /**
     * 7~? (length-7 bytes, 一般为11 bytes):
     * Parameter，参数。一般参数包含Parameter code(Unsigned integer, 1 byte)、Parameter length(Unsigned integer, 1 byte)、Parameter data三部分。
     * @param params
     */
    public void setParams(byte[] params) {
        this.params = params;
    }
}
