package com.thinking.machines.hr.pl.model;
import com.thinking.machines.hr.bl.exception.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.io.*;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.io.image.*;
import com.itextpdf.io.font.*;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.*;
import com.itextpdf.io.font.constants.*;
import com.itextpdf.layout.property.*;
import com.itextpdf.layout.borders.*;

public class DesignationModel extends AbstractTableModel
{
private String title[ ];
private java.util.List<DesignationInterface> designations;
private DesignationManager manager;
public DesignationModel( )
{
populateDataStructure( );
}

public void populateDataStructure( )
{
this.designations=new LinkedList<DesignationInterface>( );
title=new String[2];
title[0]="S.No";
title[1]="Designation";
Set<DesignationInterface> blDesignations=null;
try
{
manager=DesignationManager.getDesignationManager( );
blDesignations=manager.getDesignations( );
}
catch(BLException blException)
{
//what to do is yet to be decided ????
}
for(DesignationInterface designation:blDesignations)
{
designations.add(designation);
}
Collections.sort(designations,new Comparator<DesignationInterface>( ){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle( ).toUpperCase( ).compareTo(right.getTitle( ).toUpperCase( ));
}
});
}
public int getRowCount( )
{
return designations.size( );
}
public int getColumnCount( )
{
return title.length;
}
public String getColumnName(int columnIndex)
{
return title[columnIndex];
}
public Class getColumnClass(int columnIndex)
{
Class c=null;
try
{
if(columnIndex==0)c=Class.forName("java.lang.Integer");
if(columnIndex==1)c=Class.forName("java.lang.String");
}catch(Exception e)
{
System.out.println(e.getMessage( ));
}
return c;
}
public boolean isCellEditable(int rowIndex,int columnIndex)
{
return false;
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0)return rowIndex+1;
return designations.get(rowIndex).getTitle( );
}

public int indexOfDesignation(DesignationInterface designation) throws BLException
{
Iterator<DesignationInterface> iterator;
int index=0;
DesignationInterface desi=null;
iterator=this.designations.iterator( );
while(iterator.hasNext( ))
{
desi=iterator.next( );
if(desi.equals(designation))
{
return index;
}
index++;
}
BLException blException=new BLException( );
blException.setGenericException("Invalid Designation : "+designation.getTitle( ));
throw blException;
}
public int indexOfTitle(String title,boolean partialLeft) throws BLException
{
Iterator<DesignationInterface> iterator;
int index=0;
DesignationInterface desi=null;
iterator=this.designations.iterator( );
while(iterator.hasNext( ))
{
desi=iterator.next( );
if(partialLeft)
{
if(desi.getTitle( ).toUpperCase( ).startsWith(title.toUpperCase( )))
{
return index;
}
}
else
{
if(desi.getTitle( ).toUpperCase( ).equals(title.toUpperCase( )))
{
return index;
}
}
index++;
}
BLException blException=new BLException( );
blException.setGenericException("Invalid title : "+title);
throw blException;
}
public DesignationInterface getDesignationAt(int index) throws BLException
{
if(index<0 || index>=designations.size( ))
{
BLException blException=new BLException( );
blException.setGenericException("Invalid index : "+index);
throw blException;
}
return designations.get(index);
}

public void add(DesignationInterface designation) throws BLException
{
manager.addDesignation(designation);
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>( ){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle( ).toUpperCase( ).compareTo(right.getTitle( ).toUpperCase( ));
}
});
fireTableDataChanged( );
}
public void update(DesignationInterface designation) throws BLException
{
manager.updateDesignation(designation);
this.designations.remove(indexOfDesignation(designation));
this.designations.add(designation);
Collections.sort(this.designations,new Comparator<DesignationInterface>( ){
public int compare(DesignationInterface left,DesignationInterface right)
{
return left.getTitle( ).toUpperCase( ).compareTo(right.getTitle( ).toUpperCase( ));
}
});
fireTableDataChanged( );
}
public void delete(int code) throws BLException
{
manager.removeDesignation(code);
int index=0;
DesignationInterface desi=null;
Iterator<DesignationInterface> iterator=this.designations.iterator( );
while(iterator.hasNext( ))
{
desi=iterator.next( );
if(desi.getCode( )==code)break;
index++;
}
if(index==this.designations.size( ))
{
BLException blException=new BLException( );
blException.setGenericException("Invalid code : "+code);
throw blException;
}
this.designations.remove(index);
fireTableDataChanged( );
}
public void exportToPDF(File file) throws BLException
{
try
{
if(file.exists( ))file.delete( );
PdfWriter pdfWriter=new PdfWriter(file);
PdfDocument pdfDocument=new PdfDocument(pdfWriter);
Document document=new Document(pdfDocument);

//Image logo=new Image(ImageDataFactory.create(this.getClass( ).getResource("/icons/logo.png")));
com.itextpdf.layout.element.Image logo=new com.itextpdf.layout.element.Image(ImageDataFactory.create("logo.png"));
Paragraph logoPara=new Paragraph( );
logoPara.add(logo);

Paragraph companyNamePara=new Paragraph( );
companyNamePara.add("ABCD Corporation");
PdfFont companyNameFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
companyNamePara.setFont(companyNameFont);
companyNamePara.setFontSize(18);

Paragraph reportTitlePara=new Paragraph("List of designations");
PdfFont reportTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
reportTitlePara.setFont(reportTitleFont);
reportTitlePara.setFontSize(15);

PdfFont columnTitleFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
PdfFont dataFont=PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
PdfFont pageNumberFont=PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);

Paragraph columnTitle1=new Paragraph("S.No.");
columnTitle1.setFont(columnTitleFont);
columnTitle1.setFontSize(14);
Paragraph columnTitle2=new Paragraph("Designations");
columnTitle2.setFont(columnTitleFont);
columnTitle2.setFontSize(14);

Paragraph pageNumberParagraph;
Paragraph dataParagraph;

float topTableColumnWidths[ ]={1,5};
float dataTableColumnWidths[ ]={1,5};

int pageSize=5;
int x=0;
int sno=0;
int pageNumber=0;
int numberOfPages=this.designations.size( )/pageSize;
if((this.designations.size( )%pageSize)!=0)numberOfPages++;
boolean newPage=true;
Table pageNumberTable;
Table topTable;
Table dataTable=null;
Cell cell;
DesignationInterface designation;

while(x<designations.size( ))
{
if(newPage)
{
pageNumber++;
topTable=new Table(UnitValue.createPercentArray(topTableColumnWidths));
cell=new Cell( );
cell.setBorder(Border.NO_BORDER);
cell.add(logoPara);
topTable.addCell(cell);
cell=new Cell( );
cell.setBorder(Border.NO_BORDER);
cell.add(companyNamePara);
cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
topTable.addCell(cell);

document.add(topTable);

pageNumberParagraph=new Paragraph("Page : "+pageNumber+"/"+numberOfPages);
pageNumberParagraph.setFont(pageNumberFont);
pageNumberParagraph.setFontSize(13);
pageNumberTable=new Table(1);
pageNumberTable.setWidth(UnitValue.createPercentValue(100));
cell=new Cell( );
cell.setBorder(Border.NO_BORDER);
cell.add(pageNumberParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
pageNumberTable.addCell(cell);

document.add(pageNumberTable);

dataTable=new Table(UnitValue.createPercentArray(dataTableColumnWidths));
dataTable.setWidth(UnitValue.createPercentValue(100));

cell=new Cell(1,2);
cell.add(reportTitlePara);
cell.setTextAlignment(TextAlignment.CENTER);
dataTable.addHeaderCell(cell);
dataTable.addHeaderCell(columnTitle1);
dataTable.addHeaderCell(columnTitle2);
newPage=false;
}
designation=designations.get(x);
//add row to table
sno++;
cell=new Cell( );
dataParagraph=new Paragraph(String.valueOf(sno));
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(13);
cell.add(dataParagraph);
cell.setTextAlignment(TextAlignment.RIGHT);
dataTable.addCell(cell);
cell=new Cell( );
dataParagraph=new Paragraph(designation.getTitle( ));
dataParagraph.setFont(dataFont);
dataParagraph.setFontSize(13);
cell.add(dataParagraph);
dataTable.addCell(cell);

x++;

if(sno%pageSize==0 || x==this.designations.size( ))
{
//create footer
document.add(dataTable);
document.add(new Paragraph("Software by : Thinking Machines"));
if(x<this.designations.size( ))
{
//add new page to document
document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
newPage=true;
}
}
}


document.close( );
}
catch(Exception exception)
{
BLException blException=new BLException( );
blException.setGenericException(exception.getMessage( ));
throw blException;
}
}
};