package impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import spec.Contact;
import spec.PastMeeting;

/**
 * Created by Eric on 08/03/2017.
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting , Serializable {
  private String notes = "";

  public PastMeetingImpl(Integer id, Set<Contact> contacts, Calendar date) {
    super(id, contacts, date);
  }

  public PastMeetingImpl(Calendar date, Set<Contact> contacts) {
    super(date, contacts);
  }

  @Override
  public String getNotes() {
    return notes;
  }

  public void addNotes(String note) {
    notes += note;
  }
}