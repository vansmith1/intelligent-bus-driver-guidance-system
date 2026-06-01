import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Coverage map (Assignment 4 rubric, Bus integration testing):
 *   - Valid storage      : test 1
 *   - Invalid rejection  : tests 2, 3
 *   - Persisted updates  : tests 4, 5
 *   - Record counts      : tests 6, 7, 10
 *   - Edge cases         : tests 8, 9
 */
public class BusRepositoryTest {

    @TempDir
    Path tempDir;

    // helper: fresh repo wired to the same temp file (simulates a process restart)
    private BusRepository newRepo() {
        return new BusRepository(tempDir.resolve("buses.json").toString());
    }

    // 1. valid storage - a valid bus is persisted and round-trips through disk
    @Test
    void validBusIsStoredAndRetrievedFromDisk() {
        BusRepository writer = newRepo();
        Bus bus = new Bus("12345678", 40, 80.0, "Diesel");

        assertTrue(writer.add(bus));

        // reading via a NEW instance proves the write reached the JSON file
        Bus retrieved = newRepo().retrieve("12345678");

        assertNotNull(retrieved);
        assertEquals("12345678", retrieved.getBusId());
        assertEquals(40, retrieved.getCapacity());
        assertEquals(80.0, retrieved.getFuelLevel());
        assertEquals("Diesel", retrieved.getFuelType());
    }

    // 2. invalid rejection - busID that fails B1 (not 8 digits) is refused
    @Test
    void busWithInvalidIDFormatIsRejected() {
        BusRepository repo = newRepo();
        Bus invalid = new Bus("1234567", 40, 80.0, "Diesel"); // 7 digits

        assertFalse(repo.add(invalid));
        assertEquals(0, repo.count());
        assertNull(repo.retrieve("1234567"));
    }

    // 3. invalid rejection - duplicate busID is refused, original record untouched
    @Test
    void duplicateBusIDIsRejected() {
        BusRepository repo = newRepo();
        Bus original = new Bus("12345678", 40, 80.0, "Diesel");
        Bus duplicate = new Bus("12345678", 50, 70.0, "Hybrid");

        assertTrue(repo.add(original));
        assertFalse(repo.add(duplicate));

        assertEquals(1, repo.count());

        // verify the original record was preserved, not overwritten
        Bus stored = repo.retrieve("12345678");
        assertEquals(40, stored.getCapacity());
        assertEquals("Diesel", stored.getFuelType());
    }

    // 4. persisted update - capacity decrease survives a fresh repo instance
    @Test
    void capacityDecreaseUpdateIsPersistedAcrossInstances() {
        BusRepository writer = newRepo();
        writer.add(new Bus("12345678", 50, 80.0, "Diesel"));

        Bus updated = new Bus("12345678", 40, 80.0, "Diesel");
        assertTrue(writer.update(updated));

        // fresh instance reads from disk -> confirms persistence
        Bus reloaded = newRepo().retrieve("12345678");
        assertEquals(40, reloaded.getCapacity());
    }

    // 5. persisted update - B2 capacity increase rejected, original preserved on disk
    @Test
    void capacityIncreaseUpdateIsRejectedAndOriginalPreserved() {
        BusRepository repo = newRepo();
        repo.add(new Bus("12345678", 40, 80.0, "Diesel"));

        Bus illegalUpdate = new Bus("12345678", 50, 80.0, "Diesel");
        assertFalse(repo.update(illegalUpdate));

        Bus reloaded = newRepo().retrieve("12345678");
        assertEquals(40, reloaded.getCapacity());
    }

    // 6. record counts - count reflects each successful add
    @Test
    void countReflectsAddedBuses() {
        BusRepository repo = newRepo();
        assertEquals(0, repo.count());

        repo.add(new Bus("12345678", 40, 80.0, "Diesel"));
        repo.add(new Bus("23456789", 50, 70.0, "Hybrid"));
        repo.add(new Bus("34567890", 45, 65.0, "Electricity"));

        assertEquals(3, repo.count());
    }

    // 7. record counts - rejected adds (duplicate, invalid) leave count unchanged
    @Test
    void rejectedAddsDoNotIncrementCount() {
        BusRepository repo = newRepo();
        repo.add(new Bus("12345678", 40, 80.0, "Diesel"));

        // duplicate busID
        assertFalse(repo.add(new Bus("12345678", 50, 70.0, "Hybrid")));
        // invalid format (letters in busID)
        assertFalse(repo.add(new Bus("ABCDEFGH", 30, 50.0, "Diesel")));

        assertEquals(1, repo.count());
    }

    // 8. edge case - retrieve on an unknown busID safely returns null
    @Test
    void retrieveUnknownBusIDReturnsNull() {
        BusRepository repo = newRepo();
        repo.add(new Bus("12345678", 40, 80.0, "Diesel"));

        assertNull(repo.retrieve("99999999"));
    }

    // 9. edge case - update on an unknown busID is rejected without side effects
    @Test
    void updateUnknownBusIDIsRejected() {
        BusRepository repo = newRepo();
        repo.add(new Bus("12345678", 40, 80.0, "Diesel"));

        Bus orphan = new Bus("99999999", 30, 50.0, "Diesel");
        assertFalse(repo.update(orphan));
        assertEquals(1, repo.count());
    }

    // 10. record counts - delete removes the record and decrements count
    @Test
    void deleteRemovesBusAndUpdatesCount() {
        BusRepository repo = newRepo();
        repo.add(new Bus("12345678", 40, 80.0, "Diesel"));
        repo.add(new Bus("23456789", 50, 70.0, "Hybrid"));

        assertTrue(repo.delete("12345678"));
        assertEquals(1, repo.count());

        // confirm deletion is persisted to disk
        BusRepository fresh = newRepo();
        assertNull(fresh.retrieve("12345678"));
        assertNotNull(fresh.retrieve("23456789"));
    }
}
