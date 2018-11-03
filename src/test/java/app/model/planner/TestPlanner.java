package app.model.planner;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;

public class TestPlanner
{
	@Test
	public void testPlanNext()
	{
		assertAll(
				() -> {
					OffsetDateTime d1 = OffsetDateTime.now();
					PlannerResult result = Planner.planNext(ReviewValues.VALUE_1, d1, null);
					
					assertEquals(1, result.getDaysNext());
					assertEquals(d1.plus(1, ChronoUnit.DAYS), result.getNextDate());
				});
		
		assertAll(
				() -> {
					OffsetDateTime d1 = OffsetDateTime.now();
					PlannerResult result = Planner.planNext(ReviewValues.VALUE_4, d1, null);
					
					assertEquals(12, result.getDaysNext());
					assertEquals(d1.plus(12, ChronoUnit.DAYS), result.getNextDate());
				});
		
		assertAll(
				() -> {
					OffsetDateTime d1 = OffsetDateTime.now();
					PlannerResult result = Planner.planNext(ReviewValues.VALUE_2, d1, d1.minus(122, ChronoUnit.DAYS));
					
					assertEquals(137, result.getDaysNext());
					assertEquals(d1.plus(137, ChronoUnit.DAYS), result.getNextDate());
				});
		
		assertAll(
				() -> {
					OffsetDateTime d1 = OffsetDateTime.now();
					PlannerResult result = Planner.planNext(ReviewValues.VALUE_0, d1, null);
					
					assertEquals(0, result.getDaysNext());
					assertEquals(d1, result.getNextDate());
				});
	}

	@Test
	public void testPlanNextValueException()
	{
		assertThrows(
				IllegalArgumentException.class,
				() -> {
					OffsetDateTime d = OffsetDateTime.now();
					Planner.planNext(null, d, d);
				});
	}
	
	@Test
	public void testPlanNextDateException()
	{
		assertThrows(
				IllegalArgumentException.class,
				() -> {
					Planner.planNext(ReviewValues.VALUE_1, null, null);
				});
		
		assertThrows(
				IllegalArgumentException.class,
				() -> {
					OffsetDateTime d1 = OffsetDateTime.now();
					OffsetDateTime d2 = d1.minus(-Planner.PASSED_DAYS_MIN - 1, ChronoUnit.DAYS);
					Planner.planNext(ReviewValues.VALUE_1, d1, d2);
				});

		assertThrows(
				IllegalArgumentException.class,
				() -> {
					OffsetDateTime d1 = OffsetDateTime.now();
					OffsetDateTime d2 = d1.minus(Planner.PASSED_DAYS_MAX + 1, ChronoUnit.DAYS);
					Planner.planNext(ReviewValues.VALUE_1, d1, d2);
				});
	}
}
