package ma.dental_ops.dto;

import java.util.List;

/** A slice of a filtered result set with stable pagination metadata. */
public record PageResult<T>(List<T> items, int page, int pageSize, long totalItems) {
    public PageResult {
        items = List.copyOf(items);
        if (page < 0 || pageSize < 1 || totalItems < 0) {
            throw new IllegalArgumentException("Pagination values are invalid");
        }
    }

    public int totalPages() {
        return totalItems == 0 ? 0 : (int) ((totalItems + pageSize - 1) / pageSize);
    }
}
