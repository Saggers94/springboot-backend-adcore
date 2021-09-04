package com.springboot.adcore.crud.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.adcore.crud.model.Data;
import com.springboot.adcore.crud.repository.DataRepository;

@Service
public class DataService {

	@Autowired
	private DataRepository dr;
	
	
	String line ="";
	public void saveData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/main/resources/tree_data.csv"));
			boolean read_only = false;
			
			line = br.readLine();
			while((line = br.readLine())!=null) {
				String [] data = line.split("	");
				Data d = new Data();
				if(data.length == 5) {
					d.setName(data[1]);
					d.setDescription(data[2]);
					d.setParent(Integer.parseInt(data[3]));
					if(Integer.parseInt(data[4]) == 1) {
						read_only = true;
					}else {
						read_only = false;
					}
					d.setRead_only(read_only);
					dr.save(d);
				}	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
