package com.tmsnith.emotionsense.RequestModels;

import java.util.ArrayList;

/**
 * Created by tms on 1/4/17.
 */

public class DocumentModel {

  public ArrayList<SingleDocument> document;

    public DocumentModel() {
      document=new ArrayList<>();
    }

  public ArrayList<SingleDocument> getDocument() {
    return document;
  }

  public void setDocument(ArrayList<SingleDocument> document) {
    this.document = document;
  }
}

