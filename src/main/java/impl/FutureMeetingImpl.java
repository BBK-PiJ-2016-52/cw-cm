package impl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import spec.Contact;
import spec.FutureMeeting;

/**
 * Created by Eric on 08/03/2017.
 * An implementation of the FutureMeeting interface.
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting , Serializable {
  /**
  * Constructor for the Future Meeeting.
  * @param id the id for the future meeting.
  * @param contacts a set of contacts for the future meeting.
  * @param date the date for the future meeting.
  */
  public FutureMeetingImpl(int id, Set<Contact> contacts, Calendar date) {
    super(id, contacts, date);
  }
}