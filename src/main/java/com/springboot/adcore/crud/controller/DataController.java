package com.springboot.adcore.crud.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import java.util.Optional;

import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.adcore.crud.model.Data;
import com.springboot.adcore.crud.repository.DataRepository;
import com.springboot.adcore.crud.service.DataService;



@CrossOrigin
@RestController
@RequestMapping("/adcore/api/")
public class DataController {
	
	@Autowired
	private DataService ds;
	
	@Autowired
	private DataRepository dataRepository;
	
	
	@GetMapping("/export-csv")
	public ResponseEntity<Object> export_csv() throws IOException{
		FileWriter fileWriter = null;
		try {
			List<Data> dataList = (List<Data>) dataRepository.findAll();
			
			StringBuilder fileContent = new StringBuilder("ID,Name,Description,Parent,ReadOnly,Created,Updated");
			fileContent.append("\n");
			for (Data data: dataList) {
				fileContent
				.append(data.getData_id())
				.append("	")
				.append(data.getName())
				.append("	")
				.append(data.getDescription())
				.append("	")
				.append(data.getRead_only())
				.append("	")
				.append(data.getCreated())
				.append("	")
				.append(data.getUpdated())
				.append("\n");
			}
			
			String fileName = "C:\\Users\\dell\\Desktop\\AdcoreApi\\adcoredata.csv";
			
				fileWriter = new FileWriter(fileName);
				fileWriter.write(fileContent.toString());
				fileWriter.flush();
				
				File file = new File(fileName);
				
				InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
				headers.add("Cache-Control","no-cache, no-store, must-revalidate");
				headers.add("Pragma","no-cache");
				headers.add("Expires","0");
			
			ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/txt")).body(resource);
			return responseEntity;
		}
		catch(Exception e) {
			return new ResponseEntity<>("error occured", HttpStatus.INTERNAL_SERVER_ERROR);
		}finally {
			if(fileWriter!=null) {
				fileWriter.close();
			}
		}
		
		
	}
	
	@RequestMapping("/feedData")
	public void save_tree_in_database() {
		ds.saveData();
	}
	
	
	 
	@GetMapping("/data")
	public List<Data> get_tree(){
		return (List<Data>) dataRepository.findAll();
	}
	
	@GetMapping("/data/{id}")
	public Optional<Data> get_single_node(@PathVariable Long id) {
		return dataRepository.findById(id);
	}
	
	@PostMapping(path="/add",consumes={"application/json"})
	public int create_node(@RequestBody Data data) {
		if(data.getName()!=null && data.getDescription()!=null) {
			Data addedData = dataRepository.save(data);
			return (int) addedData.getData_id();
		}else {
			throw new DataException("Record has null values or Record is missing fields!", null);
		}
		
	}
	
	@PutMapping(path="/update/{id}", consumes= {"application/json"})
	public Data update_node(@PathVariable Long id, @RequestBody Data newData) {

		List<Data> dataArr = (List<Data>) dataRepository.findAll();
		
		if (dataRepository.findById(id).isPresent() && dataRepository.findById(id).get().getRead_only()){
            Data existingData = dataRepository.findById(id).get();
            Date date = new Date();
            
			 existingData.setName(newData.getName());
			 existingData.setDescription(newData.getDescription());
			 existingData.setParent(newData.getParent());
			 existingData.setRead_only(newData.getRead_only());
			 existingData.setCreated(date);
			 existingData.setUpdated(date);

            Data updatedData = dataRepository.save(existingData);
            return updatedData;

        }else{
        	if(id > dataArr.size()) {
        		throw new DataException("Record does not exists!", null);	
        	}else {
        		throw new DataException("Record is Not Editable!", null);
        	}
        	
        }
				
	}
	
	@DeleteMapping("/delete/{id}")
	public String delete_node(@PathVariable Long id) {
		List<Data> dataArr = (List<Data>) dataRepository.findAll();
		if(dataRepository.findById(id).isPresent() && dataRepository.findById(id).get().getRead_only()) {
			dataRepository.deleteById(id);
			return "success";
		}else {
			if(id > dataArr.size()) {
        		throw new DataException("Record does not exists!", null);	
        	}else {
        		throw new DataException("Record is Not Editable!", null);
        	}
		}
		
	}
	
}