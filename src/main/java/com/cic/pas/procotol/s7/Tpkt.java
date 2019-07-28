package com.cic.pas.procotol.s7;

public class Tpkt {
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
    public byte getVersion() {
        return version;
    }
    public void setVersion(byte version) {
        this.version = version;
    }
    public byte getResrved() {
        return resrved;
    }

    public void setResrved(byte resrved) {
        this.resrved = resrved;
    }
    public byte[] getTotalLength() {
        return totalLength;
    }
    public void setTotalLength(byte[] totalLength) {
        this.totalLength = totalLength;
    }
}
