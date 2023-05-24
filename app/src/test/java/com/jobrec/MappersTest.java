package com.jobrec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import org.assertj.core.api.Assertions;
import static org.mockito.Mockito.when;

import com.google.firebase.firestore.DocumentSnapshot;
import com.jobrec.domain.Degree;
import com.jobrec.domain.Job;
import com.jobrec.domain.Manager;
import com.jobrec.domain.UserType;
import com.jobrec.domain.WokringType;
import com.jobrec.domain.WorkingHours;
import com.jobrec.mappers.Mappers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MappersTest {
    DocumentSnapshot managerReference = mock(DocumentSnapshot.class);

    @Before
    public void setup() {
        managerReference = mock(DocumentSnapshot.class);
    }

    @After
    public void tearDown(){
        clearInvocations(managerReference);
    }

    @Test
    public void should_map_document_reference_manager_to_domain_manager() {
        when(managerReference.get("firstName", String.class)).thenReturn("Ana");
        when(managerReference.get("lastName", String.class)).thenReturn("Ionescu");
        when(managerReference.get("age", Integer.class)).thenReturn(30);
        when(managerReference.get("companyName", String.class)).thenReturn("IBM");
        when(managerReference.getId()).thenReturn("randomId");

        Manager expected = new Manager(
                "randomId",
                "Ana",
                "Ionescu",
                30,
                "IBM",
                new ArrayList<Job>()
        );
        Manager actual = Mappers.mapToManager(managerReference);

        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void mapToWorkingType_should_map_from_integer_to_domain_class(){
        assertEquals(WokringType.FULL_REMOTE,Mappers.mapToWorkingType(0));
        assertEquals(WokringType.HYBRID,Mappers.mapToWorkingType(1));
        assertEquals(WokringType.PHYSICAL,Mappers.mapToWorkingType(2));
        assertEquals(WokringType.UNKNOWN,Mappers.mapToWorkingType(3));
    }

    @Test
    public void mapToWorkingHours_should_map_from_integer_to_domain_class(){
        assertEquals(WorkingHours.FOUR_HOURS,Mappers.mapToWorkingHours(0));
        assertEquals(WorkingHours.SIX_HOURS,Mappers.mapToWorkingHours(1));
        assertEquals(WorkingHours.EIGHT_HOURS,Mappers.mapToWorkingHours(2));
        assertEquals(WorkingHours.UNKNOWN,Mappers.mapToWorkingHours(3));
        assertEquals(WorkingHours.UNKNOWN,Mappers.mapToWorkingHours(-10));
    }

    @Test
    public void mapToLastDegree_should_map_from_integer_to_domain_class(){
        assertEquals(Degree.PRIMARY_SCHOOL,Mappers.mapToLastDegree(0));
        assertEquals(Degree.HIGH_SCHOOL,Mappers.mapToLastDegree(1));
        assertEquals(Degree.LICENSE,Mappers.mapToLastDegree(2));
        assertEquals(Degree.MASTER,Mappers.mapToLastDegree(3));
        assertEquals(Degree.DOCTOR,Mappers.mapToLastDegree(4));
        assertEquals(Degree.UNKNOWN,Mappers.mapToLastDegree(5));
        assertEquals(Degree.UNKNOWN,Mappers.mapToLastDegree(-10));
    }

    @Test
    public void mapToUserType_should_map_from_integer_to_domain_class(){
        assertEquals(UserType.ADMIN,Mappers.mapToUserType(0));
        assertEquals(UserType.CANDIDATE,Mappers.mapToUserType(1));
        assertEquals(UserType.MANAGER,Mappers.mapToUserType(2));
        assertEquals(UserType.UNKNOWN,Mappers.mapToUserType(3));
        assertEquals(UserType.UNKNOWN,Mappers.mapToUserType(-10));
    }
}