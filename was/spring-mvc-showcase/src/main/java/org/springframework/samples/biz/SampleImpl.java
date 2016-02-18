package org.springframework.samples.biz;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.dao.SampleDao;
import org.springframework.stereotype.Service;

@Service
public class SampleImpl implements Sample {

	
	@Autowired
	SampleDao sampleDao;
	
	public String sysdate(){
		
		return sampleDao.sysDate();
	}
}
