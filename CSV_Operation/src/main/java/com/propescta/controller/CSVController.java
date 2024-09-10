package com.propescta.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.propescta.service.CSVProcessor;

@RestController
@RequestMapping("csv")
public class CSVController {
	private final CSVProcessor csvProcessor;

	@Autowired
	public CSVController(CSVProcessor csvProcessor) {
		this.csvProcessor = csvProcessor;
	}

	@PostMapping("/process")
	public ResponseEntity<ByteArrayResource> processCsv(@RequestParam("file") MultipartFile input) {
		try {
			// Process the uploaded CSV file
			ByteArrayOutputStream outputStream = csvProcessor.process(input);

			ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

			return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType("application/csv"))
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"processed.csv\"")
					.body(resource);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}