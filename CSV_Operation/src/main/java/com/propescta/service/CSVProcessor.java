package com.propescta.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CSVProcessor {
	private Map<String, Double> cellValues = new LinkedHashMap<>();

	public ByteArrayOutputStream process(MultipartFile file) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try (CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(file.getInputStream())));
				CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream))) {
			
			String[] nextLine;
			int rowNumber = 1;

			while ((nextLine = reader.readNext()) != null) {
				String[] processedRow = new String[nextLine.length]; 
				for (int col = 0; col < nextLine.length; col++) {
					String cellId = getCellId(rowNumber, col);
					String cellValue = nextLine[col];
					double value = processCell(cellValue);
					cellValues.put(cellId, value);
					processedRow[col] = String.valueOf(value);
				}
				writer.writeNext(processedRow);
				rowNumber++;
			}
			writer.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return outputStream;
	}
	
	private double processCell(String cellValue) {
		if (cellValue.startsWith("=")) {
			return evaluateFormula(cellValue.substring(1)); 
		} else {
			return Double.parseDouble(cellValue); 
		}
	}

	private double evaluateFormula(String formula) {
		double result = 0;
		if (formula.contains("+")) {
			String[] operands = formula.split("\\+");
			result = getValue(operands[0]) + getValue(operands[1]);
		} else if (formula.contains("-")) {
			String[] operands = formula.split("-");
			result = getValue(operands[0]) - getValue(operands[1]);
		} else if (formula.contains("*")) {
			String[] operands = formula.split("\\*");
			result = getValue(operands[0]) * getValue(operands[1]);
		} else if (formula.contains("/")) {
			String[] operands = formula.split("/");
			result = getValue(operands[0]) / getValue(operands[1]);
		}
		return result;
	}

	
	private double getValue(String cellReference) {
		cellReference = cellReference.trim(); 
		if (cellValues.containsKey(cellReference)) {
			return cellValues.get(cellReference);
		} else {
			return Double.parseDouble(cellReference); 
		}
	}

	private String getCellId(int row, int col) {
		return (char) ('A' + col) + String.valueOf(row);
	}
    
}

