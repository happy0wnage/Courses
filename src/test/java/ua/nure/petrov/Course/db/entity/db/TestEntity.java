package ua.nure.petrov.Course.db.entity.db;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Владислав on 28.07.2015.
 */
public class TestEntity {

    Entity entity;

    @Before
    public void before() {
        entity = new Entity(1);
    }

    @Test
    public void setGetTest() {
        int id = 1;
        assertEquals(id, entity.getId());
    }
}
