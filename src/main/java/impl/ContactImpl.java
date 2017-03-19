package impl;

import java.io.Serializable;
import spec.Contact;

/**
 * Created by Eric on 08/03/2017.
 */

public class ContactImpl implements Contact, Serializable {
  private String name;
  private int id;
  private String notes = "";
  private int idCounter = 0;
  private int contactId;

  /**
  * Empty constructor for ContactImpl with default parameters.
  */
  public ContactImpl(){
  }

  /**
  * Constructor for ContactImpl.
  * @param name
  */
  public ContactImpl(String name) {
  }
  /**
  * Constructor for ContactImpl.
  * @param name
  * @param notes
  */
  public ContactImpl(String name, String notes) {
    this.notes = notes;
    this.name = name;
    idCounter++;
    contactId = idCounter;
  }

  /**
  * Constructor for ContactImpl with 3 parameters.
  * @param id
  * @param name
  * @param notes
  */
  public ContactImpl(int id, String name, String notes) {
    this.notes = notes;
    this.name = name;
    idCounter++;
    contactId += idCounter;
    this.id = contactId;
  }

  /**
  * Get the ID of the contact.
  * @return the ID of the contact.
  */
  @Override
  public final int getId() {
    System.out.println(contactId);
    return contactId;
  }

  /**
  * Get the name of the contact.
  * @return the name of the contact.
  */
  @Override
  public String getName() {
    return this.name;
  }

  /**
  * Get the notes of a contact.
  * @return a String with notes, could be empty.
  */
  @Override
  public String getNotes() {
    return notes;
  }

  /**
  * Add notes about the contact.
  * @param note the notes to be added
  */
  @Override
  public void addNotes(String note) {
    if (this.notes.isEmpty()) {
      this.notes = note;
    } else {
      this.notes += note;
    }
  }
}