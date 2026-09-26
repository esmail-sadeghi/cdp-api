package com.fwutech.oss.cdpapi.repository.clickhouse;

import com.fwutech.oss.cdpapi.dto.FileResponse;
import com.fwutech.oss.cdpapi.dto.PageResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ClickHouseFileRepository implements FileRepository {

    private final JdbcTemplate jdbcTemplate;

    public ClickHouseFileRepository(
            @Qualifier("clickHouseJdbcTemplate")
            JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public PageResponse<FileResponse> findAll(int page, int size) {

        int offset = page * size;

        String countSql = """
                SELECT count()
                FROM cdr_file
                """;

        Long totalElements = jdbcTemplate.queryForObject(
                countSql,
                Long.class
        );

        if (totalElements == null) {
            totalElements = 0L;
        }

        String sql = """
                SELECT
                    file_id,
                    file_name,
                    file_source,
                    file_date,
                    file_load_date,
                    record_count,
                    ingestion_status
                FROM cdr_file
                ORDER BY file_date DESC
                LIMIT ? OFFSET ?
                """;

        List<FileResponse> content = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapRow(rs),
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
    public FileResponse findById(String fileId) {

        String sql = """
                SELECT
                    file_id,
                    file_name,
                    file_source,
                    file_date,
                    file_load_date,
                    record_count,
                    ingestion_status
                FROM cdr_file
                WHERE file_id = ?
                LIMIT 1
                """;

        List<FileResponse> result = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> mapRow(rs),
                fileId
        );

        return result.isEmpty()
                ? null
                : result.get(0);
    }

    private FileResponse mapRow(
            ResultSet rs) throws SQLException {

        return new FileResponse(
                rs.getString("file_id"),
                rs.getString("file_name"),
                rs.getString("file_source"),
                toLocalDateTime(rs, "file_date"),
                toLocalDateTime(rs, "file_load_date"),
                rs.getLong("record_count"),
                rs.getString("ingestion_status")
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