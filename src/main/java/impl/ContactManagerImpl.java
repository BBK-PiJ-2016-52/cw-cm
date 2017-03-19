package impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import spec.Contact;
import spec.ContactManager;
import spec.FutureMeeting;
import spec.Meeting;
import spec.PastMeeting;

/**
 * Created by Eric on 08/03/2017.
 */

/**
 * * @see ContactManager
 * <p>Class to manage contacts and meetings.</p>
 */
public class ContactManagerImpl implements Serializable, ContactManager {
  private Set<Contact> contacts = new HashSet<>();
  private List<FutureMeeting> futureMeeting = new ArrayList<>();
  private List<PastMeeting> pastMeetingList = new ArrayList<>();
  public Meeting pastMeeting;
  private static Calendar nowDate = Calendar.getInstance();
  private static int idCounter = 0;
  private int meetingId;
  private Calendar pastDate = Calendar.getInstance();
  private Calendar futureDate = Calendar.getInstance();

  /**
    * A constructor to add a new meeting to be held in the future.
    * @param contacts a set of contacts that will participate in the meeting
    * @param date     the date on which the meeting will take place
    * @return meetingImpl.getId().
    * @throws IllegalArgumentException if the meeting is set for a time
    *                                  in the past, of if any contact is unknown / non-existent.
    * @throws NullPointerException     if the meeting or the date are null
    */
  @Override
  public int addFutureMeeting(Set<Contact> contacts, Calendar date) throws IllegalArgumentException,
            NullPointerException {

    // loop to determine whether the contacts are found in the set.
    for (Iterator<Contact> it = contacts.iterator(); it.hasNext();) {
      if (!contacts.contains(it)) {
        throw new IllegalArgumentException("Contact not found/exist.");
      }
    }
    //When the contact exists/date is not null the date is compared to the one to be held in future.
    if (!contacts.isEmpty() || date != null) {
      if (date.after(nowDate)) {
        MeetingImpl meetingImpl = new FutureMeetingImpl(meetingId + 1, contacts, date);
        futureMeeting.add((FutureMeeting) meetingImpl);
        meetingId++;
        return meetingImpl.getId();
      } else {
        throw new IllegalArgumentException("Date can't be in the past.");
      }
    } else {
      throw new NullPointerException("Contacts/date is required cannot be null.");
    }
  }

  /**
   *
   * @param id the ID for the meeting.
   * @return the meeting with the requested ID.
   * @throws IllegalStateException when the meeting id is happening in the futureDate.
   * @throws StackOverflowError when the id is null(notice that id is now Integer).
   */
  @Override
  public PastMeeting getPastMeeting(int id) throws IllegalStateException, StackOverflowError {
    MeetingImpl meetingImpl = new PastMeetingImpl(meetingId + 1, contacts, futureDate);
    Meeting pastMeeting = getMeeting(id);
    if (pastMeeting == null) {
      return null;
    }
    Integer intObj = new Integer(id);
    if (id < 0) {
      intObj = null;
    }
    if (intObj == null) {
      throw new StackOverflowError("Id cannot be null.");
    }
    if (nowDate.after(futureDate)) {
      throw new IllegalStateException("The meeting cannot be in the future.");
    }
    return (PastMeeting) pastMeeting;
  }

  /**
   * Constructor that checks if the meeting have happened at a past time.
   * @param id the ID for the meeting.
   * @return the meeting with the requested ID or null when the meetingid is null.
   * @throws IllegalArgumentException when the meeting has an id in the past.
   */
  @Override
  public FutureMeeting getFutureMeeting(int id) throws IllegalArgumentException {
    Meeting futureMeeting = getMeeting(id);
    if (futureMeeting == null) {
      return null;
    } else {
      if (futureMeeting instanceof FutureMeeting) {
        if (futureMeeting.getDate().after(nowDate)) {
          return (FutureMeeting) futureMeeting;
        }
      } else {
        throw new IllegalArgumentException("Id is happening in the past.");
      }
      return null;
    }
  }

  /**
   * Returns the meeting with the requested id.
   * @param id the ID for the meeting.
   * @return getMeeting.
   * @throws IllegalStateException if meetign with an ID happening in the future.
   */
  @Override
  public Meeting getMeeting(int id) throws IllegalStateException {
    Meeting getMeeting;
    try {
      getMeeting = getPastMeeting(id);
      if (id > idCounter || id <= 0) {
        throw new IllegalArgumentException("ID out of range.");
      }
    } catch (IllegalStateException ex) {
      getMeeting = getFutureMeeting(id);
    }
    return getMeeting;
  }

  @Override
  public List<Meeting> getFutureMeetingList(Contact contact) throws IllegalArgumentException,
      NullPointerException {
    List<Meeting> futureMeetingList = futureMeeting.stream()
        .filter(m -> m.getDate().after(nowDate))
        .filter(n -> n.getContacts().contains(contact))
        .sorted(Comparator.comparing(Meeting::getDate))
        .collect(Collectors.toList());
    if (futureMeetingList.isEmpty()) {
      throw new IllegalArgumentException("Contact not found for future meetings.");
    }
    return futureMeetingList;
  }

  @Override
  public List<Meeting> getMeetingListOn(Calendar date) throws NullPointerException {
    pastDate.add(Calendar.YEAR, -1);
    if (date.equals(pastDate)) {
      return null;
    }
    return null;
  }

  @Override
  public List<PastMeeting> getPastMeetingListFor(Contact contact) throws
      IllegalArgumentException {
    if (contact == null) {
      throw new IllegalArgumentException("Contact cannot be null.");
    }
    return null;
  }

  @Override
  public int addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) throws
      IllegalArgumentException, NullPointerException {
    if (date == null || text == null || contacts == null) {
      throw new NullPointerException("Contacts/Date/Notes are null.");
    }
    for (Iterator<Contact> it = contacts.iterator(); it.hasNext();) {
      if (!contacts.contains(it)) {
        throw new IllegalArgumentException("Contact not found/exist.");
      }
    }

    PastMeeting newPastMeeting = new PastMeetingImpl(date, contacts);
    pastMeetingList.add(newPastMeeting);
    newPastMeeting = addMeetingNotes(newPastMeeting.getId(), text);
    return newPastMeeting.getId();
  }

  @Override
  public PastMeeting addMeetingNotes(int id, String text) throws IllegalArgumentException,
      IllegalStateException, NullPointerException {
    futureDate.add(Calendar.YEAR, 1);
    Meeting getMeeting;
    ContactImpl contactImpl;
    if (nowDate.equals(futureDate)) {
      throw new IllegalStateException("Meeting is set for a date in the future.");
    } else if (nowDate == null || text == null || contacts == null) {
      throw new NullPointerException("Contacts/Date/Notes are null.");
    }
      // TODO check whether the meeting exists.
      /*else if(contactImpl.getId().equals()){
          throw new IllegalArgumentException("Meeting doesn't exist.");
      }*/
    return null;
  }

  @Override
  public int addNewContact(String name, String notes) {
    Objects.requireNonNull(name, "Name is required cannot be null.");
    Objects.requireNonNull(notes, "Notes is required cannot be null.");

    if (name.equals("") || notes.equals("")) {
        throw new IllegalArgumentException("Passed an empty String parameter.");
    }
    Contact newContact = new ContactImpl(name, notes);
    contacts.add(newContact);
    return newContact.getId();
  }

  public Set<Contact> getContacts(String name) throws NullPointerException {
    Objects.requireNonNull(name, "Name is required cannot be null.");
    if (name.equals("")) {
      return contacts;
    } else {
      return contacts.parallelStream()
          .filter(i -> i.getName().contains(name))
          .sorted(Comparator.comparing(Contact::getId))
          .collect(Collectors.toSet());
    }
  }

  /**
   * Constructor that returns a set containing the contacts that correspond to the IDs.
   * Note that this method can be used to retrieve just one contact by passing only one ID.
   * @param ids an arbitrary number of contact IDs
   * @return a set containing the contacts that correspond to the IDs.
   * @throws IllegalArgumentException if no IDs are provided or if
   *                                  any of the provided IDs does not correspond to a real contact
   */
  @Override
  public Set<Contact> getContacts(int... ids) throws IllegalArgumentException {
    if (ids.length == 0) {
      throw new IllegalArgumentException("ids not provided.");
    }
    Set<Contact> resultSet = contacts.stream()
        .filter(p -> (Arrays.stream(ids).anyMatch(i -> i == p.getId())))// Filters ids-id
        .sorted(Comparator.comparing(Contact::getId))//Sort by Id
        .collect(Collectors.toSet());
    if (resultSet.size() != ids.length) {
      throw new IllegalArgumentException("IDs does not correspond to a real contact");
    } else {
      return resultSet;
    }
  }

  /**
   * Save all data to disk.
   * <p>
   * This method must be executed when the program is
   * closed and when/if the user requests it, but currently doesn't
   */
  @Override
  public void flush() {
    File file = new File("contacts.txt");
    try {
      ObjectOutputStream objStream = new ObjectOutputStream(
          new BufferedOutputStream(new FileOutputStream(file)));
      objStream.write(idCounter);
      objStream.write(meetingId);
      objStream.writeObject(contacts.toString());
      objStream.writeObject(futureMeeting.toString());
      objStream.writeObject(pastMeeting.toString());
      objStream.flush();
      objStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
  }
}