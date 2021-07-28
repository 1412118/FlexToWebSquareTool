package FlexToWebSquare;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileConverting {
	public static void main(String[] args){
		try {
			ArrayList<GridColumns> grid_flex  = new ArrayList<>();
			ArrayList<DataListColumns> data_list  = new ArrayList<>();
			ArrayList<DataListColumns> custom_data_list = new ArrayList<>();
			ArrayList<String> idDataList = new ArrayList<String>();
			ArrayList<Integer> gridSize = new ArrayList<Integer>();
			File grid = new File("C:/Users/Dat Duong/Desktop/grid_flex.xml");
			File dataList = new File("C:/Users/Dat Duong/Desktop/datalist_html5.xml");
			File newDataList = new File("C:/Users/Dat Duong/Desktop/datalist_target.xml");
			File codelabel = new File("C:/Users/Dat Duong/Desktop/CODELABEL.txt");
			readGridFile(grid_flex, grid, idDataList, gridSize);
			readDataListFile(data_list, dataList);
			createNewDataList(grid_flex, data_list, custom_data_list);
			getCodeLabel(grid_flex, custom_data_list, codelabel);
			writeFile(newDataList, custom_data_list, idDataList, gridSize);
			/*
			 * for (int i = 0; i < gridSize.size(); i++) {
			 * System.out.println(gridSize.get(i)); }
			 */
		}
		catch(Exception e) {
			System.out.println("ERROR: " + e);
		}
	}
	
	public static void readGridFile(ArrayList<GridColumns> grid_flex, File grid, ArrayList<String> idDataList, ArrayList<Integer> gridSize) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(grid);
			NodeList nodeDataList = doc.getElementsByTagName("gb:AdvancedDataGrid");
			for(int i = 0; i < nodeDataList.getLength(); i++) {
				Element eElement = (Element)nodeDataList.item(i);
				idDataList.add(eElement.getAttribute("id"));
				gridSize.add(eElement.getChildNodes().getLength() / 2);
			}
			NodeList nodeListGridColumns = doc.getElementsByTagName("gb:AdvancedDataGridColumn");
			for (int i = 0; i < nodeListGridColumns.getLength(); i++) {
				GridColumns col = new GridColumns();
				Node nNode = nodeListGridColumns.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					col.setDataField(eElement.getAttribute("dataField"));
					col.setProperties(eElement.getAttribute("properties"));
					col.setTextAlign(eElement.getAttribute("textAlign"));
				}
				grid_flex.add(col);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void readDataListFile(ArrayList<DataListColumns> data_list, File dataList) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(dataList);
			//NodeList nodeDataList = doc.getElementsByTagName("w2:dataList");
			NodeList nodeDataListColumns = doc.getElementsByTagName("w2:column");
			//for(int idx= 0; idx < nodeDataList.getLength(); idx++) {
				for (int i = 0; i < nodeDataListColumns.getLength(); i++) {
					DataListColumns col = new DataListColumns();
					Node nNode = nodeDataListColumns.item(i);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						col.setId(eElement.getAttribute("id"));
						col.setName(eElement.getAttribute("name"));
						col.setDataType(eElement.getAttribute("dataType"));
					}
					data_list.add(col);
				}
			//}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createNewDataList(ArrayList<GridColumns> grid_flex, ArrayList<DataListColumns> data_list, ArrayList<DataListColumns> custom_data_list){
		for(int i = 0; i < grid_flex.size(); i++) {
			for(int j = 0; j < data_list.size(); j++) {
				if(data_list.get(j).getId().equals(grid_flex.get(i).getDataField())) {
					custom_data_list.add(data_list.get(j));
					data_list.remove(j);
					break;
				}
			}
		}	
	}
	
	public static void writeFile(File newDataList, ArrayList<DataListColumns> custom_data_list,  ArrayList<String> idDataList, ArrayList<Integer> gridSize) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            Element dataCollection = doc.createElement("w2:dataCollection");
            doc.appendChild(dataCollection);
            ArrayList<Element> dataList = new ArrayList<Element>();
            int j = 0;
            int size = 0;
            
            for(int idx = 0; idx < idDataList.size(); idx++) {
            	dataList.add(doc.createElement("w2_dataList"));
            	dataCollection.appendChild(dataList.get(idx));
	            Attr id_dataList = doc.createAttribute("id");
	            Attr baseNode = doc.createAttribute("baseNode");
	            Attr saveRemovedData = doc.createAttribute("saveRemovedData");
	            Attr repeatNode = doc.createAttribute("repeatNode");
	            String str = new String();
	            str = "dlt_" + idDataList.get(idx);
	            id_dataList.setValue(str);
	            baseNode.setValue("list");
	            saveRemovedData.setValue("true");
	            repeatNode.setValue("map");
	            dataList.get(idx).setAttributeNode(id_dataList);
	            dataList.get(idx).setAttributeNode(baseNode);
	            dataList.get(idx).setAttributeNode(saveRemovedData);
	            dataList.get(idx).setAttributeNode(repeatNode);
	            
	            Element columnInfo = doc.createElement("w2:columnInfo");
	            dataList.get(idx).appendChild(columnInfo);
	            size += gridSize.get(idx);
	            for(; j < size; j++) {
		            Element column = doc.createElement("w2:column");
		            columnInfo.appendChild(column);
		            Attr id_column = doc.createAttribute("id");
		            Attr name = doc.createAttribute("name");
		            Attr dateType = doc.createAttribute("dataType");
		            id_column.setValue(custom_data_list.get(j).getId());
		            name.setValue(custom_data_list.get(j).getName());
		            dateType.setValue(custom_data_list.get(j).getDataType());
		            column.setAttributeNode(id_column);
		            column.setAttributeNode(name);
		            column.setAttributeNode(dateType);
	            }
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:/Users/Dat Duong/Desktop/datalist_target.xml"));
            transformer.transform(source, result);
		} catch (Exception e) {
			
		}
	}
	
	public static void getCodeLabel(ArrayList<GridColumns> grid_flex, ArrayList<DataListColumns> custom_data_list, File codelabel) {
		boolean flag = true;
		
		try {
			Scanner sc = new Scanner(codelabel);
			ArrayList<String> arr = new ArrayList<String>();
			while(sc.hasNext() == true) {
				arr.add(sc.nextLine());
			}
			for(int i = 0; i < grid_flex.size(); i++) {
				for(int j = 0; j < arr.size(); j++) {
					String[] str = arr.get(j).split("=");
					if(grid_flex.get(i).getProperties().equals(str[0])) {
						custom_data_list.get(i).setName(str[1]);
						flag = true;
						break;
					} else {
						flag = false;
					}
					
				}
				if(flag == false) {
					custom_data_list.get(i).setName("--------");
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
