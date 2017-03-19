package test;

import impl.PastMeetingImpl;
import org.junit.Before;
import org.junit.Test;
import spec.Contact;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PastMeetingTest {

    Calendar pastDate;
    Set<Contact> contacts;

    @Before
    public void setUp() {
        pastDate = Calendar.getInstance();
        pastDate.add(Calendar.YEAR, -1);
        contacts = new HashSet<>();
    }

    @Test
    public void testGetNotesNone() {
        PastMeetingImpl pastMeeting = new PastMeetingImpl(1,contacts,pastDate);

        assertEquals("", pastMeeting.getNotes());
    }


    @Test
    public void testAddNotesSingle() {
        PastMeetingImpl pastMeeting = new PastMeetingImpl(1,contacts,pastDate);

        pastMeeting.addNotes("Notes 1");

        assertTrue(pastMeeting.getNotes().contains("Notes 1"));
    }

    @Test
    public void testAddNotesMultiple() {
        PastMeetingImpl pastMeeting = new PastMeetingImpl(1,contacts,pastDate);

        pastMeeting.addNotes("Notes 1");
        pastMeeting.addNotes("Notes 2");

        assertTrue(pastMeeting.getNotes().contains("Notes 1"));
        assertTrue(pastMeeting.getNotes().contains("Notes 2"));
    }
}