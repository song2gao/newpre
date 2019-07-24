package com.cic.pas.mapper.impl;

import java.util.List;

import com.cic.pas.common.bean.PreCurveRecord;
import com.cic.pas.dao.DBConfigDao;
import com.cic.pas.mapper.PreCurveRecordMapper;

public class PreCurveRecordMapperImpl implements PreCurveRecordMapper {

	@Override
	public List<PreCurveRecord> find() {
		// TODO Auto-generated method stub
		return DBConfigDao.getSession().getMapper(PreCurveRecordMapper.class).find();
	}

	@Override
	public int insert(PreCurveRecord record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(PreCurveRecord record) {
		// TODO Auto-generated method stub
		return 0;
	}

}
