package com.tobia.base;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MapReader {

	private ArrayList<ArrayList<String>> map;
	
	public MapReader() {
		map = new ArrayList<ArrayList<String>>();
		
	}
	
	public void loadFile(String dateiname) {
		try {
			read("src/Maps/"+dateiname+".txt");
			//System.out.println("datei gefunden");
		}catch(IOException e) {
			//e.printStackTrace();
			System.out.println("Fehler beim Laden der Map (txt)");
		}
	}
	
	
	public void read(String dateiname) throws IOException {
		ArrayList<String> stringlist0 = new ArrayList<String>();	
		ArrayList<String> stringlist1 = new ArrayList<String>();
		ArrayList<String> stringlist2 = new ArrayList<String>();	
		ArrayList<String> stringlist3 = new ArrayList<String>();
		ArrayList<String> stringlist4 = new ArrayList<String>();	
		ArrayList<String> stringlist5 = new ArrayList<String>();
		ArrayList<String> stringlist6 = new ArrayList<String>();	
		ArrayList<String> stringlist7 = new ArrayList<String>();
		ArrayList<String> stringlist8 = new ArrayList<String>();	
		ArrayList<String> stringlist9 = new ArrayList<String>();
		ArrayList<String> stringlist10 = new ArrayList<String>();	
		ArrayList<String> stringlist11 = new ArrayList<String>();
		
		map.add(stringlist0);	
		map.add(stringlist1);
		map.add(stringlist2);	
		map.add(stringlist3);
		map.add(stringlist4);	
		map.add(stringlist5);
		map.add(stringlist6);	
		map.add(stringlist7);
		map.add(stringlist8);	
		map.add(stringlist9);
		map.add(stringlist10);	
		map.add(stringlist11);
		//map.get(0).add("TEST");
		
		FileReader reader = new FileReader(dateiname);
		BufferedReader inBuffer = new BufferedReader(reader);

		
		String line = inBuffer.readLine();
	   
		int i = 0;	//für die ArrayLists
		
		while (line != null) {
			String[] c = line.split(" ");	//da wo das leerzeichen ist, wird gesplittet
			
			switch(i) {
			  case 0:	for(String c1: c)	map.get(0).add(c1); 	//c1 sollte immer das objekt enthalten	
				  break;
			  case 1:	for(String c1: c)	map.get(1).add(c1); 	
				  break;
			  case 2:	for(String c1: c)	map.get(2).add(c1); 	
				  break;
			  case 3:	for(String c1: c)	map.get(3).add(c1); 	
				  break;
			  case 4:	for(String c1: c)	map.get(4).add(c1); 	
				  break;
			  case 5:	for(String c1: c)	map.get(5).add(c1); 	
				  break;
			  case 6:	for(String c1: c)	map.get(6).add(c1); 	
				  break;
			  case 7:	for(String c1: c)	map.get(7).add(c1); 	
				  break;
			  case 8:	for(String c1: c)	map.get(8).add(c1); 	
				  break;
			  case 9:	for(String c1: c)	map.get(9).add(c1); 	
				  break;
			  case 10:	for(String c1: c)	map.get(10).add(c1); 	
				  break;
			  case 11:	for(String c1: c)	map.get(11).add(c1); 	
				  break;
			  }
	      //System.out.println(line);
	      line = inBuffer.readLine();
	      i++;
	   } 
	}
	
	public ArrayList<ArrayList<String>> getMap(){
		return map;
	}
	
}
