package com.thinking.machines.hr.dl.interfaces.dto;

public interface DesignationDTOInterface extends Comparable<DesignationDTOInterface> , java.io.Serializable
{
public void setTitle(String title);
public String getTitle( );
public void setCode(int code);
public int getCode( );
}