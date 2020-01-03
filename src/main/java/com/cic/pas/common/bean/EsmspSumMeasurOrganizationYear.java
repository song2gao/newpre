package com.cic.pas.common.bean;


import java.math.BigDecimal;

public class EsmspSumMeasurOrganizationYear {
    private Integer id;

    private String euoCode;

    private String mmpCode;

    private String eusCode;

    private String energyCode;

    private String dateCode;

    private BigDecimal point1=new BigDecimal(0);

    private BigDecimal point2=new BigDecimal(0);

    private BigDecimal point3=new BigDecimal(0);

    private BigDecimal point4=new BigDecimal(0);

    private BigDecimal point5=new BigDecimal(0);

    private BigDecimal point6=new BigDecimal(0);

    private BigDecimal point7=new BigDecimal(0);

    private BigDecimal point8=new BigDecimal(0);

    private BigDecimal point9=new BigDecimal(0);

    private BigDecimal point10=new BigDecimal(0);

    private BigDecimal point11=new BigDecimal(0);

    private BigDecimal point12=new BigDecimal(0);
    
    private BigDecimal value=new BigDecimal(0);

    public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	private String flag;

    private BigDecimal maxValue=new BigDecimal(0);

    private BigDecimal minValue=new BigDecimal(0);

    private BigDecimal avgValue=new BigDecimal(0);

    private BigDecimal sumValue=new BigDecimal(0);

    private BigDecimal sumAmount=new BigDecimal("0");

    private BigDecimal jValue=new BigDecimal(0);

    private BigDecimal jAmount=new BigDecimal("0");

    private BigDecimal fValue=new BigDecimal(0);

    private BigDecimal fAmount=new BigDecimal("0");

    private BigDecimal pValue=new BigDecimal(0);

    private BigDecimal pAmount=new BigDecimal("0");

    private BigDecimal gValue=new BigDecimal(0);

    private BigDecimal gAmount=new BigDecimal("0");

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEuoCode() {
        return euoCode;
    }

    public void setEuoCode(String euoCode) {
        this.euoCode = euoCode == null ? null : euoCode.trim();
    }

    public String getMmpCode() {
        return mmpCode;
    }

    public void setMmpCode(String mmpCode) {
        this.mmpCode = mmpCode == null ? null : mmpCode.trim();
    }

    public String getEusCode() {
        return eusCode;
    }

    public void setEusCode(String eusCode) {
        this.eusCode = eusCode == null ? null : eusCode.trim();
    }

    public String getEnergyCode() {
        return energyCode;
    }

    public void setEnergyCode(String energyCode) {
        this.energyCode = energyCode == null ? null : energyCode.trim();
    }

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode == null ? null : dateCode.trim();
    }

    public BigDecimal getPoint1() {
        if(point1==null){
            return new BigDecimal(0);
        }else{
            return point1;
        }
    }

    public void setPoint1(BigDecimal point1) {
        this.point1 = point1;
    }

    public BigDecimal getPoint2() {
        if(point2==null){
            return new BigDecimal(0);
        }else{
            return point2;
        }
    }

    public void setPoint2(BigDecimal point2) {
        this.point2 = point2;
    }

    public BigDecimal getPoint3() {
        if(point3==null){
            return new BigDecimal(0);
        }else{
            return point3;
        }
    }

    public void setPoint3(BigDecimal point3) {
        this.point3 = point3;
    }

    public BigDecimal getPoint4() {
        if(point4==null){
            return new BigDecimal(0);
        }else{
            return point4;
        }
    }

    public void setPoint4(BigDecimal point4) {
        this.point4 = point4;
    }

    public BigDecimal getPoint5() {
        if(point5==null){
            return new BigDecimal(0);
        }else{
            return point5;
        }
    }

    public void setPoint5(BigDecimal point5) {
        this.point5 = point5;
    }

    public BigDecimal getPoint6() {
        if(point6==null){
            return new BigDecimal(0);
        }else{
            return point6;
        }
    }

    public void setPoint6(BigDecimal point6) {
        this.point6 = point6;
    }

    public BigDecimal getPoint7() {
        if(point7==null){
            return new BigDecimal(0);
        }else{
            return point7;
        }
    }

    public void setPoint7(BigDecimal point7) {
        this.point7 = point7;
    }

    public BigDecimal getPoint8() {

        if(point8==null){
            return new BigDecimal(0);
        }else{
            return point8;
        }
    }

    public void setPoint8(BigDecimal point8) {
        this.point8 = point8;
    }

    public BigDecimal getPoint9() {
        if(point9==null){
            return new BigDecimal(0);
        }else{
            return point9;
        }
    }

    public void setPoint9(BigDecimal point9) {
        this.point9 = point9;
    }

    public BigDecimal getPoint10() {

        if(point10==null){
            return new BigDecimal(0);
        }else{
            return point10;
        }
    }

    public void setPoint10(BigDecimal point10) {
        this.point10 = point10;
    }

    public BigDecimal getPoint11() {

        if(point11==null){
            return new BigDecimal(0);
        }else{
            return point11;
        }
    }

    public void setPoint11(BigDecimal point11) {
        this.point11 = point11;
    }

    public BigDecimal getPoint12() {
        if(point12==null){
            return new BigDecimal(0);
        }else{
            return point12;
        }
    }

    public void setPoint12(BigDecimal point12) {
        this.point12 = point12;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public BigDecimal getMaxValue() {
        if(maxValue==null){
            return new BigDecimal(0);
        }else{
            return maxValue;
        }
    }

    public void setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
    }

    public BigDecimal getMinValue() {

        if(minValue==null){
            return new BigDecimal(0);
        }else{
            return minValue;
        }
    }

    public void setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
    }

    public BigDecimal getAvgValue() {
        if(avgValue==null){
            return new BigDecimal(0);
        }else{
            return avgValue;
        }
    }

    public void setAvgValue(BigDecimal avgValue) {
        this.avgValue = avgValue;
    }

    public BigDecimal getSumValue() {
        if(sumValue==null){
            return new BigDecimal(0);
        }else{
            return sumValue;
        }
    }

    public void setSumValue(BigDecimal sumValue) {
        this.sumValue = sumValue;
    }

    public BigDecimal getjValue() {
        if(jValue==null){
            return new BigDecimal(0);
        }else{
            return jValue;
        }
    }

    public void setjValue(BigDecimal jValue) {
        this.jValue = jValue;
    }

    public BigDecimal getfValue() {
        if(fValue==null){
            return new BigDecimal(0);
        }else{
            return fValue;
        }
    }

    public void setfValue(BigDecimal fValue) {
        this.fValue = fValue;
    }

    public BigDecimal getpValue() {
        if(pValue==null){
            return new BigDecimal(0);
        }else{
            return pValue;
        }
    }

    public void setpValue(BigDecimal pValue) {
        this.pValue = pValue;
    }

    public BigDecimal getgValue() {
        if(gValue==null){
            return new BigDecimal(0);
        }else{
            return gValue;
        }
    }

    public void setgValue(BigDecimal gValue) {
        this.gValue = gValue;
    }

    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }

    public BigDecimal getjAmount() {
        return jAmount;
    }

    public void setjAmount(BigDecimal jAmount) {
        this.jAmount = jAmount;
    }

    public BigDecimal getfAmount() {
        return fAmount;
    }

    public void setfAmount(BigDecimal fAmount) {
        this.fAmount = fAmount;
    }

    public BigDecimal getpAmount() {
        return pAmount;
    }

    public void setpAmount(BigDecimal pAmount) {
        this.pAmount = pAmount;
    }

    public BigDecimal getgAmount() {
        return gAmount;
    }

    public void setgAmount(BigDecimal gAmount) {
        this.gAmount = gAmount;
    }
}