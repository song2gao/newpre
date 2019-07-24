package com.cic.pas.mapper;

import java.util.List;

import com.cic.pas.common.bean.*;

public interface PreCurveRecordMapper {
    int insert(PreCurveRecord record);

    int insertSelective(PreCurveRecord record);
    
    List<PreCurveRecord> find();
}