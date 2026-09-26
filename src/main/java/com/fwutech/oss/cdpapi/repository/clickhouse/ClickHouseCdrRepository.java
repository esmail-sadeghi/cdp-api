package com.fwutech.oss.cdpapi.repository.clickhouse;

import com.fwutech.oss.cdpapi.dto.CdrResponse;
import com.fwutech.oss.cdpapi.dto.PageResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ClickHouseCdrRepository implements CdrRepository {

    private final JdbcTemplate jdbcTemplate;

    public ClickHouseCdrRepository(
            @Qualifier("clickHouseJdbcTemplate")
            JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PageResponse<CdrResponse> findByFileId(
            String fileId,
            int page,
            int size) {

        int offset = page * size;

        String countSql = """
                SELECT count()
                FROM cdr
                WHERE file_id = ?
                """;

        Long totalElements = jdbcTemplate.queryForObject(
                countSql,
                Long.class,
                fileId
        );

        if (totalElements == null) {
            totalElements = 0L;
        }

        String sql = """
                SELECT
                    file_id,
                    record_id,
                    assignment_id,
                    customer_id,
                    calling_number,
                    called_number,
                    call_start_time,
                    call_end_time,
                    duration,
                    in_trunk,
                    out_trunk,
                    ingestion_time
                FROM cdr
                WHERE file_id = ?
                ORDER BY record_id
                LIMIT ? OFFSET ?
                """;

        List<CdrResponse> content = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapRow(rs),
                fileId,
                size,
                offset
        );

        return PageResponse.of(
                content,
                page,
                size,
                totalElements
        );
    }

    @Override
    public PageResponse<CdrResponse> findByAssignmentId(
            String assignmentId,
            int page,
            int size) {

        int offset = page * size;

        String countSql = """
                SELECT count()
                FROM cdr
                WHERE assignment_id = ?
                """;

        Long totalElements = jdbcTemplate.queryForObject(
                countSql,
                Long.class,
                assignmentId
        );

        if (totalElements == null) {
            totalElements = 0L;
        }

        String sql = """
                SELECT
                    file_id,
                    record_id,
                    assignment_id,
                    customer_id,
                    calling_number,
                    called_number,
                    call_start_time,
                    call_end_time,
                    duration,
                    in_trunk,
                    out_trunk,
                    ingestion_time
                FROM cdr
                WHERE assignment_id = ?
                ORDER BY call_start_time DESC, file_id, record_id
                LIMIT ? OFFSET ?
                """;

        List<CdrResponse> content = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapRow(rs),
                assignmentId,
                size,
                offset
        );

        return PageResponse.of(
                content,
                page,
                size,
                totalElements
        );
    }

    @Override
    public PageResponse<CdrResponse> findByCustomerId(
            String customerId,
            int page,
            int size) {

        int offset = page * size;

        String countSql = """
                SELECT count()
                FROM cdr
                WHERE customer_id = ?
                """;

        Long totalElements = jdbcTemplate.queryForObject(
                countSql,
                Long.class,
                customerId
        );

        if (totalElements == null) {
            totalElements = 0L;
        }

        String sql = """
                SELECT
                    file_id,
                    record_id,
                    assignment_id,
                    customer_id,
                    calling_number,
                    called_number,
                    call_start_time,
                    call_end_time,
                    duration,
                    in_trunk,
                    out_trunk,
                    ingestion_time
                FROM cdr
                WHERE customer_id = ?
                ORDER BY call_start_time DESC, file_id, record_id
                LIMIT ? OFFSET ?
                """;

        List<CdrResponse> content = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapRow(rs),
                customerId,
                size,
                offset
        );

        return PageResponse.of(
                content,
                page,
                size,
                totalElements
        );
    }

    private CdrResponse mapRow(
            ResultSet rs) throws SQLException {

        return new CdrResponse(
                rs.getString("file_id"),
                rs.getLong("record_id"),
                rs.getString("assignment_id"),
                rs.getString("customer_id"),
                rs.getString("calling_number"),
                rs.getString("called_number"),
                toLocalDateTime(rs, "call_start_time"),
                toLocalDateTime(rs, "call_end_time"),
                rs.getLong("duration"),
                rs.getString("in_trunk"),
                rs.getString("out_trunk"),
                toLocalDateTime(rs, "ingestion_time")
        );
    }

    private LocalDateTime toLocalDateTime(
            ResultSet rs,
            String column) throws SQLException {

        java.sql.Timestamp timestamp =
                rs.getTimestamp(column);

        return timestamp != null
                ? timestamp.toLocalDateTime()
                : null;
    }
}