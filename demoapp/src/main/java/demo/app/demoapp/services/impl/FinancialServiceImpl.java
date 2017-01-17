package demo.app.demoapp.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.app.demoapp.data.domain.FinancialInstrument;
import demo.app.demoapp.data.domain.FinancialRecord;
import demo.app.demoapp.data.jpa.repository.FinancialInstrumentRepository;
import demo.app.demoapp.data.jpa.repository.FinancialRecordRepository;
import demo.app.demoapp.services.FinancialService;
import demo.app.demoapp.services.ServiceException;

@Service
public class FinancialServiceImpl implements FinancialService {

	protected final static Logger log = LoggerFactory.getLogger(FinancialServiceImpl.class);

	@Autowired
	FinancialInstrumentRepository financialInstrumentRepository;
	
	@Autowired
	FinancialRecordRepository financialRecordRepository;
	
	public FinancialServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public List<FinancialInstrument> getAllFinancialInstruments() throws ServiceException {
		return financialInstrumentRepository.findAll();
	}

	public List<FinancialRecord> getAllFinancialRecords() throws ServiceException {
		return financialRecordRepository.findAll();
	}

	public List<FinancialRecord> getFinancialRecords(List<String> symbolList, Date fromDate, Date toDate)
			throws ServiceException {
		
		List<FinancialRecord> recordList = new ArrayList<FinancialRecord>();

		List<FinancialInstrument> instrumentList = financialInstrumentRepository.findBySymbolIn(symbolList);
		for (FinancialInstrument fi : instrumentList) {
			recordList.addAll(financialRecordRepository.findByInstrumentId(fi.getId(), fromDate, toDate));			
		}
		
		return recordList;
		
	}
}
