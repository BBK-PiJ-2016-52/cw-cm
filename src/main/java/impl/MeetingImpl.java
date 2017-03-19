package impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import spec.Contact;
import spec.Meeting;

/**
 * Created by Eric on 08/03/2017.
 *
 * An implementation of Meeting interface.
 */
public class MeetingImpl implements Meeting , Serializable {
  private int id;
  private final Calendar date;
  private static Set<Contact> contacts = new HashSet<>();

  private int meetingId;

  public MeetingImpl(Calendar date, Set<Contact> contacts) {
    this.date = date;
    MeetingImpl.contacts = contacts;
    int idCounter = 0;
    idCounter++;
    meetingId = idCounter;
  }

  public MeetingImpl(int id, Set<Contact> contacts, Calendar date) {
    this.contacts = contacts;
    this.date = date;
  }

  @Override
  public int getId() {
    return meetingId;
  }

  @Override
  public Calendar getDate() {
    // Returning the cloned date so the object cannot be modified as per final initial state declared
    return (Calendar) this.date.clone();
  }

  @Override
  public Set<Contact> getContacts() {
    return contacts;
  }
}