package ru.itgroup.intouch.repository;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.itgroup.intouch.dto.BirthdayUsersDto;
import ru.itgroup.intouch.dto.NotificationDto;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<BirthdayUsersDto> getBirthdayUsers() {
        final String sql = "select f.user_id_from, f.user_id_to, u.first_name, u.last_name from accounts a " +
                "left join friends f on a.id = f.user_id_to left join users u on u.id = a.id " +
                "where extract(month from birth_date) = extract(month from current_date) " +
                "and extract(day from birth_date) = extract(day from current_date) " +
                "and f.id is not null";

        RowMapper<BirthdayUsersDto> rowMapper = (resultSet, rowNum) -> BirthdayUsersDto.builder()
                .authorId(resultSet.getLong("user_id_from"))
                .receiverId(resultSet.getLong("user_id_to"))
                .name(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .build();

        return jdbcTemplate.query(sql, rowMapper);
    }

    public void saveBirthdayNotifications(@NotNull List<NotificationDto> dtoList) {
        String sqlInsert = "insert into notifications (content, created_at, notification_type, author_id, receiver_id) values (?, ?, ?, ?, ?)";
        List<Object[]> batchArgs = dtoList.stream()
                .map(dto -> new Object[]{dto.getContent(), dto.getSentTime(), dto.getNotificationType(),
                        dto.getReceiverId(), dto.getAuthorId()})
                .toList();

        jdbcTemplate.batchUpdate(sqlInsert, batchArgs);
    }
}
