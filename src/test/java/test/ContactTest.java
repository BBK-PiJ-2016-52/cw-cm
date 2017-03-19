package test;

import impl.ContactImpl;
import org.junit.Before;
import org.junit.Test;
import spec.Contact;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ContactTest {

    @Before
    public void setUp() {

    }

    @Test
    public void testContactConstructorName() {
        try {
            new ContactImpl("name", "name");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testContactConstructorSetName() {
        Contact eric = new ContactImpl("eric", "notes");
        assertEquals("eric", eric.getName());
    }

    @Test
    public void testContactConstructorNameNotes() {
        try {
            new ContactImpl("name", "notes");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testContactConstructorSetNameNotes() {
        Contact eric = new ContactImpl("eric", "notes");

        assertEquals("eric", eric.getName());
        assertEquals("notes", eric.getNotes());
    }

    @Test
    public void testGetNotesNone() {
        Contact eric = new ContactImpl("eric", "");

        assertEquals("", eric.getNotes());
    }

    @Test
    public void testAddNotesSingle() {
        Contact eric = new ContactImpl("eric", "notes");

        eric.addNotes("notes1");

        assertEquals("notes"+"notes1", eric.getNotes());
    }

    @Test
    public void testAddNotesMultiple() {
        Contact eric = new ContactImpl("eric", "notes1");
        eric.addNotes("notes2");
        eric.addNotes("notes3");

        String returned = eric.getNotes();

        assertTrue(returned.contains("notes1"));
        assertTrue(returned.contains("notes2"));
        assertTrue(returned.contains("notes3"));
    }

    @Test
    public void testContactUniqueIds() {
        Contact jorge = new ContactImpl("jorge", "notes");
        Contact eric = new ContactImpl("eric", "notes");

        assertThat(jorge.getId(), is(not(equalTo(eric.getId()))));
    }
}