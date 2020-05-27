package com.tcm.dal.qaos.dao;

import java.util.List;

import com.tcm.dal.qaos.po.KConclusionPo;

public interface QAKConclusionDao {
	/**
	 * 根据名称,模糊查找KConclusionPo列表
	 * 
	 * @param name
	 * @return
	 */
	List<KConclusionPo> selectKConclusions(String name);

	/**
	 * 根据名称,精确查找KConclusionPo列表
	 * 
	 * @param name
	 * @return
	 */
	KConclusionPo selectKConclusion(String name);
}
