package service;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.commonfarm.dao.Test;

public class TestService {
	private static Set people = new HashSet(5);
    private static long COUNT = 5;

    static {
        // create some imaginary persons
        Test p1 = new Test(new Long(1), "Patrick", "Lightbuddie");
        Test p2 = new Test(new Long(2), "Jason", "Carrora");
        Test p3 = new Test(new Long(3), "Alexandru", "Papesco");
        Test p4 = new Test(new Long(4), "Jay", "Boss");
        Test p5 = new Test(new Long(5), "Rainer", "Hermanos");
        people.add(p1);
        people.add(p2);
        people.add(p3);
        people.add(p4);
        people.add(p5);
    }

    public void createPerson(Test person) {
        person.setId(new Long(++COUNT));
        people.add(person);
    }

    public void updatePerson(Test person) {
        people.add(person);
    }

    public Set getPeople() {
        return people;
    }
    
    public Collection getObjects(Object obj) {
    	return null;
    }
    public int removeObject(Class clazz, Serializable id) {
    	return 1;
    }
}
