package dmit2015.cfourie1.project.resource;

import dmit2015.cfourie1.project.model.Birthday;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BirthdayResourceTest {

    BirthdayResourceJaxRsClient currentBirthdayResourceJaxRsClient = new BirthdayResourceJaxRsClient();
    String testDataResourceLocation;

    @Test
    @Order(2)
    void createBirthday() {
        Birthday newBirthday = new Birthday();
        newBirthday.setPersonName("Created a JUNIT Test Case method for POST");
        newBirthday.setBirthdayDate(LocalDate.parse("2021-12-12"));
        newBirthday.setBought(true);
        testDataResourceLocation = currentBirthdayResourceJaxRsClient.create(newBirthday);
        assertFalse(testDataResourceLocation.equals(""));

        Optional<Birthday> optionalBirthday = currentBirthdayResourceJaxRsClient.getOneByLocation(testDataResourceLocation);
        assertTrue(optionalBirthday.isPresent());
        Birthday existingBirthday = optionalBirthday.get();
        assertEquals(newBirthday.getPersonName(), existingBirthday.getPersonName());
        assertEquals(newBirthday.getBirthdayDate(), existingBirthday.getBirthdayDate());
        assertEquals(newBirthday.isBought(), existingBirthday.isBought());
    }

    @Test
    @Order(1)
    void readOneBirthday() {
        Long queryValidId = 2L;
        Optional<Birthday> optionalBirthday = currentBirthdayResourceJaxRsClient.getOneById(queryValidId);
        assertTrue(optionalBirthday.isPresent());
        Birthday existingBirthday = optionalBirthday.get();
        assertEquals(queryValidId, existingBirthday.getId());
        assertEquals("Chris", existingBirthday.getPersonName());
        assertEquals("2021-10-12", existingBirthday.getBirthdayDate().toString());
        assertEquals(null, existingBirthday.getGift());
        assertEquals(null, existingBirthday.getWhereToGet());
        assertEquals(null, existingBirthday.getPrice());
        assertEquals(false, existingBirthday.isBought());

        Long queryInvalidId = 9999L;
        optionalBirthday = currentBirthdayResourceJaxRsClient.getOneById(queryInvalidId);
        assertTrue(optionalBirthday.isEmpty());
    }

    @Test
    @Order(4)
    void readAll() {
        List<Birthday> birthdayList = currentBirthdayResourceJaxRsClient.getAll();
        assertEquals(4, birthdayList.size());
        //birthdayList.forEach(System.out::println);
    }

    @Test
    @Order(3)
    void updateBirthday() {
        Optional<Birthday> optionalBirthday = currentBirthdayResourceJaxRsClient.getOneByLocation(testDataResourceLocation);
        assertTrue(optionalBirthday.isPresent());
        Birthday existingBirthday = optionalBirthday.get();
        existingBirthday.setPersonName("Changed Birthday using PUT request");
        existingBirthday.setBirthdayDate(LocalDate.parse("2021-12-20"));
        existingBirthday.setBought(false);
        boolean success = currentBirthdayResourceJaxRsClient.update(existingBirthday.getId(), existingBirthday);
        assertTrue(success);

        optionalBirthday = currentBirthdayResourceJaxRsClient.getOneById(existingBirthday.getId());
        assertTrue(optionalBirthday.isPresent());
        Birthday updatedBirthday = optionalBirthday.get();
        assertEquals(existingBirthday.getId(), updatedBirthday.getId());
        assertEquals(existingBirthday.getPersonName(), updatedBirthday.getPersonName());
        assertEquals(existingBirthday.isBought(), updatedBirthday.isBought());
    }

    @Test
    @Order(5)
    void deleteBirthday() {
        Optional<Birthday> optionalBirthday = currentBirthdayResourceJaxRsClient.getOneByLocation(testDataResourceLocation);
        assertTrue(optionalBirthday.isPresent());
        Birthday existingBirthday = optionalBirthday.get();
        boolean success = currentBirthdayResourceJaxRsClient.delete(existingBirthday.getId());
        assertTrue(success);

        success = currentBirthdayResourceJaxRsClient.delete(existingBirthday.getId());
        assertFalse(success);
    }
}