package com.example.myapplication.DataModels;

public class DocumentDataModel {
  private String documentName;
  private int status;

  public DocumentDataModel(String documentName, int status) {
    this.documentName = documentName;
    this.status = status;
  }

  public String getDocumentName() {
    return documentName;
  }

  public void setDocumentName(String documentName) {
    this.documentName = documentName;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }
}
