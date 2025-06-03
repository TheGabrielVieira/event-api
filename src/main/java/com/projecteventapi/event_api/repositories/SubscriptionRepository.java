package com.projecteventapi.event_api.repositories;

import com.projecteventapi.event_api.dto.SubscriptionRankingItem;
import com.projecteventapi.event_api.entities.Event;
import com.projecteventapi.event_api.entities.Subscription;
import com.projecteventapi.event_api.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    Subscription findByEventAndSubscriber(Event event, User user);

    @Query(value = "SELECT user_name, count(subscription_number) AS quantidade, indication_user_id" +
            " FROM db_events.tbl_subscription" +
            " INNER JOIN db_events.tbl_user" +
            " ON db_events.tbl_subscription.indication_user_id = db_events.tbl_user.user_id" +
            " WHERE indication_user_id IS NOT NULL " +
            " AND event_id = :eventId" +
            " GROUP BY indication_user_id" +
            " ORDER BY quantidade DESC;", nativeQuery = true)
    List<SubscriptionRankingItem> generateRanking(@Param("eventId") Integer eventId);
}
